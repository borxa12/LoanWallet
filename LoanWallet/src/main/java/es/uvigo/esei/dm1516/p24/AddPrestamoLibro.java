package es.uvigo.esei.dm1516.p24;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Borxa on 17/11/2015.
 */
public class AddPrestamoLibro extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_prestamo_libro);

        Button btnAddLoan = (Button) this.findViewById(R.id.btnAddLibro);

        btnAddLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPrestamoLibro.this.addLoan();
            }
        });

        ImageButton btnCalendar = (ImageButton) this.findViewById(R.id.calendar);
        final EditText labelFinPrestamo = (EditText) this.findViewById(R.id.labelFinPrestamo);
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
                        labelFinPrestamo.setText(calendar.getYear() + "-" + (calendar.getMonth()+ 1) + "-" + calendar.getDayOfMonth());
                    }
                });
                alert.setNegativeButton("Cancelar",null);
                alert.create().show();
            }
        });
    }

    public void addLoan() {
        EditText labelISBN = (EditText) this.findViewById(R.id.labelISBN);
        AutoCompleteTextView labelTitulo = (AutoCompleteTextView) this.findViewById(R.id.labelTitulo);
        AutoCompleteTextView labelAutores = (AutoCompleteTextView) this.findViewById(R.id.labelAutores);
        EditText labelAno = (EditText) this.findViewById(R.id.labelAno);
        EditText labelEditorial = (EditText) this.findViewById(R.id.labelEditorial);
        EditText labelFinPrestamo = (EditText) this.findViewById(R.id.labelFinPrestamo);
        EditText labelLugarPrestamo = (EditText) this.findViewById(R.id.labelLugarPrestamo);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(new Date());

        LoanWalletSQL sqlDB = new LoanWalletSQL(this);
        SQLiteDatabase db = sqlDB.getWritableDatabase();

        if(db != null) {
            try {
                db.beginTransaction();
                db.execSQL("INSERT OR IGNORE INTO libros(ISBN,titulo,autores,ano,editorial,renovaciones,fechaPrestamo,finPrestamo,lugarPrestamo)" +
                        "VALUES(?,?,?,?,?,0,?,?,?)", new String[]{labelISBN.getText().toString(), labelTitulo.getText().toString(),
                        labelAutores.getText().toString(), labelAno.getText().toString(), labelEditorial.getText().toString(),
                        fecha, labelFinPrestamo.getText().toString(), labelLugarPrestamo.getText().toString()});
                db.setTransactionSuccessful();
                Libro libro = new Libro(labelISBN.getText().toString(), labelTitulo.getText().toString(),
                        labelAutores.getText().toString(), Integer.parseInt(labelAno.getText().toString()),
                        labelEditorial.getText().toString(),0,fecha,
                        labelFinPrestamo.getText().toString(), labelLugarPrestamo.getText().toString());
                ((App) this.getApplication()).getLibrosAdapter().add(libro);
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
