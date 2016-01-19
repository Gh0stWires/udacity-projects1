package tk.samgrogan.dudewheresmymovies;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Gh0st on 1/16/2016.
 */

public  class MovieFragment extends Fragment {

    List<String> movies = new ArrayList<String>();


    public MovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout, container, false);
        ImageArrayAdapter adapter = new ImageArrayAdapter(getActivity(), movies);
        new GetMovies().execute();

        GridView gridView = (GridView) rootView.findViewById(R.id.movie_item_image_grid);

        gridView.setAdapter(adapter);
        System.out.println(movies);

        //gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            //@Override
           // public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), movies.get(position), Toast.LENGTH_LONG).show();
            //}
        //});



        return rootView;
    }

    public class GetMovies extends AsyncTask<Void, Void, Void> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJson;

        URL url;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                //set url and connect to it
                url = new URL("http://api.themoviedb.org/3/discover/movie?api_key=0359c81bed7cce4e13cd5a744ea5cfbe");
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
                        movies.add(posterUrl);
                        System.out.println(posterUrl);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //System.out.println(movies);
            

            //adapter.addAll(movies);
            //adapter.notifyDataSetChanged();
        }
    }
}
