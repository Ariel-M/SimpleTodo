package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import static com.codepath.simpletodo.MainActivity.Item_Position;
import static com.codepath.simpletodo.MainActivity.Item_Text;

public class EditItemActivity extends AppCompatActivity {

    EditText etITemText;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etITemText = (EditText) findViewById(R.id.etItemText);
        etITemText.setText(getIntent().getStringExtra(Item_Text));
        position = getIntent().getIntExtra(Item_Position, 0);

        getSupportActionBar().setTitle("Edit Item");

    }
    public void onSaveItem(View v){
        Intent i = new Intent();

        i.putExtra(Item_Text, etITemText.getText().toString());
        i.putExtra(Item_Position, position);
        setResult(RESULT_OK, i);
        finish();
    }
}
