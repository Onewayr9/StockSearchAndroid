package oneway.stocksearch;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Oneway on 16/4/10.
 */
public class newsAdapter extends ArrayAdapter<News> {
    private final Context context;
    private final ArrayList<News> newsArrayList;
    public newsAdapter(Context context, ArrayList<News> newsArrayList){
        super(context,R.layout.feedsitem,newsArrayList);
        this.context = context;
        this.newsArrayList = newsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.feedsitem, parent, false);

        // 3. Get the two text view from the rowView
        TextView titleView = (TextView) rowView.findViewById(R.id.f_title);
        TextView descView = (TextView) rowView.findViewById(R.id.f_contents);
        TextView pubView = (TextView) rowView.findViewById(R.id.f_publisher);
        TextView dateView = (TextView) rowView.findViewById(R.id.f_dates);

        // 4. Set the text for textView
        titleView.setClickable(true);
        titleView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='"+newsArrayList.get(position).getUrl()+"'>"+newsArrayList.get(position).getTitle()+"</a>";
        titleView.setText(Html.fromHtml(text));
        descView.setText(newsArrayList.get(position).getDescription());
        pubView.setText(newsArrayList.get(position).getPublisher());
        dateView.setText(newsArrayList.get(position).getDate());

        return rowView;
    }
}
