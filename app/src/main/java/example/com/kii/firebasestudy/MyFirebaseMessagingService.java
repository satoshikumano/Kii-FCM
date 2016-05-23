package example.com.kii.firebasestudy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = "MyMessaging";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String, String> map = remoteMessage.getData();

        for (Map.Entry<String, String> ent: map.entrySet()) {
            Log.v(TAG, "entry " + ent.getKey() + " : " + ent.getValue());
        }

        // Minimal Notification Sample.
        NotificationCompat.Builder b = new NotificationCompat.Builder(this.getApplicationContext());
        b.setSmallIcon(R.mipmap.ic_launcher);
        b.setContentTitle("Kii Cloud Push");
        b.setContentText(remoteMessage.toString());

        Notification n =  b.build();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0, n);
    }
}
