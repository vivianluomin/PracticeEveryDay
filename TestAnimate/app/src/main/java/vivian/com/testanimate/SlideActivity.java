package vivian.com.testanimate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Created by asus1 on 2017/7/17.
 */

public class SlideActivity extends FragmentActivity {
    private static final  int NUM_PAGES = 5;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide);
        pager = (ViewPager)findViewById(R.id.pager);
        pagerAdapter =new  SlideAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);


    }

    @Override
    public void onBackPressed() {
        if(pager.getCurrentItem() == 0){
            super.onBackPressed();
        }else {
            pager.setCurrentItem(pager.getCurrentItem()-1);
        }

    }

    private class SlideAdapter extends FragmentStatePagerAdapter{
        public SlideAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ScreenSidePageFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
