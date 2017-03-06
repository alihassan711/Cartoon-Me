package alburraq.cartoon.me.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class Constants {
	public static boolean is_already_added=false;
	public static int category_index=-2;
	public static boolean can_delete=false;
	public static boolean can_add=false;
	public static String [] cartoon_thumbnails;
	public static String [] boy_thumbnails;
	public static String [] girl_thumbnails;
	public static String [] glasses;
	public static String [] all_thumbnails;
	public static String [] all_images;
	public static int brush_size=2;
	public static boolean prefs_flag=false;
	public static boolean is_already_touched=true;
	public static final String pref_name="alburraq.cartoon.me.prefs";
	public static final String close_pref_name="alburraq.cartoon.me.close";
	public static final String admob_unit_id="ca-app-pub-8859190738948009/9175938578";
	public static boolean CLOSE_APPLICATION = false;
	public static NotificationManager my_notification_manager = null;
	public static boolean is_already_added_on_server= false;


	private static String temp;

	public static boolean isNetworkAvailable(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}
}
