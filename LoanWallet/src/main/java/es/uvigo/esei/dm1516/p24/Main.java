package es.uvigo.esei.dm1516.p24;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Main extends Activity {

    // Documentation API Books (Google Books): https://developers.google.com/books/docs/v1/using
    // API Books: https://www.googleapis.com/books/v1/volumes?q=manual+de+ciencia+politica
    // API Movies: http://www.omdbapi.com/?t=frozen&y=&plot=short&r=json
    // Documentation API Music (Spotify): https://developer.spotify.com/web-api/search-item/

    private ArrayAdapter<Book> itemsAdapter;
    private ArrayList<Book> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btnAdd = (Button) this.findViewById(R.id.AddLoan);
        ListView lvBooks = (ListView) this.findViewById(R.id.listView);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this,AddLoan.class);
                Main.this.startActivity(intent);
            }
        });

        lvBooks.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
    }

}
