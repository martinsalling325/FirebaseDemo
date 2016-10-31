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

import java.util.ArrayList;

import im.dacer.androidcharts.LineView;
import im.dacer.androidcharts.PieHelper;
import im.dacer.androidcharts.PieView;

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

        ArrayList<String> strList = new ArrayList<>();
        strList.add("Dette");
        strList.add("Er");
        strList.add("En");
        strList.add("Test");
        strList.add("bla");

        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();

        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(200);
        integers.add(500);
        integers.add(1000);
        integers.add(720);
        integers.add(400);
        dataLists.add(integers);

        ArrayList<Integer> integers2 = new ArrayList<>();
        integers2.add(1200);
        integers2.add(1500);
        integers2.add(1300);
        integers2.add(820);
        integers2.add(600);
        dataLists.add(integers2);

        LineView lineView = (LineView)findViewById(R.id.line_view);
        lineView.setDrawDotLine(false); //optional
        lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY); //optional
        lineView.setBottomTextList(strList);
        lineView.setDataList(dataLists);

        PieView pieView = (PieView)findViewById(R.id.pie_view);
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<>();

        PieHelper p1 = new PieHelper(12);
        PieHelper p2 = new PieHelper(40);
        PieHelper p3 = new PieHelper(8);
        PieHelper p4 = new PieHelper(30);
        PieHelper p5 = new PieHelper(10);
        pieHelperArrayList.add(p1);
        pieHelperArrayList.add(p2);
        pieHelperArrayList.add(p3);
        pieHelperArrayList.add(p4);
        pieHelperArrayList.add(p5);

        pieView.setDate(pieHelperArrayList);
        pieView.selectedPie(2); //optional
        //pieView.setOnPieClickListener(listener) //optional
        pieView.showPercentLabel(true); //optional
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
