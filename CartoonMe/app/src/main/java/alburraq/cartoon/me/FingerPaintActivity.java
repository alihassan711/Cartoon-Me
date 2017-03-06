package alburraq.cartoon.me;

import java.io.ByteArrayOutputStream;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;



import alburraq.cartoon.me.utils.AlertDialogManager;
import alburraq.cartoon.me.utils.Constants;
import alburraq.cartoon.me.utils.DrawView;


public class FingerPaintActivity extends Activity {
	DrawView drawView;
	Bitmap img,save_after_transparent;
	BitmapDrawable drawable;
	RelativeLayout relativeLayout_paint;
	Bundle savedInstanceState;
	Canvas c2;
	String[] menu;
    DrawerLayout dLayout;
    ListView dList;
    AlertDialog.Builder builder;
    ArrayAdapter<String> adapter;
    AlertDialog dialog;
    SeekBar volumeControl;
   // ShowcaseView sv;
    Handler handler;
    Runnable runnable;
   // ShowcaseView.ConfigOptions co;
    Menu brush;
    String my_token;
    boolean flag = true;
	int progressChanged = 0;
	Button save_image,cancel_image,hidden_button;
    boolean  already_shown=false,already_clicked=false;
    int start_location [];
    boolean is_onResume_done=false;
    SharedPreferences prefs;
    Context context;
    String show_transparent = null;
    /** Called when the activity is first created. */
    @SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        context = getApplicationContext();
        if(already_clicked==false)
        {	
   	 		ActionBar bar = getActionBar();
   	 		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffba00")));
   	 		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
   	 		Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
   	 		TextView abTitle = (TextView) findViewById(titleId);
   	 		abTitle.setTextColor(Color.BLACK);	
   	 		abTitle.setTypeface(tf);
   	 		already_clicked=true;
        }
//      
//		if(!already_shown)
//		{
//		//	Toast.makeText(getApplicationContext(), "Trasparent undesired areas on Touch",Toast.LENGTH_LONG).show();
//			already_shown=true;
//		
//		}
        prefs = context.getSharedPreferences(Constants.pref_name,Context.MODE_PRIVATE);
        show_transparent = prefs.getString("transparent",null);
        if(show_transparent==null)
        {
        	AlertDialogManager ad = new AlertDialogManager();
        	ad.showAlertDialog(this,"Alert!","Transparent desired areas on touch", true);
        	SharedPreferences.Editor editor = prefs.edit();
        	editor.putString("transparent","done");
        	editor.commit();
        }
		Constants.CLOSE_APPLICATION = false;
        relativeLayout_paint = (RelativeLayout) findViewById(R.id.relativeLayout_paint);
        hidden_button =(Button) findViewById(R.id.hidden_button_activity_draw);
//        co = new ShowcaseView.ConfigOptions();
//        co.block=false;
        save_image = (Button) findViewById(R.id.button_save_image);
        cancel_image = (Button) findViewById(R.id.button_cancel_image);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
        save_image.setTypeface(tf);
        cancel_image.setTypeface(tf);
        drawView = new DrawView(this);
        drawView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        relativeLayout_paint.addView(drawView);
        setRequestedOrientation(getResources().getConfiguration().orientation);
        byte[] byteArray = getIntent().getByteArrayExtra("final_image");
        img = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        drawable = new BitmapDrawable(img);
        drawView.setBackground(drawable);
        drawView.requestFocus();
        start_location = new int[4];
        save_image.setOnClickListener(new View.OnClickListener()
        {
        	@Override
        	public void onClick(View v)
        	{
        	
        		relativeLayout_paint.setDrawingCacheEnabled(false);
        	    relativeLayout_paint.buildDrawingCache(false);
        		relativeLayout_paint.setDrawingCacheEnabled(true);
        	    relativeLayout_paint.buildDrawingCache(true);
        	    save_after_transparent = Bitmap.createBitmap(relativeLayout_paint.getDrawingCache());
        	    save_after_transparent = Bitmap.createScaledBitmap(save_after_transparent, 150, 200, true);
        	    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        	    save_after_transparent.compress(Bitmap.CompressFormat.PNG, 10, bytes);
                byte[] byteArray = bytes.toByteArray();
                try
                {
                	Intent intent = new Intent (FingerPaintActivity.this,FinalImageActivity.class);
                	intent.putExtra("final_image2", byteArray);
                	intent.putExtra("token",my_token);
                	startActivity(intent);
         
                	
                }
                catch(Exception e)
                {
                	e.printStackTrace();
                	
                }
               // finish();
                
        	}
        });
        cancel_image.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 relativeLayout_paint.setDrawingCacheEnabled(false);
     	         relativeLayout_paint.buildDrawingCache(false);
     	         
     	         onCreate(savedInstanceState);
				
			}
		});
      
      
        
    }
	@SuppressLint("NewApi")
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		hidden_button.getLocationInWindow(start_location);
       	Display display = getWindowManager().getDefaultDisplay();
		final Point size = new Point();
		display.getSize(size);
		float height = size.y;
		@SuppressWarnings("unused")
		float centerY=height/2;
		float width = size.x;
		@SuppressWarnings("unused")
		float centerX = width/2;
    	
//		if(sv!=null)
//    	{
//    		sv.animateGesture(centerX,centerY,2*centerX,start_location[1]-centerY);
//    	}
	}
	
    

    

	@SuppressLint("NewApi")
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.transparent_activity_action_bar, menu);
//        my_token = getIntent().getStringExtra("token");
//        if(my_token==null)
//        {
//        	sv = ShowcaseView.insertShowcaseViewWithType(ShowcaseView.ITEM_ACTION_ITEM, R.id.action_brush, this,
//                "Remove Undesired Areas", "Choose your Brush Size and remove\neverything except your face\nin order to get best looks",co);
//        	save_image.setEnabled(false);
//        	cancel_image.setEnabled(false);
//        	drawView.setEnabled(false);
//        }
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// Handle item selection
    	switch (item.getItemId())
    	{
    	case R.id.action_brush:
    		
    		changeBrushSize();
//    		save_image.setEnabled(true);
//        	cancel_image.setEnabled(true);
//        	drawView.setEnabled(true);
    	}
    	return true;
    }

    
    @SuppressLint("InflateParams")
	public void changeBrushSize()
    {	
//    		if(sv!=null)
//    			sv.setVisibility(View.GONE);
    	    LayoutInflater li = LayoutInflater.from(this);
		    View view = li.inflate(R.layout.activity_seekbar, null);
		    AlertDialog.Builder alert = new AlertDialog.Builder(this);
		    alert.setView(view);
		    dialog = alert.create();
		    dialog.show();
		
	
			relativeLayout_paint.setDrawingCacheEnabled(true);
    	    relativeLayout_paint.buildDrawingCache(true);
    	    save_after_transparent = Bitmap.createBitmap(relativeLayout_paint.getDrawingCache());
    	    save_after_transparent = Bitmap.createScaledBitmap(save_after_transparent, 500, 500, true);
		    volumeControl = (SeekBar) view.findViewById(R.id.seekbar_brush);
		    volumeControl.setDrawingCacheBackgroundColor(Color.YELLOW);
		    volumeControl.setProgress(progressChanged);
		    volumeControl.getProgressDrawable().setColorFilter(Color.parseColor("#ffba00"), Mode.SRC_IN);
			volumeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
		

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				progressChanged = progress;
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@SuppressWarnings("deprecation")
			public void onStopTrackingTouch(SeekBar seekBar) {
				Constants.brush_size=progressChanged;
				volumeControl.setProgress(progressChanged);
        	    drawView.setBackgroundDrawable(new BitmapDrawable(save_after_transparent));
				dialog.dismiss();
			}
		});
		 

	
    }
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(Constants.CLOSE_APPLICATION)
			finish();
	
	}
//	public void displayInterstitial() 
//	{
//		// If Ads are loaded, show Interstitial else show nothing.
//		if (interstitial.isLoaded()) {
//			interstitial.show();
//		}
//	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}