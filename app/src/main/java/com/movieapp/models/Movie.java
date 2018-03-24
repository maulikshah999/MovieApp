package com.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mauli on 3/23/2018.
 */
/* Movie Model
* */
public class Movie implements Parcelable {

    @SerializedName("id")
    private String id = "";
    @SerializedName("title")
    private String name = "";
    @SerializedName("original_title")
    private String original_title = "";
    @SerializedName("poster_path")
    private String imageUrl = "";
    @SerializedName("release_date")
    private String releaseDate = "";
    @SerializedName("popularity")
    private Float popularity = 0.0f;
    @SerializedName("overview")
    private String overview = "";


    @SerializedName("vote_average")
    private Double vote_average = 0.0d;
    @SerializedName("vote_count")
    private Double vote_count = 0.0d;

    private static final String baseImageUrl = "https://image.tmdb.org/t/p/w500/";


    public Movie() {

    }

    public Movie(String id, String name, String original_title, String imageUrl, String releaseDate, Float popularity, String overview, Double vote_average, Double vote_count) {
        this.id = id;
        this.name = name;
        this.original_title = original_title;
        this.imageUrl = imageUrl;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.overview = overview;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    protected Movie(Parcel in) {
        id = in.readString();
        name = in.readString();
        original_title = in.readString();
        imageUrl = in.readString();
        releaseDate = in.readString();
        popularity = in.readFloat();
        overview = in.readString();
        vote_average = in.readDouble();
        vote_count = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(original_title);
        dest.writeString(imageUrl);
        dest.writeString(releaseDate);
        dest.writeFloat(popularity);
        dest.writeString(overview);
        dest.writeDouble(vote_average);
        dest.writeDouble(vote_count);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public Double getVote_count() {
        return vote_count;
    }

    public void setVote_count(double vote_count) {
        this.vote_count = vote_count;
    }

    public static String getBaseImageUrl() {
        return baseImageUrl;
    }

    public Float getPopularity() {
        return popularity;
    }
}