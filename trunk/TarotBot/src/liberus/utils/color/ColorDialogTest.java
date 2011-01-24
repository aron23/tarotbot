package liberus.utils.color;

import liberus.tarot.android.noads.R;
import android.app.Activity;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ColorDialogTest extends Activity implements ColorDialog.OnClickListener, OnClickListener {
    private TextView b0;
	private TextView b1;
	private TextView b2;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

	private void setup(TextView tv, int color) {
        PaintDrawable left = new PaintDrawable(color);
        left.setCornerRadius(8);
        left.setBounds(0, 0, 48, 32);
		tv.setCompoundDrawables(left, null, null, null);
		tv.setOnClickListener(this);
	}

	public void onClick(Object tag, int color) {
		TextView tv = (TextView) tag;
		PaintDrawable left = (PaintDrawable) tv.getCompoundDrawables()[0];
		left.getPaint().setColor(color);
	}

	public void onClick(View view) {
		TextView tv = (TextView) view;
		boolean useAlpha = view == b1 || view == b2;
		PaintDrawable left = (PaintDrawable) tv.getCompoundDrawables()[0];
		int color = left.getPaint().getColor();
		ColorDialog dialog = new ColorDialog(this, useAlpha, view, color, this, view == b1? 0 : R.drawable.wheel);
		dialog.show();
	}
}