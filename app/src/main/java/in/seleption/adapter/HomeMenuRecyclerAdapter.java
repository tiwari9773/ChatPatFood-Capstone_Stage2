package in.seleption.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.seleption.Utility.JsonHelper;
import in.seleption.Utility.Utility;
import in.seleption.chatpatfood.R;
import in.seleption.db_helper.TableContract;
import in.seleption.listener.HomeMenuClickListener;
import in.seleption.model.Menu;

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
        holder.tvHead.setText(stallName);
        String sTime = cursor.getString(cursor.getColumnIndex(TableContract.RegisterStall.START_TIME));
        String eTime = cursor.getString(cursor.getColumnIndex(TableContract.RegisterStall.END_TIME));

        String s = cursor.getString(cursor.getColumnIndex(TableContract.RegisterStall.MENU));
        if (Utility.isValidText(s)) {

            ArrayList<Menu> menus = JsonHelper.ConvertToMenuObject(s);

            if (Utility.isValidList(menus)) {
                String text = "";
                for (Menu menu : menus) {
                    text = menu.getName() + " ,";
                }

                text = text.substring(0, text.length() - 2);
                holder.tvItem.setText(text);
            }

        }
        holder.tvTime.setText(sTime + " - " + eTime);
    }

    @Override
    public int getItemCount() {
        if (cursor == null)
            return 0;
        else
            return cursor.getCount();
    }

    class HomeMenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private HomeMenuClickListener onHomeMenuClickListener;
        private View parentView;

        public TextView tvHead;
        public TextView tvItem;
        public TextView tvTime;

        public HomeMenuHolder(View itemView, HomeMenuClickListener homeMenuClickListener) {
            super(itemView);
            this.onHomeMenuClickListener = homeMenuClickListener;
            parentView = itemView;
            tvHead = (TextView) parentView.findViewById(R.id.tv_menu_name);
            tvItem = (TextView) parentView.findViewById(R.id.tv_stall_name);
            tvTime = (TextView) parentView.findViewById(R.id.tv_timing);
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
