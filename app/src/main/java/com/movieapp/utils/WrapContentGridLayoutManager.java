package com.movieapp.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by mauli on 3/23/2018.
 */

public class WrapContentGridLayoutManager extends GridLayoutManager {


    public WrapContentGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            // This crash happens when RecyclerView modifies its' content in different thread. It does not have a side effects.   
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            // e.printStackTrace();
        }
    }
}
