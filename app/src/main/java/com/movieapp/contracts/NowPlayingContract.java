package com.movieapp.contracts;

import com.movieapp.models.MovieResponse;

/**
 * Created by mauli on 3/23/2018.
 */

public class NowPlayingContract {

    public interface View {

    }

    public interface Actions {

        void loadFirstPage();

        void loadNextPage();

        void fillNowPlayingFragmentData(MovieResponse movieResponse);

        void resetVariables();

        void setData(MovieResponse movieResponse);

        MovieResponse getData();

    }

    public interface Repository{

    }
}
