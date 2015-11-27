package com.example.renan.testepls.helper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.renan.testepls.adapter.IngredientAdapter;

/**
 * Created by c1284141 on 24/09/2015.
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final IngredientAdapter mAdapter;
    private final Boolean mDragDrop, mDismiss;

    public SimpleItemTouchHelperCallback(IngredientAdapter adapter, Boolean dragDrop, Boolean dismiss) {
        mAdapter = adapter;
        mDragDrop = dragDrop;
        mDismiss = dismiss;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = mDragDrop ? ItemTouchHelper.UP | ItemTouchHelper.DOWN : 0;
        int swipeFlags = mDismiss ? ItemTouchHelper.START | ItemTouchHelper.END : 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

}