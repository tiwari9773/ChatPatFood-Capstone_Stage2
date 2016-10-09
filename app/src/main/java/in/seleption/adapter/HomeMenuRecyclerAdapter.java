package in.seleption.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.seleption.chatpatfood.R;
import in.seleption.listener.HomeMenuClickListener;
import in.seleption.model.Menu;

/**
 * Created by Lokesh on 09-11-2015.
 */
public class HomeMenuRecyclerAdapter extends RecyclerView.Adapter<HomeMenuRecyclerAdapter.HomeMenuHolder> {

    private List<Menu> items;
    private HomeMenuClickListener onHomeMenuClickListener;

    public HomeMenuRecyclerAdapter(List<Menu> items, HomeMenuClickListener homeMenuClickListener) {
        this.items = items;
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
        holder.imageView.setImageResource(items.get(position).getDrwableId());
        holder.textView.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HomeMenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private HomeMenuClickListener onHomeMenuClickListener;
        private View parentView;

        public ImageView imageView;
        public TextView textView;

        public HomeMenuHolder(View itemView, HomeMenuClickListener homeMenuClickListener) {
            super(itemView);
            this.onHomeMenuClickListener = homeMenuClickListener;
            parentView = itemView;
            imageView = (ImageView) parentView.findViewById(R.id.iv_home_menu);
            textView = (TextView) parentView.findViewById(R.id.tv_menu_name);
            parentView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onHomeMenuClickListener != null)
                onHomeMenuClickListener.onMenuClickListener(getLayoutPosition());
        }
    }
}
