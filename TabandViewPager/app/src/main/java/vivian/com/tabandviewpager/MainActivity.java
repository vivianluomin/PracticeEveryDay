package vivian.com.tabandviewpager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    fragAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new fragAdapter(getSupportFragmentManager(),this);

        viewPager.setAdapter(adapter) ;
        //tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for(int i =0;i<tabLayout.getTabCount();i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }



    }

        class fragAdapter extends FragmentPagerAdapter{
            String[] titles = new String[]{"首页","分类","设置"};
            private int[] images = {
                    R.mipmap.ic_launcher,
                    R.mipmap.ic_launcher,
                    R.mipmap.ic_launcher
            };

            Context context;

            public fragAdapter(FragmentManager fm, Context context) {
                super(fm);
                this.context = context;
            }

            @Override
            public Fragment getItem(int position) {
                Log.d("position",String.valueOf(position));
                switch (position){
                    case 0:
                        return new Fragmentone();
                    case 1:
                        return new Fragmenttwo();
                    case 2:
                        return new Fragmentthree();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
//                Drawable image = ContextCompat.getDrawable(context,images[0]);
//                image.setBounds(0,0,image.getIntrinsicWidth(),image.getIntrinsicHeight());
//                SpannableString sb = new SpannableString("   ");
//                ImageSpan imageSpan = new ImageSpan(image,ImageSpan.ALIGN_BOTTOM);
//                sb.setSpan(imageSpan,0,3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//
//                return sb;
            return null;

            }

            public View getTabView(int position){
                View view = LayoutInflater.from(context).inflate(R.layout.tabview,null);
                TextView textView = (TextView)view.findViewById(R.id.textView);
                ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
                textView.setText(titles[position]);
                imageView.setImageResource(images[position]);
                return view;
            }


        }


    }





