package com.example.ms.firebase;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference myDatabase;

    TextView textView;
    EditText editTextName;
    EditText editTextExchange;
    EditText editTextSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabase = FirebaseDatabase.getInstance().getReference();

        textView = (TextView) findViewById(R.id.textViewCompany);
        editTextName = (EditText) findViewById(R.id.editTextCompanyName);
        editTextExchange = (EditText) findViewById(R.id.editTextExchange);
        editTextSymbol = (EditText) findViewById(R.id.editTextSymbol);
    }

    public void buttonWriteClicked(View view)
    {
        String companyName = editTextName.getText().toString();
        String exchange = editTextExchange.getText().toString();
        String symbol = editTextSymbol.getText().toString();

        Company newCompany = new Company(companyName, exchange, symbol);

        myDatabase.child("Exchange").child(exchange).child(symbol).setValue(newCompany);
    }
}
