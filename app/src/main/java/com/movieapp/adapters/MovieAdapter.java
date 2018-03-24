package com.movieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.movieapp.MovieDetailActivity;
import com.movieapp.R;
import com.movieapp.models.Movie;
import com.movieapp.utils.Constants;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by mauli on 3/23/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private Context mContext;
    private List<Movie> movieList;
    private static final String TAG = MovieAdapter.class.getName();

    public MovieAdapter(Context context, List<Movie> listMovie) {
        this.mContext = context;
        this.movieList = listMovie;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        //pagination mechanism (loading progressbar at the bottom)
        switch (viewType) {
            case ITEM:
                View view1 = mInflater.inflate(R.layout.row_movie_item, parent, false);
                viewHolder = new MovieViewHolder(view1);
                break;
            case LOADING:
                View view = mInflater.inflate(R.layout.item_load_more, parent, false);
                viewHolder = new LoadingViewHolder(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Movie model = movieList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                MovieViewHolder movieViewHolder = (MovieViewHolder) holder;

                movieViewHolder.tvMovieTitle.setText(model.getOriginal_title());

                DecimalFormat df = new DecimalFormat("###.##");
                try {
                    Float popularity = Float.parseFloat(df.format(model.getPopularity()));
                    movieViewHolder.tvMoviePopularity.setText(mContext.getString(R.string.txt_movie_popularity) + " : " + popularity);
                } catch (IllegalArgumentException ie) {
                    ie.printStackTrace();
                }

                String imgUrl = model.getImageUrl();
                if (imgUrl != null && imgUrl.length() > 0) {
                    Glide.with(mContext).load(model.getBaseImageUrl() + imgUrl).into(movieViewHolder.ivMovieBanner);
                }

                movieViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        traverseToDetails(model);
                    }
                });
                break;
            case LOADING:
                // DO NOT NEED TO DO ANYTHING
                break;
        }

    }

    @Override
    public int getItemCount() {
        return (movieList != null) ? movieList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (movieList != null && movieList.size() > 0) {
            return (position == movieList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
        }
        return position;
    }

    public void add(Movie movie) {
        movieList.add(movie);
        if (movieList.size() > 0) {
            notifyItemInserted(movieList.size() - 1);
        }
    }

    public void addAll(List<Movie> movieRes) {
        for (Movie movie : movieRes) {
            add(movie);
        }
    }

    public void remove(Movie movie) {
        int pos = movieList.indexOf(movie);
        if (pos > -1) {
            movieList.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public Movie getItem(int pos) {
        return movieList.get(pos);
    }

    public boolean isEmpty() {
        return (getItemCount() == 0) ? true : false;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Movie());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        if (movieList == null && movieList.size() <= 0) {
            return;
        }
        int position = movieList.size() - 1;
        Movie itemResult = getItem(position);
        if (itemResult != null) {
            movieList.remove(position);
            notifyItemRemoved(position);
        }
    }

    /*
    * Loading View Holder for a footer
    * */
    protected class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    /*
    * Movie View Holder
    * */
    protected class MovieViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMovieTitle, tvMoviePopularity;
        private ImageView ivMovieBanner;
        private CardView cardView;

        public MovieViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.cardView);
            tvMovieTitle = (TextView) view.findViewById(R.id.txtMovieTitleRow);
            tvMoviePopularity = (TextView) view.findViewById(R.id.txtPopulartiyRow);
            ivMovieBanner = (ImageView) view.findViewById(R.id.imgMovieBannerRow);
        }
    }

    private void traverseToDetails(Movie movie) {
        Intent intent = new Intent(mContext, MovieDetailActivity.class);
        intent.putExtra(Constants.PARSE_MOVIE_MODEL, movie);
        mContext.startActivity(intent);
    }

}
