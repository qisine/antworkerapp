/* Modeled after Github Android app's Persistable Orgs */

package ch.antworker.app.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import ch.antworker.app.ExternalOfferedAd;

import java.io.IOException;
import java.util.List;

public class ExternalOfferedAdEntries implements CacheEntries<ExternalOfferedAd> {
  public static final String TAG = "ExternalOfferedAdEntries";

  private static final String AD_CLASS_NAME = ExternalOfferedAd.class.getName();

  @Override
  public Cursor getCursor(SQLiteDatabase db) {
    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
    builder.setTables("ads");
    builder.appendWhere("type='" + AD_CLASS_NAME + "'");
    return builder.query( db,
      new String[] { "id", "title", "body", "created_date", "link" },
      null, null, null, null, null);
  }

  @Override
  public ExternalOfferedAd load(Cursor c) {
    ExternalOfferedAd ad = new ExternalOfferedAd();
    ad.setId(c.getInt(0));
    ad.setTitle(c.getString(1));
    ad.setBody(c.getString(2));
    ad.setCreatedDate(c.getString(3));
    ad.setLink(c.getString(4));
    return ad;
  }

  @Override
  public void store(SQLiteDatabase db, List<ExternalOfferedAd> ads) {
    ContentValues cv = new ContentValues(5);
    for(ExternalOfferedAd ad: ads) {
      cv.clear();

      cv.put("type", AD_CLASS_NAME);
      cv.put("id", ad.getId());
      cv.put("title", ad.getTitle());
      cv.put("body", ad.getBody());
      cv.put("link", ad.getLink());
      db.replace("ads", null, cv);
    }
  }

  @Override
  public List<ExternalOfferedAd> fetch() throws IOException {
    return null;
  }
}
