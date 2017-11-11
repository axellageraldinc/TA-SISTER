package sister.ta.app.font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by axellageraldinc on 11/11/17.
 */

public class custom_font_1 extends TextView {
    public custom_font_1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/pacifico.ttf"));
    }
}
