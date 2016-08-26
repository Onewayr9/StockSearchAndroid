package oneway.stocksearch;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oneway on 16/4/6.
 */
public class SuggestionAdapter extends ArrayAdapter<StockNameSet> {

    protected static final String TAG = "SuggestionAdapter";
    private List<StockNameSet> suggestions;
    private final Context context;
    public SuggestionAdapter(Activity context, String nameFilter) {
        super(context, R.layout.aclitem);
        this.context = context;
        suggestions = new ArrayList<StockNameSet>();
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public StockNameSet getItem(int index) {
        return suggestions.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new Filter.FilterResults();
                JSONParse jp=new JSONParse();
                if (constraint != null) {
                    // A class that queries a web API, parses the data and
                    // returns an ArrayList<GoEuroGetSet>
                    List<StockNameSet> new_suggestions =jp.getParseJsonWCF(constraint.toString());
                    suggestions.clear();
                    for (int i=0;i<new_suggestions.size();i++) {
                        suggestions.add(new_suggestions.get(i));
                    }

                    // Now assign the values and count to the FilterResults
                    // object
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence contraint,
                                          FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                if(resultValue instanceof StockNameSet){
                    return ((StockNameSet) resultValue).getSymbol();
                }
                return super.convertResultToString(resultValue);
            }
        };
        return myFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.aclitem, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.acl_title);
        TextView valueView = (TextView) rowView.findViewById(R.id.acl_value);

        labelView.setText(suggestions.get(position).getSymbol());
        valueView.setText(suggestions.get(position).getName());

        return rowView;

    }
}
