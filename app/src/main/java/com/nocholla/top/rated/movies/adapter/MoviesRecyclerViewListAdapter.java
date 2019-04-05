package com.nocholla.top.rated.movies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nocholla.top.rated.movies.model.Movie;
import java.util.List;
import android.util.Log;
import com.nocholla.top.rated.movies.R;
import com.nocholla.top.rated.movies.util.Constants;
import com.squareup.picasso.Picasso;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MoviesRecyclerViewListAdapter extends RecyclerView.Adapter<MoviesRecyclerViewListAdapter.ViewHolder>{
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

        return new ViewHolder(view);
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
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView poster;
        TextView title;
        TextView overview;
        TextView releaseDate;
        TextView voteAverage;

        public ViewHolder(View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.imgview_movie_poster);
            title = itemView.findViewById(R.id.tview_movie_title);
            overview = itemView.findViewById(R.id.tview_movie_overview);
            releaseDate = itemView.findViewById(R.id.tview_movie_release_date);
            voteAverage = itemView.findViewById(R.id.tview_movie_vote_average);

        }

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MoviesRecyclerViewListAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MoviesRecyclerViewListAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}

