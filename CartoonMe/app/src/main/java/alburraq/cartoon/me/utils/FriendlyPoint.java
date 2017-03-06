package alburraq.cartoon.me.utils;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;

public class FriendlyPoint extends Point {
	
	private final Point neighbour;
	private final int width;
	public FriendlyPoint(final float x, final float y, final int col, final Point neighbour, int width) {
		super(x, y, col, width);
		this.neighbour = neighbour;
		this.width=Constants.brush_size;
		
	}
	
	@Override
	public void draw(final Canvas canvas, final Paint paint) {
	    paint.setFlags(Paint.ANTI_ALIAS_FLAG);      
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT)); 
        paint.setColor(Color.TRANSPARENT);
        paint.setStrokeWidth(width);
        canvas.drawLine(x, y, neighbour.x, neighbour.y, paint);
        
		
	}

	
	@Override
	public String toString() {
		return x + ", " + y + ", " + col + "; N[" + neighbour.toString() + "]";
	}
	
}
