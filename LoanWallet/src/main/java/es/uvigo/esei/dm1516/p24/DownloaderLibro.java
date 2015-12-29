package es.uvigo.esei.dm1516.p24;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Borxa on 29/12/2015.
 */
public class DownloaderLibro extends AsyncTask<URL,Void,String> {

    private AddPrestamoLibro activity;
    private String ISBN;
    private String titulo;
    private String autores;
    private int ano;
    private String editorial;

    public DownloaderLibro(AddPrestamoLibro activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(URL... params) {
        try {
            HttpURLConnection conn = (HttpURLConnection) params[0].openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            JSONObject json = new JSONObject(is.toString());
            this.ISBN = json.getString("id");
            AutoCompleteTextView labelAutores = (AutoCompleteTextView) this.findViewById(R.id.labelAutores);
            labelAutores.set
        } catch (Exception networkError) {
            return networkError.getMessage();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String titulo) {
        super.onPostExecute(titulo);
    }
}
