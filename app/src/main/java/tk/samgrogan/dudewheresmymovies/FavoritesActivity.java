package tk.samgrogan.dudewheresmymovies;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class FavoritesActivity extends AppCompatActivity implements FavoritesFragment.OnMovieSelected, DetailFragment.OnRefreshList{

    Boolean twoPane;
    Fragment fragment;
    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fa = this;
        setContentView(R.layout.activity_favorites);
        if (findViewById(R.id.container1) != null){
            twoPane = true;

        }else {
            twoPane = false;
        }

    }

    /*@Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }*/

    @Override
    public void onMovie(Movies movies, int position) {

        if (twoPane){
            String posterUrl = movies.getMovies(position);
            String titleString = movies.getTitles(position);
            String descString = movies.getDesc(position);
            String rateString = movies.getRating(position);
            String date = movies.getReleaseDate(position);
            String idString = movies.getmId(position);

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
                    .replace(R.id.container1, fragment)
                    .commit();

        }else {
            String posterUrl = movies.getMovies(position);
            String titleString = movies.getTitles(position);
            String descString = movies.getDesc(position);
            String rateString = movies.getRating(position);
            String date = movies.getReleaseDate(position);
            String idString = movies.getmId(position);
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

    /*@Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(5,returnIntent);
        finish();
    }*/

    @Override
    public void onRefresh(Boolean check) {
        if (check){
            if(twoPane) {

                FavoritesFragment frag = (FavoritesFragment) getFragmentManager().findFragmentById(R.id.favorites_fragment);
                frag.onDbChanged();
                getFragmentManager().beginTransaction()
                        .remove(getFragmentManager().findFragmentById(R.id.container1)).commit();

            }


        }
    }
}
