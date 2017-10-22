package nl.stefhost.spotashot;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Spelregels extends FragmentActivity implements ViewPager.OnPageChangeListener {

    private static final int NUM_PAGES = 8;

    public ViewPager mPager;
    PagerAdapter mPagerAdapter;
    public TextView textView;

    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelregels);

        textView = (TextView)findViewById(R.id.pagina);
        button = (Button) findViewById(R.id.button);
        mPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura_bold.ttf");
        TextView textView1 = (TextView) findViewById(R.id.textview_boven);
        textView1.setTypeface(typeface);

        String help_versie = getString(R.string.help_scherm);
        int pagina = Integer.parseInt(help_versie);

        mPager.setCurrentItem(pagina);
        onPageSelected(pagina);
    }

    public void onPageScrollStateChanged(int arg0){}

    public void onPageScrolled(int arg0, float arg1, int arg2) {}

    public void onPageSelected(int arg0) {
        if (arg0 == 0){
            textView.setText(Html.fromHtml("<font color='#f26522'>●</font>○○○○○○○"));
        }else if (arg0 == 1){
            textView.setText(Html.fromHtml("○<font color='#f26522'>●</font>○○○○○○"));
        }else if (arg0 == 2){
            textView.setText(Html.fromHtml("○○<font color='#f26522'>●</font>○○○○○"));
        }else if (arg0 == 3){
            textView.setText(Html.fromHtml("○○○<font color='#f26522'>●</font>○○○○"));
        }else if (arg0 == 4){
            textView.setText(Html.fromHtml("○○○○<font color='#f26522'>●</font>○○○"));
        }else if (arg0 == 5){
            textView.setText(Html.fromHtml("○○○○○<font color='#f26522'>●</font>○○"));
        }else if (arg0 == 6){
            textView.setText(Html.fromHtml("○○○○○○<font color='#f26522'>●</font>○"));
        }else if (arg0 == 7){
            textView.setText(Html.fromHtml("○○○○○○○<font color='#f26522'>●</font>"));
            button.setVisibility(View.VISIBLE);
        }

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new Spelregels_keuze();
            Bundle bundle = new Bundle();
            bundle.putInt("pagina", position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void sluiten(View view){
        onBackPressed();
    }

}