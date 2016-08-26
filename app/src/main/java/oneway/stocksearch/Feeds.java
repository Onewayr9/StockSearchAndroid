package oneway.stocksearch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Feeds extends Fragment {
    JSONArray feeds;

    public Feeds() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        View fragmentLayout = inflater.inflate(R.layout.fragment_feeds, container, false);
        try{
            feeds = new JSONObject(bundle.getString("Feeds")).getJSONObject("d").getJSONArray("results");
        }catch (Exception e){
            e.printStackTrace();
        }
        ListView feedsView = (ListView)fragmentLayout.findViewById(R.id.feedsView);
        newsAdapter adapter = new newsAdapter(this.getActivity(),generateItems());
        feedsView.setAdapter(adapter);

        return fragmentLayout;
    }

    private ArrayList<News> generateItems(){
        ArrayList<News> newsArray = new ArrayList<>();
        try{
            for(int i=0;i<feeds.length();i++){
                newsArray.add(new News(feeds.getJSONObject(i).getString("Title"),
                        feeds.getJSONObject(i).getString("Url"),
                        feeds.getJSONObject(i).getString("Description"),
                        feeds.getJSONObject(i).getString("Source"),
                        feeds.getJSONObject(i).getString("Date")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return newsArray;
    }

}
