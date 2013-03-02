package ch.antworker.app;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import java.util.HashMap;

public class FragmentHelper<T extends Fragment> {
  public static final String TAG = "FragmentHelper";
  private static final HashMap<String, Fragment> mFragments = new HashMap<String, Fragment>();

  public void show(Activity activity, Class<T> clazz, int containerId, Bundle bundle) {
    String clsName = clazz.getName();
    FragmentManager fm = activity.getFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    T currentFragment = (T) fm.findFragmentByTag(clsName);

    if (currentFragment != null) {
      if(clazz.isInstance(currentFragment)) return;
      ft.detach(currentFragment);
    }
    
    currentFragment = (T) mFragments.get(clsName);
    if(currentFragment != null) {
      ft.attach(currentFragment);
    } else {
      currentFragment = (T) Fragment.instantiate(activity, clsName, bundle);
      ft.replace(containerId, currentFragment, clsName);
      mFragments.put(clsName, currentFragment);
    }
    
    ft.addToBackStack(null).commit();
  }
}
