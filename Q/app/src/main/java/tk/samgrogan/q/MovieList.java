package tk.samgrogan.q;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

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
import java.util.List;

public class MovieList extends AppCompatActivity {



    List<String> movies = new ArrayList<String>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ImageArrayAdapter adapter = new ImageArrayAdapter(this, movies);
        GridView gridView;

        new GetMovies().execute();
        gridView = (GridView) findViewById(R.id.movie_grid);
        gridView.setAdapter(adapter);

    }



    public class GetMovies extends AsyncTask<Void, Void, Void>{

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJson;

        URL url;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                //set url and connect to it
                url = new URL("http://api.themoviedb.org/3/search/movie?query=batman&api_key=0359c81bed7cce4e13cd5a744ea5cfbe");
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

                    for (int i = 0; i < films.length(); i++){
                        JSONObject f = films.getJSONObject(i);
                        String posterUrl = "http://image.tmdb.org/t/p/w50" + f.getString("poster_path");
                        movies.add(posterUrl);
                        System.out.println(posterUrl);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(movies);

            //adapter.addAll(movies);
            //adapter.notifyDataSetChanged();
        }
    }


}
