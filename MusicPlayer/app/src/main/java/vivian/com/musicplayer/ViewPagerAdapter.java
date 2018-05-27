package vivian.com.musicplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus1 on 2017/7/28.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public static int PagerCount =0;

    String[] titles = new String[]{
      "个性推介",
            "歌单",
            "排行榜"
    };
    private List<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        init();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return fragments.get(0);
            case 1:
                return fragments.get(1);
            case 2:
                return fragments.get(2);
        }
        return null;
    }

    @Override
    public int getCount() {
        return PagerCount;
    }

    private void init(){
        fragments = new ArrayList<>();
        fragments.add(new gexingtuijie());
        fragments.add(new gedan());
        fragments.add(new paihangbang());
        PagerCount = 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
