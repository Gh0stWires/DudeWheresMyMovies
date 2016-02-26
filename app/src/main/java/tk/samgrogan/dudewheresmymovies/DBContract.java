package tk.samgrogan.dudewheresmymovies;

import android.provider.BaseColumns;

/**
 * Created by gh0st on 2/12/16.
 */
public class DBContract {


    public static final class ReviewsEntry implements BaseColumns {
        public static final String TABLE_NAME = "Reviews";
        public static final String COLUMN_REVIEW = "review";
    }

    public static final class TrailerEntry implements BaseColumns {
        public static final String TABLE_NAME = "Trailers";
        public static final String COLUMN_TRAILERS = "trailers";
    }

    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "Movies";
        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESC = "desc";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_REVIEW_ID = "review_id";
        public static final String COLUMN_TRAILER_ID = "trailer_id";
    }
}
