package com.example.renan.testepls.helper;

/**
 * Created by c1284141 on 24/09/2015.
 */
public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
