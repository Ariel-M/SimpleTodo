package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final static int Edit_Request_Code = 20;

    public final static String Item_Text = "itemText";
    public final static String Item_Position = "itemPosition";

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);


        setupListViewListener();
    }
    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
        Toast.makeText(getApplicationContext(), "Item Added to List", Toast.LENGTH_SHORT).show();

    }
    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra(Item_Text, items.get(position));
                i.putExtra(Item_Position, position);
                startActivityForResult(i, Edit_Request_Code);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == Edit_Request_Code){
            String updatedItem = data.getExtras().getString(Item_Text);

            int position = data.getExtras().getInt(Item_Position);

            items.set(position, updatedItem);

            itemsAdapter.notifyDataSetChanged();

            writeItems();

            Toast.makeText(this, "Item Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private File getDataFile (){
        return new File(getFilesDir(), "todo.txt");
    }
    private void readItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }catch (IOException e){
            Log.e("Main Activity", "Error Reading File", e);
            items = new ArrayList<>();
        }
    }
    private void writeItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("Main Activity", "Error Reading File", e);
        }
    }
}
