package liberus.utils;

import java.util.zip.Inflater;

import liberus.tarot.android.noads.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.graphics.Typeface;

public class EfficientAdapter extends BaseAdapter {
	 private LayoutInflater mInflater;
	 public String[] menu;
	 Context myContext;
	private int myListItem;
	 public EfficientAdapter(Context context, LayoutInflater inflater, String[] menuitems, int listItem) {
	 mInflater = inflater;
	 menu = menuitems;
	 myContext = context;
	 myListItem = listItem;
	 }

	 public int getCount() {
	 return menu.length;
	 }

	 public Object getItem(int position) {
	 return position;
	 }

	 public long getItemId(int position) {
	 return position;
	 }

	 public View getView(int position, View convertView, ViewGroup parent) {
	 ViewHolder holder;
	 if (convertView == null) {
		 convertView = mInflater.inflate(myListItem, null);
		 holder = new ViewHolder();
		 holder.text = (TextView) convertView.findViewById(R.id.menuitem);

		 convertView.setTag(holder);
	 } else {
		 holder = (ViewHolder) convertView.getTag();
	 }

	 holder.text.setText(menu[position]);     

	 return convertView;
	 }

	 class ViewHolder {
	 TextView text;

	 }
	 }
