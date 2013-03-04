package ch.antworker.app;

public class OfferedAd extends BaseAd {
  private String mWorkLocation;

  public OfferedAd() { super(); }
  public OfferedAd(int id, String title, String body, String createdDate, String workLocation, String link) {
    super(id, title, body, createdDate, link);
    mWorkLocation = workLocation;
  }

  public OfferedAd(int id, String title, String body, String createdDate, String workLocation) {
    this(id, title, body, createdDate, workLocation, null);
  }

  public String getWorkLocation() { return mWorkLocation; }
  public void setWorkLocation(String workLocation) { mWorkLocation = workLocation; }
}
