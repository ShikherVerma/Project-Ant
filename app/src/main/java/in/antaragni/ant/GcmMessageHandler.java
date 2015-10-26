package in.antaragni.ant;

import com.google.android.gms.gcm.GcmListenerService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Objects;
import java.util.Random;

import in.antaragni.ant.datahandler.DatabaseAccess;
import in.antaragni.ant.fragments.EventFragment;

public class GcmMessageHandler extends GcmListenerService {
  private DatabaseAccess databaseAccess;





  @Override
  public void onMessageReceived(String from, Bundle data) {
    String type = data.getString("type");
    String title = data.getString("title");
    String message = "";
    String event_name ;
    databaseAccess = DatabaseAccess.getInstance(this);
    databaseAccess.open();
    if(type.equals("eventdetailchange"))
    {
      event_name = data.getString("eventname");
      String start_time = data.getString("start_time");
      String end_time = data.getString("end_time");
      String venue = data.getString("venue");
      message = message.concat(event_name);

      if (!start_time.isEmpty())
      {
        databaseAccess.updateinfo(event_name, Integer.parseInt(start_time),Integer.parseInt(end_time));
        message = message.concat(" " + start_time + "-" + end_time);
      }
      if (!venue.isEmpty())
      {
        databaseAccess.updateinfo(event_name, venue);
        message = message.concat(" " + venue);
      }
      createNotification(title, message, event_name, type);
    }
    else if (type.equals("result")){
      message = data.getString("result");
      event_name = data.getString("eventname");
      databaseAccess.updateresult(event_name, message);
      createNotification(title, message, event_name, type);
    }
    else {
      message = data.getString("message");
      createNotification(title, message, "notify", type);
    }
    databaseAccess.addnotification(message,type);
    databaseAccess.close();
  }

  // Creates notification based on title and body received
  private void createNotification(String title, String body, String event_name, String type) {
    Context context = getBaseContext();
    Intent intent;
    if (type.equals("eventdetailchange")) {
      intent = new Intent(context, EventDetailActivity.class);
      intent.putExtra(EventDetailActivity.EXTRA_NAME, event_name);
    }
    else if (type.equals("result")){
      intent = new Intent(context, EventDetailActivity.class);
      intent.putExtra(EventDetailActivity.EXTRA_NAME, event_name);
    }
    else {
      intent = new Intent(context, MainActivity.class);
    }
    PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
      .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title)
      .setContentText(body).setDefaults(Notification.DEFAULT_SOUND)
      .setContentIntent(resultPendingIntent).setAutoCancel(true);
    NotificationManager mNotificationManager = (NotificationManager) context
      .getSystemService(Context.NOTIFICATION_SERVICE);

    //multiple notification
    Random random = new Random();
    int m = random.nextInt(9999 - 1000) + 1000;
    //---/

    mNotificationManager.notify(m, mBuilder.build());
  }


}

