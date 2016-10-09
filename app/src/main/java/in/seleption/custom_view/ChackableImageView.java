package in.seleption.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.Toast;

import in.seleption.chatpatfood.R;

/**
 * Created by Lokesh on 29-11-2015.
 */
public class ChackableImageView extends ImageView implements Checkable {

    private boolean mChecked = false;
    private Paint paint;

    public ChackableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    public ChackableImageView(Context context) {
        super(context);
        paint = new Paint();
        setWillNotDraw(false);

    }

    public ChackableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
        Toast.makeText(getContext(), "toggle  " + mChecked, Toast.LENGTH_SHORT)
                .show();

    }

    @Override
    public boolean isChecked() {
        Toast.makeText(getContext(), "checked", Toast.LENGTH_SHORT)
                .show();
        return mChecked;
    }

    @Override
    public void setChecked(final boolean checked) {
        if (mChecked == checked)
            return;
        mChecked = checked;

        setImageAlpha(mChecked ? 100 : 255);

        refreshDrawableState();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mChecked) {
            Bitmap check = BitmapFactory.decodeResource(getResources(), android.R.drawable.checkbox_on_background);
            canvas.drawBitmap(check, 0, 0, paint);
        }
    }
}