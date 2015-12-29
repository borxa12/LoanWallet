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

public class PrestamosPeliculas extends Activity {

    public ArrayAdapter<Pelicula> peliculasAdapter;
    public ArrayList<Pelicula> peliculas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prestamos_peliculas);

        this.peliculas = ((App) this.getApplication()).getPeliculas();
        this.peliculasAdapter = ((App) this.getApplication()).getPeliculasAdapter();

        this.peliculas.clear();
        this.peliculasAdapter.notifyDataSetChanged();

        LoanWalletSQL db = ((App) this.getApplication()).getDb();
        db.onCreate(db.getWritableDatabase());
        PrestamosPeliculas.this.actualizarLista();

        Button btnAdd = (Button) this.findViewById(R.id.AddPelicula);
        ListView lvPeliculas = (ListView) this.findViewById(R.id.listViewPeliculas);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrestamosPeliculas.this,AddPrestamoPelicula.class);
                PrestamosPeliculas.this.startActivity(intent);
            }
        });

        lvPeliculas.setLongClickable(true);
        lvPeliculas.setAdapter(this.peliculasAdapter);
        lvPeliculas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if (pos >= 0) {
                    PrestamosPeliculas.this.opciones(pos);
                }
                return false;
            }
        });
    }

    private void actualizarLista() {
        LoanWalletSQL sqlDB = new LoanWalletSQL(this);
        SQLiteDatabase db = sqlDB.getReadableDatabase();

        if(db != null) {
            Cursor peliculasCursor = db.rawQuery("SELECT * FROM peliculas", null);
            if(peliculasCursor.moveToFirst()) {
                do {
                    Pelicula pelicula = new Pelicula(peliculasCursor.getString(0), peliculasCursor.getString(1), peliculasCursor.getInt(2),
                            peliculasCursor.getInt(3), peliculasCursor.getString(4), peliculasCursor.getInt(5), peliculasCursor.getString(6),
                            peliculasCursor.getString(7), peliculasCursor.getString(8));
                    PrestamosPeliculas.this.peliculas.add(pelicula);
                } while(peliculasCursor.moveToNext());
            }
        } else {
            Toast.makeText(this.getApplicationContext(), "No se ha podido actualizar la lista de préstamos", Toast.LENGTH_LONG).show();
        }
    }

    private void opciones(final int pos) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Opciones");
        alert.setItems(new String[]{"Renovar", "Modificar", "Eliminar"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    PrestamosPeliculas.this.renovar(pos);
                } else if (which == 1) {
                    PrestamosPeliculas.this.modificar(pos);
                } else if (which == 2) {
                    PrestamosPeliculas.this.eliminar(pos);
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
                PrestamosPeliculas.this.renovacion(pos,fecha.toString());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void renovacion(int pos, String fecha) {
        final Pelicula pelicula = PrestamosPeliculas.this.peliculas.get(pos);
        int renovaciones = pelicula.getRenovaciones() + 1;

        LoanWalletSQL sqlDB = new LoanWalletSQL(this);
        SQLiteDatabase db = sqlDB.getWritableDatabase();

        if(db != null) {
            try {
                db.beginTransaction();
                db.execSQL("UPDATE peliculas SET renovaciones = ?, finPrestamo = ? WHERE titulo = ?;",
                        new String[]{String.valueOf(renovaciones), fecha, pelicula.getTitulo()});
                db.setTransactionSuccessful();
                pelicula.setFinPrestamo(fecha.toString());
                pelicula.setRenovaciones(renovaciones);
                PrestamosPeliculas.this.peliculasAdapter.notifyDataSetChanged();
            } finally {
                db.endTransaction();
            }
        } else {
            Toast.makeText(this.getApplicationContext(),"No se ha podido renovar la pelicula", Toast.LENGTH_LONG).show();
        }
    }

    private void modificar(int pos) {
        Intent intent = new Intent(PrestamosPeliculas.this,ModificarPrestamoPelicula.class);
        intent.putExtra("posicion",pos);
        PrestamosPeliculas.this.startActivity(intent);
    }

    private void eliminar(final int pos) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Eliminar Préstamo");
        alert.setMessage("¿Estás seguro?");
        alert.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PrestamosPeliculas.this.eliminacion(pos);
                PrestamosPeliculas.this.peliculasAdapter.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton("Cancelar", null);
        alert.create().show();
    }

    private void eliminacion(int pos) {
        final Pelicula pelicula = PrestamosPeliculas.this.peliculas.get(pos);

        LoanWalletSQL sqlDB = new LoanWalletSQL(this);
        SQLiteDatabase db = sqlDB.getWritableDatabase();

        if(db != null) {
            try {
                db.beginTransaction();
                db.execSQL("DELETE FROM peliculas WHERE titulo = ?;",
                        new String[]{pelicula.getTitulo()});
                db.setTransactionSuccessful();
                PrestamosPeliculas.this.peliculas.remove(pos);
                PrestamosPeliculas.this.peliculasAdapter.notifyDataSetChanged();
            } finally {
                db.endTransaction();
            }
        } else {
            Toast.makeText(this.getApplicationContext(),"No se ha podido eliminar las película", Toast.LENGTH_LONG).show();
        }
    }

}
