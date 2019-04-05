package com.nocholla.top.rated.movies;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.view.MenuItem;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.nocholla.top.rated.movies.features.grid_view.GridViewActivity;
import com.nocholla.top.rated.movies.fragments.BottomSheetFragment;
import com.nocholla.top.rated.movies.util.Constants;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {
    // ButterKnife
    @BindView(R.id.cview_movie_poster)
    CardView mCardviewMoviePoster;
    @BindView(R.id.cview_movie_details)
    CardView mCardviewMovieDetails;
    @BindView(R.id.imgview_movie_poster)
    ImageView movieImage;
    @BindView(R.id.tview_movie_detail_title)
    TextView movieTitle;
    @BindView(R.id.tview_movie_detail_overview)
    TextView movieOverview;
    @BindView(R.id.tview_movie_detail_release_date)
    TextView movieReleaseDate;
    @BindView(R.id.tview_movie_detail_vote_average)
    TextView movieVoteAverage;
    @BindView(R.id.tview_movie_detail_original_language)
    TextView movieLanguage;
    @BindView(R.id.imgbtn_share)
    ImageButton movieShare;
    @BindView(R.id.imgbtn_website)
    ImageButton movieWebsite;
    @BindView(R.id.imgbtn_facebook)
    ImageButton movieFacebook;
    @BindView(R.id.imgbtn_twitter)
    ImageButton movieTwitter;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(MovieDetailActivity.this, MainActivity.class));
                    return true;
                case R.id.navigation_movies:
                    startActivity(new Intent(MovieDetailActivity.this, GridViewActivity.class));
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
        setContentView(R.layout.activity_movie_detail);

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

        // Movie Poster
        mCardviewMoviePoster.setRadius(30);

        // Movie Details
        mCardviewMovieDetails.setRadius(30);

        // Get Intent
        Intent intent = getIntent();

        final String movieDetailImageUrl = intent.getStringExtra("EXTRA_MOVIE_IMAGE_URL");
        Log.d("DEBUG DET MOVIE IMG URL", movieDetailImageUrl);

        final String movieDetailID = intent.getStringExtra("EXTRA_MOVIE_ID");
        Log.d("DEBUG DET MOVIE ID", movieDetailID);

        final String movieDetailTitle = intent.getStringExtra("EXTRA_MOVIE_TITLE");
        Log.d("DEBUG DET MOVIE TITLE", movieDetailTitle);

        final String movieDetailOverview = intent.getStringExtra("EXTRA_MOVIE_OVERVIEW");
        Log.d("DEBUG DET MOVIE OVIEW", movieDetailOverview);

        final String movieDetailReleaseDate = intent.getStringExtra("EXTRA_MOVIE_RELEASE_DATE");
        Log.d("DEBUG DET MOVIE RELDATE", movieDetailReleaseDate);

        final String movieDetailVoteAverage = intent.getStringExtra("EXTRA_MOVIE_VOTE_AVERAGE");
        Log.d("DEBUG DET MOVIE RATE", movieDetailVoteAverage);

        final String movieDetailOriginalLanguage = intent.getStringExtra("EXTRA_MOVIE_ORIGINAL_LANGUAGE");
        Log.d("DEBUG DET MOVIE LANG", movieDetailVoteAverage);


        // Begin Movie Details
        Picasso.with(getBaseContext())
                .load(movieDetailImageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(movieImage);

        movieTitle.setText(movieDetailTitle);
        movieOverview.setText(movieDetailOverview);
        movieReleaseDate.setText("Release Year: " + movieDetailReleaseDate);
        movieVoteAverage.setText("Rating: " + movieDetailVoteAverage);
        movieLanguage.setText("Language: " + movieDetailOriginalLanguage);
        // End Movie Details

        // Begin Movie Share
        movieShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Intent> targetShareIntents = new ArrayList<Intent>();
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");

                PackageManager pm = MovieDetailActivity.this.getPackageManager();
                List<ResolveInfo> resInfos = pm.queryIntentActivities(shareIntent, 0);
                if (!resInfos.isEmpty()) {
                    for (ResolveInfo resInfo : resInfos) {

                        String packageName = resInfo.activityInfo.packageName;

                        if (packageName.contains("com.twitter.android")
                                || packageName.contains("com.facebook.katana")
                                || packageName.contains("com.whatsapp")
                                || packageName.contains("com.google.android.apps.plus")
                                || packageName.contains("com.google.android.talk")
                                || packageName.contains("com.slack")
                                || packageName.contains("com.google.android.gm")
                                || packageName.contains("com.facebook.orca")
                                || packageName.contains("com.yahoo.mobile")
                                || packageName.contains("com.skype.raider")
                                || packageName.contains("com.android.mms")
                                || packageName.contains("com.linkedin.android")
                                || packageName.contains("com.google.android.apps.messaging")) {

                            // Begin Share Saved Image
                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                            intent.putExtra("AppName", resInfo.loadLabel(pm).toString());
                            intent.setAction(Intent.ACTION_SEND);
                            //intent.setType("image/*");
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this Movie - " + movieDetailTitle);
                            intent.putExtra(Intent.EXTRA_TEXT, "View this Movie: "
                                    + " \n "
                                    + " \n "
                                    + movieDetailImageUrl
                                    + " \n "
                                    + " \n "
                                    + movieDetailTitle
                                    + " \n "
                                    + " \n "
                                    + movieDetailOverview
                                    + " \n "
                                    + " \n "
                                    + "Release Year: " + movieDetailReleaseDate
                                    + " \n "
                                    + " \n "
                                    + "Rating: " + movieDetailVoteAverage
                                    + " \n "
                                    + " \n "
                                    + "Language: " + movieDetailOriginalLanguage);

                            intent.setPackage(packageName);
                            targetShareIntents.add(intent);
                            // End Share
                        }
                    }
                    if (!targetShareIntents.isEmpty()) {
                        Collections.sort(targetShareIntents, new Comparator<Intent>() {
                            @Override
                            public int compare(Intent o1, Intent o2) {
                                return o1.getStringExtra("AppName").compareTo(o2.getStringExtra("AppName"));
                            }
                        });
                        Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0), "Share Via");
                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
                        MovieDetailActivity.this.startActivity(chooserIntent);
                    } else {
                        Toast.makeText(MovieDetailActivity.this, "No app to share.", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        // End Movie Share

        // Begin Movie Website
        movieWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri webPage = Uri.parse(Constants.TMDB_WEBSITE_URL);
                    Intent myWebsite = new Intent(Intent.ACTION_VIEW, webPage);
                    startActivity(myWebsite);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MovieDetailActivity.this, getString(R.string.toast_no_application_can_handle_this_request_website),  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        // End Movie Website

        // Begin Movie Facebook
        movieFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri webPage = Uri.parse(Constants.TMDB_FACEBOOK_URL);
                    Intent myFacebook = new Intent(Intent.ACTION_VIEW, webPage);
                    startActivity(myFacebook);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MovieDetailActivity.this, getString(R.string.toast_no_application_can_handle_this_request_facebook),  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        // End Movie Facebook

        // Begin Movie Twitter
        movieTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri webPage = Uri.parse(Constants.TMDB_TWITTER_URL);
                    Intent myTwitter = new Intent(Intent.ACTION_VIEW, webPage);
                    startActivity(myTwitter);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MovieDetailActivity.this, getString(R.string.toast_no_application_can_handle_this_request_twitter),  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        // Begin Movie Twitter

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
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return true;
    }

    /**
     * Back Home
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_bottom_sheet:
                // Bottom Sheet Fragment
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
