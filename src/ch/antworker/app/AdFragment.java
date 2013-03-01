package ch.antworker.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

public class AdFragment extends Fragment {
  public static final String TAG = "AdFragment";
  public enum AdType { OFFERED, WANTED }

  private final AdType mAdType;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mAdType = AdType.valueOf(bundle.getString(AdType.getName()));
    View v = super.onCreateView(inflater, container, savedInstanceState);
    return v;
  }
}

