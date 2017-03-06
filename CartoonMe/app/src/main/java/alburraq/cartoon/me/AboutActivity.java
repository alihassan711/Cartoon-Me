package alburraq.cartoon.me;



import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.appbrain.AppBrain;
import com.appbrain.AppBrainBanner;

public class AboutActivity extends Activity{
	TextView copyright,site,version;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		AppBrain.init(this);
		
		ActionBar bar = getActionBar();
	 	bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffba00")));
		bar.setDisplayHomeAsUpEnabled(true);
		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
   	 	TextView abTitle = (TextView) findViewById(titleId);
   	 	abTitle.setTextColor(Color.BLACK);
   	 	Typeface t = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
	 	abTitle.setTypeface(t);
   	 	
//        //ad mob
//        interstitial = new InterstitialAd(AboutActivity.this);
//    	AdView adView = (AdView) this.findViewById(R.id.adView_about);
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
   	 	
	 	AppBrain.init(this);
        AppBrainBanner banner = (AppBrainBanner) findViewById(R.id.appbrain_banner_about_activity);
        banner.requestAd();
   	 	copyright = (TextView) findViewById(R.id.textView_copy_right_about);
   	 	site = (TextView) findViewById(R.id.textView_site_adress_about);
   	 	version = (TextView) findViewById(R.id.textView_version);
   	    Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
        copyright.setTypeface(tf);
        site.setTypeface(tf);
        version.setTypeface(tf);
   	 	copyright.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url = "http://www.al-burraq.com";
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(url));
				startActivity(intent);	
			}
		});
     site.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url = "http://www.al-burraq.com";
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(url));
				startActivity(intent);	
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case android.R.id.home:
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
