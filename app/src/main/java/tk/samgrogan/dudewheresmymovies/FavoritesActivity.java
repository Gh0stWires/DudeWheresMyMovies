package tk.samgrogan.dudewheresmymovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                .add(R.id.container, new FavoritesFragment())
                .commit();
        }
    }


}
