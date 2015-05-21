package com.cepheuen.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.progresspageindicator.ProgressPageIndicator;


public class PagerActivity extends AppCompatActivity {

    private String[] TITLES = {"Sky 1", "Sky 2", "Sky 3", "Sky 4", "Sky 5"};
    private int[] IMGS = {R.drawable.sky1, R.drawable.sky2, R.drawable.sky3, R.drawable.sky4, R.drawable.sky5, R.drawable.skydef};

    private ProgressPageIndicator dots_layout;
    private NonSwipeableViewPager viewPager;
    private TextView title;
    private Button next, prev;

    private int maxPages = TITLES.length;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        title = (TextView) findViewById(R.id.title);
        next = (Button) findViewById(R.id.next);
        prev = (Button) findViewById(R.id.prev);
        dots_layout = (ProgressPageIndicator) findViewById(R.id.dotsL);
        viewPager = (NonSwipeableViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        dots_layout.setViewPager(viewPager);
        dots_layout.setFillColor(getResources().getColor(android.R.color.holo_green_light));
        title.setText(TITLES[currentPage]);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage++;
                if (currentPage < maxPages) {
                    viewPager.setCurrentItem(currentPage, true);
                    title.setText(TITLES[currentPage]);
                } else {
                    currentPage = maxPages - 1;
                    String[] newArray = new String[TITLES.length + 1];
                    System.arraycopy(TITLES, 0, newArray, 0, TITLES.length);
                    newArray[maxPages] = "Added Page " + (maxPages + 1);
                    TITLES = newArray;
                    maxPages = maxPages + 1;

                    dots_layout.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "End of pages", Toast.LENGTH_LONG).show();
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage--;
                if (currentPage >= 0) {
                    viewPager.setCurrentItem(currentPage, true);
                    title.setText(TITLES[currentPage]);
                } else {
                    currentPage = 0;
                    Toast.makeText(getApplicationContext(), "Start of pages", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            int img;
            try {
                img = IMGS[position];
            } catch (ArrayIndexOutOfBoundsException e) {
                img = R.drawable.skydef;
            }
            return BlankFragment.newInstance(TITLES[position], img);
        }
    }
}
