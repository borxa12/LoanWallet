package es.uvigo.esei.dm1516.p24;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Borxa on 17/11/2015.
 */
public class AddPrestamoPelicula extends Activity {

    private EditText labelTitulo;
    private EditText labelDirector;
    private EditText labelGenero;
    private EditText labelAno;
    private EditText labelDuracion;
    private EditText labelFinPrestamo;
    private EditText labelLugarPrestamo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_prestamo_pelicula);

        this.labelTitulo = (EditText) this.findViewById(R.id.labelTituloPelicula);
        this.labelDirector = (EditText) this.findViewById(R.id.labelDirectorPelicula);
        this.labelGenero = (EditText) this.findViewById(R.id.labelGeneroPelicula);
        this.labelAno = (EditText) this.findViewById(R.id.labelAnoPelicula);
        this.labelDuracion = (EditText) this.findViewById(R.id.labelDuracionPelicula);
        this.labelFinPrestamo = (EditText) this.findViewById(R.id.labelFinPrestamoPelicula);
        this.labelLugarPrestamo = (EditText) this.findViewById(R.id.labelLugarPrestamoPelicula);

        final ArrayList<EditText> campos = new ArrayList<>();
        campos.add(labelTitulo);
        campos.add(labelDirector);
        campos.add(labelGenero);
        campos.add(labelAno);
        campos.add(labelDuracion);
        campos.add(labelFinPrestamo);
        campos.add(labelLugarPrestamo);

        Button btnActualizar = (Button) this.findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if(networkInfo != null && networkInfo.isConnected()) {
                        String busqueda = labelTitulo.getText().toString().replace(" ","+");
                        String url = "http://www.omdbapi.com/?t=" + busqueda + "&y=&plot=short&r=json";
                        Log.d("URL", url);
                        new DownloaderPelicula(AddPrestamoPelicula.this).execute(new URL(url));
                    } else {
                        Toast.makeText(AddPrestamoPelicula.this.getApplicationContext(),"No hay conexión a Internet", Toast.LENGTH_LONG).show();
                    }
                } catch (MalformedURLException e) {
                    Log.e("MalformedURLException", e.getMessage());
                }
            }
        });

        Button btnAddPelicula = (Button) this.findViewById(R.id.btnAddPelicula);
        btnAddPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.comprobacionCampos(campos)) {
                    AddPrestamoPelicula.this.addLoan();
                }
            }
        });

        ImageButton btnCalendar = (ImageButton) this.findViewById(R.id.calendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = AddPrestamoPelicula.this.getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.calendar, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(AddPrestamoPelicula.this);
                alert.setView(dialoglayout);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatePicker calendar = (DatePicker) dialoglayout.findViewById(R.id.datePicker);
                        AddPrestamoPelicula.this.labelFinPrestamo.setText(calendar.getYear() + "-" + (calendar.getMonth()+ 1) + "-" + calendar.getDayOfMonth());
                    }
                });
                alert.setNegativeButton("Cancelar",null);
                alert.create().show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("onPause()", "Llamado el método onPause() de la clase AddPrestamoPelicula");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.edicion_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.mainMenuAtras:
                this.finish();
                break;
        }
        return true;
    }

    private void addLoan() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(new Date());

        LoanWalletSQL sqlDB = new LoanWalletSQL(this);
        SQLiteDatabase db = sqlDB.getWritableDatabase();

        if(db != null) {
            try {
                db.beginTransaction();
                db.execSQL("INSERT OR IGNORE INTO peliculas(titulo,director,ano,duracion,genero,renovaciones,fechaPrestamo,finPrestamo,lugarPrestamo)" +
                        "VALUES(?,?,?,?,?,0,?,?,?)", new String[]{this.labelTitulo.getText().toString(), this.labelDirector.getText().toString(),
                        this.labelAno.getText().toString(), this.labelDuracion.getText().toString(), this.labelGenero.getText().toString(), fecha,
                        this.labelFinPrestamo.getText().toString(), this.labelLugarPrestamo.getText().toString()});
                db.setTransactionSuccessful();
                Pelicula pelicula = new Pelicula(this.labelTitulo.getText().toString(), this.labelDirector.getText().toString(),
                        Integer.parseInt(this.labelAno.getText().toString()), Integer.parseInt(this.labelDuracion.getText().toString()),
                        this.labelGenero.getText().toString(),0,fecha, this.labelFinPrestamo.getText().toString(),
                        this.labelLugarPrestamo.getText().toString());
                ((App) this.getApplication()).getPeliculasAdapter().add(pelicula);
                Toast.makeText(this.getApplicationContext(),"El préstamo se ha insertado satisfactoriamente", Toast.LENGTH_LONG).show();
            } finally {
                db.endTransaction();
            }
        } else {
            Toast.makeText(this.getApplicationContext(),"No se ha podida insetar el préstamo", Toast.LENGTH_LONG).show();
        }

        this.finish();
    }

}
