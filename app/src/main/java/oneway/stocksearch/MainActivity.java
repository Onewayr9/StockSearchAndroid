package oneway.stocksearch;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView atv;
    favoriteAdapter adapter;
    FavoriteListOperator operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        operator = new FavoriteListOperator(this);
        getSupportActionBar().setTitle("Stock Market Viewer");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        atv = (AutoCompleteTextView) findViewById(R.id.Symbol);
        atv.setThreshold(3);
        atv.setAdapter(new SuggestionAdapter(this, atv.getText().toString()));

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                adapter.setFavoriteArrayList(generateItems());
                adapter.notifyDataSetChanged();
                handler.postDelayed(this,10000);
            }
        };
        Switch autofresh = (Switch) findViewById(R.id.switch1);
        autofresh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    runnable.run();
                }else{
                    handler.removeCallbacks(runnable);
                }
            }
        });

        final Context thisContext = this;
        adapter = new favoriteAdapter(this,generateItems());
        final DynamicListView listView = (DynamicListView) findViewById(R.id.favoritelist);
        listView.enableSwipeToDismiss(new OnDismissCallback() {
            @Override
            public void onDismiss(@NonNull ViewGroup listView, @NonNull int[] reverseSortedPositions) {
                for (final int position : reverseSortedPositions) {
                    new AlertDialog.Builder(thisContext)
                            .setMessage("Want to delete "+adapter.getItem(position).getName()+" from favorites?")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do nothing
                                }
                            })
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    operator.delete(adapter.getItem(position).getSymbol());
                                    adapter.remove(position);
                                }
                            }).show();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                favoriteItem item = (favoriteItem) listView.getItemAtPosition(position);
                try{
                    JSONObject details = new JSONObject(new getInfoTask().execute(item.getSymbol()).get());
                    Intent intent = new Intent(thisContext,DetailsActivity.class);
                    intent.putExtra("Details", details.toString());
                    intent.putExtra("Symbol", item.getSymbol());
                    new getFeedTask(thisContext,intent,item.getSymbol()).execute();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
        listView.setAdapter(adapter);
    }

    public void submit(View v){
        try{
            if(atv.getText().toString().length()==0){
                new AlertDialog.Builder(this)
                        .setMessage("Please enter a Stock Name/Symbol")
                        .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
            } else {
                JSONObject details = new JSONObject(new getInfoTask().execute(atv.getText().toString()).get());
                if (details.getString("Status").equals("FAILED")) {
                    new AlertDialog.Builder(this)
                            .setMessage("Invalid Symbol")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                } else {
                    Intent intent = new Intent(this,DetailsActivity.class);
                    intent.putExtra("Details", details.toString());
                    intent.putExtra("Symbol", atv.getText().toString());
                    intent.putExtra("Name",details.getString("Name"));
                    new getFeedTask(this,intent,atv.getText().toString()).execute();
                }
            }
        }catch (Exception e1) {
            // TODO Auto-generated catch block
            new AlertDialog.Builder(this)
                    .setMessage("Invalid Symbol")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
            e1.printStackTrace();
        }
    }

    public void refresh(View v){
        adapter.setFavoriteArrayList(generateItems());
        adapter.notifyDataSetChanged();
    }

    public void clear(View v){
        atv.setText("");
    }

    public ArrayList<favoriteItem> generateItems(){
        ArrayList<favoriteItem> items = new ArrayList<>();
        Set<String> favoriteSet = operator.getFavoriteSet();
        Iterator<String> favoriteIterator = favoriteSet.iterator();
        while(favoriteIterator.hasNext()){
            String symbol = favoriteIterator.next();
            try{
                JSONObject details = new JSONObject(new getInfoTask().execute(symbol).get());
                String name = details.getString("Name");
                String value = details.getString("LastPrice");
                String change = String.format("%.2f", Double.parseDouble(details.getString("Change")));
                double marketCap = Double.parseDouble(details.getString("MarketCap"));
                String marketCapStr = marketCap > 1000000000?String.format("%.2f",marketCap/1000000000)+" Billion":(marketCap > 1000000?String.format("%.2f",marketCap/1000000)+" Million":marketCap+"");
                items.add(new favoriteItem(symbol,value,change,name,marketCapStr));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return items;
    }
}
