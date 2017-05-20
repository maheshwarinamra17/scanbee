package scanbee.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import scanbee.fragments.CompletedOrdersFragment;
import scanbee.fragments.PendingOrdersFragment;

/**
 * Created by namra on 09/02/17.
 */

public class OrdersPagerAdapter  extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public OrdersPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PendingOrdersFragment pendingOrdersFragment = new PendingOrdersFragment();
                return pendingOrdersFragment;
            case 1:
                CompletedOrdersFragment completedOrdersFragment = new CompletedOrdersFragment();
                return completedOrdersFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
