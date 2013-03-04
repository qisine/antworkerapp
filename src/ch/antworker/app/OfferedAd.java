package ch.antworker.app;

public class OfferedAd extends BaseAd {
  private int mPay;

  public OfferedAd() { super(); }
  public OfferedAd(int id, String title, String body, String workLocation, String createdDate, int pay) {
    super(id, title, body, workLocation, createdDate);
    mPay = pay;
  }

  public int getPay() { return mPay; }
  public void setPay(int pay) { mPay = pay; }
}
