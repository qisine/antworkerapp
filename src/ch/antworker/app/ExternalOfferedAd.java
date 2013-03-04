package ch.antworker.app;

public class ExternalOfferedAd extends BaseAd {
  private String mLink;

  public ExternalOfferedAd() { super(); }
  public ExternalOfferedAd(
    int id, String title, String body, String workLocation, String createdDate, String link) {
    super(id, title, body, workLocation, createdDate);
    mLink = link;
  }

  public String getLink() { return mLink; }
  public void setLink(String link) { mLink = link; }
}
