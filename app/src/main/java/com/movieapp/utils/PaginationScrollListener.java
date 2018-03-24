package com.movieapp.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by mauli on 3/22/2018.
 */

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private static String TAG = PaginationScrollListener.class.getName();

    private GridLayoutManager linearLayoutManager;

    public PaginationScrollListener(GridLayoutManager gridLayoutManager) {
        this.linearLayoutManager = gridLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (linearLayoutManager == null) {
            return;
        }
        int visibleItemCount = linearLayoutManager.getChildCount();
        int totalItemCount = linearLayoutManager.getItemCount();
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()) {

            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && getCurrentPage() < getTotalPageCount()) {
                loadMoreItems();
            }
        }
    }


    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract int getCurrentPage();

    public abstract boolean isLoading();

    public abstract boolean isLastPage();

}
