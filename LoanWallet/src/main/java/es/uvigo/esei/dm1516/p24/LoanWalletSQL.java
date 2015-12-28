package es.uvigo.esei.dm1516.p24;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Borxa on 28/12/2015.
 */
public class LoanWalletSQL extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LoanWallet";
    private static final int DATABASE_VERSION = 1;

    public LoanWalletSQL(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS libros (" +
                            "ISBN VARCHAR(20) NOT NULL PRIMARY KEY," +
                            "titulo VARCHAR(200) NOT NULL," +
                            "autores VARCHAR(200) NULL," +
                            "ano INT(4) NULL," +
                            "editorial VARCHAR(50) NULL," +
                            "renovaciones INT NULL DEFAULT 0," +
                            "fechaPrestamo DATETIME NULL," +
                            "finPrestamo DATETIME NULL," +
                            "lugarPrestamo VARCHAR(80) NULL" +
                            ")");
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.beginTransaction();
        try {
            db.execSQL("DROP TABLE IF EXISTS libros");
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        this.onCreate(db);
    }
}
