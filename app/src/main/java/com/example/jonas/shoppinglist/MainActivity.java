package com.example.jonas.shoppinglist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.jonas.shoppinglist.ButtonFragment.OnFragmentInteractionListener;
import com.example.jonas.shoppinglist.communication.MailSender;
import com.example.jonas.shoppinglist.processes.ShoppingContacts;
import com.example.jonas.shoppinglist.processes.ShoppingMessages;

import java.util.ArrayList;

public class MainActivity extends Activity implements OnNewItemAddedListener, OnFragmentInteractionListener {

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
        final ShoppingListFragment shoppingListFragment = (ShoppingListFragment) fm.findFragmentById(R.id.shoppingListFragment);
        final ButtonFragment buttonFragment = (ButtonFragment) fm.findFragmentById(R.id.buttonFragment);

        buttonFragment.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.clear();
                int counter = 0;
                for(String item : shoppingItems) {
                    myDb.insertData(item);
                    counter++;
                }

                new ShoppingContacts(MainActivity.this).execute();
            }
        });

        buttonFragment.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.clear();
                aa = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, new ArrayList<String>());
                shoppingListFragment.setListAdapter(aa);

                new ShoppingMessages(MainActivity.this).execute();
            }
        });
        //the list of shopping items
        shoppingItems = myDb.getAllItems();

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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
