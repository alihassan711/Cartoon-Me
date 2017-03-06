package alburraq.cartoon.me;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import alburraq.cartoon.me.utils.Constants;

public class MyBroadCastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		// TODO Auto-generated method stud
		if(context!=null)
		{
			Intent playstore = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=alburraq.cartoon.me"));
			playstore.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(playstore);
			if(Constants.my_notification_manager!=null)
			{
				Constants.my_notification_manager.cancelAll();
			
			}
		}
	}

}
