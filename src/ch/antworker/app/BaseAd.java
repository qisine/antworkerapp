package ch.antworker.app;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public abstract class BaseAd implements Ad {
  private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
  private int mId;
  private String mTitle;
  private String mBody;
  private String mWorkLocation;
  private Date mCreatedDate;

  public BaseAd() { super(); }
  public BaseAd(int id, String title, String body, String workLocation, String createdDate) {
    mId = id;
    mTitle = title;
    mBody = body;
    mWorkLocation = workLocation;
    setCreatedDate(createdDate);
  }

  public int getId() { return mId; }
  public void setId(int id) { mId = id; }

  public String getTitle() { return mTitle; }
  public void setTitle(String title) { mTitle = title; }

  public String getBody() { return mBody; }
  public void setBody(String body) { mBody = body; }

  public String getWorkLocation() { return mWorkLocation; }
  public void setWorkLocation(String workLocation) { mWorkLocation = workLocation; }

  public String getCreatedDate() {
      return FORMATTER.format(mCreatedDate);
  }

  public void setCreatedDate(String createdDate) {
      while (!createdDate.endsWith("00")){
          createdDate += "0";
      }
      try {
          mCreatedDate = FORMATTER.parse(createdDate.trim());
      } catch (ParseException e) {
          throw new RuntimeException(e);
      }
  }
}
