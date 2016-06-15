package tk.samgrogan.dudewheresmymovies;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
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
    OnMovieSelected mCallback;

    public interface OnMovieSelected{
        public void onMovie(Movies movies, int position);
    }

    //TODO I'm so close
    public void onDbChanged(){
        mAdapter.clear();
        movies.getMList().clear();
        movies.getTList().clear();
        movies.getDList().clear();
        movies.getRList().clear();
        movies.getRDList().clear();
        movies.getIDList().clear();
        dbHandler.dbSetTitles(movies);
        dbHandler.dbSetPoster(movies);
        dbHandler.dbSetDate(movies);
        dbHandler.dbSetDesc(movies);
        dbHandler.dbSetMovieId(movies);
        dbHandler.dbSetRate(movies);
        movies.getTList();




        //mList.setAdapter(mAdapter);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnMovieSelected)activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }


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
                mCallback.onMovie(movies, position);

            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }


}
