package es.uvigo.esei.dm1516.p24;

import android.app.Application;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Borxa on 28/12/2015.
 */
public class App extends Application {
    private ArrayAdapter<Libro> itemsAdapter;
    private ArrayList<Libro> items;

    @Override
    public void onCreate() {
        super.onCreate();

        this.items = new ArrayList<>();
        this.itemsAdapter = new ArrayAdapter<Libro>(
                this.getApplicationContext(),
                android.R.layout.simple_selectable_list_item,
                this.items
        );
    }

    public ArrayAdapter<Libro> getItemsAdapter() {
        return itemsAdapter;
    }

    public ArrayList<Libro> getItems() {
        return items;
    }

}
