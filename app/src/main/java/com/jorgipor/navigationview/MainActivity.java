package com.jorgipor.navigationview;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jorgipor.navigationview.fragments.InboxFragment;
import com.jorgipor.navigationview.fragments.LoginFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoginFragment.OnFragmentInteractionListener {

    private static final String SELECTED_ITEM_ID = "selected_item_id";
    Toolbar toolbar;
    private NavigationView mDrawer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        mDrawer = (NavigationView) findViewById(R.id.main_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mDrawer.setNavigationItemSelectedListener(this);

        // Cargamos el fragment inicial
        mSelectedItem = savedInstanceState == null ? R.id.nav_item_1 : savedInstanceState.getInt(SELECTED_ITEM_ID);
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        LoginFragment loginFragment = LoginFragment.newInstance("", "");
        fragmentManager.beginTransaction()
                .add(R.id.container, loginFragment)
                .commit();*/
        navigate(mSelectedItem);

    }

    private void navigate(int mSelectedItem) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (mSelectedItem) {
            case R.id.nav_item_1:
                LoginFragment loginFragment = LoginFragment.newInstance("", "");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, loginFragment)
                        .commit();
                getSupportActionBar().setTitle(R.string.nav_item_1);
                break;

            default:
                InboxFragment inboxFragment = InboxFragment.newInstance("", "");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, inboxFragment)
                        .commit();
                getSupportActionBar().setTitle(R.string.nav_item_2);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        menuItem.setChecked(true);
        mSelectedItem = menuItem.getItemId();

        navigate(mSelectedItem);

        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mDrawer);
        }

        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, mSelectedItem);
    }

    @Override
    public void onBackPressed() {

        if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
        {
            mDrawerLayout.closeDrawer(mDrawer);
        }
        else
        {
            super.onBackPressed();
        }
    }
}
