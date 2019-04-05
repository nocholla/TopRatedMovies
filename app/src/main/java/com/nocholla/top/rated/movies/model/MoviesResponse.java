package com.nocholla.top.rated.movies.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MoviesResponse {
    @SerializedName("results")
    private List<Movie> results;

    @SerializedName("page")
    private int page;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}

/*
{
"results": [
  {
    "vote_count": 2676,
    "id": 335983,
    "video": false,
    "vote_average": 6.5,
    "title": "Venom",
    "popularity": 490.168,
    "poster_path": "/2uNW4WbgBXL25BAbXGLnLqX71Sw.jpg",
    "original_language": "en",
    "original_title": "Venom",
    "genre_ids": [
    878
    ],
    "backdrop_path": "/VuukZLgaCrho2Ar8Scl9HtV3yD.jpg",
    "adult": false,
    "overview": "Eddie Brock is a reporter—investigating people who want to go unnoticed. But after he makes a terrible discovery at the Life Foundation, he begins to transform into ‘Venom’.  The Foundation has discovered creatures called symbiotes, and believes they’re the key to the next step in human evolution. Unwittingly bonded with one, Eddie discovers he has incredible new abilities—and a voice in his head that’s telling him to embrace the darkness.",
    "release_date": "2018-10-03"
  }
  ],
  "page": 1,
  "total_results": 1178,
  "dates": {
    "maximum": "2018-12-01",
    "minimum": "2018-10-14"
  },
  "total_pages": 59
}
*/