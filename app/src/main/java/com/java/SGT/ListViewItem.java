package com.java.SGT;

import android.support.v7.widget.CardView;

public class ListViewItem {
    private CardView card;
    private int type;

    public ListViewItem(CardView card, int type) {
        this.card = card;
        this.type = type;
    }

    public CardView getCard() {
        return card;
    }

    public void setText(CardView card) {
        this.card = card;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
