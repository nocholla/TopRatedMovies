package com.nocholla.top.rated.movies.features.grid_view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import com.nocholla.top.rated.movies.MainActivity;
import com.nocholla.top.rated.movies.R;
import com.nocholla.top.rated.movies.adapter.MoviesRecyclerViewGridAdapter;
import com.nocholla.top.rated.movies.api.ApiClient;
import com.nocholla.top.rated.movies.api.ApiInterface;
import com.nocholla.top.rated.movies.features.list_view.ListViewActivity;
import com.nocholla.top.rated.movies.helper.GalleryGridSpacingItemDecoration;
import com.nocholla.top.rated.movies.model.Movie;
import com.nocholla.top.rated.movies.model.MoviesResponse;
import com.nocholla.top.rated.movies.util.Constants;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridViewActivity extends AppCompatActivity {
    private static final String TAG = GridViewActivity.class.getSimpleName();

    // ButterKnife
    @BindView(R.id.movie_recycler_view_grid)
    RecyclerView recyclerView;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.cview_top_rated_movies)
    CardView mCardviewTopRatedMoviesIntro;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(GridViewActivity.this, MainActivity.class));
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
        setContentView(R.layout.activity_grid_view);

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

        // Movie Grid Layout
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GridViewActivity.this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Add spacing between photos in gallery
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_layout_margin);
        recyclerView.addItemDecoration(new GalleryGridSpacingItemDecoration(3, spacingInPixels, true, 0));

        // Get Movies Top Rated Grid
        getMoviesTopRatedGrid();

    }

    /**
     * @method getMoviesTopRatedGrid
     */
    private void getMoviesTopRatedGrid() {
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
                recyclerView.setAdapter(new MoviesRecyclerViewGridAdapter(getApplicationContext(), movieList));

            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    /**
     * @method getMoviesNowPlayingGrid
     */
    private void getMoviesNowPlayingGrid() {
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
                recyclerView.setAdapter(new MoviesRecyclerViewGridAdapter(getApplicationContext(), movies));
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
        getMenuInflater().inflate(R.menu.menu_grid, menu);
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
            case R.id.action_list:
                startActivity(new Intent(this, ListViewActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

