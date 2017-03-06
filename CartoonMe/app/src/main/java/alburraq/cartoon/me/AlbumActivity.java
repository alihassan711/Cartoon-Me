package alburraq.cartoon.me;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import alburraq.cartoon.me.adapter.AlbumViewPagerAdapter;
import com.appbrain.AppBrain;
import com.appbrain.AppBrainBanner;

public class AlbumActivity extends Activity{
	ViewPager viewPager;
	AlbumViewPagerAdapter adapter;
	Drawable [] images;
	Bitmap savedImage;
	File[] filelist;
	Bundle savedInstanceState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager_album_activity);
		AppBrain.init(this);
//	    interstitial = new InterstitialAd(AlbumActivity.this);
//	    AdView adView = (AdView) this.findViewById(R.id.adView_album);
//        interstitial.setAdUnitId(Constants.admob_unit_id);
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
        AppBrainBanner banner = (AppBrainBanner) findViewById(R.id.appbrain_banner_album_activity);
        banner.requestAd();
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffba00")));
		bar.setDisplayHomeAsUpEnabled(true);
		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
		Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
   	 	TextView abTitle = (TextView) findViewById(titleId);
   	 	abTitle.setTextColor(Color.BLACK);
   	 	abTitle.setText(R.string.my_album);
   	 	abTitle.setTypeface(tf);
		File dir = new File(Environment.getExternalStorageDirectory().toString()+"/Cartoon.Me");
		if(dir!=null)
			filelist = dir.listFiles();
		if(filelist!=null && filelist.length>0)
		{
		
			viewPager = (ViewPager) findViewById(R.id.pager);
			adapter = new AlbumViewPagerAdapter(AlbumActivity.this,filelist);
			viewPager.setAdapter(adapter);
		}
		else
		{
			Toast.makeText(getApplicationContext(), R.string.no_albums_found, Toast.LENGTH_SHORT).show();
			finish();
		}
		

	}
	   
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.album_activity, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId())
		{
		case R.id.action_share:
			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("image/jpeg");
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			savedImage = BitmapFactory.decodeFile(""+filelist[viewPager.getCurrentItem()]);
			savedImage.compress(Bitmap.CompressFormat.JPEG, 100 , bytes);
			File f = new File(Environment.getExternalStorageDirectory()+"/Cartoon.Me"+"sharing.jpeg");
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
			startActivity(Intent.createChooser(share, "Share Image"));
			return true;
		
		case android.R.id.home:
			finish();
			return true;
		case R.id.action_delete:
		if(viewPager!=null)
		{
			if(filelist!=null && filelist.length!=0)
			if(filelist[viewPager.getCurrentItem()]!=null)
			{
				filelist[viewPager.getCurrentItem()].delete();
				//Toast.makeText(getApplicationContext(),R.string.image_deleted, Toast.LENGTH_SHORT).show();
				onCreate(savedInstanceState);
			}
		}
		else
		{
			Toast.makeText(getApplicationContext(),R.string.nothing_to_delete, Toast.LENGTH_SHORT).show();
		}
		
			
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		finish();
	}
	
	

}
