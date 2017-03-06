package alburraq.cartoon.me;

import static alburraq.cartoon.me.utils.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static alburraq.cartoon.me.utils.CommonUtilities.EXTRA_MESSAGE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



import alburraq.cartoon.me.utils.AlertDialogManager;
import alburraq.cartoon.me.utils.ConnectionDetector;
import alburraq.cartoon.me.utils.Constants;
import alburraq.cartoon.me.utils.DatabaseHandler;
import alburraq.cartoon.me.utils.ImagesLinks;
import alburraq.cartoon.me.utils.RuntimePermissionsActivity;
import alburraq.cartoon.me.utils.ServerUtilities;
import alburraq.cartoon.me.utils.WakeLocker;
import com.appbrain.AppBrain;
import com.appbrain.AppBrainBanner;
//import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends RuntimePermissionsActivity {

	public static final String CROP_VERSION_SELECTED_KEY = "crop";
	private static final int CAMERA_REQUEST = 1888;
	final private int REQUEST_PERMISSIONS = 123;
	Intent data;
	// ShowcaseView sv;
	public static final int VERSION_1 = 1;
	public static final int VERSION_2 = 2;
	protected static final int SELECT_PICTURE = 1;
	protected static final int RESULT_LOAD_IMAGE = 0;
	ImageView img;
	ImageButton take_image,from_gallery,album;
	Bundle bud;
	Bitmap photo;
	Uri myUri;
	ByteArrayOutputStream bs;
	RelativeLayout root,upper;
	TextView copy_rights,site_address;
	Button button_start_tour;
	Handler handler;
	Runnable runnable;
	boolean flag=true;
	int left,right,top,bottom;
	View view;
	int [] start_location,end_location;
	//ShowcaseView.ConfigOptions co;
	SharedPreferences prefs;
	String is_allowed_tour,first_time;
	Context context;
	TextView album_text,gallery_text,camera_text;
	TextView lblMessage;
	AsyncTask<Void, Void, Void> mRegisterTask;
	AlertDialogManager alert = new AlertDialogManager();
	ConnectionDetector cd;
	String regId = "";
	public static String name;
	public static String email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainActivity.super.requestAppPermissions(new
						String[]{
						android.Manifest.permission.CAMERA,
						android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, R.string.runtime_permissions_txt
				, REQUEST_PERMISSIONS);
		setContentView(R.layout.main);
		context = getApplicationContext();
		prefs = getSharedPreferences(Constants.pref_name, Context.MODE_PRIVATE);

		//      if(Constants.prefs_flag==false)
		//      {
		//        	is_allowed_tour=prefs.getString("pass",null);
		//        	first_time = prefs.getString("first_time", null);
		//      }
		root = (RelativeLayout) findViewById(R.id.root);
		upper = (RelativeLayout) findViewById(R.id.upper_layout);
		upper.setClickable(false);
		button_start_tour = (Button) findViewById(R.id.button_learn);
		button_start_tour.setVisibility(View.GONE);
		take_image= (ImageButton) findViewById(R.id.imageButton_camera);
		from_gallery = (ImageButton)findViewById(R.id.imageButton_gallery);
		album = (ImageButton) findViewById(R.id.imageButton_album);
		camera_text = (TextView) findViewById(R.id.textView_camera_label);
		gallery_text = (TextView) findViewById(R.id.textView_gallery_label);
		album_text = (TextView)findViewById(R.id.textView_album_label);
		Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
		camera_text.setTypeface(tf);
		gallery_text.setTypeface(tf);
		album_text.setTypeface(tf);
		button_start_tour.setTypeface(tf);
		//push notifications
		register_device();


		//ad mob
		//        interstitial = new InterstitialAd(MainActivity.this);
		//    	AdView adView = (AdView) this.findViewById(R.id.adView);
		//    	interstitial.setAdUnitId(Constants.admob_unit_id);
		//
		//   		// Request for Ads
		//   		AdRequest adRequest = new AdRequest.Builder()
		//
		//   		// Add a test device to show Test Ads
		//   		 .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		//   		 .addTestDevice("")
		//   				.build();
		//
		//   		// Load ads into Banner Ads
		//   		adView.loadAd(adRequest);
		//
		//   		// Load ads into Interstitial Ads
		//   		interstitial.loadAd(adRequest);
		//
		//appbrain
		AppBrain.init(this);
		AppBrainBanner banner = (AppBrainBanner) findViewById(R.id.appbrain_banner);
		banner.requestAd();
		start_location = new int [4];
		end_location = new int [4];

		//        if(is_allowed_tour==null || first_time==null)
		//        {
		//        	co = new ShowcaseView.ConfigOptions();
		//        	co.hideOnClickOutside = true;
		//        	sv = ShowcaseView.insertShowcaseView(R.id.button_learn, this, "Welcome","Find out every thing you need to\nknow to use Cartoon Me\nPress Start Tour button", co);
		//        	Constants.prefs_flag=true;
		//        	SharedPreferences.Editor editor = prefs.edit();
		//	    	editor.putString("first_time","done");
		//	    	editor.commit();
		//	    	take_image.setEnabled(false);
		//	    	from_gallery.setEnabled(false);
		//	    	album.setEnabled(false);
		//
		//
		//        }
		//        else
		//        {
		//        	button_start_tour.setVisibility(View.GONE);
		//        }
		button_start_tour.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//				if (sv.isShown())
				//		        {
				//		            sv.animateGesture(0,0,start_location[0]-end_location[0], start_location[1]-end_location[1]);
				//		        }
				//				SharedPreferences.Editor editor = prefs.edit();
				//		    	editor.putString("pass","done");
				//		    	editor.commit();
				//				runnable.run();


			}
		});
		//    	handler = new Handler();
		//		runnable = new Runnable() {
		//
		//			@Override
		//			public void run() {
		//				// TODO Auto-generated method stub
		//				if(flag)
		//				{
		//					flag=false;
		//					handler.postDelayed(runnable, 1900);
		//				}
		//				else
		//				{
		//					sv.setVisibility(View.GONE);
		//					button_start_tour.setVisibility(View.GONE);
		//					sv = ShowcaseView.insertShowcaseView(R.id.imageButton_camera, MainActivity.this, "Selfie Time", "Tap Camera Button in Order to take a picture\ntry to focus your face only", co);
		//					take_image.setEnabled(true);
		//
		//				}
		//			}
		//		};

		take_image.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{

//				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//				int x = 0;
//				x = getFrontCameraId();
//				if(x!=-1)
//					cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", x);
//
//				startActivityForResult(cameraIntent, CAMERA_REQUEST);

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
				startActivityForResult(intent, 1);


			}
		});
		from_gallery.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setType("image/*");
//				intent.setAction(Intent.ACTION_GET_CONTENT);
//				startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
				Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, 2);

			}
		});
		album.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (MainActivity.this,AlbumActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public void onPermissionsGranted(int requestCode) {

	}


	//	public void displayInterstitial()
	//	{
	//		// If Ads are loaded, show Interstitial else show nothing.
	//		if (interstitial.isLoaded()) {
	//			interstitial.show();
	//		}
	//	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		take_image.getLocationInWindow(start_location);
		button_start_tour.getLocationInWindow(end_location);
	}


	protected void onActivityResult(int requestCode, int resultCode , Intent data)
	{


		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
				Intent intent = new Intent(MainActivity.this,CropActivity.class);
				File f = new File(Environment.getExternalStorageDirectory().toString());
				for (File temp : f.listFiles()) {
					if (temp.getName().equals("temp.jpg")) {
						f = temp;
						break;
					}
				}

					intent.putExtra("byteArray", f);
					intent.putExtra("token", is_allowed_tour);


				startActivity(intent);
				finish();

			} else if (requestCode == 2) {

				if(data!=null) {

					Uri selectedImageUri = data.getData();
					Intent intent = new Intent(MainActivity.this,CropActivity.class);
					intent.putExtra("imageUri", selectedImageUri.toString());
					intent.putExtra("token", is_allowed_tour);
					startActivity(intent);
					finish();
				}
			}
		}
//
//		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK)
//		{
//			Intent intent = new Intent(MainActivity.this,CropActivity.class);
//			Uri uri = data.getData();
//			if(uri==null)
//			{
//				bs = new ByteArrayOutputStream();
//				Bitmap image = (Bitmap) data.getExtras().get("data");
//				image.compress(Bitmap.CompressFormat.PNG,100, bs);
//				byte [] byteArray = bs.toByteArray();
//				intent.putExtra("byteArray", byteArray);
//				intent.putExtra("token", is_allowed_tour);
//
//			}
//			else
//			{
//				intent.putExtra("image", uri.toString());
//				intent.putExtra("token", is_allowed_tour);
//			}
//
//			startActivity(intent);
//			finish();
//
//		}
//		if (resultCode == RESULT_OK) {
//			if (requestCode == SELECT_PICTURE) {
//				Uri selectedImageUri = data.getData();
//				Intent intent = new Intent(MainActivity.this,CropActivity.class);
//				intent.putExtra("imageUri", selectedImageUri.toString());
//				intent.putExtra("token", is_allowed_tour);
//				startActivity(intent);
//				finish();
//			}
//		}
	}
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	public int getFrontCameraId() {
		CameraInfo ci = new CameraInfo();
		for (int i = 0 ; i < Camera.getNumberOfCameras(); i++) {
			Camera.getCameraInfo(i, ci);
			if (ci.facing == CameraInfo.CAMERA_FACING_FRONT) return i;
		}
		return -1; // No front-facing camera found
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		android.os.Process.killProcess(android.os.Process.myPid());
	}
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
//			Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			WakeLocker.release();
		}
	};
	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
//			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
//			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}
	public void SignUp()
	{
		new AsyncTask<Void, Void, JSONObject>()
		{

			@Override
			protected JSONObject doInBackground(Void... params) {
				// TODO Auto-generated method stub
				JSONObject jobj = null;
//				JSONParser jParser = new JSONParser();
				try
				{
//					jobj = jParser.makeHttpRequest("http://apps.al-burraq.com/gcm_server_files/register.php?name="+"Cartoon%20Me"+"&"+"regId="+regId, "GET", null);
				}
				catch (Exception e)
				{

				}

				return jobj;
			}

			@Override
			protected void onPostExecute(JSONObject result) {

				// TODO Auto-generated method stub
				if(result!=null)
				{
					//Toast.makeText(getApplicationContext(), "Device already reg. on server",Toast.LENGTH_SHORT).show();
					Constants.is_already_added_on_server = true;
				}
				else
				{
					//Toast.makeText(getApplicationContext(), "Device added on server",Toast.LENGTH_SHORT).show();
				}
				super.onPostExecute(result);
			}

		}.execute();
	}
	@SuppressLint("CommitPrefEdits")
	public void register_device()
	{
		cd = new ConnectionDetector(getApplicationContext());
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			//			alert.showAlertDialog(MainActivity.this,
			//					"Internet Connection Error",
			//					"Please connect to working Internet connection", false);
			//			// stop executing code by return
			return;
		}
		try
		{
			if(Constants.is_already_added_on_server==false)
			{
//				GCMRegistrar.checkDevice(this);
//				GCMRegistrar.checkManifest(this);
				registerReceiver(mHandleMessageReceiver, new IntentFilter(
						DISPLAY_MESSAGE_ACTION));


//				regId = GCMRegistrar.getRegistrationId(this);
				if (regId.equals("")) 
				{			
//					GCMRegistrar.register(this, SENDER_ID);
				}
				else
				{
//					if (GCMRegistrar.isRegisteredOnServer(this))
					if (true)
					{
						// Skips registration.				
						DatabaseHandler check_db = new DatabaseHandler(MainActivity.this);
						List<ImagesLinks> images = new ArrayList<ImagesLinks>();
						images = check_db.getAllLinks();
						if(images.size()<0 || images.size()==0)
						{
							final Context context = this;
							mRegisterTask = new AsyncTask<Void, Void, Void>() {

								@Override
								protected Void doInBackground(Void... params) {
									// Register on our server
									// On server creates a new user
									ServerUtilities.register(context, name, email, regId);
									SignUp();
									return null;
								}

								@Override
								protected void onPostExecute(Void result) {
									mRegisterTask = null;
								}

							};
							mRegisterTask.execute(null, null, null);

						}
					}
					else
					{
						final Context context = this;
						mRegisterTask = new AsyncTask<Void, Void, Void>() {

							@Override
							protected Void doInBackground(Void... params) {
								// Register on our server
								// On server creates a new user
								ServerUtilities.register(context, name, email, regId);
								SignUp();
								return null;
							}

							@Override
							protected void onPostExecute(Void result) {
								mRegisterTask = null;
							}

						};
						mRegisterTask.execute(null, null, null);
					}
				} 
			}
		}
		catch(Exception e)
		{
//			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if(Constants.my_notification_manager!=null)
		{
			Constants.my_notification_manager.cancelAll();
		}
	}
}
