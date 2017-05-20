package scanbee.activities;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import scanbee.adapters.OrdersPagerAdapter;
import scanbee.utils.BasicSetup;
import scanbee.utils.TestingData;

public class OrdersActivity extends AppCompatActivity {

    Activity activity;
    BasicSetup basicSetup;
    TestingData testingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        activity = this;
        basicSetup = new BasicSetup(activity);
        testingData = new TestingData();
        basicSetup.setupCustomToolbar(getString(R.string.orders));
        setTabsAndPager();

    }

    public void setTabsAndPager(){

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.pending_orders)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.completed_orders)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        setupTabsFont(tabLayout);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.orders_pager);
        final OrdersPagerAdapter adapter = new OrdersPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void setupTabsFont(TabLayout tabLayout){
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = tabLayout.getTabCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(basicSetup.getNuniR());
                }
            }
        }
    }
}
