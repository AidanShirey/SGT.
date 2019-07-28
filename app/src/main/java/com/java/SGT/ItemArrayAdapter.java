package com.java.SGT;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemArrayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_INCOMPLETE = 0;
    public static final int TYPE_COMPLETE = 1;


    private ArrayList<ListViewItem> itemList;

    // Constructor of the class
    public ItemArrayAdapter(ArrayList<ListViewItem> itemList) {
        this.itemList = itemList;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // determine which layout to use for the row
    @Override
    public int getItemViewType(int position) {
        ListViewItem item = itemList.get(position);
        if (item.getType() == TYPE_INCOMPLETE) {
            return TYPE_INCOMPLETE;
        } else if (item.getType() == TYPE_COMPLETE) {
            return TYPE_COMPLETE;
        } else {
            return -1;
        }
    }


    // specify the row layout file and click for each row
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_INCOMPLETE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewlayout, parent, false);
            return new ViewHolderOne(view);
        } else if (viewType == TYPE_COMPLETE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewcomplete, parent, false);
            return new ViewHolderOne(view);
        } else {
            throw new RuntimeException("The type has to be ONE or TWO");
        }
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int listPosition) {
        switch (holder.getItemViewType()) {
            case TYPE_INCOMPLETE:
                initLayoutOne((ViewHolderOne) holder, listPosition);
                break;
            case TYPE_COMPLETE:
                initLayoutOne((ViewHolderOne) holder, listPosition);
                break;
            default:
                break;
        }
    }

    private void initLayoutOne(ViewHolderOne holder, int pos) {
        int realpos = pos + 1;
        String task = "Task " + realpos;
        holder.item.setText(task);
    }


    // Static inner class to initialize the views of rows
    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView item;

        public ViewHolderOne(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.line1);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView rv) {
        super.onAttachedToRecyclerView(rv);
    }

}