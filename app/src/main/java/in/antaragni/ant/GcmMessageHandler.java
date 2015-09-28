package in.antaragni.ant;

import com.google.android.gms.gcm.GcmListenerService;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import in.antaragni.ant.datahandler.DatabaseAccess;

public class GcmMessageHandler extends GcmListenerService {
  public static final int MESSAGE_NOTIFICATION_ID = 435345;
  private DatabaseAccess databaseAccess;





  @Override
  public void onMessageReceived(String from, Bundle data) {
    String title = data.getString("title");
    String time = data.getString("time");
    String venue = data.getString("venue");
    String event_name = data.getString("eventname");
    String message = "" ;
    message = message.concat(event_name);
    databaseAccess = DatabaseAccess.getInstance(this);
    databaseAccess.open();

    if (!time.isEmpty())  {
      databaseAccess.updateinfo(event_name, Integer.parseInt(time));
      message = message.concat( " " + time);
    }
    if (!venue.isEmpty()) {
      databaseAccess.updateinfo(event_name, venue);
      message = message.concat( " " + venue);
    }
    createNotification(title, message);
    databaseAccess.close();
  }

  // Creates notification based on title and body received
  private void createNotification(String title, String body) {
    Context context = getBaseContext();
    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
      .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title)
      .setContentText(body);
    NotificationManager mNotificationManager = (NotificationManager) context
      .getSystemService(Context.NOTIFICATION_SERVICE);
    mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
  }


}

