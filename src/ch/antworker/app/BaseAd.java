package ch.antworker.app;

public abstract class BaseAd implements Ad {
  private String mTitle;
  private String mBody;

  public BaseAd(String title, String body) {
    mTitle = title;
    mBody = body;
  }

  public String getTitle() { return mTitle; }
  public void setTitle(String title) { mTitle = title; }

  public String getBody() { return mBody; }
  public void setBody(String body) { mBody = body; }
}
