package tk.samgrogan.dudewheresmymovies;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieFragment())
                    .commit();
        }
    }
    public static class MovieFragment extends Fragment {

        public MovieFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_layout, container, false);

            String[] moviePostersArray = {"http://image.tmdb.org/t/p/w50/kBf3g9crrADGMc2AMAMlLBgSm2h.jpg",
                    "http://image.tmdb.org/t/p/w50/d3DUCNaHgsPktFus7PDxfn1qxFS.jpg"
                    , "http://image.tmdb.org/t/p/w50/mYsoCOq82b08juHGxd3WnotiCAh.jpg",
                    "http://image.tmdb.org/t/p/w50/Aq6infMlOUH5peq3Llf4yGy7ait.jpg",
                    "http://image.tmdb.org/t/p/w50/jX5THE1yW3zTdeD9dupcIyQvKiG.jpg"
                    , "http://image.tmdb.org/t/p/w50/hnuIc1ZC1jxSNoefaN8Bp6ixgBJ.jpg",
                    "http://image.tmdb.org/t/p/w50/7wnRn8iQ0QInEK1CaZFqw1zPhht.jpg",
                    "http://image.tmdb.org/t/p/w50/eJrlh2g9UGAd7R6mQAOQIIs329H.jpg",
                    "http://image.tmdb.org/t/p/w50/jjHu128XLARc2k4cJrblAvZe0HE.jpg",
                    "http://image.tmdb.org/t/p/w50/7eaHkUKAzfstt6XQCiXyuKiZUAw.jpg",
                    "http://image.tmdb.org/t/p/w50/6g7iQJAgyDn9mep98RXhLI64RcA.jpg",
                    "http://image.tmdb.org/t/p/w50/zMY2QVCN9z6YH0eS32NOrSss0gb.jpg",
                    "http://image.tmdb.org/t/p/w50/jY5DEgyMmHUh4AbWTu6pOb5g7vk.jpg",
                    "http://image.tmdb.org/t/p/w50/t1UAqk1Yej8rfxEUqNLFyKkcny1.jpg",
                    "http://image.tmdb.org/t/p/w50/sWa1Y5QhGuJMjw8uuFoggGLqZ0y.jpg",
                    "http://image.tmdb.org/t/p/w50/bI1YVuhBN6Vws1GP9Mf01DyhC2s.jpg",
                    "http://image.tmdb.org/t/p/w50/3KsZUcifrnHqs6AGrZBW4iUTLYs.jpg",
                    "http://image.tmdb.org/t/p/w50/wNtrbql45NqvomsYKr3uHXgFj2D.jpg",
                    "http://image.tmdb.org/t/p/w50/gZb4h7aYRDzUsziErNyrPACo8wv.jpg",
                    "http://image.tmdb.org/t/p/w50/zbr3DvPXFJkLnxZMGGwXaRVAOxQ.jpg"};
            List<String> moviePosters = new ArrayList<>(Arrays.asList(moviePostersArray));

            ImageArrayAdapter adapter = new ImageArrayAdapter(getActivity(), moviePosters);

            GridView gridView = (GridView) rootView.findViewById(R.id.movie_item_image_grid);

            gridView.setAdapter(adapter);



            return rootView;
        }
    }
}
