package es.uvigo.esei.dm1516.p24;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class PrestamosLibros extends Activity {

    public ArrayAdapter<Libro> itemsAdapter;
    public ArrayList<Libro> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prestamos_libros);

        this.items = ((App) this.getApplication()).getLibros();
        this.itemsAdapter = ((App) this.getApplication()).getLibrosAdapter();

        this.items.clear();
        this.itemsAdapter.notifyDataSetChanged();

        PrestamosLibros.this.actualizarLista();

        Button btnAdd = (Button) this.findViewById(R.id.AddLoan);
        ListView lvBooks = (ListView) this.findViewById(R.id.listView);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrestamosLibros.this,AddPrestamoLibro.class);
                PrestamosLibros.this.startActivity(intent);
            }
        });

        lvBooks.setLongClickable(true);
        lvBooks.setAdapter(this.itemsAdapter);
        lvBooks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos >= 0) {
                    PrestamosLibros.this.opciones(pos);
                }
                return false;
            }
        });
    }

    private void actualizarLista() {
        LoanWalletSQL sqlDB = new LoanWalletSQL(this);
        SQLiteDatabase db = sqlDB.getReadableDatabase();

        if(db != null) {
            Cursor librosCursor = db.rawQuery("SELECT * FROM libros", null);
            if(librosCursor.moveToFirst()) {
                do {
                    Libro libro = new Libro(librosCursor.getString(0), librosCursor.getString(1), librosCursor.getString(2),
                            librosCursor.getInt(3), librosCursor.getString(4), librosCursor.getInt(5), librosCursor.getString(6),
                            librosCursor.getString(7), librosCursor.getString(8));
                    PrestamosLibros.this.items.add(libro);
                } while(librosCursor.moveToNext());
            }
        } else {
            Toast.makeText(this.getApplicationContext(),"No se ha podida actualizar la lista de préstamos", Toast.LENGTH_LONG).show();
        }
    }

    private void opciones(final int pos) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Opciones");
        alert.setItems(new String[]{"Renovar", "Modificar", "Eliminar"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    PrestamosLibros.this.renovar(pos);
                } else if (which == 1) {
                    PrestamosLibros.this.modificar(pos);
                } else if (which == 2) {
                    PrestamosLibros.this.eliminar(pos);
                }
            }
        });
        alert.create().show();
    }

    private void renovar(final int pos) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                StringBuilder fecha = new StringBuilder();
                fecha.append(String.valueOf(year)).append("-").append(String.valueOf(month + 1)).append("-").append(String.valueOf(day));
                PrestamosLibros.this.renovacion(pos,fecha.toString());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void renovacion(int pos, String fecha) {
        final Libro libro = PrestamosLibros.this.items.get(pos);
        int renovaciones = libro.getRenovaciones() + 1;

        LoanWalletSQL sqlDB = new LoanWalletSQL(this);
        SQLiteDatabase db = sqlDB.getWritableDatabase();

        if(db != null) {
            try {
                db.beginTransaction();
                db.execSQL("UPDATE libros SET renovaciones = ?, finPrestamo = ? WHERE ISBN = ?;",
                        new String[]{String.valueOf(renovaciones), fecha, libro.getISBN()});
                db.setTransactionSuccessful();
                libro.setFinPrestamo(fecha.toString());
                libro.setRenovaciones(renovaciones);
                PrestamosLibros.this.itemsAdapter.notifyDataSetChanged();
            } finally {
                db.endTransaction();
            }
        } else {
            Toast.makeText(this.getApplicationContext(),"No se ha podido renovar el libro", Toast.LENGTH_LONG).show();
        }
    }

    private void modificar(int pos) {
        Intent intent = new Intent(PrestamosLibros.this,ModificarPrestamoLibro.class);
        intent.putExtra("posicion",pos);
        PrestamosLibros.this.startActivity(intent);
    }

    private void eliminar(final int pos) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Eliminar Préstamo");
        alert.setMessage("¿Estás seguro?");
        alert.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PrestamosLibros.this.eliminacion(pos);
                PrestamosLibros.this.itemsAdapter.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton("Cancelar", null);
        alert.create().show();
    }

    private void eliminacion(int pos) {
        final Libro libro = PrestamosLibros.this.items.get(pos);

        LoanWalletSQL sqlDB = new LoanWalletSQL(this);
        SQLiteDatabase db = sqlDB.getWritableDatabase();

        if(db != null) {
            try {
                db.beginTransaction();
                db.execSQL("DELETE FROM libros WHERE ISBN = ?;",
                        new String[]{libro.getISBN()});
                db.setTransactionSuccessful();
                PrestamosLibros.this.items.remove(pos);
                PrestamosLibros.this.itemsAdapter.notifyDataSetChanged();
            } finally {
                db.endTransaction();
            }
        } else {
            Toast.makeText(this.getApplicationContext(),"No se ha podido eliminar el libro", Toast.LENGTH_LONG).show();
        }
    }

}