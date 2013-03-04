package ch.antworker.app.db;

import android.provider.BaseColumns;

public class Schemas {
  public static class BaseAd implements BaseColumns {
    public static final String TABLE_NAME = "ads";
    public static final String ID = "id";
    public static final String TYPE = "type";
    public static final String TITLE = "title";
    public static final String BODY = "body";
    public static final String CREATED_DATE = "created_date";
  }

  public static class OfferedAd extends Schemas.BaseAd {
    public static final String WORK_LOCATION = "work_location";
  }
}
