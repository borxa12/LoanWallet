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

/**
 * Created by Borxa on 28/12/2015.
 */
public class ModificarPrestamoPelicula extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_prestamo_pelicula);

        AutoCompleteTextView labelTitulo = (AutoCompleteTextView) this.findViewById(R.id.labelTituloPelicula);
        AutoCompleteTextView labelDirector = (AutoCompleteTextView) this.findViewById(R.id.labelDirectorPelicula);
        AutoCompleteTextView labelGenero = (AutoCompleteTextView) this.findViewById(R.id.labelGeneroPelicula);
        EditText labelAno = (EditText) this.findViewById(R.id.labelAnoPelicula);
        EditText labelDuracion = (EditText) this.findViewById(R.id.labelDuracionPelicula);
        final EditText labelFinPrestamo = (EditText) this.findViewById(R.id.labelFinPrestamoPelicula);
        EditText labelLugarPrestamo = (EditText) this.findViewById(R.id.labelLugarPrestamoPelicula);

        final Pelicula pelicula = ((App) this.getApplication()).getPeliculas().get((int) this.getIntent().getExtras().get("posicion"));

        labelTitulo.setText(pelicula.getTitulo());
        labelDirector.setText(pelicula.getDirector());
        labelGenero.setText(pelicula.getGenero());
        labelAno.setText(String.valueOf(pelicula.getAno()));
        labelDuracion.setText(String.valueOf(pelicula.getDuracion()));
        labelFinPrestamo.setText(pelicula.getFinPrestamo());
        labelLugarPrestamo.setText(pelicula.getLugarPrestamo());

        Button btnModificarPelicula = (Button) this.findViewById(R.id.btnModificarPelicula);

        btnModificarPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModificarPrestamoPelicula.this.modificarPrestamo(pelicula);
            }
        });

        ImageButton btnCalendar = (ImageButton) this.findViewById(R.id.calendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = ModificarPrestamoPelicula.this.getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.calendar, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(ModificarPrestamoPelicula.this);
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
        Log.i("onPause()", "Llamado el método onPause() de la clase ModificarPrestamoPelicula");
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

    public void modificarPrestamo(Pelicula pelicula) {
        AutoCompleteTextView labelTitulo = (AutoCompleteTextView) this.findViewById(R.id.labelTituloPelicula);
        AutoCompleteTextView labelDirector = (AutoCompleteTextView) this.findViewById(R.id.labelDirectorPelicula);
        AutoCompleteTextView labelGenero = (AutoCompleteTextView) this.findViewById(R.id.labelGeneroPelicula);
        EditText labelAno = (EditText) this.findViewById(R.id.labelAnoPelicula);
        EditText labelDuracion = (EditText) this.findViewById(R.id.labelDuracionPelicula);
        final EditText labelFinPrestamo = (EditText) this.findViewById(R.id.labelFinPrestamoPelicula);
        EditText labelLugarPrestamo = (EditText) this.findViewById(R.id.labelLugarPrestamoPelicula);

        LoanWalletSQL sqlDB = new LoanWalletSQL(this);
        SQLiteDatabase db = sqlDB.getWritableDatabase();

        if(db != null) {
            try {
                db.beginTransaction();
                db.execSQL("UPDATE peliculas SET titulo=?, director=?, ano=?, duracion=?, genero=?, finPrestamo=?, lugarPrestamo=? WHERE titulo=?;",
                        new String[]{labelTitulo.getText().toString(), labelDirector.getText().toString(),
                                labelAno.getText().toString(), labelDuracion.getText().toString(), labelGenero.getText().toString(),
                                labelFinPrestamo.getText().toString(), labelLugarPrestamo.getText().toString(),
                                pelicula.getTitulo().toString()});
                db.setTransactionSuccessful();
                ((App) this.getApplication()).getPeliculas().get((int) this.getIntent().getExtras().get("posicion")).setTitulo(labelTitulo.getText().toString());
                ((App) this.getApplication()).getPeliculas().get((int) this.getIntent().getExtras().get("posicion")).setDirector(labelDirector.getText().toString());
                ((App) this.getApplication()).getPeliculas().get((int) this.getIntent().getExtras().get("posicion")).setAno(Integer.parseInt(labelAno.getText().toString()));
                ((App) this.getApplication()).getPeliculas().get((int) this.getIntent().getExtras().get("posicion")).setDuracion(Integer.parseInt(labelDuracion.getText().toString()));
                ((App) this.getApplication()).getPeliculas().get((int) this.getIntent().getExtras().get("posicion")).setGenero(labelGenero.getText().toString());
                ((App) this.getApplication()).getPeliculas().get((int) this.getIntent().getExtras().get("posicion")).setFinPrestamo(labelFinPrestamo.getText().toString());
                ((App) this.getApplication()).getPeliculas().get((int) this.getIntent().getExtras().get("posicion")).setLugarPrestamo(labelLugarPrestamo.getText().toString());
                ((App) this.getApplication()).getPeliculasAdapter().notifyDataSetChanged();
            } finally {
                db.endTransaction();
            }
        }

        this.finish();
    }

}
