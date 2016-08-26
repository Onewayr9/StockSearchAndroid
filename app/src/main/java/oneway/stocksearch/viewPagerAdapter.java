package oneway.stocksearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Oneway on 16/4/8.
 */
public class viewPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Bundle info;
    Details tab1;
    Charts tab2;
    Feeds tab3;

    public viewPagerAdapter(FragmentManager fm, int NumOfTabs, Bundle info) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.info = info;
        tab1 = new Details();
        Bundle bundle = new Bundle();
        bundle.putString("Details", info.getString("Details"));
        tab1.setArguments(bundle);

        tab2 = new Charts();
        Bundle bundle2 = new Bundle();
        bundle2.putString("Symbol", info.getString("Symbol"));
        tab2.setArguments(bundle2);

        tab3 = new Feeds();
        Bundle bundle3 = new Bundle();
        bundle3.putString("Feeds", info.getString("Feeds"));
        tab3.setArguments(bundle3);

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return tab1;
            case 1:
                return tab2;
            case 2:
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
