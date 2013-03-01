package ch.antworker.app;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class FragmentHelper {
  public static final String TAG = "FragmentHelper";

  public void show(Activity activity, Class<T extends Fragment> clazz, int containerId, Bundle bundle) {
    FragmentManager fm = activity.getFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    T currentFragment = fm.findFragmentByTag(clazz.getName());

    if (currentFragment != null) ft.detach(currentFragment);

    currentFragment = Fragment.instantiate(activity, clazz.getName(), bundle);
    ft.replace(containerId, currentFragment, clazz.getName());
    
    ft.addToBackStack(null).commit();
  }
}
