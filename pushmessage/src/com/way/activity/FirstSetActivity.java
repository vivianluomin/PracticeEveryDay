package com.way.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.google.gson.Gson;
import com.way.adapter.HeadAdapter;
import com.way.adapter.SexAdapter;
import com.way.app.PushApplication;
import com.way.baidupush.client.PushMessageReceiver;
import com.way.bean.User;
import com.way.common.util.DialogUtil;
import com.way.common.util.NetUtil;
import com.way.common.util.SendMsgAsyncTask;
import com.way.common.util.SendMsgAsyncTask.OnSendScuessListener;
import com.way.common.util.SharePreferenceUtil;
import com.way.common.util.T;
import com.way.db.UserDB;
import com.way.push.R;
import com.way.wheel.WheelView;

public class FirstSetActivity extends Activity implements OnClickListener,
		PushMessageReceiver.EventHandler {
	private static final int LOGIN_OUT_TIME = 0;
	private Button mFirstStartBtn;
	private WheelView mHeadWheel;
	private WheelView mSexWheel;
	private EditText mNickEt;
	private PushApplication mApplication;
	private SharePreferenceUtil mSpUtil;
	private Gson mGson;
	private UserDB mUserDB;
	private View mNetErrorView;
	private TextView mTitle;
	private LoginOutTimeProcess mLoginOutTimeProcess;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case LOGIN_OUT_TIME:
				if (mLoginOutTimeProcess != null
						&& mLoginOutTimeProcess.running)
					mLoginOutTimeProcess.stop();
				if (mConnectServerDialog != null
						&& mConnectServerDialog.isShowing())
					mConnectServerDialog.dismiss();
				if (task != null)
					task.stop();
				T.showShort(FirstSetActivity.this, "登录超时，请重试");
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_set_layout);
		initData();
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!NetUtil.isNetConnected(this))
			mNetErrorView.setVisibility(View.VISIBLE);
		else
			mNetErrorView.setVisibility(View.GONE);
	}

	private void initData() {
		// TODO Auto-generated method stub
		mApplication = PushApplication.getInstance();
		mLoginOutTimeProcess = new LoginOutTimeProcess();
		mSpUtil = mApplication.getSpUtil();
		mGson = mApplication.getGson();
		mUserDB = mApplication.getUserDB();
		PushMessageReceiver.ehList.add(this);// 监听推送的消息
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (task != null)
			task.stop();
		PushMessageReceiver.ehList.remove(this);// 注销推送的消息
	}

	private void initView() {
		mNetErrorView = findViewById(R.id.net_status_bar_top);
		mTitle = (TextView) findViewById(R.id.ivTitleName);
		mTitle.setVisibility(View.VISIBLE);
		mTitle.setText(R.string.first_start_title);
		mNetErrorView.setOnClickListener(this);
		mFirstStartBtn = (Button) findViewById(R.id.first_start_btn);
		mFirstStartBtn.setOnClickListener(this);
		mNickEt = (EditText) findViewById(R.id.nick_ed);
		mNickEt.setText(mSpUtil.getNick());
		mHeadWheel = (WheelView) findViewById(R.id.acount_head);
		mSexWheel = (WheelView) findViewById(R.id.acount_sex);
		mHeadWheel.setViewAdapter(new HeadAdapter(this));
		mSexWheel.setViewAdapter(new SexAdapter(this));
	}

	private Dialog mConnectServerDialog;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.first_start_btn:
			if (!NetUtil.isNetConnected(this)) {
				T.showLong(this, R.string.net_error_tip);
				return;
			}
			String nick = mNickEt.getText().toString();
			if (TextUtils.isEmpty(nick)) {
				T.showShort(getApplicationContext(), R.string.first_start_tips);
				return;
			}
			mSpUtil.setNick(nick);
			mSpUtil.setHeadIcon(mHeadWheel.getCurrentItem());
			mSpUtil.setTag(SexAdapter.SEXS[mSexWheel.getCurrentItem()]);
			PushManager.startWork(getApplicationContext(),
					PushConstants.LOGIN_TYPE_API_KEY, PushApplication.API_KEY);// 无baidu帐号登录,以apiKey随机获取一个id
			mConnectServerDialog = DialogUtil.getLoginDialog(this);
			mConnectServerDialog.show();
			mConnectServerDialog.setCancelable(false);// 返回键不能取消
			if (mLoginOutTimeProcess != null && !mLoginOutTimeProcess.running)
				mLoginOutTimeProcess.start();
			break;
		case R.id.net_status_bar_info_top:
			// 跳转到网络设置
			startActivity(new Intent(
					android.provider.Settings.ACTION_WIFI_SETTINGS));
			break;
		default:
			break;
		}
	}

	@Override
	public void onMessage(com.way.bean.Message message) {
		// TODO Auto-generated method stub

	}

	private SendMsgAsyncTask task;

	@Override
	public void onBind(String method, int errorCode, String content) {
		// TODO Auto-generated method stub
		if (errorCode == 0) {// 如果绑定账号成功，由于第一次运行，给同一tag的人推送一条新人消息
			User u = new User(mSpUtil.getUserId(), mSpUtil.getChannelId(),
					mSpUtil.getNick(), mSpUtil.getHeadIcon(), 0);
			mUserDB.addUser(u);// 把自己添加到数据库
			com.way.bean.Message msgItem = new com.way.bean.Message(
					System.currentTimeMillis(), "hi", mSpUtil.getTag());
			task = new SendMsgAsyncTask(mGson.toJson(msgItem), "");
			task.setOnSendScuessListener(new OnSendScuessListener() {

				@Override
				public void sendScuess() {
					startActivity(new Intent(FirstSetActivity.this,
							MainActivity.class));
					if (mConnectServerDialog != null
							&& mConnectServerDialog.isShowing())
						mConnectServerDialog.dismiss();

					if (mLoginOutTimeProcess != null
							&& mLoginOutTimeProcess.running)
						mLoginOutTimeProcess.stop();
					T.showShort(mApplication, R.string.first_start_scuess);
					finish();
				}
			});
			task.send();
		}
	}

	@Override
	public void onNotify(String title, String content) {

	}

	@Override
	public void onNetChange(boolean isNetConnected) {
		if (!isNetConnected) {
			T.showShort(this, R.string.net_error_tip);
			mNetErrorView.setVisibility(View.VISIBLE);
		} else {
			mNetErrorView.setVisibility(View.GONE);
		}
	}

	@Override
	public void onNewFriend(User u) {
		// do nothing
	}

	// 登录超时处理线程
	class LoginOutTimeProcess implements Runnable {
		public boolean running = false;
		private long startTime = 0L;
		private Thread thread = null;

		LoginOutTimeProcess() {
		}

		public void run() {
			while (true) {
				if (!this.running)
					return;
				if (System.currentTimeMillis() - this.startTime > 20 * 1000L) {
					mHandler.sendEmptyMessage(LOGIN_OUT_TIME);
				}
				try {
					Thread.sleep(10L);
				} catch (Exception localException) {
				}
			}
		}

		public void start() {
			try {
				this.thread = new Thread(this);
				this.running = true;
				this.startTime = System.currentTimeMillis();
				this.thread.start();
			} finally {
			}
		}

		public void stop() {
			try {
				this.running = false;
				this.thread = null;
				this.startTime = 0L;
			} finally {
			}
		}
	}
}
