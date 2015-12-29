package es.uvigo.esei.dm1516.p24;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Borxa on 29/12/2015.
 */
// Documentation API Books (Google Books): https://developers.google.com/books/docs/v1/using
// API Books: https://www.googleapis.com/books/v1/volumes?q=manual+de+ciencia+politica
// API Movies: http://www.omdbapi.com/?t=frozen&y=&plot=short&r=json
// Documentation API Music (Spotify): https://developer.spotify.com/web-api/search-item/

public class Main extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btnLibros = (Button) this.findViewById(R.id.btnLibros);
        Button btnPeliculas = (Button) this.findViewById(R.id.btnPeliculas);

        btnLibros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this,PrestamosLibros.class);
                Main.this.startActivity(intent);
            }
        });

        btnPeliculas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this,PrestamosPeliculas.class);
                Main.this.startActivity(intent);
            }
        });

    }

}
