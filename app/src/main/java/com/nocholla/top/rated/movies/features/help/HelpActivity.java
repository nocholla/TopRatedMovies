package com.nocholla.top.rated.movies.features.help;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import com.nocholla.top.rated.movies.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpActivity extends AppCompatActivity {
    // ButterKnife
    @BindView(R.id.cview_help)
    CardView mCardviewHelp;

    /**
     * Method : onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // Back Home Button
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

        // Card View
        mCardviewHelp.setRadius(30);

    }

    /**
     * Set Screen Orientation
     */
    private boolean isTablet() {
        return (this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
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
        }
        return super.onOptionsItemSelected(item);
    }

}



