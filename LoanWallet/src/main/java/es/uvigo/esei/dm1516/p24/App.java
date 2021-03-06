package es.uvigo.esei.dm1516.p24;

import android.app.Application;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Borxa on 28/12/2015.
 */
public class App extends Application {

    private LoanWalletSQL db;

    private ArrayAdapter<Libro> librosAdapter;
    private ArrayList<Libro> libros;

    public ArrayAdapter<Pelicula> peliculasAdapter;
    public ArrayList<Pelicula> peliculas;

    @Override
    public void onCreate() {
        super.onCreate();

        this.db = new LoanWalletSQL(this.getApplicationContext());

        this.libros = new ArrayList<>();
        this.librosAdapter = new ArrayAdapter<Libro>(
                this.getApplicationContext(),
                android.R.layout.simple_selectable_list_item,
                this.libros
        );

        this.peliculas = new ArrayList<>();
        this.peliculasAdapter = new ArrayAdapter<Pelicula>(
                this.getApplicationContext(),
                android.R.layout.simple_selectable_list_item,
                this.peliculas

        );
    }

    public LoanWalletSQL getDb() {
        return db;
    }

    public ArrayAdapter<Libro> getLibrosAdapter() {
        return librosAdapter;
    }

    public ArrayList<Libro> getLibros() {
        return libros;
    }

    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public ArrayAdapter<Pelicula> getPeliculasAdapter() {
        return peliculasAdapter;
    }

    public static boolean comprobacionCampos(ArrayList<EditText> campos) {
        boolean toret = true;
        for(int i = 0; i < campos.size(); i++) {
            if(TextUtils.isEmpty(campos.get(i).getText())) {
                campos.get(i).setError("El campo no puede ser vacío");
                toret = false;
            }
        }
        return toret;
    }

}
