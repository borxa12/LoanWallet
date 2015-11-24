package es.uvigo.esei.dm1516.p24;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

        Button btnAddLoan = (Button) this.findViewById(R.id.btnAddLoan);

        btnAddLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLoan.this.addLoan();
            }
        });

        ImageButton btnCalendar = (ImageButton) this.findViewById(R.id.calendar);
        EditText lblEndLoan =(EditText) this.findViewById(R.id.lblEndLoan);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = AddLoan.this.getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.calendar, null);

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
        AutoCompleteTextView lblTitle = (AutoCompleteTextView) this.findViewById(R.id.lblTitle);
        AutoCompleteTextView lblAutor = (AutoCompleteTextView) this.findViewById(R.id.lblAuthors);
        EditText lblYear = (EditText) this.findViewById(R.id.lblYear);
        EditText lblDescription = (EditText) this.findViewById(R.id.lblDescription);
        EditText lblEndLoan =(EditText) this.findViewById(R.id.lblEndLoan);
        EditText lblLender = (EditText) this.findViewById(R.id.lblLender);
        ImageButton btnCalendar = (ImageButton) this.findViewById(R.id.calendar);


    }
}
