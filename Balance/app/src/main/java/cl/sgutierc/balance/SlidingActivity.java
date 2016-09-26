package cl.sgutierc.balance;


import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

import cl.sgutierc.balance.view.ListableFragment;

/**
 * Created by sgutierc on 21-09-2016.
 */

public class SlidingActivity extends AppCompatActivity {
    private List<ListableFragment> fragmentItems;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_slide);
        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listview = (ListView) findViewById(R.id.navList);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed) {
            public void onDrawerClosed(View view) {
                //getSupportActionBar().setTitle("");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                //getSupportActionBar().setTitle("OPEN");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.addDrawerListener(mToggle);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        {
            fragmentItems = new ArrayList<>();
            ListableFragment resumenFrg = new ResumenActivity();

            fragmentItems.add(new GastoActivity());
            fragmentItems.add(new PresupuestoActivity());
            fragmentItems.add(new CategoriaActivity());
            fragmentItems.add(resumenFrg);

            ArrayAdapter<ListableFragment> itemsAdapter = new ArrayAdapter<ListableFragment>(this, android.R.layout.simple_list_item_1, fragmentItems);
            listview.setAdapter(itemsAdapter);
            loadFragment(resumenFrg);
            listview.setItemChecked(fragmentItems.size() - 1, true);
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                loadFragment(fragmentItems.get(position));
                listview.setItemChecked(position, true);
                drawerLayout.closeDrawers();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleDrawer() {
        View view = findViewById(R.id.drawerLayout);
        boolean isOpen = drawerLayout.isDrawerOpen(view);
        if (isOpen)
            drawerLayout.closeDrawer(view);
        else
            drawerLayout.openDrawer(view);
    }

    private void loadFragment(ListableFragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, fragment)
                .commit();

        this.setTitle(fragment.getTitle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mToggle.onConfigurationChanged(newConfig);
    }
}
