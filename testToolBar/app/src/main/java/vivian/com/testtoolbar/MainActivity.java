package vivian.com.testtoolbar;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    PopupWindow popupWindow;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Title");
        toolbar.setTitleTextColor(Color.RED);
        toolbar.setSubtitle("sub title");
        toolbar.setSubtitleTextColor(Color.YELLOW);
        toolbar.setNavigationIcon(R.drawable.sousuo);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                toast.setText("click NavigationIcon");
                toast.show();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit:
                        toast.setText("click edit");
                        break;
                    case R.id.action_share:
                        toast.setText("click share");
                        break;
                    case R.id.action_overflow:
                        //弹出自定义overflow
                        popUpMyOverflow();
                        return true;
                }
                toast.show();
                return true;
            }
        });



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;

    }
    public void popUpMyOverflow() {
        //获取状态栏高度
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        //状态栏高度+toolbar的高度
        int yOffset = frame.top + toolbar.getHeight();
        if (null == popupWindow) {
            //初始化PopupWindow的布局
            View popView = getLayoutInflater().inflate(R.layout.action_overflow_popwindow, null);
            //popView即popupWindow的布局，ture设置focusAble.
            popupWindow = new PopupWindow(popView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            //必须设置BackgroundDrawable后setOutsideTouchable(true)才会有效
            popupWindow.setBackgroundDrawable(new ColorDrawable());
            //点击外部关闭。
            popupWindow.setOutsideTouchable(true);
            //设置一个动画。
            popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
            //设置Gravity，让它显示在右上角。
            popupWindow.showAtLocation(toolbar, Gravity.RIGHT | Gravity.TOP, 0, yOffset);
            //设置item的点击监听
            popView.findViewById(R.id.ll_item1).setOnClickListener(this);
            popView.findViewById(R.id.ll_item2).setOnClickListener(this);
            popView.findViewById(R.id.ll_item3).setOnClickListener(this);
        } else {
            popupWindow.showAtLocation(toolbar, Gravity.RIGHT | Gravity.TOP, 0, yOffset);
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_item1:
                toast.setText("哈哈");
                break;
            case R.id.ll_item2:
                toast.setText("呵呵");
                break;
            case R.id.ll_item3:
                toast.setText("嘻嘻");
                break;
        }
        //点击PopWindow的item后,关闭此PopWindow
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        toast.show();
    }

}
