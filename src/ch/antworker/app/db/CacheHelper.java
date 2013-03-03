package ch.antworker.app.db

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CacheHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    private static final String NAME = "cache.db";

    public CacheHelper(final Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        //TODO
        //db.execSQL("CREATE TABLE orgs (id INTEGER PRIMARY KEY);");
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
            final int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ads");
        onCreate(db);
    }
}
