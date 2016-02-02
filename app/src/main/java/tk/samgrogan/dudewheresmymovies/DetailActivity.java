package tk.samgrogan.dudewheresmymovies;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent;
            intent = new Intent(this, SettingsActivity.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

        ArrayAdapter tAdapter;
        ArrayAdapter rAdapter;
        ListView trailerList;
        ListView reviewList;
        SingleMovie mMovie = new SingleMovie();

        public DetailFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Intent mainData = getActivity().getIntent();
            mMovie.setmId(mainData.getStringExtra("ID"));

            String tUrlString = "http://api.themoviedb.org/3/movie/" + mMovie.getmId() + "/videos?api_key=0359c81bed7cce4e13cd5a744ea5cfbe";
            String rUrlString = "http://api.themoviedb.org/3/movie/" + mMovie.getmId() + "/reviews?api_key=0359c81bed7cce4e13cd5a744ea5cfbe";
            URL tapiUrl = null;
            URL rapiUrl = null;
            try {
                tapiUrl = new URL(tUrlString);
                rapiUrl = new URL(rUrlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            GetTrailers trailersTask = new GetTrailers();
            GetReviews reviewsTask = new GetReviews();
            trailersTask.execute(tapiUrl);
            reviewsTask.execute(rapiUrl);


            tAdapter = new ArrayAdapter(getActivity(), R.layout.trailer_item, mMovie.getTrailerTitle());
            rAdapter = new ArrayAdapter(getActivity(), R.layout.trailer_item, mMovie.getmContent());

            mMovie.setPoster(mainData.getStringExtra("POSTER"));
            mMovie.setTitle(mainData.getStringExtra("TITLE"));
            mMovie.setDesc(mainData.getStringExtra("DESC"));
            mMovie.setRate(mainData.getStringExtra("RATE"));
            Float rate = Float.parseFloat(mainData.getStringExtra("RATE"));
            mMovie.setDate(mainData.getStringExtra("DATE"));
            ImageView imageView = (ImageView)rootView.findViewById(R.id.poster);

            TextView titleTextView = (TextView)rootView.findViewById(R.id.title);
            TextView descTextView = (TextView) rootView.findViewById(R.id.desc);
            TextView dateTextView = (TextView) rootView.findViewById(R.id.release);
            RatingBar bar = (RatingBar)rootView.findViewById(R.id.ratingBar);
            trailerList = (ListView)rootView.findViewById(R.id.trailers);
            reviewList = (ListView) rootView.findViewById(R.id.reviews);



            titleTextView.setText(mMovie.getTitle());
            descTextView.setText("Synopsis: \n" + mMovie.getDesc());
            dateTextView.setText("Release Date:  " + mMovie.getDate());
            bar.setRating(rate);

            Glide
                    .with(getActivity())
                    .load(mMovie.getPoster())
                    .into(imageView);

            trailerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mMovie.getTrailer(position))));
                }
            });

            return rootView;
        }


        public class GetTrailers extends AsyncTask<URL, Void, Void> {

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
                            String video = "https://www.youtube.com/watch?v=" + f.getString("key");
                            String name = f.getString("name");
                            String tTitle = mMovie.getTitle() + " " + name;
                            mMovie.setTrailer(video);
                            mMovie.setTrailerTitle(tTitle);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                trailerList.setAdapter(tAdapter);
            }
        }


        public class GetReviews extends AsyncTask<URL, Void, Void> {

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
                            String author = f.getString("author");
                            String content = f.getString("content") + " - " + author;
                            mMovie.setmContent(content);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                reviewList.setAdapter(rAdapter);


            }
        }

    }


}
