package alburraq.cartoon.me;

import com.google.android.gms.gcm.GcmListenerService;

public class GCMIntentService extends GcmListenerService {
//	Context context;
//	private static final String TAG = "GCMIntentService";
//
//    public GCMIntentService() {
//        super(SENDER_ID);
//    }
//
//    /**
//     * Method called on device registered
//     **/
//    @Override
//    protected void onRegistered(Context context, String registrationId) {
//        Log.i(TAG, "Device registered: regId = " + registrationId);
//        displayMessage(context, "Your device registred with GCM");
//        this.context = context;
//        //Log.d("NAME", MainActivity.name);
//        ServerUtilities.register(context, GCMActivity.name, GCMActivity.email, registrationId);
//    }
//
//    /**
//     * Method called on device un registred
//     * */
//    @Override
//    protected void onUnregistered(Context context, String registrationId) {
//        Log.i(TAG, "Device unregistered");
//        displayMessage(context, getString(R.string.gcm_unregistered));
//        ServerUtilities.unregister(context, registrationId);
//    }
//
//    /**
//     * Method called on Receiving a new message
//     * */
//    @Override
//    protected void onMessage(Context context, Intent intent) {
//        Log.i(TAG, "Received message");
//        String message = intent.getExtras().getString("msg");
//        generateNotification(context, message);
//    }
//
//    /**
//     * Method called on receiving a deleted message
//     * */
//    @Override
//    protected void onDeletedMessages(Context context, int total) {
//        Log.i(TAG, "Received deleted messages notification");
//        String message = getString(R.string.gcm_deleted, total);
//        displayMessage(context, message);
//        // notifies user
//        generateNotification(context, message);
//    }
//
//    /**
//     * Method called on Error
//     * */
//    @Override
//    public void onError(Context context, String errorId) {
//        Log.i(TAG, "Received error: " + errorId);
//        displayMessage(context, getString(R.string.gcm_error, errorId));
//    }
//
//    @Override
//    protected boolean onRecoverableError(Context context, String errorId) {
//        // log message
//        Log.i(TAG, "Received recoverable error: " + errorId);
//        displayMessage(context, getString(R.string.gcm_recoverable_error,
//                errorId));
//        return super.onRecoverableError(context, errorId);
//    }
//
//    /**
//     * Issues a notification to inform the user that server has sent a message.
//     */
//    @SuppressWarnings("deprecation")
//	private static void generateNotification(Context context, String message) {
//        int icon = R.drawable.ic_launcher;
//        long when = System.currentTimeMillis();
//        if(message.charAt(0)=='0')
//        {
//          message = message.substring(1, message.length());
//          NotificationManager notificationManager = (NotificationManager)
//          context.getSystemService(Context.NOTIFICATION_SERVICE);
//		  Notification notification = new Notification(icon, message, when);
//
//		  String title = context.getString(R.string.app_name);
//
//		  Intent notificationIntent = new Intent(context, MainActivity.class);
//		  Intent playstore = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.alburraq.cartoon.me"));
//		  // set intent so it does not start a new activity
//		  notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//		          Intent.FLAG_ACTIVITY_SINGLE_TOP);
//		  PendingIntent intent =
//		          PendingIntent.getActivity(context, 0, playstore, Intent.FLAG_ACTIVITY_NEW_TASK);
//		  notification.setLatestEventInfo(context, title, message, intent);
//		  notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//		  // Play default notification sound
//		  notification.defaults |= Notification.DEFAULT_SOUND;
//
//		  //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
//
//		  // Vibrate if vibrate is enabled
//		  notification.defaults |= Notification.DEFAULT_VIBRATE;
//		  notificationManager.notify(0, notification);
//        }
//        if(message.charAt(0)=='1')
//        {
//        	message = message.substring(1, message.length());
//        	Constants.my_notification_manager = (NotificationManager)
//                    context.getSystemService(Context.NOTIFICATION_SERVICE);
//            Notification notification1 = new Notification(icon, message, when);
//            String title1 = context.getString(R.string.app_name);
//
//            Intent notificationIntent1 = new Intent(context,MyBroadCastReceiver.class);
//            // Intent playstore1 = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.alburraq.cartoon.me"));
//            //playstore1.setAction("com.alburraq.cartoon.me.MY_CUSTOM_INTENT");
//            //context.sendBroadcast(playstore1);
//
//            //notificationIntent1.setAction("com.alburraq.cartoon.me.MY_CUSTOM_INTENT");
//
//            // set intent so it does not start a new activity
////            notificationIntent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
////                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            PendingIntent intent1 = null;
//            try
//            {
//            	intent1 = PendingIntent.getBroadcast(context, 0, notificationIntent1,0);
//            }
//            catch(Exception e)
//            {
//            	Log.d("playstore 1",e.getMessage());
//            }
//
//            notification1.setLatestEventInfo(context, title1, message, intent1);
//            notification1.flags |= Notification.FLAG_ONLY_ALERT_ONCE|Notification.FLAG_NO_CLEAR;
//
//            // Play default notification sound
//            notification1.defaults |= Notification.DEFAULT_SOUND;
//
//            //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
//
//            // Vibrate if vibrate is enabled
//            notification1.defaults |= Notification.DEFAULT_VIBRATE;
//            Constants.my_notification_manager.notify(0, notification1);
//
//        }
//
//
//
//
//    }

}
