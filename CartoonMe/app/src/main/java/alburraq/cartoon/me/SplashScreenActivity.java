package alburraq.cartoon.me;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;



import alburraq.cartoon.me.utils.SecretTextView;

public class SplashScreenActivity extends Activity {

	Handler handler;
	Runnable runnable;
	boolean flag = true;
	SecretTextView fun_begin;
	TextView copy_right_text,site_address_text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		fun_begin = (SecretTextView) findViewById(R.id.textView_splash_screeen_fun);
		copy_right_text = (TextView) findViewById(R.id.textView_copy_right_splash);
		site_address_text = (TextView) findViewById(R.id.textView_site_adress_splash);
		Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
		copy_right_text.setTypeface(tf);
		site_address_text.setTypeface(tf);
		tf = Typeface.createFromAsset(getAssets(), "Amaranth-Regular.otf");
		fun_begin.setTypeface(tf);
		fun_begin.setTextSize(30);
		fun_begin.setmDuration(1000);
		fun_begin.toggle();
		handler = new Handler();
		runnable = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(flag)
				{
					flag=false;
					handler.postDelayed(runnable, 1000);
				}
				else
				{
					Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.in, R.anim.out);
				}
			}
		};
		runnable.run();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	
	

}
