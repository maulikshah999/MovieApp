package com.movieapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.movieapp.NowPlayingFragment;
import com.movieapp.UpcomingMoviesFragment;

/**
 * Created by maulik on 3/23/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int mNo_of_tabs = 0;
    private FragmentManager mFragmentManager;

    public ViewPagerAdapter(FragmentManager fm, int no_of_tabs) {
        super(fm);
        this.mNo_of_tabs = no_of_tabs;
        this.mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new NowPlayingFragment();
                break;
            case 1:
                fragment = new UpcomingMoviesFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mNo_of_tabs;
    }
}
