package alburraq.cartoon.me;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import alburraq.cartoon.me.adapter.GlassesAdapter;
import alburraq.cartoon.me.adapter.NavDrawerListAdapter;
import alburraq.cartoon.me.gestures.MoveGestureDetector;
import alburraq.cartoon.me.gestures.RotateGestureDetector;
import alburraq.cartoon.me.utils.Constants;
import alburraq.cartoon.me.utils.NavDrawerItem;
import com.appbrain.AppBrain;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.FailReason;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


@SuppressWarnings("deprecation")
@SuppressLint("DefaultLocale")
public class WriteActivity extends Activity	implements OnTouchListener	{
		boolean isImageSaved=false;
	    private Matrix mMatrix = new Matrix();
	    private float mScaleFactor = 0.9f;
	    private float mRotationDegrees = 0.f;
	    private float mFocusX = 0.f;
	    private float mFocusY = 0.f;
	    private int mImageHeight, mImageWidth;
	    private ScaleGestureDetector mScaleDetector;
	    private RotateGestureDetector mRotateDetector;
	    private MoveGestureDetector mMoveDetector;
	    ImageView background;
	    FrameLayout root_layout;
	    Bitmap savedImage;
	    String write_something="";
	    String filename;
	    String [] glasses = {"Black Glasses","Blue Glasses","Parrot Green Glasses","Sea Green Glasses","Purple Glasses","Teal Glasses","Yellow Glasses"};
	    Integer [] glasses_images = {R.drawable.glasses_1,R.drawable.glasses_2,R.drawable.glasses_3,R.drawable.glasses_4,R.drawable.glasses_5,R.drawable.glasses_6,R.drawable.glasses_7};
	    String [] texts = new String []{"Simple","Rabiohead","Daniel","Desyrel","Segoe-Print","Roboto-Light",};
	    String [] colors = {"Black","Green","Blue","Red","Yellow"};
	    List<String> listDataHeader;
		private ArrayList<NavDrawerItem> navDrawerItems;
	    HashMap<String, List<String>> listDataChild;
	    private String[] navMenuTitles;
	    private TypedArray navMenuIcons;
		DrawerLayout mDrawerLayout;
		ListView mDrawerList;
		NavDrawerListAdapter adapter;
		boolean drawerOpen;
//		ImageLoader imageLoader;
		ImageView imageView_glasses,launcher_icon ;
		ImageView iv;
		ProgressDialog pd;
		Context context;
		boolean rated = false,is_share_save_Called=false;
		boolean is_keep_discard_show=false;
		ImageView open_playstore;
		ImageButton drawer_image_button,glasses_image_button,write_image_button,save_image_button,share_image_button;
		private ActionBarDrawerToggle mDrawerToggle;
		@SuppressLint("InflateParams")
		protected void onCreate(Bundle savedInstanceState) {
			 super.onCreate(savedInstanceState);
			 AppBrain.init(this);
	 
			 ActionBar bar = getActionBar();
		 	 bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffba00")));
			 bar.setDisplayHomeAsUpEnabled(true);
			 context = getApplicationContext();
			 View mActionBarView = getLayoutInflater().inflate(R.layout.custom_action_bar, null);
			 TextView custom_bar_app_name = (TextView) mActionBarView.findViewById(R.id.textView_app_namne);
			 Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
			 custom_bar_app_name.setTypeface(tf);
			 drawer_image_button = (ImageButton) mActionBarView.findViewById(R.id.button_drawer);
			 launcher_icon = (ImageView) mActionBarView.findViewById(R.id.imageView_ic_launcher);
			 glasses_image_button = (ImageButton) mActionBarView.findViewById(R.id.custom_action_bar_glasses);
			 write_image_button = (ImageButton) mActionBarView.findViewById(R.id.custom_action_bar_write);
			 save_image_button = (ImageButton) mActionBarView.findViewById(R.id.custom_action_bar_save);
			 share_image_button = (ImageButton) mActionBarView.findViewById(R.id.custom_action_bar_share);
	 
			 switch (getResources().getDisplayMetrics().densityDpi) 
				{
				case DisplayMetrics.DENSITY_LOW:
				   
				    break;
				case DisplayMetrics.DENSITY_MEDIUM:
				    custom_bar_app_name.setTextSize(15);
				    break;
				case DisplayMetrics.DENSITY_HIGH:
				    custom_bar_app_name.setTextSize(15);
				    break;
				case DisplayMetrics.DENSITY_XHIGH:
				    
				    break;
				case DisplayMetrics.DENSITY_XXHIGH:
					
				}
			 drawer_image_button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!drawerOpen)
		        		mDrawerLayout.openDrawer(mDrawerList);
		        	else
		        	{
		        		mDrawerLayout.closeDrawer(mDrawerList);
		        	}
				}
			});
			 launcher_icon.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!drawerOpen)
		        		mDrawerLayout.openDrawer(mDrawerList);
		        	else
		        	{
		        		mDrawerLayout.closeDrawer(mDrawerList);
		        	}
				}
			});
			 glasses_image_button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showGlassesDialog();
				}
			});
			 write_image_button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				showDialog();	
				}
			});
			 save_image_button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				onSaveImage();	
				}
			});
			 share_image_button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				onShareImage();	
				}
			});
			 
			 bar.setCustomView(mActionBarView);
			 bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			 filename = getIntent().getStringExtra("filename");
			 setContentView(R.layout.write_activity);
			 root_layout = (FrameLayout) findViewById(R.id.root_layout);
			 open_playstore = (ImageView) findViewById(R.id.imageView_appname);
		     String path = Environment.getExternalStorageDirectory().toString();
		     Drawable background = Drawable.createFromPath(path+File.separator+"Cartoon.Me"+"/"+filename);
		     root_layout.setBackgroundDrawable(background);
		   
		     
		     // load slide menu items
		     navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		
		     // nav drawer icons from resources
		     navMenuIcons = getResources()
		             .obtainTypedArray(R.array.nav_drawer_icons);
		
		     mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_write_activity);
		     mDrawerList = (ListView) findViewById(R.id.left_drawer_write_activity);
		
		     navDrawerItems = new ArrayList<NavDrawerItem>();
//			int one=1,two=2,three=3,four=4;
		
		     // adding nav drawer items to array
		     // Find People
		     navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		     // Photos
		     navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		     // Communities, Will add a counter here
		     navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		     // Pages
		     navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		      
		
		     // Recycle the typed array
		     navMenuIcons.recycle();

		     // setting the nav drawer list adapter
		     adapter = new NavDrawerListAdapter(this,getApplicationContext(),
		             navDrawerItems);
		    mDrawerList.setAdapter(adapter);
		 	mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
		            R.drawable.menu_bar_botton, //nav menu toggle icon
		            R.string.app_name, // nav drawer open - description for accessibility
		            R.string.app_name // nav drawer close - description for accessibility
		    ){
		        public void onDrawerClosed(View view) {
		           
		            // calling onPrepareOptionsMenu() to show action bar icons
		            invalidateOptionsMenu();
		        }
		
		        public void onDrawerOpened(View drawerView) {
		           
		            // calling onPrepareOptionsMenu() to hide action bar icons
		            invalidateOptionsMenu();
		        }
		    };
		    mDrawerLayout.setDrawerListener(mDrawerToggle);
		    mDrawerList.setOnItemClickListener(new OnItemClickListener(){
		
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					// TODO Auto-generated method stub
					displayView(position);
				}
		    	 
		     });
		    open_playstore.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=alburraq.cartoon.me"));
					startActivity(intent);
				}
			});
		}

public void displayView(int position)
{
	if(position==0)
	{
		context.getSharedPreferences(Constants.pref_name, 0).edit().clear().commit();
		try
		{
			Intent intent = new Intent(WriteActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "Can not start tour",Toast.LENGTH_SHORT).show();
		}
	}
	if(position==1)
	{
		    String urlToShare = "https://play.google.com/store/apps/details?id=alburraq.cartoon.me";
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            // intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB:
            // has no effect!
            intent.putExtra(Intent.EXTRA_TEXT, urlToShare);

            // See if official Facebook app is found
            boolean facebookAppFound = false;
            List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
            for (ResolveInfo info : matches) {
                if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                    intent.setPackage(info.activityInfo.packageName);
                    facebookAppFound = true;
                    break;
                }
            }

            // As fallback, launch sharer.php in a browser
            if (!facebookAppFound)
            {
                String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u="+ urlToShare;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            }

            startActivity(intent);
	}
	if(position==2)
	{
		Intent intent = new Intent(WriteActivity.this,AboutActivity.class);
		startActivity(intent);
	}
	if(position==3)
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"info@al-burraq.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "");
		i.putExtra(Intent.EXTRA_TEXT   , "");
		try 
		{
		    startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex)
		{
		    Toast.makeText(WriteActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
 @SuppressLint("ClickableViewAccessibility")
public boolean onTouch(View v, MotionEvent event) {
     mScaleDetector.onTouchEvent(event);
     mRotateDetector.onTouchEvent(event);
     mMoveDetector.onTouchEvent(event);
     isImageSaved=false;
     float scaledImageCenterX = (mImageWidth * mScaleFactor) / 2;
     float scaledImageCenterY = (mImageHeight * mScaleFactor) / 2;

     mMatrix.reset();
     mMatrix.postScale(mScaleFactor, mScaleFactor);
     mMatrix.postRotate(mRotationDegrees, scaledImageCenterX, scaledImageCenterY);
     mMatrix.postTranslate(mFocusX - scaledImageCenterX, mFocusY - scaledImageCenterY);

     ImageView view = (ImageView) v;
     view.setImageMatrix(mMatrix);
     return true;
 }
 
 private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
     @Override
     public boolean onScale(ScaleGestureDetector detector) {
         mScaleFactor *= detector.getScaleFactor();
         mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));

         return true;
     }
 }

 private class RotateListener extends RotateGestureDetector.SimpleOnRotateGestureListener {
     @Override
     public boolean onRotate(RotateGestureDetector detector) {
         mRotationDegrees -= detector.getRotationDegreesDelta();
         return true;
     }
 }

 private class MoveListener extends MoveGestureDetector.SimpleOnMoveGestureListener {

     @Override
     public boolean onMove(MoveGestureDetector detector) {
         PointF d = detector.getFocusDelta();
         mFocusX += d.x;
         mFocusY += d.y;
         return true;
     }
 }
 
 
 
 public void onSaveImage()
	{
	 	is_share_save_Called=true;
	 	is_keep_discard_show=true;
		root_layout.setDrawingCacheEnabled(true);
		root_layout.buildDrawingCache(true);
		savedImage=Bitmap.createBitmap(root_layout.getDrawingCache());
		root_layout.setDrawingCacheEnabled(false);
		File path = Environment.getExternalStorageDirectory();
		OutputStream fout = null;
		File file = new File(path+File.separator+"Cartoon.Me",filename);
		try 
		{
			fout = new FileOutputStream(file);
			savedImage.compress(Bitmap.CompressFormat.JPEG, 100, fout);
			try 
			{
				fout.flush();
				Toast.makeText(getApplicationContext(), "Image Saved", Toast.LENGTH_SHORT).show();
				isImageSaved=true;
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try 
			{
				fout.close();
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void onShareImage()
	{
			onSaveImage();
			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("image/jpeg");
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			savedImage.compress(Bitmap.CompressFormat.JPEG, 100 , bytes);
			File f = new File(Environment.getExternalStorageDirectory() + File.separator+"Cartoon.Me"+File.separator + filename);
			try 
			{
				f.createNewFile();
				@SuppressWarnings("resource")
				FileOutputStream fout = new FileOutputStream(f);
				fout.write(bytes.toByteArray());
			}
			catch(IOException e)
			{                       
		        e.printStackTrace();
			}
			share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
			//share.putExtra(Intent.EXTRA_STREAM, Uri.parse(Environment.getExternalStorageDirectory()+File.separator+"Cartoon.Me"+File.separator+filename));
			share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=alburraq.cartoon.me");
			startActivity(Intent.createChooser(share, "Share"));
//			if (FacebookDialog.canPresentShareDialog(getApplicationContext(),
//					FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
//					    // Publish the post using the Share Dialog
//					    FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
//					            .setPicture("http://www.happyologist.co.uk/wp-content/uploads/happy.jpeg")
//					    		.setLink("https://play.google.com/store/apps/details?id=alburraq.cartoon.me")
//					    		.build();
//					    uiHelper.trackPendingDialogCall(shareDialog.present());
//			}
//			else
//			{
//				publishFeedDialog();
//			}
	}
//	private void publishFeedDialog() {
//	    Bundle params = new Bundle();
//	    params.putString("name", "");
//	    params.putString("caption", "");
//	    params.putString("description", "");
//	    params.putString("link", "https://play.google.com/store/apps/details?id=com.alburraq.cartoon.me");
//	    params.putString("picture", Environment.getExternalStorageDirectory()+File.separator+"Cartoon.Me"+filename);
//
//	    WebDialog feedDialog = (
//	            new WebDialog.FeedDialogBuilder(this,
//	                    Session.getActiveSession(),
//	                    params)).build();
//	    feedDialog.show();
//	}
	@SuppressLint("InflateParams")
	public void showDialog()
	{
		   
		   if(imageView_glasses!=null)
				imageView_glasses.setOnTouchListener(null);
		   LayoutInflater li = LayoutInflater.from(this);
		   View view = li.inflate(R.layout.write_something, null);
		   AlertDialog.Builder alert = new AlertDialog.Builder(this);
		   alert.setView(view);
		   alert.setTitle("Writing");
		   final EditText edit = (EditText) view.findViewById(R.id.editTextDialogUserInput);
		   final Spinner spiner_text = (Spinner) view.findViewById(R.id.spinner_text);
		   final Spinner spiner_color = (Spinner) view.findViewById(R.id.spinner_color);
		   TextView header = (TextView) view.findViewById(R.id.textView_writeSomething);
		   TextView font_text = (TextView) view.findViewById(R.id.textView_font);
		   TextView color_text = (TextView) view.findViewById(R.id.textView_color);
		   Typeface t = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
		   header.setTypeface(t);
		   font_text.setTypeface(t);
		   color_text.setTypeface(t);
		   @SuppressWarnings({ "rawtypes", "unchecked" })
	    	ArrayAdapter adapter_text = new ArrayAdapter(WriteActivity.this,R.layout.spinner_layout,texts);
		   @SuppressWarnings({ "rawtypes", "unchecked" })
		   ArrayAdapter adapter_color = new ArrayAdapter(WriteActivity.this,R.layout.spinner_layout,colors);
		   spiner_text.setAdapter(adapter_text);
		   spiner_color.setAdapter(adapter_color);
		   alert.setCancelable(false)
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				// get user input and set it to result
				// edit text
			    	 write_something=edit.getText().toString();
			    	 LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1000, 100);
			    	 TextView tv = new TextView(WriteActivity.this);
			         tv.setLayoutParams(layoutParams);
			         tv.setText(write_something);
			         tv.setTextSize(40);
			         if(spiner_color.getSelectedItem().toString().equals("Black"))
			        	 tv.setTextColor(Color.BLACK);
			         if(spiner_color.getSelectedItem().toString().equals("Blue"))
			         	tv.setTextColor(Color.BLUE);
			         if(spiner_color.getSelectedItem().toString().equals("Red"))
				         	tv.setTextColor(Color.RED);
			         if(spiner_color.getSelectedItem().toString().equals("Green"))
				         	tv.setTextColor(Color.GREEN);
			         if(spiner_color.getSelectedItem().toString().equals("Yellow"))
			        	 tv.setTextColor(Color.YELLOW);
			         		
				     if(spiner_text.getSelectedItem().toString().equals("Rabiohead"))
				     {
				    	Typeface tf = Typeface.createFromAsset(getAssets(), "rabiohead.ttf");
				    	tv.setTypeface(tf);
				     }
				     if(spiner_text.getSelectedItem().toString().equals("Daniel"))
				     {
				    	Typeface tf = Typeface.createFromAsset(getAssets(), "daniel.ttf");
				    	tv.setTypeface(tf);
				     }
				     if(spiner_text.getSelectedItem().toString().equals("Desyrel"))
				     {
				    	Typeface tf = Typeface.createFromAsset(getAssets(), "DESYREL_.ttf");
				    	tv.setTypeface(tf);
				     }
				     if(spiner_text.getSelectedItem().toString().equals("Segeo-Print"))
				     {
				    	Typeface tf = Typeface.createFromAsset(getAssets(), "segoe-print-1361529596.ttf");
				    	tv.setTypeface(tf);
				     }
				     if(spiner_text.getSelectedItem().toString().equals("Simple"))
				     {
				    	Typeface tf = Typeface.createFromAsset(getAssets(), "Amaranth-Regular.otf");
				    	tv.setTypeface(tf);
				     }
				     if(spiner_text.getSelectedItem().toString().equals("Roboto-Light"))
				     {
				    	 Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
					     tv.setTypeface(tf);
				     }
			         tv.setBackgroundColor(Color.TRANSPARENT);

			         Bitmap text_to_image;

			         text_to_image = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
			         Canvas c = new Canvas(text_to_image);
			         tv.layout(0, 0, 1000, 1000);
			         tv.draw(c);

			         iv = (ImageView)findViewById(R.id.write_on_image);
			         iv.setImageBitmap(text_to_image);
			     	 mMatrix.postScale(mScaleFactor, mScaleFactor);
			    	 iv.setImageMatrix(mMatrix);
			    	 iv.setOnTouchListener(WriteActivity.this);
			    	 // Setup Gesture Detectors
			    	 mScaleDetector = new ScaleGestureDetector(getApplicationContext(), new ScaleListener());
			    	 mRotateDetector = new RotateGestureDetector(getApplicationContext(), new RotateListener());
			    	 mMoveDetector = new MoveGestureDetector(getApplicationContext(), new MoveListener());
			    	 
			    }
			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

		// create alert dialog
		AlertDialog alertDialog = alert.create();
		// show it
		alertDialog.show();
		}
	
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.action_write:
        	showDialog();
        	return true;
        case R.id.action_save2:
        	onSaveImage();
        	return true;
        case R.id.action_share:
        	onShareImage();
        	return true;
        case android.R.id.home:
        	if(!drawerOpen)
        		mDrawerLayout.openDrawer(mDrawerList);
        	else
        	{
        		mDrawerLayout.closeDrawer(mDrawerList);
        	}
        	return true;
        case R.id.action_glasses:
        	showGlassesDialog();
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
		drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @SuppressLint("InflateParams")
	public void showGlassesDialog()
    {
    	if(iv!=null)
    		iv.setOnTouchListener(null);
 	   LayoutInflater li = LayoutInflater.from(this);
	   View view = li.inflate(R.layout.glasses_list_view, null);
	   imageView_glasses = (ImageView) findViewById(R.id.imageView_glasses_write_activity);
	   AlertDialog.Builder alert = new AlertDialog.Builder(this);
	   alert.setView(view);
	   ListView glasses_list = (ListView) view.findViewById(R.id.listview_glasses);
	   GlassesAdapter adapter = new GlassesAdapter(WriteActivity.this,glasses,glasses_images);
	   glasses_list.setAdapter(adapter);
	   final AlertDialog dialog = alert.create();
	   dialog.show();
	   glasses_list.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) 
		{
			// TODO Auto-generated method stub
			dialog.dismiss();
			String url=null;
//			DisplayImageOptions displayimageOptions = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().build();
//			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).
//		            defaultDisplayImageOptions(displayimageOptions).build();
			try
			{
				url = Constants.glasses[position];
			}
			catch(Exception e)
			{
				
			}

			Integer pics = getResources().getIdentifier(
					"alburraq.cartoon.me:drawable/glasses_"+(position+1), null, null);

			imageView_glasses.setImageResource(pics);
			//Uri myUri = Uri.parse(url);
//			imageLoader = ImageLoader.getInstance();
//			imageLoader.init(config);
//			if(url!=null)
//			{
//				url = "http://"+url;
//
//				imageLoader.displayImage(url,imageView_glasses,displayimageOptions,new ImageLoadingListener() {
//
//
//					@Override
//					public void onLoadingStarted(String imageUri, View view) {
//						pd = ProgressDialog.show(WriteActivity.this, "Please wait...", "Loading image...",true);
//					}
//
//					@Override
//					public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//						pd.dismiss();
//						Toast.makeText(getApplicationContext(), "Please check your internet connection\nCan not download image from server", Toast.LENGTH_SHORT).show();
//					}
//
//					@Override
//					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//						pd.dismiss();
//					}
//
//					@Override
//					public void onLoadingCancelled(String imageUri, View view) {
//
//					}
//			});
//			}
//			else
//			{
//				Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
//			}
			//mMatrix.postScale(mScaleFactor, mScaleFactor);
			//imageView_glasses.setImageMatrix(mMatrix);
			imageView_glasses.setOnTouchListener(WriteActivity.this);
	    	 // Setup Gesture Detectors
	    	 mScaleDetector = new ScaleGestureDetector(getApplicationContext(), new ScaleListener());
	    	 mRotateDetector = new RotateGestureDetector(getApplicationContext(), new RotateListener());
	    	 mMoveDetector = new MoveGestureDetector(getApplicationContext(), new MoveListener());
			
		}
	});
    }
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		AlertDialog.Builder builder_rate = new AlertDialog.Builder(WriteActivity.this);
		if(is_share_save_Called==false)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
			builder.setTitle("Alert!");
			builder.setMessage("If you go back, your draft will be discarded ");
			builder.setPositiveButton("Keep", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					onSaveImage();
					is_keep_discard_show=true;
				}
			});
			builder.setNegativeButton("Discard",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					is_keep_discard_show=true;
					//android.os.Process.killProcess(android.os.Process.myPid());
					finish();
					if (isFinishing()) 
				    {
				        AppBrain.getAds().showInterstitial(WriteActivity.this);
				    }
					
					
					
				}
			});
			builder.create().show();
		}
		if(	is_keep_discard_show==true)
		{
			final SharedPreferences prefs = getSharedPreferences(Constants.pref_name, Context.MODE_PRIVATE);
			if(prefs.getString("rated", null)==null)
			{	
				builder_rate = new AlertDialog.Builder(WriteActivity.this);
				builder_rate.setTitle("Alert!");
				builder_rate.setMessage("Rate us Cartoon Me on PlayStore");
				builder_rate.setPositiveButton("Rate us", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=alburraq.cartoon.me"));
						startActivity(intent);
						SharedPreferences.Editor editor = prefs.edit();
						editor.putString("rated", "done");
						editor.commit();
						rated=true;
						
					}
				});
				builder_rate.setNegativeButton("Not now",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
//						android.os.Process.killProcess(android.os.Process.myPid());
						finish();
						if (isFinishing()) 
					    {
					        AppBrain.getAds().showInterstitial(WriteActivity.this);
					    }
						
					}
				});
				builder_rate.create().show();
				
			}	
			else
			{
				finish();
				if (isFinishing()) 
				{
				       AppBrain.getAds().showInterstitial(WriteActivity.this);
				}
				
				
			}
		}
		
	}
}
