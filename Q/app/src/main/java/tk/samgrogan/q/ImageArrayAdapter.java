package tk.samgrogan.q;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Gh0st on 1/14/2016.
 */
public class ImageArrayAdapter extends ArrayAdapter<String> {


    public ImageArrayAdapter(Context context, List<String> imageUrls){
        super(context, R.layout.movie_item, imageUrls);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.movie_item, parent, false);

        String singleItem = getItem(position);
        ImageView image = (ImageView) customView.findViewById(R.id.movie_poster);


        Glide
                .with(getContext())
                .load(singleItem)
                .into(image);

        return customView;
    }
}
