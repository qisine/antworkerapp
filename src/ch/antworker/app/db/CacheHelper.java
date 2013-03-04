package ch.antworker.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CacheHelper extends SQLiteOpenHelper {
  public static final String TAG = "CacheHelper";

  private static final int VERSION = 1;

  private static final String NAME = "cache.db";

  public CacheHelper(final Context context) {
    super(context, NAME, null, VERSION);
  }

  @Override
  public void onCreate(final SQLiteDatabase db) {
    db.execSQL(
    "CREATE TABLE " + Schemas.BaseAd.TABLE_NAME + "("
      + Schemas.BaseAd.ID + " INTEGER PRIMARY KEY, "
      + Schemas.BaseAd.TYPE + " STRING, "
      + Schemas.BaseAd.TITLE + " STRING, "
      + Schemas.BaseAd.BODY + " STRING, " 
      + Schemas.BaseAd.WORK_LOCATION + " STRING, "
      + Schemas.BaseAd.CREATED_DATE + " TIMESTAMP, "
      + Schemas.OfferedAd.PAY + " INT, "
      + Schemas.ExternalOfferedAd.LINK + " STRING"
      + ");");
  }

  @Override
  public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS ads");
    onCreate(db);
  }
}
