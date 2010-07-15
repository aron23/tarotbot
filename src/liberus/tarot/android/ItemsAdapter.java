package liberus.tarot.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class ItemsAdapter extends ArrayAdapter<Object> {

    private Object[] items;
    private LayoutInflater vi;
    public ItemsAdapter(Context context, int textViewResourceId, Object[] items, LayoutInflater inflater) {
            super(context, textViewResourceId, items);
            this.items = items;
            vi = inflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            /*if (v == null) {
            	v = vi.inflate(R.layout.anticipatequery, null);
            }*/

            Object it = items[position];
            if (it != null) {
                    ImageView iv = (ImageView) v.findViewById(R.id.activecard);
                    if (iv != null) {
                            //iv.setImageDrawable(R.drawable.cups_01);
                    }
            }

            return v;
    }
}
