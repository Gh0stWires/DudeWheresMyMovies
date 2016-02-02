package tk.samgrogan.dudewheresmymovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Gh0st on 1/16/2016.
 */
public class ImageArrayAdapter extends ArrayAdapter<String>{


    public ImageArrayAdapter(Context context, List<String> imageUrls){
        super(context,R.layout.movie_item,imageUrls);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.movie_item, parent, false);

        if (customView == null) customView = inflater.inflate(R.layout.movie_item,null);

        String singleItem = getItem(position);
        ImageView image = (ImageView) customView.findViewById(R.id.movie_item_image);


        Glide
                .with(getContext())
                .load(singleItem)
                .into(image);

        return customView;
    }
}
