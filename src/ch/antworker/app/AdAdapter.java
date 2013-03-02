package ch.antworker.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.HashMap;

public class AdAdapter<A extends Ad> extends ArrayAdapter<A> {
  private final Activity mActivity;
  private final int mLayoutId;
  private final List<A> mAds;
  private final Class<A> mType;
     
  public AdAdapter(Activity activity, int layoutId, List<A> ads, Class<A> type) {
    super(activity, layoutId, ads);
    mActivity = activity;
    mLayoutId = layoutId;
    mAds = ads;
    mType = type;
  }

  @Override
  public View getView(int pos, View convertView, ViewGroup parent) {
    View v = convertView;
    if(pos > mAds.size() - 1) return v;
    A ad = mAds.get(pos);

    if(v == null) {
      LayoutInflater li = mActivity.getLayoutInflater();
      v = li.inflate(mLayoutId, null);
      HashMap<String, Object> viewElements = new HashMap<String, Object>();
      viewElements.put("title", v.findViewById(R.id.ad_title));
      viewElements.put("body", v.findViewById(R.id.ad_body));
      addSpecificElements(viewElements, v);
      v.setTag(viewElements);
    }

    HashMap<String, Object> viewElements = (HashMap<String, Object>) v.getTag();
    ((TextView) viewElements.get("title")).setText(ad.getTitle());
    ((TextView) viewElements.get("body")).setText(ad.getBody());
    applySpecificElements(viewElements, ad);

    return v;
  }

  private void addSpecificElements(HashMap<String, Object> viewElements, View v) {
    if(mType.isAssignableFrom(OfferedAd.class)) {
      viewElements.put("logo", v.findViewById(R.id.ad_logo));
    }
  }

  private void applySpecificElements(HashMap<String, Object> viewElements, A ad) {
    if(mType.isAssignableFrom(OfferedAd.class)) {
      ImageView logo = (ImageView) viewElements.get("logo");
    }
  }
}
