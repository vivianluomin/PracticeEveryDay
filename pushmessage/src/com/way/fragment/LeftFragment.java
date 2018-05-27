package com.way.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.way.activity.ChatActivity;
import com.way.activity.MainActivity;
import com.way.app.PushApplication;
import com.way.bean.User;
import com.way.common.util.L;
import com.way.common.util.SharePreferenceUtil;
import com.way.common.util.T;
import com.way.db.MessageDB;
import com.way.db.RecentDB;
import com.way.db.UserDB;
import com.way.pullrefresh.PullToRefreshBase;
import com.way.pullrefresh.SoundPullEventListener;
import com.way.pullrefresh.PullToRefreshBase.OnRefreshListener;
import com.way.pullrefresh.PullToRefreshBase.State;
import com.way.pullrefresh.PullToRefreshHorizontalScrollView;
import com.way.push.R;
import com.way.quick_action_bar.QuickAction;
import com.way.quick_action_bar.QuickActionBar;
import com.way.quick_action_bar.QuickActionWidget;
import com.way.quick_action_bar.QuickActionWidget.OnQuickActionClickListener;
import com.way.xlistview.IphoneTreeView;
import com.way.xlistview.IphoneTreeView.IphoneTreeHeaderAdapter;

public class LeftFragment extends Fragment {
	private static final String[] groups = { "未分组好友", "我的好友", "我的同学", "我的家人",
			"我的同事" };
	private QuickActionWidget mBar;
	private PushApplication mApplication;
	private UserDB mUserDB;
	private RecentDB mRecentDB;
	private MessageDB mMsgDB;
	private SharePreferenceUtil mSpUtil;
	private IphoneTreeView xListView;
	private LayoutInflater mInflater;
	private List<String> mGroup;// 组名
	private Map<Integer, List<User>> mChildren;// 每一组对应的child
	private MyExpandableListAdapter mAdapter;
	private int mLongPressGroupId, mLongPressChildId;
	private PullToRefreshHorizontalScrollView mPullRefreshScrollView;
	private SoundPullEventListener mSoundListener;

	public void updateAdapter() {
		if (xListView != null) {
			L.i("update friend...");
			initUserData();
		}
	}

	public void onPullRefreshListener(boolean isChecked) {
		if (isChecked && mSoundListener != null)
			mPullRefreshScrollView.setOnPullEventListener(mSoundListener);
		else
			mPullRefreshScrollView.setOnPullEventListener(null);
	}

	private void showChildQuickActionBar(View view) {
		mBar = new QuickActionBar(getActivity());
		mBar.addQuickAction(new QuickAction(getActivity(),
				R.drawable.ic_action_share_pressed, R.string.open));
		mBar.addQuickAction(new QuickAction(getActivity(),
				R.drawable.ic_action_rename_pressed, R.string.rename));
		mBar.addQuickAction(new QuickAction(getActivity(),
				R.drawable.ic_action_move_pressed, R.string.move));
		mBar.addQuickAction(new QuickAction(getActivity(),
				R.drawable.ic_action_delete_pressed, R.string.delete));
		mBar.setOnQuickActionClickListener(mActionListener);
		mBar.show(view);
	}

	private void showGroupQuickActionBar(View view) {
		mBar = new QuickActionBar(getActivity());
		mBar.addQuickAction(new QuickAction(getActivity(),
				R.drawable.ic_action_rename_pressed, R.string.rename));
		mBar.addQuickAction(new QuickAction(getActivity(),
				R.drawable.ic_action_delete_pressed, R.string.delete));
		mBar.setOnQuickActionClickListener(new OnQuickActionClickListener() {

			@Override
			public void onQuickActionClicked(QuickActionWidget widget,
					int position) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					T.showShort(getActivity(), "rename group "
							+ mLongPressGroupId);
					break;
				case 1:
					T.showShort(getActivity(), "delete group "
							+ mLongPressGroupId);
					break;
				default:
					break;
				}
			}
		});
		mBar.show(view);
	}

	private OnQuickActionClickListener mActionListener = new OnQuickActionClickListener() {
		public void onQuickActionClicked(QuickActionWidget widget, int position) {
			User u = mChildren.get(mLongPressGroupId).get(mLongPressChildId);
			if (u != null)
				switch (position) {
				case 0:
					mMsgDB.clearNewCount(u.getUserId());// 新消息置空
					Intent toChatIntent = new Intent(getActivity(),
							ChatActivity.class);
					toChatIntent.putExtra("user", u);
					startActivity(toChatIntent);
					// T.showShort(mApplication, "open");
					break;
				case 1:
					T.showShort(mApplication, "rename");
					break;
				case 2:
					T.showShort(mApplication, "move");
					break;
				case 3:
					mUserDB.delUser(u);
					updateAdapter();
					mRecentDB.delRecent(u.getUserId());
					((MainActivity) getActivity()).upDateList();
					T.showShort(mApplication, "删除成功！");
					break;
				default:
					break;
				}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_left_fragment, container,
				false);
		initView(view);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication = PushApplication.getInstance();
		mUserDB = mApplication.getUserDB();
		mMsgDB = mApplication.getMessageDB();
		mRecentDB = mApplication.getRecentDB();
		mSpUtil = mApplication.getSpUtil();
		mSoundListener = new SoundPullEventListener(getActivity());
		mSoundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
		mSoundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
		mSoundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
	}

	@Override
	public void onResume() {
		super.onResume();
		initUserData();
	}

	private void initView(View view) {
		mInflater = LayoutInflater.from(getActivity());
		// prepareQuickActionBar();
		// title
		view.findViewById(R.id.ivTitleBtnLeft).setVisibility(View.GONE);
		view.findViewById(R.id.ivTitleBtnRigh).setVisibility(View.GONE);
		TextView title = (TextView) view.findViewById(R.id.ivTitleName);
		title.setText(R.string.left_title_name);

		xListView = (IphoneTreeView) view.findViewById(R.id.friend_xlistview);

		xListView.setGroupIndicator(null);
		xListView.setHeaderView(mInflater.inflate(
				R.layout.contact_buddy_list_group, xListView, false));
		// xListView.setPullLoadEnable(false);// 禁止下拉加载更多
		// xListView.setXListViewListener(new IXListViewListener() {
		//
		// @Override
		// public void onRefresh() {
		// // TODO Auto-generated method stub
		// // handler.postDelayed(add, 2000);
		// initUserData();
		// xListView.setAdapter(mAdapter);
		// xListView.stopRefresh();
		// xListView.setRefreshTime(System.currentTimeMillis());
		// }
		//
		// });
		xListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				User u = (User) mAdapter.getChild(groupPosition, childPosition);
				mMsgDB.clearNewCount(u.getUserId());// 新消息置空
				Intent toChatIntent = new Intent(getActivity(),
						ChatActivity.class);
				toChatIntent.putExtra("user", u);
				startActivity(toChatIntent);
				return false;
			}
		});
		xListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				int groupPos = (Integer) view.getTag(R.id.xxx01); // 参数值是在setTag时使用的对应资源id号
				int childPos = (Integer) view.getTag(R.id.xxx02);
				mLongPressGroupId = groupPos;
				mLongPressChildId = childPos;
				if (childPos == -1) {// 长按的是父项
					// 根据groupPos判断你长按的是哪个父项，做相应处理（弹框等）
					showGroupQuickActionBar(view
							.findViewById(R.id.group_indicator));
					T.showShort(getActivity(), "LongPress group position = "
							+ groupPos);
				} else {
					// 根据groupPos及childPos判断你长按的是哪个父项下的哪个子项，然后做相应处理。
					// T.showShort(getActivity(), "onClick child position = "
					// + groupPos + ":" + childPos);
					showChildQuickActionBar(view.findViewById(R.id.icon));

				}

				return false;
			}
		});
		xListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if (mBar != null && mBar.isShowing()) {
					mBar.clearAllQuickActions();
					mBar.dismiss();
				}
			}
		});
		mPullRefreshScrollView = (PullToRefreshHorizontalScrollView) view
				.findViewById(R.id.pull_refresh_horizontalscrollview);
		mPullRefreshScrollView
				.setOnRefreshListener(new OnRefreshListener<HorizontalScrollView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<HorizontalScrollView> refreshView) {
						new GetDataTask().execute();
					}
				});
		if (mSpUtil.getPullRefreshSound() && mSoundListener != null)
			mPullRefreshScrollView.setOnPullEventListener(mSoundListener);

	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			// Do some stuff here

			// Call onRefreshComplete when the list has been refreshed.
			initUserData();
			xListView.setAdapter(mAdapter);
			mPullRefreshScrollView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

	/**
	 * 初始化好友数组
	 */
	private void initUserData() {
		// TODO Auto-generated method stub
		// 实例化组名
		mGroup = new ArrayList<String>();
		mChildren = new HashMap<Integer, List<User>>();// 给每一组实例化child
		List<User> dbUsers = mUserDB.getUser();// 查询本地数据库所有好友
		// 初始化组名和child
		for (int i = 0; i < groups.length; ++i) {
			mGroup.add(groups[i]);// 组名
			List<User> childUsers = new ArrayList<User>();// 每一组的child
			mChildren.put(i, childUsers);
		}
		// 给每一组添加数据
		for (User u : dbUsers) {
			for (int i = 0; i < mGroup.size(); ++i) {
				if (u.getGroup() == i) {
					mChildren.get(i).add(u);
				}
			}
		}
		mAdapter = new MyExpandableListAdapter(mGroup, mChildren);
		xListView.setAdapter(mAdapter);
	}

	public class MyExpandableListAdapter extends BaseExpandableListAdapter
			implements IphoneTreeHeaderAdapter {
		private List<String> mGroup;// 组名
		private Map<Integer, List<User>> mChildren;// 每一组对应的child

		public MyExpandableListAdapter(List<String> group,
				Map<Integer, List<User>> children) {
			// TODO Auto-generated constructor stub
			mGroup = group;
			mChildren = children;
		}

		public void addUser(User u) {
			int groupId = u.getGroup();
			if (groupId < mGroup.size()) {
				mChildren.get(groupId).add(u);
				notifyDataSetChanged();// 更新一下
			}
		}

		public Object getChild(int groupPosition, int childPosition) {
			return mChildren.get(groupPosition).get(childPosition);
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public int getChildrenCount(int groupPosition) {
			return mChildren.get(groupPosition).size();
		}

		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.contact_list_item_for_buddy, null);
			}
			TextView nick = (TextView) convertView
					.findViewById(R.id.contact_list_item_name);
			final User u = (User) getChild(groupPosition, childPosition);
			nick.setText(u.getNick());
			TextView state = (TextView) convertView
					.findViewById(R.id.cpntact_list_item_state);
			state.setText(u.getUserId());
			ImageView head = (ImageView) convertView.findViewById(R.id.icon);
			head.setImageResource(PushApplication.heads[u.getHeadIcon()]);
			// 必须使用资源Id当key（不是资源id会出现运行时异常），android本意应该是想用tag来保存资源id对应组件。
			// 将groupPosition，childPosition通过setTag保存,在onItemLongClick方法中就可以通过view参数直接拿到了
			// 我这里的xxx01和xxx02是两个长宽都为0的空view，只是用来setTag
			convertView.setTag(R.id.xxx01, groupPosition);
			convertView.setTag(R.id.xxx02, childPosition);

			return convertView;
		}

		public Object getGroup(int groupPosition) {
			return mGroup.get(groupPosition);
		}

		public int getGroupCount() {
			return mGroup.size();
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		/**
		 * create group view and bind data to view
		 */
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.contact_buddy_list_group, null);
			}
			TextView groupName = (TextView) convertView
					.findViewById(R.id.group_name);
			groupName.setText(getGroup(groupPosition).toString());
			TextView onlineNum = (TextView) convertView
					.findViewById(R.id.online_count);
			onlineNum.setText(getChildrenCount(groupPosition) + "/"
					+ getChildrenCount(groupPosition));
			ImageView indicator = (ImageView) convertView
					.findViewById(R.id.group_indicator);
			if (isExpanded)
				indicator.setImageResource(R.drawable.indicator_expanded);
			else
				indicator.setImageResource(R.drawable.indicator_unexpanded);
			// 必须使用资源Id当key（不是资源id会出现运行时异常），android本意应该是想用tag来保存资源id对应组件。
			// 将groupPosition，childPosition通过setTag保存,在onItemLongClick方法中就可以通过view参数直接拿到了
			convertView.setTag(R.id.xxx01, groupPosition);
			convertView.setTag(R.id.xxx02, -1);
			return convertView;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		public boolean hasStableIds() {
			return true;
		}

		@Override
		public int getTreeHeaderState(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			final int childCount = getChildrenCount(groupPosition);
			if (childPosition == childCount - 1) {
				return PINNED_HEADER_PUSHED_UP;
			} else if (childPosition == -1
					&& !xListView.isGroupExpanded(groupPosition)) {
				return PINNED_HEADER_GONE;
			} else {
				return PINNED_HEADER_VISIBLE;
			}
		}

		@Override
		public void configureTreeHeader(View header, int groupPosition,
				int childPosition, int alpha) {
			// TODO Auto-generated method stub
			((TextView) header.findViewById(R.id.group_name))
					.setText(groups[groupPosition]);
			((TextView) header.findViewById(R.id.online_count))
					.setText(getChildrenCount(groupPosition) + "/"
							+ getChildrenCount(groupPosition));
		}

		private HashMap<Integer, Integer> groupStatusMap = new HashMap<Integer, Integer>();

		@Override
		public void onHeadViewClick(int groupPosition, int status) {
			// TODO Auto-generated method stub
			groupStatusMap.put(groupPosition, status);
		}

		@Override
		public int getHeadViewClickStatus(int groupPosition) {
			if (groupStatusMap.containsKey(groupPosition)) {
				return groupStatusMap.get(groupPosition);
			} else {
				return 0;
			}
		}

	}

}
