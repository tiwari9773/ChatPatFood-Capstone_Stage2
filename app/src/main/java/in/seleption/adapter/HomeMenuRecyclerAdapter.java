package in.seleption.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.seleption.chatpatfood.R;
import in.seleption.db_helper.TableContract;
import in.seleption.listener.HomeMenuClickListener;

/**
 * Created by Lokesh on 09-11-2015.
 */
public class HomeMenuRecyclerAdapter extends RecyclerView.Adapter<HomeMenuRecyclerAdapter.HomeMenuHolder> {

    private Cursor cursor;
    private HomeMenuClickListener onHomeMenuClickListener;

    public HomeMenuRecyclerAdapter(HomeMenuClickListener homeMenuClickListener) {
        this.onHomeMenuClickListener = homeMenuClickListener;
    }

    @Override
    public HomeMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_menu, parent, false);
        HomeMenuHolder weatherViewHolder = new HomeMenuHolder(view, onHomeMenuClickListener);
        return weatherViewHolder;
    }

    @Override
    public void onBindViewHolder(HomeMenuHolder holder, int position) {

        cursor.moveToPosition(position);
        String stallName = cursor.getString(cursor.getColumnIndex(TableContract.RegisterStall.NAME));
        holder.textView.setText(stallName);
    }

    @Override
    public int getItemCount() {
        if (cursor == null)
            return 0;
        else
            return cursor.getCount();
    }

    public class HomeMenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private HomeMenuClickListener onHomeMenuClickListener;
        private View parentView;

        public TextView textView;

        public HomeMenuHolder(View itemView, HomeMenuClickListener homeMenuClickListener) {
            super(itemView);
            this.onHomeMenuClickListener = homeMenuClickListener;
            parentView = itemView;
            textView = (TextView) parentView.findViewById(R.id.tv_menu_name);
            parentView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onHomeMenuClickListener != null)
                onHomeMenuClickListener.onMenuClickListener(getLayoutPosition());
        }
    }

    public void swapCursor(Cursor newC) {
        if (cursor != null)
            cursor.close();

        cursor = newC;
        notifyDataSetChanged();
    }
}
