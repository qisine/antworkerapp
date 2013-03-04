/* Modeled after Github Android app's Persistable Orgs */

package ch.antworker.app.db

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import ch.antworker.app.OfferedAd;

import java.io.IOException;
import java.util.List;

public class OfferedAdEntries implements CacheEntries<OfferedAd> {
  public static final String TAG = "OfferedAdEntries";

  private final static AD_CLASS_NAME = OfferedAd.class.getName();

  @Override
  public Cursor getCursor(SQLiteDatabase db) {
    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
    builder.setTables("ads");
    builder.appendWhere("type='" + AD_CLASS_NAME + '");
    return builder.query( db,
                          new String[] { "id", "title", "body", "work_location", "created_date" },
                          null, null, null, null, null);
  }

  @Override
  public OfferedAd load(Cursor c) {
    OfferedAd ad = new OfferedAd();
    ad.setId(c.getInt(0));
    ad.setTitle(c.getString(1));
    ad.setBody(c.getString(2));
    ad.setWorkLocation(c.getString(3));
    ad.setCreatedDate(c.getString(4));
    return ad;
  }

  @Override
  public void store(SQLiteDatabase db, List<OfferedAd> ads) {
    ContentValues cv = new ContentValues(5);
    for(OfferedAd ad: ads) {
      cv.clear();

      cv.put("id", ad.getId());
      cv.put("title", ad.getTitle());
      cv.put("body", ad.getBody());
      cv.put("work_location", ad.getWorkLocation());
      db.replace("ads", null, cv)
    }
  }

  @Override
  public List<OfferedAd> fetch() throws IOException {

  }
}
