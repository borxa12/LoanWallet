package es.uvigo.esei.dm1516.p24;

import android.app.Application;

/**
 * Created by Borxa on 28/12/2015.
 */
public class App extends Application {
    private LoanWalletSQL db;

    @Override
    public void onCreate() {
        super.onCreate();
        this.db = new LoanWalletSQL(this);
    }

    public LoanWalletSQL getDb() {
        return this.db;
    }
}
