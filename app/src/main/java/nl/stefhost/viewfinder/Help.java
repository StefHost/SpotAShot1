package nl.stefhost.viewfinder;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

public class Help extends FragmentActivity implements ViewPager.OnPageChangeListener {

    private static final int NUM_PAGES = 3;

    private ViewPager mPager;
    PagerAdapter mPagerAdapter;
    public static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        textView = (TextView)findViewById(R.id.pagina);
        mPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura.ttf");
        TextView textView1 = (TextView) findViewById(R.id.textview_boven);
        textView1.setTypeface(typeface);
    }

    public void onPageScrollStateChanged(int arg0){}
    public void onPageScrolled(int arg0, float arg1, int arg2) {}

    public void onPageSelected(int arg0) {
        if (arg0 == 0){
            textView.setText("●○○");
        }else if (arg0 == 1){
            textView.setText("○●○");
        }else{
            textView.setText("○○●");
        }
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0){
            super.onBackPressed();
        }else{
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0){
                return new Help_1();
            }else if(position == 1){
                return new Help_2();
            }else{
                return new Help_3();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}