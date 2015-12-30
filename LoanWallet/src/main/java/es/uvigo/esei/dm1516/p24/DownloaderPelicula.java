package es.uvigo.esei.dm1516.p24;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Borxa on 29/12/2015.
 */
public class DownloaderPelicula extends AsyncTask<URL,Void,Boolean> {

    private AddPrestamoPelicula activity;
    private String titulo;
    private String director;
    private String genero;
    private String ano;
    private String duracion;

    public DownloaderPelicula(AddPrestamoPelicula activity) {
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(URL... params) {
        try {
            HttpURLConnection conn = (HttpURLConnection) params[0].openConnection();
            conn.connect();
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            byte[] contents = new byte[1024];
            int bytesRead = 0;
            String info = null;
            while( (bytesRead = in.read(contents)) != -1){
                info = new String(contents,0,bytesRead);
            }
            JSONObject json = new JSONObject(info);
            this.titulo = json.getString("Title");
            this.director = json.getString("Director");
            this.genero = json.getString("Genre");
            this.ano = json.getString("Year");
            String duracion = json.getString("Runtime");
            this.duracion = duracion.substring(0,3).trim();
            return true;
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        EditText labelTitulo = (EditText) this.activity.findViewById(R.id.labelTituloPelicula);
        EditText labelDirector = (EditText) this.activity.findViewById(R.id.labelDirectorPelicula);
        EditText labelGenero = (EditText) this.activity.findViewById(R.id.labelGeneroPelicula);
        EditText labelAno = (EditText) this.activity.findViewById(R.id.labelAnoPelicula);
        EditText labelDuracion = (EditText) this.activity.findViewById(R.id.labelDuracionPelicula);

        labelTitulo.setText(this.titulo);
        labelDirector.setText(this.director);
        labelGenero.setText(this.genero);
        labelAno.setText(this.ano);
        labelDuracion.setText(this.duracion);
    }
}
