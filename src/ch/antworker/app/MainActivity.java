package ch.antworker.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;

public class MainActivity extends Activity {

  public void onCreate(Bundle savedInstanceState) {
    getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater mi = getMenuInflater();
    mi.inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Bundle bundle = new Bundle();
    switch(item.getId()) {
      case R.id.offered_ads_item:
        bundle.putString(AdFragment.AdType.getName(), AdFragment.AdType.OFFERED);
        FragmentHelper.show<AdFragment>(this, AdFragment.class, android.R.id.content, bundle);
        return true;
      case R.id.wanted_ads_item:
        bundle.putString(AdFragment.AdType.getName(), AdFragment.AdType.WANTED);
        FragmentHelper.show<AdFragment>(this, AdFragment.class, android.R.id.content, bundle);
        return true;
      case R.id.my_ant_item:
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
