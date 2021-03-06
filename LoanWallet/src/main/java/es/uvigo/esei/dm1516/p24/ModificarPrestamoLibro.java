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
public class ModificarPrestamoLibro extends Activity {

    private EditText labelISBN;
    private EditText labelTitulo;
    private EditText labelAutores;
    private EditText labelAno;
    private EditText labelEditorial;
    private EditText labelFinPrestamo;
    private EditText labelLugarPrestamo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_prestamo_libro);

        this.labelISBN = (EditText) this.findViewById(R.id.labelISBN);
        this.labelTitulo = (EditText) this.findViewById(R.id.labelTitulo);
        this.labelAutores = (EditText) this.findViewById(R.id.labelAutores);
        this.labelAno = (EditText) this.findViewById(R.id.labelAno);
        this.labelEditorial = (EditText) this.findViewById(R.id.labelEditorial);
        this.labelFinPrestamo = (EditText) this.findViewById(R.id.labelFinPrestamo);
        this.labelLugarPrestamo = (EditText) this.findViewById(R.id.labelLugarPrestamo);

        final Libro libro = ((App) this.getApplication()).getLibros().get((int) this.getIntent().getExtras().get("posicion"));

        this.labelISBN.setText(libro.getISBN());
        this.labelTitulo.setText(libro.getTitulo());
        this.labelAutores.setText(libro.getAutores());
        this.labelAno.setText(String.valueOf(libro.getAno()));
        this.labelEditorial.setText(libro.getEditorial());
        this.labelFinPrestamo.setText(libro.getFinPrestamo());
        this.labelLugarPrestamo.setText(libro.getLugarPrestamo());

        final ArrayList<EditText> campos = new ArrayList<>();
        campos.add(labelISBN);
        campos.add(labelTitulo);
        campos.add(labelAutores);
        campos.add(labelAno);
        campos.add(labelEditorial);
        campos.add(labelFinPrestamo);
        campos.add(labelLugarPrestamo);

        Button btnModificarLibro = (Button) this.findViewById(R.id.btnModificarLibro);
        btnModificarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.comprobacionCampos(campos)) {
                    ModificarPrestamoLibro.this.modificarPrestamo(libro);
                }
            }
        });

        ImageButton btnCalendar = (ImageButton) this.findViewById(R.id.calendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = ModificarPrestamoLibro.this.getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.calendar, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(ModificarPrestamoLibro.this);
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
        Log.i("onPause()", "Llamado el método onPause() de la clase ModificarPrestamoLibro");
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

    private void modificarPrestamo(Libro libro) {
        LoanWalletSQL sqlDB = new LoanWalletSQL(this);
        SQLiteDatabase db = sqlDB.getWritableDatabase();

        if(db != null) {
            try {
                db.beginTransaction();
                db.execSQL("UPDATE libros SET ISBN=?, titulo=?, autores=?, ano=?, editorial=?, finPrestamo=?, lugarPrestamo=? WHERE ISBN=?;",
                        new String[]{this.labelISBN.getText().toString(), this.labelTitulo.getText().toString(),
                                this.labelAutores.getText().toString(), this.labelAno.getText().toString(),
                                this.labelEditorial.getText().toString(), this.labelFinPrestamo.getText().toString(),
                                this.labelLugarPrestamo.getText().toString(), libro.getISBN().toString()});
                db.setTransactionSuccessful();
                ((App) this.getApplication()).getLibros().get((int) this.getIntent().getExtras().get("posicion")).setISBN(this.labelISBN.getText().toString());
                ((App) this.getApplication()).getLibros().get((int) this.getIntent().getExtras().get("posicion")).setTitulo(this.labelTitulo.getText().toString());
                ((App) this.getApplication()).getLibros().get((int) this.getIntent().getExtras().get("posicion")).setAutores(this.labelAutores.getText().toString());
                ((App) this.getApplication()).getLibros().get((int) this.getIntent().getExtras().get("posicion")).setAno(Integer.parseInt(this.labelAno.getText().toString()));
                ((App) this.getApplication()).getLibros().get((int) this.getIntent().getExtras().get("posicion")).setEditorial(this.labelEditorial.getText().toString());
                ((App) this.getApplication()).getLibros().get((int) this.getIntent().getExtras().get("posicion")).setFinPrestamo(this.labelFinPrestamo.getText().toString());
                ((App) this.getApplication()).getLibros().get((int) this.getIntent().getExtras().get("posicion")).setLugarPrestamo(this.labelLugarPrestamo.getText().toString());
                ((App) this.getApplication()).getLibrosAdapter().notifyDataSetChanged();
            } finally {
                db.endTransaction();
            }
        }

        this.finish();
    }

}
