package com.nocholla.top.rated.movies.api;

import com.nocholla.top.rated.movies.model.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    // Get Top Rated Movies = http://api.themoviedb.org/3/movie/top_rated?api_key=06b3e2bcc5baf7b7cc892a9404d7af85
    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    // Get Now Playing Movies = http://api.themoviedb.org/3/movie/now_playing?api_key=06b3e2bcc5baf7b7cc892a9404d7af85
    @GET("movie/now_playing")
    Call<MoviesResponse> getNowPlayingMovies(@Query("api_key") String apiKey);

    // Get Movie Detail = https://api.themoviedb.org/3/movie/19404?api_key=06b3e2bcc5baf7b7cc892a9404d7af85
    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

}
