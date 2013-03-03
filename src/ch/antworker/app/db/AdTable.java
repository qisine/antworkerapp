package ch.antworker.app.db;

import android.provider.BaseColumns;

public class Ad {
  public static class AdEntry implements BaseColumns {
    public static final String TABLE_NAME = "ads";
    public static final String TITLE = "title";
    public static final String BODY = "body";
    public static final String CREATED_DATE = "created_date";
  }
}
