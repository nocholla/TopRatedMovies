package com.nocholla.top.rated.movies.features.list_view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import com.nocholla.top.rated.movies.MainActivity;
import com.nocholla.top.rated.movies.R;
import com.nocholla.top.rated.movies.adapter.MoviesRecyclerViewListAdapter;
import com.nocholla.top.rated.movies.api.ApiClient;
import com.nocholla.top.rated.movies.api.ApiInterface;
import com.nocholla.top.rated.movies.features.grid_view.GridViewActivity;
import com.nocholla.top.rated.movies.model.Movie;
import com.nocholla.top.rated.movies.model.MoviesResponse;
import com.nocholla.top.rated.movies.util.Constants;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewActivity extends AppCompatActivity {
    private static final String TAG = ListViewActivity.class.getSimpleName();

    // ButterKnife
    @BindView(R.id.movie_recycler_view_list)
    RecyclerView recyclerView;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.cview_top_rated_movies)
    CardView mCardviewTopRatedMoviesIntro;

    // Movie List
    private MoviesRecyclerViewListAdapter moviesRecyclerViewListAdapter;
    private ArrayList<Movie> movieList;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(ListViewActivity.this, MainActivity.class));
                    return true;
                case R.id.navigation_movies:
                    Toast.makeText(getBaseContext(), getString(R.string.toast_movies_page), Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };

    /**
     * @method onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // Back Home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Begin Lock Screen Orientation for Phones to Portrait Mode
        if (!isTablet()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        // Begin Lock Screen Orientation for Tablets to Landscape Mode
        if (isTablet()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        // ButterKnife
        ButterKnife.bind(this);

        // Bottom Nav
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Card View
        mCardviewTopRatedMoviesIntro.setRadius(30);

        //recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(moviesRecyclerViewListAdapter);

        // Get Movies Top Rated List
        getMoviesTopRatedList();
    }

    /**
     * @method getMoviesTopRatedList
     */
    private void getMoviesTopRatedList() {
        // API Key Check
        if (Constants.API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_no_api_key), Toast.LENGTH_LONG).show();
            return;
        }

        // API Service
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        // API Get Movies Top Rated
        Call<MoviesResponse> call = apiService.getTopRatedMovies(Constants.API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                int statusCode = response.code();

                // Movie List
                List<Movie> movieList = response.body().getResults();

                // RecyclerView
                recyclerView.setAdapter(new MoviesRecyclerViewListAdapter(getApplicationContext(), movieList));

            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    /**
     * @method getMoviesNowPlayingList
     */
    private void getMoviesNowPlayingList() {
        // API Key Check
        if (Constants.API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_no_api_key), Toast.LENGTH_LONG).show();
            return;
        }

        // API Service
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        // API Get Movies Now Playing
        Call<MoviesResponse> call = apiService.getNowPlayingMovies(Constants.API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                int statusCode = response.code();

                // Movie List
                List<Movie> movies = response.body().getResults();

                // RecyclerView
                recyclerView.setAdapter(new MoviesRecyclerViewListAdapter(getApplicationContext(), movies));
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    /**
     * Set Screen Orientation
     */
    private boolean isTablet() {
        return (this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * @Method onCreateOptionsMenu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    /**
     * @Method onOptionsItemSelected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_grid:
                startActivity(new Intent(this, GridViewActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

