package oneway.stocksearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Oneway on 16/4/8.
 */
public class listAdapter extends ArrayAdapter<DetailItems> {
    private final Context context;
    private final ArrayList<DetailItems> itemsArrayList;
    public listAdapter(Context context, ArrayList<DetailItems> itemsArrayList){
        super(context,R.layout.listitem,itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.listitem, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.d_title);
        TextView valueView = (TextView) rowView.findViewById(R.id.d_value);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.d_img);

        // 4. Set the text for textView
        labelView.setText(itemsArrayList.get(position).getTitle());
        valueView.setText(itemsArrayList.get(position).getDescription());

        if(itemsArrayList.get(position).getTitle().startsWith("CHANGE")){
            if(itemsArrayList.get(position).getDescription().startsWith("-")){
                imageView.setImageResource(R.drawable.down_arrow);
            }else{
                imageView.setImageResource(R.drawable.up_arrow);
            }
        }

        return rowView;
    }
}
