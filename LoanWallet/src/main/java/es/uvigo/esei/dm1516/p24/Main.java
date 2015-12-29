package es.uvigo.esei.dm1516.p24;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
                Intent intent = new Intent(Main.this, PrestamosLibros.class);
                Main.this.startActivity(intent);
            }
        });

        btnPeliculas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, PrestamosPeliculas.class);
                Main.this.startActivity(intent);
            }
        });

    }

    /** La aplicación no necesita un control del ciclo de vida muy exhaustivo, ya que todos los datos se almacenan
     *  en la base de datos en el mismo instante de crearse, modificarse o eleiminarse. Es posible implementar los metodos
     *  onPause() y onResume() en cada una de las actividaes de la aplicacón para visualizar un Toast, por ejemplo, pero
     *  es demasiado engorroso visualmente. Por lo tanto solo realiza algunha acción, visualizar un Toast, en el momento
     *  en el que se destruye la aplicación (onDestroy()). Este método se activa con la pulsación del botón "Atrás" en el
     *  dispositivo.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this.getApplicationContext(), "Aplicación finalizada. Datos guardados.", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.mainSubMenuLibro:
                    Intent intent1 = new Intent(Main.this,AddPrestamoLibro.class);
                    Main.this.startActivity(intent1);
                break;
            case R.id.mainSubMenuPelicula:
                    Intent intent2 = new Intent(Main.this,AddPrestamoPelicula.class);
                    Main.this.startActivity(intent2);
                break;
            case R.id.mainMenuItemBorrarDatos:
                    LoanWalletSQL sqlDB = new LoanWalletSQL(this);
                    SQLiteDatabase db = sqlDB.getReadableDatabase();
                    if(db != null) {
                        db.beginTransaction();
                        try {
                            db.execSQL("DROP TABLE IF EXISTS libros");
                            db.execSQL("DROP TABLE IF EXISTS peliculas");
                            db.setTransactionSuccessful();
                        } finally {
                            db.endTransaction();
                            Toast.makeText(this.getApplicationContext(), "Datos Borrados", Toast.LENGTH_LONG).show();
                        }
                    }
                break;
        }
        return true;
    }
}
