package oneway.stocksearch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Oneway on 16/4/10.
 */
public class getFeedTask extends AsyncTask<String, Void, String> {
    private ProgressDialog progressDialog;
    private Intent intent;
    private String Symbol;
    private Context context;
    public getFeedTask(Context context, Intent intent, String Symbol){
        this.context = context;
        progressDialog = new ProgressDialog(context,R.style.MyTheme);
        this.intent = intent;
        this.Symbol = Symbol;
    }

    @Override
    protected void onPreExecute(){
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String json = null;
        try {
            URL url = new URL("http://stocksearch-1267.appspot.com/?feed="+Symbol);
            json = getJSONFromURL(url);
        }catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onPostExecute(String jsonObjects) {
        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        intent.putExtra("Feeds",jsonObjects);
        context.startActivity(intent);
        //super.onPostExecute(jsonObjects);
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
