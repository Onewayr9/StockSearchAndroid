package oneway.stocksearch;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oneway on 16/4/6.
 */
public class JSONParse {
    double current_latitude,current_longitude;
    public JSONParse(){}
    public JSONParse(double current_latitude,double current_longitude){
        this.current_latitude=current_latitude;
        this.current_longitude=current_longitude;
    }
    public List<StockNameSet> getParseJsonWCF(String sName) {
        List<StockNameSet> ListData = new ArrayList<StockNameSet>();
        try {
            String temp = sName.replace(" ", "%20");
            URL js = new URL("http://stocksearch-1267.appspot.com/?input=" + temp);
            URLConnection jc = js.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(jc.getInputStream()));
            String line = reader.readLine();
            JSONArray jsonArray = new JSONArray(line);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject r = jsonArray.getJSONObject(i);
                ListData.add(new StockNameSet(r.getString("Symbol"), r.getString("Name")+" ("+r.getString("Exchange")+")"));
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return ListData;

    }
}
