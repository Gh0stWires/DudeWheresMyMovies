package tk.samgrogan.dudewheresmymovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Gh0st on 2/2/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 4;
    private static final String DB_NAME = "movies.db";
    private static final String MOVIES = "Movies";
    private static final String TRAILERS = "Trailers";
    private static final String REVIEWS = "Reviews";
    private static final int COLUMN_ID = 0;
    private static final String COLUMN_MOVIE_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESC = "desc";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_POSTER = "poster";
    private static final String COLUMN_REVIEW = "review";
    private static final String COLUMN_DATE = "date";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpgrade(db,DB_VERSION,DB_VERSION);
        String query = "CREATE TABLE " + MOVIES + "(" +
                COLUMN_MOVIE_ID + " TEXT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESC + " TEXT, " +
                COLUMN_RATING + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_POSTER + " TEXT, " +
                COLUMN_REVIEW + " TEXT " +
                ");";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MOVIES);
        onCreate(db);
    }
    //Add movie to db
    public void addMovie(SingleMovie movie){
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_ID, movie.getmId());
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_DESC, movie.getDesc());
        values.put(COLUMN_RATING, movie.getRate());
        values.put(COLUMN_DATE, movie.getDate());
        values.put(COLUMN_POSTER, movie.getPoster());
        Log.d(getClass().getSimpleName(), values.toString());
        for (int i = 0; i < movie.getmContent().size(); i++){
            values.put(COLUMN_REVIEW, movie.getContentList(i));
            Log.d(getClass().getSimpleName(), values.toString());
        }
        SQLiteDatabase db = getWritableDatabase();
        db.insert(MOVIES, null, values);
        Log.d(getClass().getSimpleName(), values.toString());
        db.close();
    }

    public void deleteMovie(SingleMovie movie){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + MOVIES + " WHERE " + COLUMN_MOVIE_ID + "=\"" + movie.getmId() + "\";");

    }

    public String dataBaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + MOVIES + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();


        if (c.getString(c.getColumnIndex("review"))!= null){
            dbString = c.getString(c.getColumnIndex("review"));
            Log.d(getClass().getSimpleName(), dbString);

                //dbString += "\n";
                //dbString += c.getString(c.getColumnIndex("desc"));
            }


        db.close();
        return dbString;
    }

}
