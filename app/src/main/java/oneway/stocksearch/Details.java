package oneway.stocksearch;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * A simple {@link Fragment} subclass.
 */
public class Details extends Fragment {
    JSONObject details;

    public Details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        View fragmentLayout = inflater.inflate(R.layout.fragment_details, container, false);
        final ImageView imageView = new ImageView(this.getActivity());
        try{
            details = new JSONObject(bundle.getString("Details"));
            new ImageLoadTask("http://chart.finance.yahoo.com/t?s="+details.getString("Symbol")+"&lang=en-US&width=600&height=400",imageView).execute();
        }catch(Exception e){
            e.printStackTrace();
        }
        ListView listView = (ListView)fragmentLayout.findViewById(R.id.listView);
        listAdapter adapter = new listAdapter(this.getActivity(),generateItems());
        listView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();
        LayoutInflater minflater = LayoutInflater.from(getActivity());
        View dialogLayout = minflater.inflate(R.layout.photo_zoom, null);

        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                ImageView mImageView = (ImageView) dialog.findViewById(R.id.iv_photo);
                mImageView.setImageDrawable(imageView.getDrawable());
                PhotoViewAttacher mAttacher = new PhotoViewAttacher(mImageView);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));

        TextView textView = new TextView(this.getActivity());
        textView.setText("Today's Stock Activity");
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(20);
        listView.addFooterView(textView);
        listView.addFooterView(imageView);

        TextView textView2 = new TextView(this.getActivity());
        textView2.setTextColor(Color.BLACK);
        textView2.setTextSize(20);
        textView2.setText("Stock Details");
        textView2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        listView.addHeaderView(textView2);

        return fragmentLayout;
    }

    private ArrayList<DetailItems> generateItems(){
        ArrayList<DetailItems> items = new ArrayList<>();
        try{
            items.add(new DetailItems("NAME",details.getString("Name")));
            items.add(new DetailItems("SYMBOL",details.getString("Symbol")));
            items.add(new DetailItems("LASTPRICE", details.getString("LastPrice")));
            String change = String.format("%.2f", Double.parseDouble(details.getString("Change")));
            String changePercent = String.format("%.2f", Double.parseDouble(details.getString("ChangePercent")));
            if(Double.parseDouble(details.getString("ChangePercent"))>0) changePercent = "+" + changePercent;
            items.add(new DetailItems("CHANGE",change+"("+changePercent+"%)"));
            double marketCap = Double.parseDouble(details.getString("MarketCap"));
            String marketCapStr = marketCap > 1000000000?String.format("%.2f",marketCap/1000000000)+" Billion":(marketCap > 1000000?String.format("%.2f",marketCap/1000000)+" Million":marketCap+"");
            items.add(new DetailItems("MARKETCAP",marketCapStr));
            items.add(new DetailItems("VOLUME",details.getString("Volume")));
            String changeYTD = String.format("%.2f", Double.parseDouble(details.getString("ChangeYTD")));
            String changePercentYTD = String.format("%.2f", Double.parseDouble(details.getString("ChangePercentYTD")));
            if(Double.parseDouble(details.getString("ChangePercentYTD"))>0) changePercentYTD = "+" + changePercentYTD;
            else changeYTD = "-" + changeYTD;
            items.add(new DetailItems("CHANGEYTD",changeYTD+"("+changePercentYTD+"%)"));
            items.add(new DetailItems("HIGH",String.format("%.2f",Double.parseDouble(details.getString("High")))));
            items.add(new DetailItems("LOW",String.format("%.2f",Double.parseDouble(details.getString("Low")))));
            items.add(new DetailItems("OPEN",String.format("%.2f",Double.parseDouble(details.getString("Open")))));
        }catch(Exception e){
            e.printStackTrace();
        }
        return items;
    }
}
