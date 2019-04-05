package com.nocholla.top.rated.movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.nocholla.top.rated.movies.MovieDetailActivity;
import com.nocholla.top.rated.movies.R;
import com.nocholla.top.rated.movies.model.Movie;
import com.nocholla.top.rated.movies.util.Constants;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesRecyclerViewGridAdapter extends RecyclerView.Adapter<MoviesRecyclerViewGridAdapter.ViewHolder> {
    private Context context;
    private List<Movie> movieList;

    public MoviesRecyclerViewGridAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_thumbnail_grid, parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        String moviePoster =  movie.getPosterPath();
        Log.d("DEBUG MOVIE POSTER", moviePoster);

        String posterUrl = Constants.POSTER_URL;
        Log.d("DEBUG POSTER URL", posterUrl);

        String movieImageUrl = posterUrl + moviePoster;
        Log.d("DEBUG MOVIE IMAGE URL", movieImageUrl);

        Picasso.with(context)
                .load(movieImageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.thumbnail);

        Integer movieID = movie.getId();
        Log.d("DEBUG MOVIE ID", movieID.toString());

        String movieTitle = movie.getTitle();
        Log.d("DEBUG MOVIE TITLE", movieTitle);

        String movieOverview = movie.getOverview();
        Log.d("DEBUG MOVIE OVERVIEW", movieOverview);

        String movieReleaseDate = movie.getReleaseDate();
        Log.d("DEBUG MOVIE RELSE DATE", movieReleaseDate);

        Double movieVoteAverage = movie.getVoteAverage();
        Log.d("DEBUG MOVIE VOTE AVG", movieVoteAverage.toString());

        String movieOriginalLanguage = movie.getOriginalLanguage();
        Log.d("DEBUG MOVIE LANGUAGE", movieOriginalLanguage);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setFilter(List<Movie> movieLists){
        movieList = new ArrayList<>();
        movieList.addAll(movieLists);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        public ViewHolder(View itemView, final Context ctx) {
            super(itemView);
            context = ctx;
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Movie movie = movieList.get(getAdapterPosition());

                    String moviePoster =  movie.getPosterPath();
                    Log.d("DEBUG MOVIE DET POSTER", moviePoster);

                    String posterUrl = Constants.POSTER_URL;
                    Log.d("DEBUG POSTER DET URL", posterUrl);

                    String movieImageUrl = posterUrl + moviePoster;
                    Log.d("DEBUG MOVIE DET IMG URL", movieImageUrl);

                    Integer movieID = movie.getId();
                    String movieIDString = movieID.toString();
                    Log.d("DEBUG MOVIE DET ID", movieIDString);

                    String movieTitle = movie.getTitle();
                    Log.d("DEBUG MOVIE DET TITLE", movieTitle);

                    String movieOverview = movie.getOverview();
                    Log.d("DEBUG MOVIE DET OVIEW", movieOverview);

                    String movieReleaseDate = movie.getReleaseDate();
                    Log.d("DEBUG MOVIE DET RE DATE", movieReleaseDate);

                    Double movieVoteAverage = movie.getVoteAverage();
                    String movieVoteAverageString = movieVoteAverage.toString();
                    Log.d("DEBUG MOVIE DET RATE", movieVoteAverageString);

                    String movieOriginalLanguage = movie.getOriginalLanguage();
                    Log.d("DEBUG MOVIE DET LANG", movieOriginalLanguage);

                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    intent.putExtra("EXTRA_MOVIE_IMAGE_URL", movieImageUrl);
                    intent.putExtra("EXTRA_MOVIE_ID", movieIDString);
                    intent.putExtra("EXTRA_MOVIE_TITLE", movieTitle);
                    intent.putExtra("EXTRA_MOVIE_OVERVIEW", movieOverview);
                    intent.putExtra("EXTRA_MOVIE_RELEASE_DATE", movieReleaseDate);
                    intent.putExtra("EXTRA_MOVIE_VOTE_AVERAGE", movieVoteAverageString);
                    intent.putExtra("EXTRA_MOVIE_ORIGINAL_LANGUAGE", movieOriginalLanguage);

                    ctx.startActivity(intent);

                    // Toast
                    //Toast.makeText(ctx, "Movie Name : " + movieTitle, Toast.LENGTH_SHORT).show();

                }
            });

        }

        @Override
        public void onClick(View v) {

        }
    }

}

