package in.seleption.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import in.seleption.chatpatfood.R;


/**
 * Created by Lokesh on 01-11-2015.
 */
public class RecycleMarginDecoration extends RecyclerView.ItemDecoration {

    private int margin;

    public RecycleMarginDecoration(Context context) {
        margin = context.getResources().getDimensionPixelSize(R.dimen.padding_four);
    }

    @Override
    public void getItemOffsets(
            Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(margin, margin / 2, margin / 2, 0);
    }
}
