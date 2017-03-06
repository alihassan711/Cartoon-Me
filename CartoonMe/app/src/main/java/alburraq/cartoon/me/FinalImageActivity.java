package alburraq.cartoon.me;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import alburraq.cartoon.me.adapter.ExpandableListAdapter;
import alburraq.cartoon.me.adapter.GlassesAdapter;
import alburraq.cartoon.me.gestures.MoveGestureDetector;
import alburraq.cartoon.me.gestures.RotateGestureDetector;
import alburraq.cartoon.me.utils.Constants;
import alburraq.cartoon.me.utils.DatabaseHandler;
import alburraq.cartoon.me.utils.ImagesLinks;
import alburraq.cartoon.me.utils.NavDrawerItem;
import com.appbrain.AppBrain;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.FailReason;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


@SuppressLint("Recycle")
@SuppressWarnings("deprecation")
public class FinalImageActivity extends Activity implements OnTouchListener {
	FrameLayout root_layout;
	boolean is_saved=false;
	boolean open=false;
    static ByteArrayOutputStream bs;
    private Matrix mMatrix = new Matrix();
    private float mScaleFactor = 0.8f;
    private float mRotationDegrees = 0.f;
    private float mFocusX = 0.f;
    private float mFocusY = 0.f;
    private int mImageHeight, mImageWidth;
    private ScaleGestureDetector mScaleDetector;
    private RotateGestureDetector mRotateDetector;
    private MoveGestureDetector mMoveDetector;
    ImageView final_image;
    Bitmap finalImage,savedImage;
    boolean isImageSaved=false;
    Random random_number;
    String[] menu;
    DrawerLayout drawLayout;
    ExpandableListView drawList;
    ArrayAdapter<String> adapter;
    static int index=0;
    Bundle bundle;
    String folder;
	ProgressDialog dialog,ad,pd;
    String [] images;
    String [] names={"boy1","boy2","boy3","boy4","boy5","boy6","boy7","boy8","boy9","boy10","boy11","cartoon1","cartoon2","cartoon3","cartoon4","cartoon5","cartoon6","cartoon7","cartoon8","cartoon9","cartoon10","cartoon11","cartoon12","cartoon13","cartoon14","girl1","girl2","girl3","girl4","girl5","girl6","girl7","girl8","girl9","girl10","girl11"};
    ArrayList<String> image;
    Drawable background;
    View view;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
	private ArrayList<NavDrawerItem> navDrawerItems;
    HashMap<String, List<String>> listDataChild;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    boolean drawerOpen = false;
//    ImageLoaderConfiguration config;
//    ImageLoader imageLoader;
    ProgressDialog thumbnail_dialog;
    int index_cartoon_thumbnails;
    Activity act;
//    ShowcaseView.ConfigOptions co;
//    ShowcaseView sv;
    Context context;
    boolean list_open=false;
    ImageView icon,launcher_icon,iv,imageView_glasses;
    Button hidden;
    int [] start_location;
    String write_something="";
    String filename;
    boolean is_share_save_Called = false,is_keep_discard_show = false,rated=false;
    String [] texts = new String []{"Simple","Rabiohead","Daniel","Desyrel","Segoe-Print","Roboto-Light",};
    String [] colors = {"Black","Green","Blue","Red","Yellow"};
    String[] boy_names,girl_names,cartoon_names,boy_images,girl_images,cartoon_images,boy_thumbnails,girl_thumbnails;
    ImageButton drawer_image_button,glasses_image_button,write_image_button,save_image_button,share_image_button,face;
    String [] glasses = {"Black Glasses","Blue Glasses","Parrot Green Glasses","Sea Green Glasses","Purple Glasses","Teal Glasses","Yellow Glasses"};
    Integer [] glasses_images = {R.drawable.glasses_1,R.drawable.glasses_2,R.drawable.glasses_3,R.drawable.glasses_4,R.drawable.glasses_5,R.drawable.glasses_6,R.drawable.glasses_7};
    DatabaseHandler db;
    List<ImagesLinks> get_all_images;
    ArrayList<ImagesLinks> image_links;
    // slide menu items
  

    public static final int DISPLAY_IMAGE = 3;
	@SuppressWarnings("static-access")
	@SuppressLint({ "DefaultLocale", "InflateParams" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.final_image);
		AppBrain.init(this);
		act=this;
		context = getApplicationContext();
		switch (getResources().getDisplayMetrics().densityDpi) 
		{
		case DisplayMetrics.DENSITY_LOW:
		    folder="2";
		    break;
		case DisplayMetrics.DENSITY_MEDIUM:
		    folder="3";
		    break;
		case DisplayMetrics.DENSITY_HIGH:
		    folder="1";
		    break;
		case DisplayMetrics.DENSITY_XHIGH:
		    folder="4";
		    break;
		case DisplayMetrics.DENSITY_XXHIGH:
			folder="5";
		}
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffba00")));
		bar.setDisplayHomeAsUpEnabled(true);
		View mActionBarView = getLayoutInflater().inflate(R.layout.custom_action_bar, null);
		bar.setCustomView(mActionBarView);
		bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		TextView custom_bar_app_name = (TextView) mActionBarView.findViewById(R.id.textView_app_namne);
		Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
		custom_bar_app_name.setTypeface(tf);
		custom_bar_app_name.setVisibility(view.GONE);
		drawer_image_button = (ImageButton) mActionBarView.findViewById(R.id.button_drawer);
		launcher_icon = (ImageView) mActionBarView.findViewById(R.id.imageView_ic_launcher);
		glasses_image_button = (ImageButton) mActionBarView.findViewById(R.id.custom_action_bar_glasses);
		write_image_button = (ImageButton) mActionBarView.findViewById(R.id.custom_action_bar_write);
		save_image_button = (ImageButton) mActionBarView.findViewById(R.id.custom_action_bar_save);
		share_image_button = (ImageButton) mActionBarView.findViewById(R.id.custom_action_bar_share);
		face = (ImageButton) mActionBarView.findViewById(R.id.custom_action_bar_face);
		drawer_image_button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!open)
					{
						drawLayout.openDrawer(drawList);
						open = true;
					}
		        	else
		        	{
		        		drawLayout.closeDrawer(drawList);
		        		open = false;
		        	}
				}
			});
			 launcher_icon.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!open)
					{
						drawLayout.openDrawer(drawList);
						open = true;
					}
		        	else
		        	{
		        		drawLayout.closeDrawer(drawList);
		        		open = false;
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
			 face.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(iv!=null)
						iv.setOnTouchListener(null);
					if(imageView_glasses!=null)
						imageView_glasses.setOnTouchListener(null);
					if(final_image!=null)
						final_image.setOnTouchListener(FinalImageActivity.this);
				}
			});
		
//		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
//		Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
//   	TextView abTitle = (TextView) findViewById(titleId);
//   	abTitle.setTextColor(Color.BLACK);
//   	abTitle.setTypeface(tf);
//   	 	
//   	co = new ShowcaseView.ConfigOptions();
//      co.block=false;
   	 	
//		config = new ImageLoaderConfiguration.Builder(this).build();
//		imageLoader = ImageLoader.getInstance();
		bundle = savedInstanceState;
		navDrawerItems = new ArrayList<NavDrawerItem>();
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		  
	 
	    navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
		
	
		
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		
	    drawLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawList = (ExpandableListView) findViewById(R.id.left_drawer);
		drawList.setGroupIndicator(null);
		
		DatabaseHandler check_db = new DatabaseHandler(FinalImageActivity.this);
		get_all_images = new ArrayList<ImagesLinks> ();
		get_all_images = check_db.getAllLinks();
//		dialog = new ProgressDialog(FinalImageActivity.this);
//		dialog.setCancelable(false);
//		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//		dialog.setMessage("Loading images urls\nPlease wait...");
//		dialog.show();
//		if(get_all_images.size()>0)
		{
			
			image = new ArrayList<String> (); 
			for(int i=0;i<get_all_images.size();i++)
			{
				image.add(get_all_images.get(i).getLink());
			}
			images = new String [image.size()];
			for(int i =0;i<image.size();i++)
				images[i] = image.get(i);
			prepareListData();
			listAdapter = new ExpandableListAdapter(act,FinalImageActivity.this, listDataHeader, listDataChild);
	        // setting list adapter
			drawList.setAdapter(listAdapter);
			if(dialog!=null)
				dialog.dismiss();
		}
//		else
//		{
////			getAllImages();
//		}
		start_location = new int [4];
		
		root_layout = (FrameLayout) findViewById(R.id.root_backgorund);
		root_layout.setBackgroundResource(R.drawable.cartoon_2_5);
		final_image= (ImageView) findViewById(R.id.final_image_id);
		
		
		
		byte [] byteArray = getIntent().getByteArrayExtra("final_image2");
		finalImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
	    final_image.setImageBitmap(finalImage);
	    
//	    if(getIntent().getStringExtra("token")!=null)
	    final_image.setOnTouchListener(this);
		mMatrix.postScale(mScaleFactor, mScaleFactor);
        final_image.setImageMatrix(mMatrix);
	    
        
	    mScaleDetector = new ScaleGestureDetector(getApplicationContext(), new ScaleListener());
        mRotateDetector = new RotateGestureDetector(getApplicationContext(), new RotateListener());
        mMoveDetector = new MoveGestureDetector(getApplicationContext(), new MoveListener());
   
    
     
        drawList.setOnChildClickListener( new ExpandableListView.OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub

				Log.e("POS",""+groupPosition);
//				root_layout.setBackgroundDrawable(new BitmapDrawable(loadedImage));
				if(images!=null)
				{
					if(Constants.is_already_added)
					{
						for(int i =listDataHeader.size()-1;i>4;i--)
						{
							listDataHeader.remove(i);
						}
						listAdapter.notifyDataSetChanged();
					}
					if(childPosition==0)
					{
						for(int i =0;i<cartoon_names.length;i++)
							listDataHeader.add(cartoon_names[i]);
						Constants.is_already_added=true;
						Constants.category_index=0;
						Constants.can_delete=true;
						Constants.can_add=true;
						listAdapter.notifyDataSetChanged();
						setProgressBarIndeterminateVisibility(true);
					}
					if(childPosition==1)
					{
						for(int i =0;i<boy_names.length;i++)
							listDataHeader.add(boy_names[i]);
						Constants.is_already_added=true;
						Constants.can_add=true;
						Constants.category_index=1;
						Constants.can_delete=true;
						listAdapter.notifyDataSetChanged();
						setProgressBarIndeterminateVisibility(true);
					}
					if(childPosition==2)
					{
						for(int i =0;i<girl_names.length;i++)
							listDataHeader.add(girl_names[i]);
						Constants.is_already_added=true;
						Constants.can_add=true;
						Constants.category_index=2;
						Constants.can_delete=true;
						listAdapter.notifyDataSetChanged();
						setProgressBarIndeterminateVisibility(true);
					}
				}
				else
				{
					
				}
				
				return false;
			}
		});
        drawList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

//				Log.e("POSI",""+groupPosition);
				if(groupPosition>3) {
					Integer pics = getResources().getIdentifier(
							"alburraq.cartoon.me:drawable/cartoon_"+(groupPosition-3), null, null);
					root_layout.setBackgroundDrawable(getResources().getDrawable(pics));
//					ad.dismiss();
					drawLayout.closeDrawer(drawList);
				}
				// TODO Auto-generated method stub
//				if(sv!=null)
//	        		sv.setVisibility(View.GONE);
//	        	final_image.setOnTouchListener(FinalImageActivity.this);
				if(groupPosition==0)
				{
					context.getSharedPreferences(Constants.pref_name, 0).edit().clear().commit();
					try
					{
						Intent intent = new Intent(FinalImageActivity.this,MainActivity.class);
						startActivity(intent);
						finish();
					}
					catch(Exception e)
					{
						Toast.makeText(getApplicationContext(), "Can not start tour",Toast.LENGTH_SHORT).show();
					}
				}
				if(groupPosition==1)
				{
					    String urlToShare = "https://play.google.com/store/apps/details?id=alburraq.cartoon.me";
		                Intent intent = new Intent(Intent.ACTION_SEND);
		                intent.setType("text/plain");
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
				if(groupPosition==2)
				{
					Intent intent = new Intent(FinalImageActivity.this,AboutActivity.class);
					startActivity(intent);
				}
				if(groupPosition==3)
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
					    Toast.makeText(FinalImageActivity.this,R.string.no_client, Toast.LENGTH_SHORT).show();
					}
				}
//				if(groupPosition>3)
//				{
//					DisplayImageOptions displayimageOptions = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().build();
//					ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).
//				            defaultDisplayImageOptions(displayimageOptions).build();
//					if(Constants.category_index==0)
//					{
//						String url = cartoon_images[groupPosition-4];
//						url = "http://"+url;
//						imageLoader.init(config);
//						imageLoader.loadImage(url, displayimageOptions,new ImageLoadingListener() {
////						imageLoader.loadImage(getApplicationContext(), url, displayimageOptions,new ImageLoadingListener() {
//							@Override
//							public void onLoadingStarted(String imageUri, View view) {
//								ad = new ProgressDialog(FinalImageActivity.this);
//								ad.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//								ad.setCancelable(false);
//								ad.setMessage("Loading Image\nPlease wait...");
//								ad.show();
//							}
//
//							@Override
//							public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//								ad.dismiss();
//								Toast.makeText(getApplicationContext(), R.string.check_internet, Toast.LENGTH_SHORT).show();
//
//							}
//
//							@Override
//							public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//								ad.dismiss();
//								root_layout.setBackgroundDrawable(new BitmapDrawable(loadedImage));
//								drawLayout.closeDrawer(drawList);
//							}
//
//							@Override
//							public void onLoadingCancelled(String imageUri, View view) {
//
//							}
//
//						});
//
//
//					}
//					if(Constants.category_index==1)
//					{
//						String url = boy_images[groupPosition-5];
//						url = "http://"+url;
//						imageLoader.init(config);
////						imageLoader.loadImage(getApplicationContext(), url, displayimageOptions,new ImageLoadingListener() {
//						imageLoader.loadImage(url, displayimageOptions,new ImageLoadingListener() {
//
//							@Override
//							public void onLoadingStarted(String imageUri, View view) {
//								ad = new ProgressDialog(FinalImageActivity.this);
//								ad.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//								ad.setCancelable(false);
//								ad.setMessage("Loading Image\nPlease wait...");
//								ad.show();
//							}
//
//							@Override
//							public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//								ad.dismiss();
//								Toast.makeText(getApplicationContext(), R.string.check_internet, Toast.LENGTH_SHORT).show();
//							}
//
//							@Override
//							public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//								ad.dismiss();
//								root_layout.setBackgroundDrawable(new BitmapDrawable(loadedImage));
//								drawLayout.closeDrawer(drawList);
//
//							}
//
//							@Override
//							public void onLoadingCancelled(String imageUri, View view) {
//
//							}
//						});
//
//
//					}
//					if(Constants.category_index==2)
//					{
//						String url = girl_images[groupPosition-5];
//						url = "http://"+url;
//						imageLoader.init(config);
////						imageLoader.loadImage(getApplicationContext(), url, displayimageOptions,new ImageLoadingListener() {
//						imageLoader.loadImage(url, displayimageOptions,new ImageLoadingListener() {
//							@Override
//							public void onLoadingStarted(String imageUri, View view) {
//								ad = new ProgressDialog(FinalImageActivity.this);
//								ad.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//								ad.setCancelable(false);
//								ad.setMessage("Loading Image\nPlease wait...");
//								ad.show();
//							}
//
//							@Override
//							public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//								ad.dismiss();
//								Toast.makeText(getApplicationContext(), R.string.check_internet, Toast.LENGTH_SHORT).show();
//							}
//
//							@Override
//							public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//								ad.dismiss();
//								root_layout.setBackgroundDrawable(new BitmapDrawable(loadedImage));
//								drawLayout.closeDrawer(drawList);
//
//							}
//
//							@Override
//							public void onLoadingCancelled(String imageUri, View view) {
//
//							}
//
//						});
//
//					}
//					String url = Constants.all_images[groupPosition-4];
//					url = "http://"+url;
//					imageLoader.init(config);
////					imageLoader.loadImage(getApplicationContext(), url, displayimageOptions,new ImageLoadingListener() {
//					imageLoader.loadImage(url, displayimageOptions,new ImageLoadingListener() {
//
//						@Override
//						public void onLoadingStarted(String imageUri, View view) {
//							ad = new ProgressDialog(FinalImageActivity.this);
//							ad.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//							ad.setCancelable(false);
//							ad.setMessage("Loading Image\nPlease wait...");
//							ad.show();
//						}
//
//						@Override
//						public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//							ad.dismiss();
//							Toast.makeText(getApplicationContext(), R.string.check_internet, Toast.LENGTH_SHORT).show();
//						}
//
//						@Override
//						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//							ad.dismiss();
//							root_layout.setBackgroundDrawable(new BitmapDrawable(loadedImage));
//							drawLayout.closeDrawer(drawList);
//
//						}
//
//						@Override
//						public void onLoadingCancelled(String imageUri, View view) {
//
//						}
//
//					});
//
//				}
				return false;
			}
		});
	}
	@SuppressLint("NewApi")
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
//		super.onWindowFocusChanged(hasFocus);
//		hidden.getLocationInWindow(start_location);
//       	Display display = getWindowManager().getDefaultDisplay();
//		final Point size = new Point();
//		display.getSize(size);
//		float height = size.y;
//		float centerY=height/2;
//		float width = size.x;
//		float centerX = width/2;
//    	
//		if(sv!=null)
//    	{
//    		sv.animateGesture(centerX,centerY,-centerX,(start_location[1]-centerY));
//    	}
	}
//	 @Override
//	    public boolean onPrepareOptionsMenu(Menu menu) {
//	        // if nav drawer is opened, hide the action items
//	        //drawerOpen = drawLayout.isDrawerOpen(drawList);
//	        return super.onPrepareOptionsMenu(menu);
//	    }
//
//	    @Override
//	    protected void onPostCreate(Bundle savedInstanceState) {
//	        super.onPostCreate(savedInstanceState);
//	        // Sync the toggle state after onRestoreInstanceState has occurred.
//	        mDrawerToggle.syncState();
//	    }
//	 
//	    @Override
//	    public void onConfigurationChanged(Configuration newConfig) {
//	        super.onConfigurationChanged(newConfig);
//	        // Pass any configuration change to the drawer toggls
//	        mDrawerToggle.onConfigurationChanged(newConfig);
//	    }
	public void getAllImages()
	{
		new AsyncTask<Void,Void,JSONObject>()
		{

			@Override
			protected void onPostExecute(JSONObject result) {
				if(result!=null)
				{
					image = new ArrayList<String>();
					try
					{
						
						JSONArray result_array = result.getJSONArray("result");
						db = new DatabaseHandler(FinalImageActivity.this);
						image_links = new ArrayList<ImagesLinks>();
						for ( int i=0;i<result_array.length();i++)
						{
							image.add(result_array.getString(i));
							ImagesLinks new_image = new ImagesLinks(result_array.getString(i));
							db.addLink(new_image);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//db.close();
					images = new String[image.size()];
					for(int i = 0 ; i <image.size();i++)
						images[i]=image.get(i);
					prepareListData();
					listAdapter = new ExpandableListAdapter(act,FinalImageActivity.this, listDataHeader, listDataChild);
				        // setting list adapter
				    drawList.setAdapter(listAdapter);
					dialog.dismiss();
//					if(getIntent().getStringExtra("token")==null)
//						drawLayout.openDrawer(drawList);
		    	}
				else
				{
					dialog.dismiss();
					prepareListData();
					listAdapter = new ExpandableListAdapter(act,FinalImageActivity.this, listDataHeader, listDataChild);
				    drawList.setAdapter(listAdapter);
					drawList.setAdapter(listAdapter);
					Toast.makeText(getApplicationContext(),R.string.check_internet, Toast.LENGTH_SHORT).show();
				}
			
		
			}
			
			@Override
			protected JSONObject doInBackground(Void... params) {
				JSONObject jobj=null;
//				JSONParser jParser = new JSONParser();
				//String deviceName = getDeviceName();
//				List<NameValuePair> param = new ArrayList<NameValuePair>();
		
			    	try
			    	{		
//			    		param.add(new BasicNameValuePair("folder", folder));
//			    		jobj = jParser.makeHttpRequest("http://apps.al-burraq.com/getFiles.php","POST", param);
			    	}
			    	catch(Exception e)
					{
						e.printStackTrace();
					}
			    
			
			    return jobj;
			}
			
		}.execute();
	}
	@SuppressLint("SdCardPath")
	public void onSaveImage()
	{
		is_share_save_Called = true;
		is_keep_discard_show = true;
		root_layout.setDrawingCacheEnabled(true);
		root_layout.buildDrawingCache(true);
		savedImage=Bitmap.createBitmap(root_layout.getDrawingCache());
		root_layout.setDrawingCacheEnabled(false);
		String path = Environment.getExternalStorageDirectory().toString();
		File wallpaperDirectory = new File(path+File.separator+"Cartoon.Me/");
		wallpaperDirectory.mkdirs();
		OutputStream fout = null;
		random_number = new Random();
		int x= random_number.nextInt();
		filename = x+"MyImage.jpeg";
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
//				Intent intent = new Intent(FinalImageActivity.this,WriteActivity.class);
//				intent.putExtra("filename",filename);
//				startActivity(intent);
				SharedPreferences pref = getSharedPreferences(Constants.close_pref_name, Context.MODE_PRIVATE);
				SharedPreferences.Editor edit = pref.edit();
				edit.putString("close", "yes");
				edit.commit();
				Constants.CLOSE_APPLICATION=true;
				//finish();
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
			Toast.makeText(getApplicationContext(), "Can not save image", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		
	}

	 @SuppressLint("ClickableViewAccessibility")
	public boolean onTouch(View v, MotionEvent event) {
	        mScaleDetector.onTouchEvent(event);
	        mRotateDetector.onTouchEvent(event);
	        mMoveDetector.onTouchEvent(event);

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
	    class RetrieveBitmap extends AsyncTask<String, Void, Bitmap> {

			
			
			protected Bitmap doInBackground(String... urls) {
		        try
		        {

		        	 URL url = new URL(urls[0]);
		             HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		             connection.setDoInput(true);
		             connection.connect();
		             InputStream input = connection.getInputStream();
		             savedImage= BitmapFactory.decodeStream(input);
		             return savedImage;
		        } catch (Exception e)
		        {
		           
		            return null;
		        }
		    }

		    @SuppressLint("NewApi")
			protected void onPostExecute(Bitmap result) {
		    	if(result!=null)
		    	{
		    		ad.dismiss();
		    		root_layout.setBackground(new BitmapDrawable(savedImage));	
		    		
		    	}
		    	else
		    	{
		    		ad.dismiss();
		    		Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
		    	}
		    }
		}
	    
//	    public boolean onCreateOptionsMenu(Menu menu) {
//	        MenuInflater inflater = getMenuInflater();
//	        inflater.inflate(R.menu.activity_main_actions, menu);
//	        if(!open && getIntent().getStringExtra("token")==null)
//	        {
//	        	sv = ShowcaseView.insertShowcaseViewWithType(ShowcaseView.ITEM_ACTION_HOME,android.R.id.home, this,
//	                "Get Amazing Backgrounds", "Click Categories to choose the background of your Face\nand be a SuperHero.",co);
//	        	open=true;
//	        }
//	        return super.onCreateOptionsMenu(menu);
//	    }
//	    public boolean onOptionsItemSelected(MenuItem item) {
//	        // Take appropriate action for each action item click
//	    	
//	    	  if (mDrawerToggle.onOptionsItemSelected(item)) {
//	              return true;
//	          }
//	        switch (item.getItemId()) {
//	        case R.id.action_save:
//	        	onSaveImage();
//	        	return true;
//	        case android.R.id.home:
//	        	
//	        	if(!drawerOpen)
//	        		drawLayout.openDrawer(drawList);
//	        	else
//	        	{
//	        		drawLayout.closeDrawer(drawList);
//	        	}
//	        	
//	        	return true;
//	        default:
//	            return super.onOptionsItemSelected(item);
//	        }
//	    }
	    private void prepareListData() {
	        listDataHeader = new ArrayList<String>();
	        listDataChild = new HashMap<String, List<String>>();
	        try
	        {
		        // Adding child data
		       // listDataHeader.add("Categories");
		        listDataHeader.add("Make New Image");
		        listDataHeader.add("Share Cartoon Me");
		        listDataHeader.add("About");
		        listDataHeader.add("Contact us");
		        listDataHeader.add("");
		        listDataHeader.add("");
		        listDataHeader.add("");
		        listDataHeader.add("");
		        listDataHeader.add("");
		        listDataHeader.add("");
		        listDataHeader.add("");

		        // Adding child data
		        List<String> category = new ArrayList<String>();
		        category.add("        Cartoon");
		        category.add("        Boy");
		        category.add("        Girl");
		        List<String> use = new ArrayList<String>();
		       // listDataChild.put(listDataHeader.get(0), category); // Header, Child data
		        listDataChild.put(listDataHeader.get(0), use);
		        listDataChild.put(listDataHeader.get(1), use);
		        listDataChild.put(listDataHeader.get(2), use);
		        listDataChild.put(listDataHeader.get(3), use);
		          
//		        boy_names = new String[11];
//		        boy_names[0]= names[0];
//		        boy_names[1]= names[1];
//		        boy_names[2]= names[2];
//		        boy_names[3]= names[3];
//		        boy_names[4]= names[4];
//		        boy_names[5]= names[5];
//		        boy_names[6]= names[6];
//		        boy_names[7]= names[7];
//		        boy_names[8]= names[8];
//		        boy_names[9]= names[9];
//		        boy_names[10] = names[10];
//		      
//		        cartoon_names  = new String[14];
//		        cartoon_names[0] = names[11];
//		        cartoon_names[1] = names[12];
//		        cartoon_names[2] = names[13];
//		        cartoon_names[3] = names[14];
//		        cartoon_names[4] = names[15];
//		        cartoon_names[5] = names[16];
//		        cartoon_names[6] = names[17];
//		        cartoon_names[7] = names[18];
//		        cartoon_names[8] = names[19];
//		        cartoon_names[9] = names[20];
//		        cartoon_names[10] = names[21];
//		        cartoon_names[11] = names[22];
//		        cartoon_names[12] = names[23];
//		        cartoon_names[13] = names[24];
//		        
//		        girl_names = new String [11];
//		        girl_names[0] = names[25];
//		        girl_names[1] = names[26];
//		        girl_names[2] = names[27];
//		        girl_names[3] = names[28];
//		        girl_names[4] = names[29];
//		        girl_names[5] = names[30];
//		        girl_names[6] = names[31];
//		        girl_names[7] = names[32];
//		        girl_names[8] = names[33];
//		        girl_names[9] = names[34];
//		        girl_names[10] = names[35];
//		        if(images!=null)
//	    	
//		        {
//			        cartoon_images = new String[2*cartoon_names.length];
//			        Constants.cartoon_thumbnails = new String[14];
//			        for(int i = 0,j=0;i<images.length;i++)
//			        {
//			        	if(images[i].contains("cartoon"))
//			        	{
//			        		cartoon_images[j] = images[i];
//			        		j++;
//			        	}
//			        }
//			        for(int i=0,j=0;i<cartoon_images.length;i++ )
//			        {
//			        	if(cartoon_images[i].contains("th"))
//			        	{
//			        		Constants.cartoon_thumbnails[j]=cartoon_images[i];
//			        		j++;
//			        	}
//			        }
//			        boy_images = new String[2*boy_names.length];
//			        Constants.boy_thumbnails = new String[boy_images.length];
//			        for(int i = 0,j=0;i<images.length;i++)
//			        {
//			        	if(images[i].contains("boy"))
//			        	{
//			        		boy_images[j] = images[i];
//			        		j++;
//			        	}
//			        }
//			        for(int i=0,j=0;i<boy_images.length;i++ )
//			        {
//			        	if(boy_images[i].contains("th"))
//			        	{
//			        		Constants.boy_thumbnails[j]=boy_images[i];
//			        		j++;
//			        	}
//			        }
//			        girl_images = new String[2*girl_names.length];
//			        Constants.girl_thumbnails = new String[girl_images.length];
//			        for(int i = 0,j=0;i<images.length;i++)
//			        {
//			        	if(images[i].contains("girl"))
//			        	{
//			        		girl_images[j] = images[i];
//			        		j++;
//			        	}
//			        }
//			        for(int i=0,j=0;i<girl_images.length;i++ )
//			        {
//			        	if(girl_images[i].contains("th"))
//			        	{
//			        		Constants.girl_thumbnails[j]=girl_images[i];
//			        		j++;
//			        	}
//			        }
//			        Constants.glasses = new String [7];
//			        for(int i =0,j=0;i<images.length;i++)
//			        {
//			        	if(images[i].contains("gl"))
//			        	{
//			        		Constants.glasses[j]=images[i];
//			        		j++;
//			        	}
//			        }
//		        }
//		        else
//		        {
//		        	Toast.makeText(getApplicationContext(),R.string.check_internet,Toast.LENGTH_SHORT).show();
//		        }
		        Constants.all_images = new String[(images.length-7)/2];
		        Constants.all_thumbnails = new String[(images.length-7)/2];
		        Constants.glasses = new String[7];
		        if(images!=null)
		        {
		        	for(int i = 0,j=0,k=0,g=0;i<images.length;i++)
		        	{
		        		if(images[i].contains("th"))
		        		{
		        			Constants.all_thumbnails[j]= images[i];
		        			j++;
		        		}
		        		else if(images[i].contains("gl"))
		        		{
		        			Constants.glasses[g] = images[i];
		        			g++;
		        		}
		        		else
		        		{
		        			Constants.all_images[k] = images[i];
		        			k++;
		        		}
		        	}
		        	for(int i = 0;i<Constants.all_thumbnails.length;i++)
		        	{
//		        		listDataHeader.add(Constants.all_thumbnails[i]);
		        	}
		        }
	        }
	        catch(Exception e)
	        {
//	        	Toast.makeText(getApplicationContext(), "Error while downloading images", Toast.LENGTH_SHORT).show();
	        }
	        
	        
	    }
	    @SuppressLint("InflateParams")
		public void showDialog()
	    {
	    	   if(final_image!=null)
					final_image.setOnTouchListener(null);
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
		       ArrayAdapter adapter_text = new ArrayAdapter(FinalImageActivity.this,R.layout.spinner_layout,texts);
			   @SuppressWarnings({ "rawtypes", "unchecked" })
			   ArrayAdapter adapter_color = new ArrayAdapter(FinalImageActivity.this,R.layout.spinner_layout,colors);
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
				    	 TextView tv = new TextView(FinalImageActivity.this);
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
				    	 iv.setOnTouchListener(FinalImageActivity.this);
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
	    @SuppressLint("InflateParams")
		public void showGlassesDialog()
	    {
	    	if(iv!=null)
	    		iv.setOnTouchListener(null);
	    	if(final_image!=null)
	    		final_image.setOnTouchListener(null);
	 	   LayoutInflater li = LayoutInflater.from(this);
		   View view = li.inflate(R.layout.glasses_list_view, null);
		   imageView_glasses = (ImageView) findViewById(R.id.imageView_glasses_write_activity);
		   AlertDialog.Builder alert = new AlertDialog.Builder(this);
		   alert.setView(view);
		   ListView glasses_list = (ListView) view.findViewById(R.id.listview_glasses);
		   GlassesAdapter adapter = new GlassesAdapter(FinalImageActivity.this,glasses,glasses_images);
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
				Integer pics = getResources().getIdentifier(
						"alburraq.cartoon.me:drawable/glasses_"+(position+1), null, null);

				imageView_glasses.setImageResource(pics);

//				khsd kghgkj hgkgs

//				String url=null;
//				DisplayImageOptions displayimageOptions = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().build();
//				ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).
//			            defaultDisplayImageOptions(displayimageOptions).build();
//				try
//				{
//					url = Constants.glasses[position];
//				}
//				catch(Exception e)
//				{
//
//				}
//				imageLoader = ImageLoader.getInstance();
//				imageLoader.init(config);
//				if(url!=null)
//				{
//					url = "http://"+url;
//
//					Log.e("Glasses",""+url);
//
//					imageLoader.displayImage(url,imageView_glasses,displayimageOptions,new ImageLoadingListener() {
//
//
//						@Override
//						public void onLoadingStarted(String imageUri, View view) {
//							pd = ProgressDialog.show(FinalImageActivity.this, "Please wait...", "Loading image...",true);
//						}
//
//						@Override
//						public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//							pd.dismiss();
//							Toast.makeText(getApplicationContext(), "Please check your internet connection\nCan not download image from server", Toast.LENGTH_SHORT).show();
//						}
//
//						@Override
//						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//							pd.dismiss();
//						}
//
//						@Override
//						public void onLoadingCancelled(String imageUri, View view) {
//
//						}
//
//				});
//				}
//				else
//				{
//					Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
//				}
				//mMatrix.postScale(mScaleFactor, mScaleFactor);
				//imageView_glasses.setImageMatrix(mMatrix);
				imageView_glasses.setOnTouchListener(FinalImageActivity.this);
		    	 // Setup Gesture Detectors
		    	 mScaleDetector = new ScaleGestureDetector(getApplicationContext(), new ScaleListener());
		    	 mRotateDetector = new RotateGestureDetector(getApplicationContext(), new RotateListener());
		    	 mMoveDetector = new MoveGestureDetector(getApplicationContext(), new MoveListener());
				
			}
		});
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
	    }
		@Override
		public void onBackPressed() {
			AlertDialog.Builder builder_rate = new AlertDialog.Builder(FinalImageActivity.this);
			if(is_share_save_Called==false)
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(FinalImageActivity.this);
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
						Constants.CLOSE_APPLICATION=true;
						//android.os.Process.killProcess(android.os.Process.myPid());
						finish();
						if (isFinishing()) 
					    {
					        AppBrain.getAds().showInterstitial(FinalImageActivity.this);
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
					builder_rate = new AlertDialog.Builder(FinalImageActivity.this);
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
//							android.os.Process.killProcess(android.os.Process.myPid());
							finish();
							if (isFinishing()) 
						    {
						        AppBrain.getAds().showInterstitial(FinalImageActivity.this);
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
					       AppBrain.getAds().showInterstitial(FinalImageActivity.this);
					}
					
					
				}
			}
		}
	
}
