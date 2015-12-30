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

/**
 * Created by Borxa on 28/12/2015.
 */
public class ModificarPrestamoLibro extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_prestamo_libro);

        EditText labelISBN = (EditText) this.findViewById(R.id.labelISBN);
        EditText labelTitulo = (EditText) this.findViewById(R.id.labelTitulo);
        EditText labelAutores = (EditText) this.findViewById(R.id.labelAutores);
        EditText labelAno = (EditText) this.findViewById(R.id.labelAno);
        EditText labelEditorial = (EditText) this.findViewById(R.id.labelEditorial);
        final EditText labelFinPrestamo = (EditText) this.findViewById(R.id.labelFinPrestamo);
        EditText labelLugarPrestamo = (EditText) this.findViewById(R.id.labelLugarPrestamo);

        final Libro libro = ((App) this.getApplication()).getLibros().get((int) this.getIntent().getExtras().get("posicion"));

        labelISBN.setText(libro.getISBN());
        labelTitulo.setText(libro.getTitulo());
        labelAutores.setText(libro.getAutores());
        labelAno.setText(String.valueOf(libro.getAno()));
        labelEditorial.setText(libro.getEditorial());
        labelFinPrestamo.setText(libro.getFinPrestamo());
        labelLugarPrestamo.setText(libro.getLugarPrestamo());

        Button btnModificarLibro = (Button) this.findViewById(R.id.btnModificarLibro);

        btnModificarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModificarPrestamoLibro.this.modificarPrestamo(libro);
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

    public void modificarPrestamo(Libro libro) {
        EditText labelISBN = (EditText) this.findViewById(R.id.labelISBN);
        EditText labelTitulo = (EditText) this.findViewById(R.id.labelTitulo);
        EditText labelAutores = (EditText) this.findViewById(R.id.labelAutores);
        EditText labelAno = (EditText) this.findViewById(R.id.labelAno);
        EditText labelEditorial = (EditText) this.findViewById(R.id.labelEditorial);
        EditText labelFinPrestamo = (EditText) this.findViewById(R.id.labelFinPrestamo);
        EditText labelLugarPrestamo = (EditText) this.findViewById(R.id.labelLugarPrestamo);

        LoanWalletSQL sqlDB = new LoanWalletSQL(this);
        SQLiteDatabase db = sqlDB.getWritableDatabase();

        if(db != null) {
            try {
                db.beginTransaction();
                db.execSQL("UPDATE libros SET ISBN=?, titulo=?, autores=?, ano=?, editorial=?, finPrestamo=?, lugarPrestamo=? WHERE ISBN=?;",
                        new String[]{labelISBN.getText().toString(), labelTitulo.getText().toString(),
                                labelAutores.getText().toString(), labelAno.getText().toString(), labelEditorial.getText().toString(),
                                labelFinPrestamo.getText().toString(), labelLugarPrestamo.getText().toString(),
                                libro.getISBN().toString()});
                db.setTransactionSuccessful();
                ((App) this.getApplication()).getLibros().get((int) this.getIntent().getExtras().get("posicion")).setISBN(labelISBN.getText().toString());
                ((App) this.getApplication()).getLibros().get((int) this.getIntent().getExtras().get("posicion")).setTitulo(labelTitulo.getText().toString());
                ((App) this.getApplication()).getLibros().get((int) this.getIntent().getExtras().get("posicion")).setAutores(labelAutores.getText().toString());
                ((App) this.getApplication()).getLibros().get((int) this.getIntent().getExtras().get("posicion")).setAno(Integer.parseInt(labelAno.getText().toString()));
                ((App) this.getApplication()).getLibros().get((int) this.getIntent().getExtras().get("posicion")).setEditorial(labelEditorial.getText().toString());
                ((App) this.getApplication()).getLibros().get((int) this.getIntent().getExtras().get("posicion")).setFinPrestamo(labelFinPrestamo.getText().toString());
                ((App) this.getApplication()).getLibros().get((int) this.getIntent().getExtras().get("posicion")).setLugarPrestamo(labelLugarPrestamo.getText().toString());
                ((App) this.getApplication()).getLibrosAdapter().notifyDataSetChanged();
            } finally {
                db.endTransaction();
            }
        }

        this.finish();
    }

}
