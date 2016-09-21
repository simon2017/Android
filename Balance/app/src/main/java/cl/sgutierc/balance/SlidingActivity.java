package cl.sgutierc.balance;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cl.sgutierc.balance.view.ListableFragment;

/**
 * Created by sgutierc on 21-09-2016.
 */

public class SlidingActivity extends AppCompatActivity {
    private List<ListableFragment> fragmentItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_slide);
        ListView listview = (ListView) findViewById(R.id.navList);
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
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                loadFragment(fragmentItems.get(position));

            }
        });

    }

    private void loadNavListItems() {

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
}
