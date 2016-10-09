package in.seleption.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.seleption.chatpatfood.R;
import in.seleption.chatpatfood.activity.RegisterStallActivity;
import in.seleption.model.Menu;

/**
 * Created by Lokesh on 30-11-2015.
 */
public class ImageAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Menu> lsItem;
    private RegisterStallActivity.OnClickFoodmenu onClickFoodmenu;

    public ImageAdapter(Context context, List<Menu> lsItem, RegisterStallActivity.OnClickFoodmenu onClickFoodmenu) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.onClickFoodmenu = onClickFoodmenu;
        this.lsItem = lsItem;
    }

    public int getCount() {
        return lsItem.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(
                    R.layout.item_category_menu, null);
            holder.imageview = (ImageView) convertView.findViewById(R.id.iv_category_menu);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.cb_item_category_check_box);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_menu_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        convertView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                CheckBox cb = (CheckBox) v.findViewById(R.id.cb_item_category_check_box);
                holder.checkbox.setChecked(!cb.isChecked());

                if (Build.VERSION.SDK_INT >= 16) {
                    if (cb.isChecked()) {
                        holder.imageview.setImageAlpha(50);
                        onClickFoodmenu.onClickFoodMenu(lsItem.get(position).getName(), position, true);
                    } else {
                        onClickFoodmenu.onClickFoodMenu(lsItem.get(position).getName(), position, false);
                        holder.imageview.setImageAlpha(255);
                    }
                }
            }
        });

        holder.imageview.setImageResource(lsItem.get(position).getDrwableId());
        holder.textView.setText(lsItem.get(position).getName());
        holder.checkbox.setChecked(false);
        holder.checkbox.setEnabled(false);
        holder.id = position;
        return convertView;
    }
}

class ViewHolder {
    ImageView imageview;
    CheckBox checkbox;
    TextView textView;
    int id;
}

