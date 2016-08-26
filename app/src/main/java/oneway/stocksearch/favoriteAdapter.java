package oneway.stocksearch;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Oneway on 16/4/10.
 */
public class favoriteAdapter extends com.nhaarman.listviewanimations.ArrayAdapter<favoriteItem> {
    private ArrayList<favoriteItem> mfavoriteArrayList;
    private Context mContext;

    public favoriteAdapter(Context context,ArrayList<favoriteItem> favoriteArrayList){
        super(favoriteArrayList);
        mContext = context;
        mfavoriteArrayList = favoriteArrayList;
    }

    public void setFavoriteArrayList(ArrayList<favoriteItem> favoriteArrayList){
        mfavoriteArrayList = favoriteArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.favoritelistitem, parent, false);

        // 3. Get the two text view from the rowView
        TextView symbolView = (TextView)rowView.findViewById(R.id.fav_symbol);
        TextView valueView = (TextView)rowView.findViewById(R.id.fav_val);
        TextView changeView = (TextView)rowView.findViewById(R.id.fav_percent);
        TextView nameView = (TextView)rowView.findViewById(R.id.fav_name);
        TextView mktcpView = (TextView)rowView.findViewById(R.id.fav_mktcp);

        // 4. Set the text for textView
        favoriteItem selectedCustomer = mfavoriteArrayList.get(position);
        symbolView.setText(selectedCustomer.getSymbol());
        valueView.setText("$ "+selectedCustomer.getValue());
        double change = Double.parseDouble(selectedCustomer.getChange());
        if(change>0){
            changeView.setText("+"+change+"%");
            changeView.setBackgroundColor(Color.GREEN);
        }else{
            changeView.setText(change+"%");
            changeView.setBackgroundColor(Color.RED);
        }
        nameView.setText(selectedCustomer.getName());
        mktcpView.setText("Market Cap : "+selectedCustomer.getMarketCap());

        return rowView;
    }
}
