package oneway.stocksearch;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Oneway on 16/4/6.
 */
public class getInfoTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        String Symbol = params[0];
        String json = null;
        try {
            URL url = new URL("http://stocksearch-1267.appspot.com/?symbol="+Symbol);
            json = getJSONFromURL(url);
        }catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onPostExecute(String jsonObjects) {
        super.onPostExecute(jsonObjects);
    }
    public String getJSONFromURL(URL url){
        String line = null;
        try{
            URLConnection jc = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(jc.getInputStream()));
            line = reader.readLine();
        }catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return line;
    }
}
