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
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Borxa on 17/11/2015.
 */
public class AddPrestamoLibro extends Activity {

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
        setContentView(R.layout.nuevo_prestamo_libro);

        this.labelISBN = (EditText) this.findViewById(R.id.labelISBN);
        this.labelTitulo = (EditText) this.findViewById(R.id.labelTitulo);
        this.labelAutores = (EditText) this.findViewById(R.id.labelAutores);
        this.labelAno = (EditText) this.findViewById(R.id.labelAno);
        this.labelEditorial = (EditText) this.findViewById(R.id.labelEditorial);
        this.labelFinPrestamo = (EditText) this.findViewById(R.id.labelFinPrestamo);
        this.labelLugarPrestamo = (EditText) this.findViewById(R.id.labelLugarPrestamo);

        final ArrayList<EditText> campos = new ArrayList<>();
        campos.add(labelISBN);
        campos.add(labelTitulo);
        campos.add(labelAutores);
        campos.add(labelAno);
        campos.add(labelEditorial);
        campos.add(labelFinPrestamo);
        campos.add(labelLugarPrestamo);


        Button btnAddLoan = (Button) this.findViewById(R.id.btnAddLibro);
        btnAddLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.comprobacionCampos(campos)) {
                    AddPrestamoLibro.this.addLoan();
                }
            }
        });

        ImageButton btnCalendar = (ImageButton) this.findViewById(R.id.calendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = AddPrestamoLibro.this.getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.calendar, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(AddPrestamoLibro.this);
                alert.setView(dialoglayout);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatePicker calendar = (DatePicker) dialoglayout.findViewById(R.id.datePicker);
                        labelFinPrestamo.setText(calendar.getYear() + "-" + (calendar.getMonth() + 1) + "-" + calendar.getDayOfMonth());
                    }
                });
                alert.setNegativeButton("Cancelar", null);
                        alert.create().show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("onPause()", "Llamado el método onPause() de la clase AddPrestamoLibro");
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
                db.execSQL("INSERT OR IGNORE INTO libros(ISBN,titulo,autores,ano,editorial,renovaciones,fechaPrestamo,finPrestamo,lugarPrestamo)" +
                        "VALUES(?,?,?,?,?,0,?,?,?)", new String[]{this.labelISBN.getText().toString(), this.labelTitulo.getText().toString(),
                        this.labelAutores.getText().toString(), this.labelAno.getText().toString(), this.labelEditorial.getText().toString(),
                        fecha, this.labelFinPrestamo.getText().toString(), this.labelLugarPrestamo.getText().toString()});
                db.setTransactionSuccessful();
                Libro libro = new Libro(this.labelISBN.getText().toString(), this.labelTitulo.getText().toString(),
                        this.labelAutores.getText().toString(), Integer.parseInt(this.labelAno.getText().toString()),
                        this.labelEditorial.getText().toString(),0,fecha,
                        this.labelFinPrestamo.getText().toString(), this.labelLugarPrestamo.getText().toString());
                ((App) this.getApplication()).getLibrosAdapter().add(libro);
                Toast.makeText(this.getApplicationContext(),"El préstamo se ha insertado satisfactoriamente", Toast.LENGTH_LONG).show();
            } finally {
                db.endTransaction();
            }
        } else {
            if(db == null) {
                Toast.makeText(this.getApplicationContext(),"No se ha podida insetar el préstamo", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this.getApplicationContext(), "Hay campos vacíos", Toast.LENGTH_LONG).show();
            }
        }

        this.finish();
    }

}
