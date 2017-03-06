package alburraq.cartoon.me;

import static alburraq.cartoon.me.utils.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static alburraq.cartoon.me.utils.CommonUtilities.EXTRA_MESSAGE;
import org.json.JSONObject;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;



import alburraq.cartoon.me.utils.AlertDialogManager;
import alburraq.cartoon.me.utils.ConnectionDetector;
import alburraq.cartoon.me.utils.ServerUtilities;
import alburraq.cartoon.me.utils.WakeLocker;


public class GCMActivity extends Activity {
	// label to display gcm messages
	TextView lblMessage;
	
	// Asyntask
	AsyncTask<Void, Void, Void> mRegisterTask;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	// Connection detector
	ConnectionDetector cd;
	String regId = null;
	public static String name;
	public static String email;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(GCMActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}
		
		// Getting name, email from intent
		Intent i = getIntent();
		
		name = i.getStringExtra("name");
		email = i.getStringExtra("regId");		
		
		// Make sure the device has the proper dependencies.
//		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
//		GCMRegistrar.checkManifest(this);

		lblMessage = (TextView) findViewById(R.id.lblMessage);
		
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));
		
		// Get GCM registration id
//		regId = GCMRegistrar.getRegistrationId(this);
	
		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM			
//			GCMRegistrar.register(this, SENDER_ID);
		} else {
			// Device is already registered on GCM
//			if (GCMRegistrar.isRegisteredOnServer(this))
			if (true)
			{
				// Skips registration.				
				Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						ServerUtilities.unregister(GCMActivity.this, regId);
						return null;
					}
					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
	
				Toast.makeText(getApplicationContext(), "Unregister", Toast.LENGTH_LONG).show();
			} else 
				{
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
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

	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			lblMessage.append(newMessage + "\n");			
			Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();
			
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
//					jobj = jParser.makeHttpRequest("http://apps.al-burraq.com/gcm_server_files/register.php?name="+"Cartoon Me"+"&"+"regId="+regId, "GET", null);
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
					Toast.makeText(getApplicationContext(), "Data sent",Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Data not sent",Toast.LENGTH_SHORT).show();
				}
				super.onPostExecute(result);
			}
			
		}.execute();
	}

}
