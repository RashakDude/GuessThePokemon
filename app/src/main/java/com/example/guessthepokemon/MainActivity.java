package com.example.guessthepokemon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;
            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStream.read();
                while(data!=-1){
                    result += (char) data;
                    data = inputStreamReader.read();
                }
                return result;
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result = null;
        try {
            result = task.execute("https://db.pokemongohub.net/pokemon-list/gen-1").get();

        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}