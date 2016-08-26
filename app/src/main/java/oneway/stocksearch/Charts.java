package oneway.stocksearch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Charts extends Fragment {
    public Charts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        View fragmentLayout = inflater.inflate(R.layout.fragment_charts, container, false);
        try{
            String symbol = bundle.getString("Symbol");
            WebView webView = (WebView) fragmentLayout.findViewById(R.id.webView);
            //Log.w("cha",symbol);
            String html = "<html> <head> <script src=\"http://code.jquery.com/jquery-1.10.2.js\"></script> <script src=\"https://code.highcharts.com/stock/highstock.js\"></script> <script> function initChart(symbol){$.ajax({url: \"http://stocksearch-1267.appspot.com/\", dataType: \"json\", data: {chart: symbol }, type: 'GET', crossDomain: true, success: function(response, status, xhr){var dates = response.Dates || []; var values = response.Elements[0].DataSeries.close.values; var chartSeries = []; for (var i = 0, datLen = dates.length; i < datLen; i++) {var dat = new Date(dates[i]); dat = Date.UTC(dat.getFullYear(), dat.getMonth(), dat.getDate()); var pointData = [dat, values[i] ]; chartSeries.push( pointData ); } $(\"#charts\").highcharts('StockChart', {rangeSelector : {selected : 1 }, title : {text : symbol+' Stock Value'}, chart : {width : $(\"#charts\").width() }, yAxis : [{title: {text: 'Stock Value'}, min: 0 }], series : [{name : symbol+' Stock Value', data : chartSeries, type : 'area', threshold : null, tooltip : {valueDecimals : 2 }, fillColor : {linearGradient : {x1: 0, y1: 0, x2: 0, y2: 1 }, stops : [[0, Highcharts.getOptions().colors[0]], [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')] ] } }] }); $(\"#charts\").attr('style','width: '+$('#panel-body').width()+'px'); $(\"#charts\").highcharts().setSize($(\"#charts\").width(),$(\"#charts\").height()); }, error: function(jqXHR, textStatus, errorThrown){console.log(errorThrown); console.log(textStatus); } }); } initChart('"+symbol+"'); </script> </head> <body> <div id=\"charts\"></div> </body> </html>";
            webView.loadData(html,"text/html", null);
            webView.getSettings().setJavaScriptEnabled(true);
        }catch(Exception e){
            e.printStackTrace();
        }
        return fragmentLayout;
    }

}
