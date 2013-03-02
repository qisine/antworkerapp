package ch.antworker.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.Window;

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
    FragmentHelper<AdListFragment> helper = new FragmentHelper<AdListFragment>();
    switch(item.getItemId()) {
      case R.id.offered_ads_item:
        bundle.putString(AdListFragment.AdType.class.getName(), AdListFragment.AdType.OFFERED.toString());
        helper.show(this, AdListFragment.class, android.R.id.content, bundle);
        return true;
      case R.id.wanted_ads_item:
        bundle.putString(AdListFragment.AdType.class.getName(), AdListFragment.AdType.WANTED.toString());
        helper.show(this, AdListFragment.class, android.R.id.content, bundle);
        return true;
      case R.id.my_ant_item:
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
