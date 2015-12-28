package es.uvigo.esei.dm1516.p24;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import org.w3c.dom.Text;

/**
 * Created by Borxa on 17/11/2015.
 */
public class AddLoan extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_loan);

        Button btnAddLoan = (Button) this.findViewById(R.id.btnAddLibro);

        btnAddLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLoan.this.addLoan();
            }
        });

        ImageButton btnCalendar = (ImageButton) this.findViewById(R.id.calendar);
        final EditText lblEndLoan =(EditText) this.findViewById(R.id.labelFinPrestamo);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = AddLoan.this.getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.calendar, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(AddLoan.this);
                alert.setView(dialoglayout);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatePicker calendar = (DatePicker) dialoglayout.findViewById(R.id.datePicker);
                        lblEndLoan.setText(calendar.getDayOfMonth() + "/" + (calendar.getMonth()+ 1) + "/" + calendar.getYear());
                    }
                });
                alert.setNegativeButton("Cancel",null);
                alert.create().show();
            }
        });
    }

    public void addLoan() {
        AutoCompleteTextView labelTitulo = (AutoCompleteTextView) this.findViewById(R.id.labelTitulo);
        AutoCompleteTextView labelAutores = (AutoCompleteTextView) this.findViewById(R.id.labelAutores);
        EditText labelAno = (EditText) this.findViewById(R.id.labelAno);
        EditText labelFinPrestamo = (EditText) this.findViewById(R.id.labelFinPrestamo);
        EditText labelLugarPrestamo = (EditText) this.findViewById(R.id.labelLugarPrestamo);
        ImageButton btnCalendar = (ImageButton) this.findViewById(R.id.calendar);

        LoanWalletSQL sqlDB = ((App) this.getApplication()).getDb();
        SQLiteDatabase db = sqlDB.getWritableDatabase();

        db.execSQL("INSERT OR");
        INSERT INTO `loanwallet`.`libro` (`id`, `ISBN`, `titulo`, `autores`, `ano`, `editorial`, `renovaciones`,
        `fechaPrestamo`, `finPrestamo`, `lugarPrestamo`) VALUES (NULL, '20325-3545-6536-5445', 'Titulo', 'Algún habra',
                '1995', 'Anaya', '0', CURRENT_DATE(), CURRENT_DATE(), 'Arbo');
    }
}
