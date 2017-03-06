package alburraq.cartoon.me.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;

public class Point {
	public final float x, y;
	public final int col;
	public final int width;
	
	public Point(final float x, final float y, final int col, final int width) {
		this.x = x;
		this.y = y;
		this.col = col;
		this.width = width;
	}
	
	public void draw(final Canvas canvas, final Paint paint) {
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);      
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT)); 
        paint.setColor(Color.TRANSPARENT);
      //paint.setMaskFilter(new BlurMaskFilter(10, Blur.NORMAL));
		paint.setStrokeWidth(width);
		//canvas.drawCircle(x, y, width/2, paint);
	}
	
	@Override
	public String toString() {
		return x + ", " + y + ", " + col;
	}


}
