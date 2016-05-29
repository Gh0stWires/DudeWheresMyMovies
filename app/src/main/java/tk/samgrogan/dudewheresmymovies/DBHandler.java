package tk.samgrogan.dudewheresmymovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import tk.samgrogan.dudewheresmymovies.DBContract.MovieEntry;

/**
 * Created by Gh0st on 2/2/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 4;
    private static final String DB_NAME = "movies.db";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //onUpgrade(db,DB_VERSION,DB_VERSION);
        String query = "CREATE TABLE " + MovieEntry.TABLE_NAME + "(" +
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieEntry.COLUMN_MOVIE_ID + " TEXT, " +
                MovieEntry.COLUMN_TITLE + " TEXT, " +
                MovieEntry.COLUMN_DESC + " TEXT, " +
                MovieEntry.COLUMN_RATING + " TEXT, " +
                MovieEntry.COLUMN_DATE + " TEXT, " +
                MovieEntry.COLUMN_POSTER + " TEXT " +
                ");";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
    //Add movie to db
    public void addMovie(SingleMovie movie){
        ContentValues values = new ContentValues();
        values.put(MovieEntry.COLUMN_MOVIE_ID, movie.getmId());
        values.put(MovieEntry.COLUMN_TITLE, movie.getTitle());
        values.put(MovieEntry.COLUMN_DESC, movie.getDesc());
        values.put(MovieEntry.COLUMN_RATING, movie.getRate());
        values.put(MovieEntry.COLUMN_DATE, movie.getDate());
        values.put(MovieEntry.COLUMN_POSTER, movie.getPoster());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(MovieEntry.TABLE_NAME, null, values);
        Log.d(getClass().getSimpleName(), values.toString());
        db.close();
    }

    public void deleteMovie(SingleMovie movie){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + MovieEntry.TABLE_NAME + " WHERE " + MovieEntry.COLUMN_MOVIE_ID + "=\"" + movie.getmId() + "\";");
        db.close();

    }

    public void dbSetTitles(Movies movies) {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + MovieEntry.TABLE_NAME + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        //c.moveToFirst();

        while (c.moveToNext()) {
            if (c.getString(c.getColumnIndex("title")) != null) {
                movies.setTitles(c.getString(c.getColumnIndex("title")));
                Log.d(getClass().getSimpleName(), dbString);

            }
        }

        c.close();
        db.close();
    }

    public boolean checkTitlte(SingleMovie movie){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + MovieEntry.TABLE_NAME + " WHERE " + MovieEntry.COLUMN_TITLE + "=\"" + movie.getTitle() + "\";";

        Cursor c = db.rawQuery(query, null);

        if (c.getCount() <= 0){
            c.close();
            return false;
        }else {
            c.close();
            return true;
        }

    }

    public void dbSetPoster(Movies movies) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + MovieEntry.TABLE_NAME + " WHERE 1";

        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()) {
            if (c.getString(c.getColumnIndex("poster")) != null) {
                movies.setMovies(c.getString(c.getColumnIndex("poster")));
            }
        }

        c.close();
        db.close();
    }

    public void dbSetDesc(Movies movies) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + MovieEntry.TABLE_NAME + " WHERE 1";

        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()) {
            if (c.getString(c.getColumnIndex("desc")) != null) {
                movies.setDesc(c.getString(c.getColumnIndex("desc")));
            }
        }

        c.close();
        db.close();
    }

    public void dbSetMovieId(Movies movies) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + MovieEntry.TABLE_NAME + " WHERE 1";

        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()) {
            if (c.getString(c.getColumnIndex("id")) != null) {
                movies.setmId(c.getString(c.getColumnIndex("id")));
            }
        }

        c.close();
        db.close();
    }

    public void dbSetRate(Movies movies) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + MovieEntry.TABLE_NAME + " WHERE 1";

        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()) {
            if (c.getString(c.getColumnIndex("rating")) != null) {
                movies.setRating(c.getString(c.getColumnIndex("rating")));
            }
        }

        c.close();
        db.close();
    }

    public void dbSetDate(Movies movies) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + MovieEntry.TABLE_NAME + " WHERE 1";

        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()) {
            if (c.getString(c.getColumnIndex("date")) != null) {
                movies.setReleaseDate(c.getString(c.getColumnIndex("date")));
            }
        }

        c.close();
        db.close();
    }

}
