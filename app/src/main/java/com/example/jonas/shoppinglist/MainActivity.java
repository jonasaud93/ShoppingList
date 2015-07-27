package com.example.jonas.shoppinglist;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends Activity implements OnNewItemAddedListener {

    private ArrayAdapter<String> aa;
    private ArrayList<String> shoppingItems;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflating the view
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        //References to the fragments
        FragmentManager fm = getFragmentManager();
        ShoppingListFragment shoppingListFragment = (ShoppingListFragment) fm.findFragmentById(R.id.shoppingListFragment);

        //the list of shopping items
        shoppingItems = new ArrayList<String>();

        //the array adapter, which binds the shopping list items array to the listview
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,shoppingItems);

        //Bind the adapter to the listview
        shoppingListFragment.setListAdapter(aa);
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
    public void onNewItemAdded(String newItem) {
        shoppingItems.add(newItem);
        aa.notifyDataSetChanged();
    }
}
