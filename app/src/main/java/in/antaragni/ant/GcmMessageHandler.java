package in.antaragni.ant;

import com.google.android.gms.gcm.GcmListenerService;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class GcmMessageHandler extends GcmListenerService {
  public static final int MESSAGE_NOTIFICATION_ID = 435345;

  @Override
  public void onMessageReceived(String from, Bundle data) {
    String message = data.getString("message");

    createNotification(message, from);
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

