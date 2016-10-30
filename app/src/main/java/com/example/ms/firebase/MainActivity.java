package com.example.ms.firebase;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference myDatabase;
    String companyName;
    String exchange;
    String symbol;

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
        companyName = editTextName.getText().toString();
        exchange = editTextExchange.getText().toString();
        symbol = editTextSymbol.getText().toString();

        myDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.child("Exchange").child(exchange).hasChild(symbol)){

                    Company newCompany = new Company(companyName, exchange, symbol);

                    myDatabase.child("Exchange").child(exchange).child(symbol).setValue(newCompany);
                    Toast.makeText(MainActivity.this, "Company " + companyName + " is added to Firebase database", Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(MainActivity.this, "Company already exists in Firebase database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
