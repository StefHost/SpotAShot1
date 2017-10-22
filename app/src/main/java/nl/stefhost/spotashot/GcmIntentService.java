package nl.stefhost.spotashot;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
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
                sendNotification(extras.getString("message"));
				Log.d("Viewfinder", "Notificatie: "+extras.getString("message"));
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

	String id;

    public void sendNotification(String msg) {
    	
    	StringTokenizer tokens = new StringTokenizer(msg, "|");
    	String notificatie_nummer = tokens.nextToken();
    	String naam = tokens.nextToken();
    	String action = tokens.nextToken();
		String notificatie_titel;
		String notificatie_tekst;

		if(action.contains("RELOAD")){

			StringTokenizer stringTokenizer = new StringTokenizer(action, "-");
			String actie = stringTokenizer.nextToken();
			String id = stringTokenizer.nextToken();

			SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
			String huidige_spel = sharedPreferences.getString("huidige_spel", "");
			String huidige_chat = sharedPreferences.getString("huidige_chat", "");

			if(actie.equals("CHAT") && huidige_chat.equals(id)){
				Intent intent = new Intent(this, Chat.class);
				Bundle bundle = new Bundle();
				bundle.putString("nummer", id);
				intent.putExtras(bundle);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}else if(huidige_spel.equals(id)) {
				Intent intent = new Intent(this, Spel.class);
				Bundle bundle = new Bundle();
				bundle.putString("id", id);
				intent.putExtras(bundle);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}

		}else{

			SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
			String geluid = sharedPreferences.getString("geluid", "");

			Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_notificatie);

			if (notificatie_nummer.equals("notificatie_1")){
				notificatie_titel = getString(R.string.notificatie_1_titel);
				notificatie_tekst = getString(R.string.notificatie_1_tekst);
				notificatie_tekst = notificatie_tekst.replace("[naam]", naam);
			}else if (notificatie_nummer.equals("notificatie_2")){
				notificatie_titel = getString(R.string.notificatie_2_titel);
				notificatie_tekst = getString(R.string.notificatie_2_tekst);
				notificatie_tekst = notificatie_tekst.replace("[naam]", naam);
			}else if (notificatie_nummer.equals("notificatie_3")){
				notificatie_titel = getString(R.string.notificatie_3_titel);
				notificatie_tekst = getString(R.string.notificatie_3_tekst);
			}else if (notificatie_nummer.equals("notificatie_4")){
				notificatie_titel = getString(R.string.notificatie_4_titel);
				notificatie_tekst = getString(R.string.notificatie_4_tekst);
				notificatie_titel = notificatie_titel.replace("[naam]", naam);
			}else if (notificatie_nummer.equals("notificatie_5")){
				notificatie_titel = getString(R.string.notificatie_5_titel);
				notificatie_tekst = getString(R.string.notificatie_5_tekst);
			}else if (notificatie_nummer.equals("notificatie_6")){
				notificatie_titel = getString(R.string.notificatie_6_titel);
				notificatie_tekst = getString(R.string.notificatie_6_tekst);
				notificatie_tekst = notificatie_tekst.replace("[aantal]", naam);
			}else if (notificatie_nummer.equals("notificatie_7")){
				notificatie_titel = getString(R.string.notificatie_7_titel);
				notificatie_tekst = getString(R.string.notificatie_7_tekst);
				notificatie_tekst = notificatie_tekst.replace("[naam]", naam);
			}else if (notificatie_nummer.equals("notificatie_8")){
				notificatie_titel = getString(R.string.notificatie_8_titel);
				notificatie_tekst = getString(R.string.notificatie_8_tekst);
				notificatie_tekst = notificatie_tekst.replace("[naam]", naam);
			}else{
				notificatie_titel = getString(R.string.notificatie_9_titel);
				notificatie_tekst = getString(R.string.notificatie_9_tekst);
				notificatie_tekst = notificatie_tekst.replace("[naam]", naam);
			}

			NotificationCompat.Builder Builder = new NotificationCompat.Builder(this)
					.setSmallIcon(R.drawable.notificatie)
					//.setLargeIcon(bitmap)
					.setColor(0x000000)
					.setContentTitle(notificatie_titel)
					.setContentText(notificatie_tekst)
					.setStyle(new NotificationCompat.BigTextStyle()
							.bigText(notificatie_tekst))
					.setAutoCancel(true);

			if (geluid.equals("") || geluid.equals("AAN")) {
				Builder.setSound(alarmSound);
			}

			NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender();

			Builder.extend(wearableExtender);

			Intent intent;
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

			if (action.equals("SPELLEN")) {
				intent = new Intent(this, Spellen.class);
				stackBuilder.addParentStack(Spellen.class);
			}else if(action.contains("SPEL")){
				StringTokenizer stringTokenizer = new StringTokenizer(action, "-");
				id = stringTokenizer.nextToken();
				id = stringTokenizer.nextToken();
				intent = new Intent(this, Spel.class);
				Bundle bundle = new Bundle();
				bundle.putString("id", id);
				intent.putExtras(bundle);
				stackBuilder.addParentStack(Spel.class);
			}else if(action.contains("CHAT")){
				StringTokenizer stringTokenizer = new StringTokenizer(action, "-");
				id = stringTokenizer.nextToken();
				id = stringTokenizer.nextToken();
				intent = new Intent(this, Chat.class);
				Bundle bundle = new Bundle();
				bundle.putString("nummer", id);
				intent.putExtras(bundle);
				stackBuilder.addParentStack(Chat.class);
			}else{
				intent = new Intent(this, Home.class);
			}

			int nummer = sharedPreferences.getInt("nummer", 0);

			stackBuilder.addNextIntent(intent);
			PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(nummer, PendingIntent.FLAG_UPDATE_CURRENT);
			Builder.setContentIntent(resultPendingIntent);

			String huidige_spel = sharedPreferences.getString("huidige_spel", "");
			String huidige_chat = sharedPreferences.getString("huidige_chat", "");

			if (!huidige_spel.equals(id) && !huidige_chat.equals(id)) {

				String notificaties_1 = sharedPreferences.getString("notificaties_1", "");
				String notificaties_2 = sharedPreferences.getString("notificaties_2", "");
				String notificaties_3 = sharedPreferences.getString("notificaties_3", "");
				String notificaties_4 = sharedPreferences.getString("notificaties_4", "");
				String notificaties_5 = sharedPreferences.getString("notificaties_5", "");

				String notificatie = "nee";

				if (notificatie_nummer.equals("notificatie_1") && (notificaties_1.equals("") || notificaties_1.equals("AAN")) ) {
					notificatie = "ja";
				}else if (notificatie_nummer.equals("notificatie_2") && (notificaties_2.equals("") || notificaties_2.equals("AAN")) ){
					notificatie = "ja";
				}else if (notificatie_nummer.contains("notificatie_3") && (notificaties_3.equals("") || notificaties_3.equals("AAN")) ){
					notificatie = "ja";
				}else if ((notificatie_nummer.contains("notificatie_4") || notificatie_nummer.contains("notificatie_6") || notificatie_nummer.contains("notificatie_7"))  && (notificaties_4.equals("") || notificaties_4.equals("AAN")) ) {
					notificatie = "ja";
				}else if (notificatie_nummer.contains("notificatie_8") && (notificaties_5.equals("") || notificaties_5.equals("AAN")) ) {
					notificatie = "ja";
				}else if (notificatie_nummer.contains("notificatie_9") || notificatie_nummer.contains("notificatie_5")){
					notificatie = "ja";
				}

				if (notificatie.equals("ja")){

					//notificatie opslaan
					ArrayList<String> arrayList_notificaties;
					arrayList_notificaties = new ArrayList<>();
					Set<String> stringSet;

					if (notificatie_nummer.equals("notificatie_1") || notificatie_nummer.equals("notificatie_2") || notificatie_nummer.equals("notificatie_6") || notificatie_nummer.equals("notificatie_7")) {
						stringSet = sharedPreferences.getStringSet("notificaties_spellen", new HashSet<String>());
					}else if (notificatie_nummer.equals("notificatie_3") || notificatie_nummer.equals("notificatie_4") || notificatie_nummer.equals("notificatie_5")) {
						stringSet = sharedPreferences.getStringSet("notificaties_spel_"+id, new HashSet<String>());
					}else if (notificatie_nummer.equals("notificatie_8")) {
						stringSet = sharedPreferences.getStringSet("notificaties_chat_"+id, new HashSet<String>());
					}else{
						stringSet = new HashSet<>();
					}

					for (String string : stringSet) {
						arrayList_notificaties.add(string);
					}
					arrayList_notificaties.add(String.valueOf(nummer));
					SharedPreferences.Editor editor = sharedPreferences.edit();
					if (notificatie_nummer.equals("notificatie_1") || notificatie_nummer.equals("notificatie_2") || notificatie_nummer.equals("notificatie_6") || notificatie_nummer.equals("notificatie_7")) {
						editor.putStringSet("notificaties_spellen", new HashSet<>(arrayList_notificaties));
					}else if (notificatie_nummer.equals("notificatie_3") || notificatie_nummer.equals("notificatie_4") || notificatie_nummer.equals("notificatie_5")) {
						editor.putStringSet("notificaties_spel_"+id, new HashSet<>(arrayList_notificaties));
					}else if (notificatie_nummer.equals("notificatie_8")) {
						editor.putStringSet("notificaties_chat_"+id, new HashSet<>(arrayList_notificaties));
					}

					// notificatie maken
					NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
					mNotificationManager.notify(nummer, Builder.build());
					nummer++;

					editor.putInt("nummer", nummer);
					editor.apply();

				}

			}

		}
    	


    }
}