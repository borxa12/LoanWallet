package es.uvigo.esei.dm1516.p24;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Borxa on 17/11/2015.
 */
public class AddPrestamoPelicula extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_prestamo_pelicula);

        Button btnAddPelicula = (Button) this.findViewById(R.id.btnAddPelicula);

        btnAddPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPrestamoPelicula.this.addLoan();
            }
        });

        ImageButton btnCalendar = (ImageButton) this.findViewById(R.id.calendar);
        final EditText labelFinPrestamo = (EditText) this.findViewById(R.id.labelFinPrestamoPelicula);
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
                        labelFinPrestamo.setText(calendar.getYear() + "-" + (calendar.getMonth()+ 1) + "-" + calendar.getDayOfMonth());
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

    public void addLoan() {
        AutoCompleteTextView labelTitulo = (AutoCompleteTextView) this.findViewById(R.id.labelTituloPelicula);
        AutoCompleteTextView labelDirector = (AutoCompleteTextView) this.findViewById(R.id.labelDirectorPelicula);
        AutoCompleteTextView labelGenero = (AutoCompleteTextView) this.findViewById(R.id.labelGeneroPelicula);
        EditText labelAno = (EditText) this.findViewById(R.id.labelAnoPelicula);
        EditText labelDuracion = (EditText) this.findViewById(R.id.labelDuracionPelicula);
        final EditText labelFinPrestamo = (EditText) this.findViewById(R.id.labelFinPrestamoPelicula);
        EditText labelLugarPrestamo = (EditText) this.findViewById(R.id.labelLugarPrestamoPelicula);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(new Date());

        LoanWalletSQL sqlDB = new LoanWalletSQL(this);
        SQLiteDatabase db = sqlDB.getWritableDatabase();

        if(db != null) {
            try {
                db.beginTransaction();
                db.execSQL("INSERT OR IGNORE INTO peliculas(titulo,director,ano,duracion,genero,renovaciones,fechaPrestamo,finPrestamo,lugarPrestamo)" +
                        "VALUES(?,?,?,?,?,0,?,?,?)", new String[]{labelTitulo.getText().toString(), labelDirector.getText().toString(),
                        labelAno.getText().toString(), labelDuracion.getText().toString(), labelGenero.getText().toString(), fecha,
                        labelFinPrestamo.getText().toString(), labelLugarPrestamo.getText().toString()});
                db.setTransactionSuccessful();
                Pelicula pelicula = new Pelicula(labelTitulo.getText().toString(), labelDirector.getText().toString(),
                        Integer.parseInt(labelAno.getText().toString()), Integer.parseInt(labelDuracion.getText().toString()),
                        labelGenero.getText().toString(),0,fecha, labelFinPrestamo.getText().toString(), labelLugarPrestamo.getText().toString());
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
