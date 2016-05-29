package tk.samgrogan.dudewheresmymovies;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FavoritesFragment extends Fragment {


    public FavoritesFragment() {
    }

    ArrayAdapter mAdapter;
    DBHandler dbHandler;
    Movies movies = new Movies();
    ListView mList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        dbHandler = new DBHandler(rootView.getContext());

        dbHandler.dbSetTitles(movies);
        dbHandler.dbSetPoster(movies);
        dbHandler.dbSetDate(movies);
        dbHandler.dbSetDesc(movies);
        dbHandler.dbSetMovieId(movies);
        dbHandler.dbSetRate(movies);

        mAdapter = new ArrayAdapter(getActivity(), R.layout.trailer_item, movies.getTList());

        mList = (ListView) rootView.findViewById(R.id.db_titles);

        mList.setAdapter(mAdapter);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String posterUrl = movies.getMovies(position);
                String titleString = movies.getTitles(position);
                String descString = movies.getDesc(position);
                String rateString = movies.getRating(position);
                String date = movies.getReleaseDate(position);
                String idString = movies.getmId(position);
                Intent intent = new Intent(getActivity(),DetailActivity.class)
                        .putExtra("POSTER", posterUrl)
                        .putExtra("TITLE", titleString)
                        .putExtra("DESC",descString)
                        .putExtra("RATE",rateString)
                        .putExtra("DATE",date)
                        .putExtra("ID", idString);
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

}
