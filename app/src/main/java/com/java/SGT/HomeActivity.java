package com.java.SGT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends Activity {
    private Context mContext;
    private ListView cards;
    private CardArrayAdapter cardArrayAdapter;
    private ListView cardcomplete;
    private CardArrayAdapter comcard;
    private ListView reference;
    private CardArrayAdapter refAdapter;
    private CardView card;
    private CardView refcard;
    private TextView name;
    private TextView rank;
    private Integer id = 1001;
    private String namestring = "Name";
    private Integer position;
    private User user;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Find all views that may need to be modified
        cards = findViewById(R.id.lvcard);
        reference = findViewById(R.id.reference);
        name = findViewById(R.id.name);
        rank = findViewById(R.id.rank);


        Intent mintent = getIntent();
        namestring = mintent.getExtras().getString("username");

        // Set up database for queries
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");

        // Set up top left corner name
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


        // Set up list
        cards.addHeaderView(new View(this));
        cards.addFooterView(new View(this));

        // Set up card adapters
        cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.id.lvcard);
        comcard = new CardArrayAdapter(getApplicationContext(), R.layout.cardviewcomplete);
        refAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.cardviewlayout);


        for (int i = 0; i < 10; i++) {
            addcard(i);
        }
        cards.setAdapter(cardArrayAdapter);
        cards.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // switch to briefing activity
                Intent intent = new Intent(getApplicationContext(), BriefingActivity.class);
                intent.putExtra("taskselect", position);
                intent.putExtra("username", namestring);
                startActivity(intent);
            }
        });


    }

    public void addcard(Integer pos)
    {
        pos++;
        position = pos;
        String posit = Integer.toString(pos);
        String quizselect = "q" + posit;


        userRef.child(namestring).child(quizselect).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean temp = dataSnapshot.getValue(boolean.class);
                if (!temp) {
                    ListViewItem list = new ListViewItem(card, CardArrayAdapter.TYPE_INCOMPLETE);
                    cardArrayAdapter.add(list);
                }
                if (temp) {
                    ListViewItem list = new ListViewItem(card, CardArrayAdapter.TYPE_COMPLETE);
                    cardArrayAdapter.add(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
