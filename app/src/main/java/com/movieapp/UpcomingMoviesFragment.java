package com.movieapp;


import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.movieapp.adapters.MovieAdapter;
import com.movieapp.apis.Client;
import com.movieapp.apis.Service;
import com.movieapp.models.Movie;
import com.movieapp.models.MovieResponse;
import com.movieapp.utils.AppUtils;
import com.movieapp.utils.ConnectionRefreshable;
import com.movieapp.utils.Constants;
import com.movieapp.utils.CustomGridRecyclerView;
import com.movieapp.utils.PaginationScrollListener;
import com.movieapp.utils.WrapContentGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingMoviesFragment extends Fragment implements ConnectionRefreshable {

    private MovieAdapter adapter;
    private List<Movie> movieList;
    private SwipeRefreshLayout swipeRefresh;
    private static final String TAG = NowPlayingFragment.class.getName();
    private CustomGridRecyclerView recyclerView;
    private int page = 1;
    private CoordinatorLayout coordinatorLayout;
    private GridLayoutManager gridLayoutManager;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private static int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private int TOTAL_PAGES = 0;
    private Service apiSrv;
    private MovieResponse mMovieResponse;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_movies, container, false);
        init(view, savedInstanceState);
        return view;
    }

    private void init(View view, Bundle savedInstanceState) {
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // call APIs from here
                loadFirstPage();
            }
        });

        recyclerView = (CustomGridRecyclerView) view.findViewById(R.id.recyclerView);
        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayoutMovieList);

        movieList = new ArrayList<>();
        adapter = new MovieAdapter(getActivity(), movieList);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new WrapContentGridLayoutManager(getActivity(), 2);
        } else {
            gridLayoutManager = new WrapContentGridLayoutManager(getActivity(), 4);
        }
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage = currentPage + 1;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public int getCurrentPage() {
                return currentPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return false;
            }
        });

        apiSrv = Client.getClient().create(Service.class);
        /*
        * Configration Changes handled here
        * */
        if (savedInstanceState != null) {
            mMovieResponse = (MovieResponse) savedInstanceState.getParcelable(Constants.UPCOING_MOVIES);
            setData(mMovieResponse);
        } else {
            loadFirstPage();
        }
    }

    private void loadFirstPage() {
        if (!AppUtils.isNetworkConnected(getActivity())) {
            AppUtils.showNoConnectionSnackBar(coordinatorLayout, getActivity(), this);
            dismissSwipeLayout();
            return;
        }

        Log.d(TAG, "loadFirstPage " + currentPage);

        try {
            if (BuildConfig.THE_MOVIE_DB_API_KEY.isEmpty()) {
                Toast.makeText(getActivity(), "The Movie DB API Key not found", Toast.LENGTH_SHORT).show();
                return;
            }

            resetVariables();
            Call<com.movieapp.models.MovieResponse> callMovieResponse = apiSrv.getUpComingMoviesV3(BuildConfig.THE_MOVIE_DB_API_KEY, currentPage, Constants.CONTENT_TYPE);
            callMovieResponse.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    Log.d(TAG, "" + response.toString());
                    MovieResponse movieResponse = response.body();
                    if (movieResponse != null) {
                        List<Movie> movies = response.body().getResults();
                        adapter.addAll(movies);
                        TOTAL_PAGES = movieResponse.getTotal_pages();
                        mMovieResponse = movieResponse;

                        Log.d(TAG, "TOTAL_PAGES: " + TOTAL_PAGES);
                        if (currentPage <= TOTAL_PAGES) {
                            adapter.addLoadingFooter();
                        } else {
                            isLastPage = true;
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                    Toast.makeText(getActivity(), "Error while fetching data", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        dismissSwipeLayout();

    }

    private void loadNextPage() {

        Log.d(TAG, "loadNextPage " + currentPage);

        Call<MovieResponse> callMovieResponse = apiSrv.getUpComingMoviesV3(BuildConfig.THE_MOVIE_DB_API_KEY, currentPage, Constants.CONTENT_TYPE);
        callMovieResponse.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.d(TAG, "" + response.toString());
                MovieResponse movieResponse = response.body();
                if (movieResponse != null) {
                    adapter.removeLoadingFooter();
                    isLoading = false;

                    List<Movie> movies = response.body().getResults();
                    adapter.addAll(movies);
                    TOTAL_PAGES = movieResponse.getTotal_pages();
                    Log.d(TAG, "TOTAL_PAGES: " + TOTAL_PAGES);
                    if (currentPage <= TOTAL_PAGES) {
                        adapter.addLoadingFooter();
                    } else {
                        isLastPage = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Toast.makeText(getActivity(), "Error while fetching data", Toast.LENGTH_SHORT).show();
            }
        });
        dismissSwipeLayout();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        MovieResponse mResponse = getData();
        if (mResponse != null) {
            outState.putParcelable(Constants.UPCOING_MOVIES, getData());
        }

    }

    public void setData(MovieResponse movieResponse) {
        mMovieResponse = movieResponse;
        fillNowPlayingFragmentData(mMovieResponse);
    }

    public MovieResponse getData() {
        if (mMovieResponse != null) {
            mMovieResponse.setResults(movieList);
        }
        return mMovieResponse;
    }

    private void fillNowPlayingFragmentData(MovieResponse movieResponse) {
        currentPage = movieResponse.getPage();
        TOTAL_PAGES = movieResponse.getTotal_pages();
        movieList = movieResponse.getResults();
        adapter.addAll(movieList);
        if (movieList.size() <= 0) {
            loadFirstPage();
        }
    }

    private void dismissSwipeLayout() {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }

    private void resetVariables() {
        isLastPage = false;
        isLoading = false;
        currentPage = PAGE_START;
        TOTAL_PAGES = 0;
        movieList.clear();
        //  adapter.clear();
    }

    @Override
    public void onRefreshPage() {
        // call APIs on RETRY INTERNET connection
        loadFirstPage();
    }
}
