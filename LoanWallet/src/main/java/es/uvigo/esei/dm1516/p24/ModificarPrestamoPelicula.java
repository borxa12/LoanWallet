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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Created by Borxa on 28/12/2015.
 */
public class ModificarPrestamoPelicula extends Activity {

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
        setContentView(R.layout.modificar_prestamo_pelicula);

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

        final Pelicula pelicula = ((App) this.getApplication()).getPeliculas().get((int) this.getIntent().getExtras().get("posicion"));

        this.labelTitulo.setText(pelicula.getTitulo());
        this.labelDirector.setText(pelicula.getDirector());
        this.labelGenero.setText(pelicula.getGenero());
        this.labelAno.setText(String.valueOf(pelicula.getAno()));
        this.labelDuracion.setText(String.valueOf(pelicula.getDuracion()));
        this.labelFinPrestamo.setText(pelicula.getFinPrestamo());
        this.labelLugarPrestamo.setText(pelicula.getLugarPrestamo());

        Button btnModificarPelicula = (Button) this.findViewById(R.id.btnModificarPelicula);
        btnModificarPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.comprobacionCampos(campos)) {
                    ModificarPrestamoPelicula.this.modificarPrestamo(pelicula);
                }
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
                        ModificarPrestamoPelicula.this.labelFinPrestamo.setText(calendar.getYear() + "-" + (calendar.getMonth()+ 1) + "-" + calendar.getDayOfMonth());
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

    private void modificarPrestamo(Pelicula pelicula) {
        LoanWalletSQL sqlDB = new LoanWalletSQL(this);
        SQLiteDatabase db = sqlDB.getWritableDatabase();

        if(db != null) {
            try {
                db.beginTransaction();
                db.execSQL("UPDATE peliculas SET titulo=?, director=?, ano=?, duracion=?, genero=?, finPrestamo=?, lugarPrestamo=? WHERE titulo=?;",
                        new String[]{this.labelTitulo.getText().toString(), this.labelDirector.getText().toString(),
                                this.labelAno.getText().toString(), this.labelDuracion.getText().toString(),
                                this.labelGenero.getText().toString(), this.labelFinPrestamo.getText().toString(),
                                this.labelLugarPrestamo.getText().toString(), pelicula.getTitulo().toString()});
                db.setTransactionSuccessful();
                ((App) this.getApplication()).getPeliculas().get((int) this.getIntent().getExtras().get("posicion")).setTitulo(this.labelTitulo.getText().toString());
                ((App) this.getApplication()).getPeliculas().get((int) this.getIntent().getExtras().get("posicion")).setDirector(this.labelDirector.getText().toString());
                ((App) this.getApplication()).getPeliculas().get((int) this.getIntent().getExtras().get("posicion")).setAno(Integer.parseInt(this.labelAno.getText().toString()));
                ((App) this.getApplication()).getPeliculas().get((int) this.getIntent().getExtras().get("posicion")).setDuracion(Integer.parseInt(this.labelDuracion.getText().toString()));
                ((App) this.getApplication()).getPeliculas().get((int) this.getIntent().getExtras().get("posicion")).setGenero(this.labelGenero.getText().toString());
                ((App) this.getApplication()).getPeliculas().get((int) this.getIntent().getExtras().get("posicion")).setFinPrestamo(this.labelFinPrestamo.getText().toString());
                ((App) this.getApplication()).getPeliculas().get((int) this.getIntent().getExtras().get("posicion")).setLugarPrestamo(this.labelLugarPrestamo.getText().toString());
                ((App) this.getApplication()).getPeliculasAdapter().notifyDataSetChanged();
            } finally {
                db.endTransaction();
            }
        }

        this.finish();
    }

}
