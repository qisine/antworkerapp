/* Modeled after Github Android app's PersistableResource */

package ch.antworker.app.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.List;

public interface CacheEntries<T> {
  Cursor getCursor(SQLiteDatabase db);
  T load(Cursor c);
  void store(SQLiteDatabase db, List<T> l);
  List<T> fetch() throws IOException;
}

