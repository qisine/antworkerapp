package ch.antworker.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

public class AdListFragment extends Fragment {
  public static final String TAG = "AdListFragment";
  public enum AdType { OFFERED, WANTED }

  private AdType mAdType;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mAdType = AdType.valueOf(savedInstanceState.getString(AdType.class.getName()));
    View v = inflater.inflate(R.layout.ad_list, container, false);
    switch(mAdType) {
      case OFFERED:
      case WANTED:
      /* default: return super.onCreateView(inflater, container, savedInstanceState);*/
    }
    /*mAdpt = new ArrayAdapter<Message>(
      getActivity(),
      android.R.layout.simple_list_item_1,
      android.R.id.text1,
      posts);

    ListView lv = (ListView) ll.findViewById(R.id.posts); 
    lv.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> par, View v, int pos, long id) {
        Message m = (Message) par.getItemAtPosition(pos);
        Intent i = new Intent(getActivity(), DetailsActivity.class);
        i.putExtra(Message.class.getName(), m);
        startActivity(i);
      }
    });

    lv.setAdapter(mAdpt);
    return ll;*/
    return v;
  }
}
