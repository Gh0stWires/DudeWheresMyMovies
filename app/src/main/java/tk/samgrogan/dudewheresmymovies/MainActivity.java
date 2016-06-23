package tk.samgrogan.dudewheresmymovies;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MovieFragment.OnMovieSelected, DetailFragment.OnRefreshList {

    Boolean twoPane;
    DetailFragment fragment;
    MovieFragment movieFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("onCreate","onCreate");

        if (savedInstanceState != null){
            movieFragment = (MovieFragment) getFragmentManager().getFragment(savedInstanceState,"FRAGMENT");
        }
        if (findViewById(R.id.container) != null){
            twoPane = true;

        }else {
            twoPane = false;
        }




    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        movieFragment = (MovieFragment) getFragmentManager().findFragmentById(R.id.movie_fragment);
        getFragmentManager().putFragment(outState,"FRAGMENT", movieFragment);
        super.onSaveInstanceState(outState);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ugradeDB) {
            DBHandler dbHandler = new DBHandler(getApplicationContext());
            SQLiteDatabase db = dbHandler.getWritableDatabase();
            dbHandler.onUpgrade(db, 4, 4);
        }
        if (id == R.id.action_settings) {
            Intent intent;
            intent = new Intent(this, SettingsActivity.class);

            startActivity(intent);
        }

        if (id == R.id.action_favorites) {
            Intent intent;
            intent = new Intent(this, FavoritesActivity.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public void onMovie(Movies movie, int position) {
        if (twoPane){
            String posterUrl = movie.getMovies(position);
            String titleString = movie.getTitles(position);
            String descString = movie.getDesc(position);
            String rateString = movie.getRating(position);
            String date = movie.getReleaseDate(position);
            String idString = movie.getmId(position);



            Bundle args = new Bundle();
            args.putString("POSTER", posterUrl);
            args.putString("TITLE", titleString);
            args.putString("DESC",descString);
            args.putString("RATE",rateString);
            args.putString("DATE",date);
            args.putString("ID", idString);

            fragment = new DetailFragment();
            fragment.setArguments(args);

            getFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();

        }else {
            String posterUrl = movie.getMovies(position);
            String titleString = movie.getTitles(position);
            String descString = movie.getDesc(position);
            String rateString = movie.getRating(position);
            String date = movie.getReleaseDate(position);
            String idString = movie.getmId(position);
            Intent intent = new Intent(this, DetailActivity.class)
                    .putExtra("POSTER", posterUrl)
                    .putExtra("TITLE", titleString)
                    .putExtra("DESC", descString)
                    .putExtra("RATE", rateString)
                    .putExtra("DATE", date)
                    .putExtra("ID", idString);
            startActivity(intent);
        }

    }



    @Override
    public void onRefresh(Boolean check) {
        //just here so it wont crash
    }

    /*@Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());*/

        /*if (frag != null){
            frag.onCheck();
        }*/

}
