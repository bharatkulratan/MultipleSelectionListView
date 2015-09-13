package info.techienotes.mutipleselectiondemo;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Bharat Kul Ratan(@bharatkulratan)
 */
public class CustomAdapter extends ArrayAdapter<String>{
    private SparseBooleanArray mSelectedItemsIds;
    private LayoutInflater inflater;
    private Context mContext;
    private List<String> list;

    public CustomAdapter (Context context, int resourceId, List<String> list) {
        super(context, resourceId, list);
        mSelectedItemsIds = new SparseBooleanArray();
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.list = list;
    }

    private static class ViewHolder {
        TextView itemName;
    }

    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.custom_textview, null);
            holder.itemName = (TextView) view.findViewById(R.id.custom_tv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.itemName.setText(list.get(position));
        return view;
    }

    @Override
    public void remove(String string) {
        list.remove(string);
        notifyDataSetChanged();
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}


