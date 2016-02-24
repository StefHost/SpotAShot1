package nl.stefhost.viewfinder;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.StringTokenizer;

public class GcmIntentService extends IntentService {

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				Log.d("Viewfinder", "MESSAGE_TYPE_SEND_ERROR");
			}else if(GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				Log.d("Viewfinder", "MESSAGE_TYPE_DELETED");
            }else if(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
				Log.d("Viewfinder", "Notificatie ontvangen");
				Log.d("Viewfinder", extras.getString("message"));
                sendNotification(extras.getString("message"));
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

	String id;

    public void sendNotification(String msg) {
    	
    	StringTokenizer tokens = new StringTokenizer(msg, "|");
    	String titel = tokens.nextToken();
    	String tekst = tokens.nextToken();
    	String action = tokens.nextToken();
    	tekst = tekst.replace("[enter]", "\n");

		if(action.contains("RELOAD")){
			StringTokenizer stringTokenizer = new StringTokenizer(action, "-");
			String actie = stringTokenizer.nextToken();
			String id = stringTokenizer.nextToken();

			SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
			String huidig_spel = sharedPreferences.getString("huidig_spel", "");

			if (huidig_spel.equals(id)){
				Intent intent = new Intent (this, Spel.class);
				Bundle bundle = new Bundle();
				bundle.putString("id", id);
				intent.putExtras(bundle);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}

		}else{

			Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

			NotificationCompat.Builder Builder = new NotificationCompat.Builder(this)
					.setSmallIcon(R.drawable.logo)
					.setLargeIcon(bitmap)
					.setContentTitle(titel)
					.setContentText(tekst)
					.setAutoCancel(true)
					.setSound(alarmSound);

			NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender();

			Builder.extend(wearableExtender);

			Intent intent;
			if (action.equals("SPELLEN")) {
				intent = new Intent(this, Spellen.class);
			}else if(action.contains("SPEL")){
				StringTokenizer stringTokenizer = new StringTokenizer(action, "-");
				String actie = stringTokenizer.nextToken();
				id = stringTokenizer.nextToken();
				intent = new Intent(this, Spel.class);
				Bundle bundle = new Bundle();
				bundle.putString("id", id);
				intent.putExtras(bundle);
			}else{
				intent = new Intent(this, Home.class);
			}

			TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
			stackBuilder.addParentStack(Home.class);
			stackBuilder.addNextIntent(intent);
			PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
			Builder.setContentIntent(resultPendingIntent);

			SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
			String huidig_spel = sharedPreferences.getString("huidig_spel", "");
			if (!huidig_spel.equals(id)) {
				NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				mNotificationManager.notify(0, Builder.build());
			}

		}
    	


    }
}