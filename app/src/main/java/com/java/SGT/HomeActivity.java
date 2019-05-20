package com.java.SGT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class HomeActivity extends Activity {
    private ListView cards;
    private CardArrayAdapter cardArrayAdapter;
    private CardView card;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        cards = findViewById(R.id.lvcard);
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
