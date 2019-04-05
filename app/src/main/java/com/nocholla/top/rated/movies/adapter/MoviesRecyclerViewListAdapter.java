package com.nocholla.top.rated.movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nocholla.top.rated.movies.MovieDetailActivity;
import com.nocholla.top.rated.movies.model.Movie;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import com.nocholla.top.rated.movies.R;
import com.nocholla.top.rated.movies.util.Constants;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesRecyclerViewListAdapter extends RecyclerView.Adapter<MoviesRecyclerViewListAdapter.ViewHolder>  {
    private Context context;
    private List<Movie> movieList;

    public MoviesRecyclerViewListAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public MoviesRecyclerViewListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list, parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(MoviesRecyclerViewListAdapter.ViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        String moviePoster =  movie.getPosterPath();
        Log.d("DEBUG MOVIE POSTER", moviePoster);

        String posterUrl = Constants.POSTER_URL;
        Log.d("DEBUG POSTER URL", posterUrl);

        String movieImageUrl = posterUrl + moviePoster;
        Log.d("DEBUG MOVIE IMAGE URL", movieImageUrl);

        Picasso.with(context)
                .load(movieImageUrl)
                .placeholder(android.R.drawable.ic_btn_speak_now)
                .into(holder.poster);

        String movieTitle = movie.getTitle();
        Log.d("DEBUG MOVIE TITLE", movieTitle);
        holder.title.setText(movieTitle);

        String movieOverview = movie.getOverview();
        Log.d("DEBUG MOVIE OVERVIEW", movieOverview);
        holder.overview.setText(movieOverview);

        String movieReleaseDate = movie.getReleaseDate();
        Log.d("DEBUG MOVIE RELSE DATE", movieReleaseDate);
        holder.releaseDate.setText(movieReleaseDate);

        Double movieVoteAverage = movie.getVoteAverage();
        Log.d("DEBUG MOVIE VOTE AVG", movieVoteAverage.toString());
        holder.voteAverage.setText(movieVoteAverage.toString());

        String movieOriginalLanguage = movie.getOriginalLanguage();
        Log.d("DEBUG MOVIE LANGUAGE", movieOriginalLanguage);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setFilter(List<Movie> movieModels){
        movieList = new ArrayList<>();
        movieList.addAll(movieModels);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.imgview_movie_poster)
        ImageView poster;
        @BindView(R.id.tview_movie_title)
        TextView title;
        @BindView(R.id.tview_movie_overview)
        TextView overview;
        @BindView(R.id.tview_movie_release_date)
        TextView releaseDate;
        @BindView(R.id.tview_movie_vote_average)
        TextView voteAverage;

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

                }
            });

        }

        @Override
        public void onClick(View v) {

        }
    }

}