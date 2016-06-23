package tk.samgrogan.dudewheresmymovies;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Gh0st on 1/16/2016.
 */

public  class MovieFragment extends Fragment {

    //List<String> movies = new ArrayList<String>();
    //List<String> titles = new ArrayList<String>();
    //List<String> desc = new ArrayList<String>();
    //List<String> rating = new ArrayList<String>();
    //List<String> releaseDate = new ArrayList<String>();
    //List<String> mId = new ArrayList<String>();
    Movies mMovies = new Movies();
    GridView gridView;
    ImageArrayAdapter adapter;
    OnMovieSelected mCallback;



    public MovieFragment() {
    }

    public interface OnMovieSelected{
        public void onMovie(Movies movie, int position);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout, container, false);
        adapter = new ImageArrayAdapter(getActivity(), mMovies.getMList());
        Log.w("Survey Says", String.valueOf(savedInstanceState));
        if (savedInstanceState == null) {
            updateMovies();
        }
        //new GetMovies().execute();

        gridView = (GridView) rootView.findViewById(R.id.movie_item_image_grid);

        //System.out.println(movies);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onMovie(mMovies, position);

            }
        });



        return rootView;
    }

    private void updateMovies(){
        GetMovies movietask = new GetMovies();
        String url = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(getString(R.string.url_list_pref_key), getString(R.string.url_default));
        mMovies.getMList().clear();
        mMovies.getTList().clear();
        mMovies.getDList().clear();
        mMovies.getRList().clear();
        mMovies.getRDList().clear();
        mMovies.getIDList().clear();
        try {
            URL aUrl = new URL(url);
            movietask.execute(aUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {


        outState.putStringArrayList("Poster", (ArrayList<String>) mMovies.getMList());
        outState.putStringArrayList("Title", (ArrayList<String>) mMovies.getTList());
        outState.putStringArrayList("Desc", (ArrayList<String>) mMovies.getDList());
        outState.putStringArrayList("Rating", (ArrayList<String>) mMovies.getRList());
        outState.putStringArrayList("ReleaseDate", (ArrayList<String>) mMovies.getRDList());
        outState.putStringArrayList("ID", (ArrayList<String>) mMovies.getIDList());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            mMovies.getMList().clear();
            mMovies.getTList().clear();
            mMovies.getDList().clear();
            mMovies.getRList().clear();
            mMovies.getRDList().clear();
            mMovies.getIDList().clear();
            mMovies.setMList(savedInstanceState.getStringArrayList("Poster"));
            mMovies.setTList(savedInstanceState.getStringArrayList("Title"));
            mMovies.setDList(savedInstanceState.getStringArrayList("Desc"));
            mMovies.setRList(savedInstanceState.getStringArrayList("Rating"));
            mMovies.setRDList(savedInstanceState.getStringArrayList("ReleaseDate"));
            mMovies.setIDList(savedInstanceState.getStringArrayList("ID"));
            adapter = new ImageArrayAdapter(getActivity(), mMovies.getMList());
            gridView.setAdapter(adapter);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.w("onStop","onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.w("onPause","onPause");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.w("onResume", "onResume");
        //updateMovies();

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w("onDestroy","onDestroy");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.w("onStart", "onStart");


    }



    public class GetMovies extends AsyncTask<URL, Void, Void> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJson;

        URL url;



        @Override
        protected Void doInBackground(URL... params) {

            try {

                //set url and connect to it
                url = params[0];
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null){
                    buffer.append(line +'\n');
                }

                if (buffer.length() == 0){
                    return null;
                }

                moviesJson = buffer.toString();


            } catch (IOException e) {
                Log.e("MovieList", "Error ", e);
                return null;
            }finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
                if (reader != null){
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("MovieList", "Error ", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            if (moviesJson != null){
                try{
                    JSONObject jsonObject = new JSONObject(moviesJson);
                    JSONArray films = jsonObject.getJSONArray("results");

                    //Parse the json tree for poster Urls
                    for (int i = 0; i < films.length(); i++){
                        JSONObject f = films.getJSONObject(i);
                        String posterUrl = "http://image.tmdb.org/t/p/w185" + f.getString("poster_path");
                        String title = f.getString("title");
                        String description = f.getString("overview");
                        String rate = f.getString("vote_average");
                        String release = f.getString("release_date");
                        String id = f.getString("id");
                        mMovies.setMovies(posterUrl);
                        //movies.add(posterUrl);
                        mMovies.setTitles(title);
                        //titles.add(title);
                        mMovies.setDesc(description);
                        //desc.add(description);
                        mMovies.setRating(rate);
                        //rating.add(rate);
                        mMovies.setReleaseDate(release);
                        //releaseDate.add(release);
                        mMovies.setmId(id);
                        //mId.add(id);
                        //System.out.println(posterUrl);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //System.out.println(movies);
            gridView.setAdapter(adapter);
            //adapter.addAll(movies);
            //adapter.notifyDataSetChanged();
        }
    }
}
