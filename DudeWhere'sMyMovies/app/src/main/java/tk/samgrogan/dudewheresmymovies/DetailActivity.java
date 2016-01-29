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

        List<String> mTrailer = new ArrayList<String>();
        String mId;
        ArrayAdapter adapter;
        ListView trailerList;
        String title;
        List<String> trailerTitle = new ArrayList<String>();

        public DetailFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Intent mainData = getActivity().getIntent();
            mId = mainData.getStringExtra("ID");

            String apiUrlString = "http://api.themoviedb.org/3/movie/" + mId + "/videos?api_key=";
            URL apiUrl = null;
            try {
                apiUrl = new URL(apiUrlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            GetTrailers trailersTask = new GetTrailers();

            trailersTask.execute(apiUrl);

            adapter = new ArrayAdapter(getActivity(), R.layout.trailer_item, trailerTitle);

            String poster = mainData.getStringExtra("POSTER");
            title = mainData.getStringExtra("TITLE");
            String desc = mainData.getStringExtra("DESC");
            Float rate = Float.parseFloat(mainData.getStringExtra("RATE"));
            String date = mainData.getStringExtra("DATE");
            ImageView imageView = (ImageView)rootView.findViewById(R.id.poster);

            TextView titleTextView = (TextView)rootView.findViewById(R.id.title);
            TextView descTextView = (TextView) rootView.findViewById(R.id.desc);
            TextView dateTextView = (TextView) rootView.findViewById(R.id.release);
            RatingBar bar = (RatingBar)rootView.findViewById(R.id.ratingBar);
            trailerList = (ListView)rootView.findViewById(R.id.trailers);



            titleTextView.setText(title);
            descTextView.setText("Synopsis: \n" + desc);
            dateTextView.setText("Release Date:  " + date);
            bar.setRating(rate);

            Glide
                    .with(getActivity())
                    .load(poster)
                    .into(imageView);

            trailerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mTrailer.get(position))));
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
                            String tTitle = title + " Trailer " + i+1;
                            mTrailer.add(video);
                            trailerTitle.add(tTitle);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                trailerList.setAdapter(adapter);
                System.out.println(mTrailer);
                System.out.println(trailerTitle);

            }
        }

    }


}
