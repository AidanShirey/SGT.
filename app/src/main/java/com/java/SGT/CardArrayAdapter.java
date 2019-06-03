package com.java.SGT;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardArrayAdapter  extends ArrayAdapter<ListViewItem> {
    public static final int TYPE_INCOMPLETE = 0;
    public static final int TYPE_COMPLETE = 1;

    private static final String TAG = "CardArrayAdapter";
    private List<ListViewItem> cardList = new ArrayList<ListViewItem>();

    static class CardViewHolder {
        TextView line1;
    }

    public CardArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }
    @Override
    public int getItemViewType(int position){
        return cardList.get(position).getType();

    }

    @Override
    public void add(ListViewItem object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public ListViewItem getItem(int index) {
        return this.cardList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder viewHolder;
        int listtype = getItemViewType(position);
        if (row == null) {
            if(listtype == TYPE_COMPLETE)
                row = LayoutInflater.from(getContext()).inflate(R.layout.cardviewcomplete, null);
            else
                row = LayoutInflater.from(getContext()).inflate(R.layout.cardviewlayout, null);
            viewHolder = new CardViewHolder();
            viewHolder.line1 = row.findViewById(R.id.line1);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder) row.getTag();
        }
        int realpos = position + 1;
        ListViewItem card = getItem(position);
        String textset = "Task " + realpos;
        viewHolder.line1.setText(textset);
        return row;
    }
}