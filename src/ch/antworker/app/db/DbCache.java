/* Modeled after Github Android app's DatabaseCache */

package ch.antworker.app.db

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DbCache {
  public static final String TAG = "DbCache";

  private final SQLiteOpenHelper mHelper;

  public DbCache(Context ctxt) {
    mHelper = new SQLiteDatabase(ctxt);
  }

  private SQLiteDatabase getReadableDb() {
    try {
      return mHelper.getReadableDatabase();
    } catch(SQLiteException e) {
      return null;
    }
  }

  private SQLiteDatabase getWritableDb() {
    try {
      return mHelper.getWritableDatabase();
    } catch(SQLiteException e) {
      return null;
    }
  }

  public <T> List<T> loadOrFetch(CacheEntries<T> entries) {
    SQLiteDatabase db = getReadableDb();
    try {
      List<T> cached = load(entries);
      if(cached != null) return cached;
      return fetchAndStore(entries);
    } finally {
      mHelper.close();
    }
  }

  public <T> List<T> fetchAndStore(CacheEntries<T> entries) {
    List<T> fetched = entries.fetch();
    store(entries);
    return fetched;
  }

  public <T> void store(CacheEntries<T> entries) {
    SQLiteDatabase db = getWritableDb();
    try {
      entries.store(db, fetched);
    } finally {
      mHelper.close();
    }
  }

  private <T> List<T> load(CacheEntries<T> entries) {
    SQLiteDatabase db = getReadableDb();

    Cursor c = entries.getCursor(db);
    try {
      if(!cursor.moveToFirst()) return null;

      List<T> cached = new ArrayList<T>();
      do
        cached.add(entries.load(c));
      while(c.moveToNext());

      return cached;
    } finally {
      cursor.close();
    }
  }
}
