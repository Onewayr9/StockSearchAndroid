package oneway.stocksearch;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {
    String symbol;
    FavoriteListOperator operator;
    Bundle bundle;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        symbol = bundle.getString("Symbol");
        operator = new FavoriteListOperator(this);

        ActionBar actionBar = getSupportActionBar();
        try{
            actionBar.setTitle(new JSONObject(bundle.getString("Details")).getString("Name"));
        }catch(Exception e){
            e.printStackTrace();
        }
        actionBar.setDisplayHomeAsUpEnabled(true);

        TabLayout tab_layout = (TabLayout) findViewById(R.id.tabLayout);
        tab_layout.setTabTextColors(Color.GRAY,Color.BLACK);
        tab_layout.addTab(tab_layout.newTab().setText("CURRENT"));
        tab_layout.addTab(tab_layout.newTab().setText("HISTORICAL"));
        tab_layout.addTab(tab_layout.newTab().setText("NEWS"));

        final ViewPager view_pager = (ViewPager) findViewById(R.id.pager);

        final viewPagerAdapter adapter = new viewPagerAdapter
                (getSupportFragmentManager(), tab_layout.getTabCount(),bundle);

        view_pager.setAdapter(adapter);

        view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));

        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem favorite = menu.findItem(R.id.action_favorite);
        if(operator.containsSymbol(symbol)){
            favorite.setIcon(R.drawable.ic_star_yellow);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_favorite:
                if(operator.containsSymbol(symbol)){
                    operator.delete(symbol);
                    item.setIcon(R.drawable.ic_star_white);
                }else{
                    operator.add(symbol);
                    Toast.makeText(DetailsActivity.this, "Bookmarked "+bundle.getString("Name")+"!!", Toast.LENGTH_SHORT).show();
                    item.setIcon(R.drawable.ic_star_yellow);
                }
                return true;
            case R.id.action_share:
                try{
                    JSONObject details = new JSONObject(bundle.getString("Details"));
                    String name = details.getString("Name");
                    String value = details.getString("LastPrice");
                    String image = "http://chart.finance.yahoo.com/t?s="+details.getString("Symbol")+"&lang=en-US&width=300&height=200";
                    ShareLinkContent content = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("http://finance.yahoo.com/q?s="+details.getString("Symbol")))
                            .setContentTitle("Current Stock Price of "+name+", "+value)
                            .setContentDescription("Stock information of "+name)
                            .setImageUrl(Uri.parse(image))
                            .build();
                    ShareDialog shareDialog = new ShareDialog(this);
                    shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                        @Override
                        public void onSuccess(Sharer.Result result) {
                            Toast.makeText(DetailsActivity.this, "You shared this post.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onError(FacebookException error) {

                        }
                    });
                    shareDialog.show(content);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
