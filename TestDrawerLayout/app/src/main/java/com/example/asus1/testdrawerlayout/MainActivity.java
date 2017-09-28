package com.example.asus1.testdrawerlayout;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavtigationView;
    private ImageView mToolImage;
    private ActionBarDrawerToggle mDrawerLinstener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private  void init(){
        mToolBar = (Toolbar)findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);
        mNavtigationView = (NavigationView)findViewById(R.id.nv_leftdrawer);
        //mToolImage = (ImageView)findViewById(R.id.iv_showdrawer);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLinstener = new ActionBarDrawerToggle
                (this,mDrawerLayout,mToolBar,
                        R.string.opendrawer,
                        R.string.closedrawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mToolBar.setTitle(R.string.opendrawer);
                invalidateOptionsMenu();


            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mToolBar.setTitle(R.string.closedrawer);
                invalidateOptionsMenu();

            }


        };

        mDrawerLinstener.syncState();
        mDrawerLayout.addDrawerListener(mDrawerLinstener);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.setting2).
                setVisible(mDrawerLayout.isDrawerOpen(mNavtigationView));
        return super.onPrepareOptionsMenu(menu);
    }
}
