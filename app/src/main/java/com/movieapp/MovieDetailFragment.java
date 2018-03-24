package com.movieapp;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.movieapp.models.Movie;
import com.movieapp.utils.Constants;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private TextView tv_title, tv_rating, tv_detail, tv_release;
    private ImageView imgMovieBanner;
    private static final String TAG = MovieDetailFragment.class.getName();
    private Movie movie = null;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {

        if (getArguments().containsKey(Constants.PARSE_MOVIE_MODEL)) {
            movie = (Movie) getArguments().getParcelable(Constants.PARSE_MOVIE_MODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);

        init(rootView);

        return rootView;
    }

    private void init(View view) {

        initCollapsingToolbar(view);
        tv_title = (TextView) getActivity().findViewById(R.id.txtMovieTitleDetails);
        tv_rating = (TextView) getActivity().findViewById(R.id.txtRatingDetails);
        tv_detail = (TextView) getActivity().findViewById(R.id.txtDetailsPage);
        tv_release = (TextView) getActivity().findViewById(R.id.txtReleaseDateDetails);

        imgMovieBanner = (ImageView) getActivity().findViewById(R.id.imgMovieBannerMovieDetail);

        if (movie != null) {
            fillDataInView();
        } else {
            Log.e(TAG, "API Data does not found on detail page");
        }

    }

    //Collapse toolbar on scroll
    private void initCollapsingToolbar(View view) {
        collapsingToolbarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(" ");
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.app_bar);
        appBarLayout.setExpanded(true);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isDisplay = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.title_movie_detail));
                    isDisplay = true;
                } else if (isDisplay) {
                    collapsingToolbarLayout.setTitle(" ");
                    isDisplay = false;
                }

            }
        });

    }

    //fill data view
    private void fillDataInView() {
        StringBuilder builder = new StringBuilder();
        tv_title.setText(movie.getOriginal_title());
        tv_detail.setText(movie.getOverview());
        builder.append(getActivity().getString(R.string.txt_release_date));
        builder.append(" : ");
        builder.append(movie.getReleaseDate());
        tv_release.setText(builder.toString());
        // clear builder to reuse for other fields
        builder.setLength(0);
        builder.append(getActivity().getString(R.string.txt_user_rating));
        builder.append(" : ");
        builder.append(movie.getVote_average());
        builder.append("/10");
        tv_rating.setText(builder.toString());
        String imgUrl = movie.getImageUrl();
        if (imgUrl != null && imgUrl.length() > 0) {
            Glide.with(this).load(movie.getBaseImageUrl() + imgUrl).into(imgMovieBanner);
        }
    }
}