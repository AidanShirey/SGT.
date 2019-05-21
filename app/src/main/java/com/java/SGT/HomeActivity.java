package com.java.SGT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends Activity {
    private ListView cards;
    private CardArrayAdapter cardArrayAdapter;
    private CardView card;
    private TextView name;
    private TextView rank;
    private String namestring = "Name";
    private User user;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        cards = findViewById(R.id.lvcard);
        name = findViewById(R.id.name);
        Intent mintent = getIntent();
        namestring = mintent.getExtras().getString("username");
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
        userRef.child(namestring).child("usern").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class);
                name.setText(temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        rank = findViewById(R.id.rank);
        cards.addHeaderView(new View(this));
        cards.addFooterView(new View(this));
        cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.cardviewlayout);

        for (int i = 0; i < 10; i++) {
            cardArrayAdapter.add(card);
        }
        cards.setAdapter(cardArrayAdapter);
        cards.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // switch to briefing activity
                Intent intent = new Intent(getApplicationContext(), BriefingActivity.class);
                intent.putExtra("taskselect", position);
                startActivity(intent);
            }
        });
    }
}
