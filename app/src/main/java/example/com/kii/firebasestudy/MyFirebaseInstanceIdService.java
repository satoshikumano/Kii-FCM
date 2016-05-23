package example.com.kii.firebasestudy;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.kii.cloud.storage.Kii;
import com.kii.cloud.storage.KiiCallback;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.callback.KiiPushCallBack;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    public static final String TAG = "MyIDService";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        // Get updated InstanceID token.
        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        final SharedPreferences spf = this.getSharedPreferences("FCM", 0);
        spf.edit().putString("FCMToken", refreshedToken).apply();

        Kii.initialize(this.getApplicationContext(), "9ab34d8b", "7a950d78956ed39f3b0815f0f001b43b", Kii.Site.JP);

        // Try to login with Stored Credentials if there's user sign up/in to Kii Cloud.
        KiiUser.loginWithStoredCredentials(new KiiCallback<KiiUser>() {
            @Override
            public void onComplete(KiiUser kiiUser, Exception e) {
                if (e != null) {
                    Log.v(TAG, "Failed to login to Cloud or No user logged in.");
                    // Don't worry. Push will be installed when user sign up/in to Kii Cloud on MainActivity.
                    return;
                }
                KiiUser.pushInstallation().install(refreshedToken, new KiiPushCallBack() {
                    @Override
                    public void onInstallCompleted(int taskId, @Nullable Exception e) {
                        if (e != null) {
                            Log.v(TAG, "Failed to Install push");
                            return;
                        }
                        Log.v(TAG, "Push Installation succeeded.");
                    }
                });
            }
        });
    }
}
