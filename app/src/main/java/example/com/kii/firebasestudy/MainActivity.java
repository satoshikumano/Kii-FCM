package example.com.kii.firebasestudy;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.kii.cloud.storage.Kii;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.callback.KiiPushCallBack;
import com.kii.cloud.storage.callback.KiiUserCallBack;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Kii.initialize(this, "9ab34d8b", "7a950d78956ed39f3b0815f0f001b43b", Kii.Site.JP);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        final TextView messageText = (TextView) findViewById(R.id.messageText);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageText.setText("Login to Kii Cloud...");
                EditText nameEdit = (EditText) findViewById(R.id.loginNameEditText);
                EditText passwordEdit = (EditText) findViewById(R.id.passwordEditText);
                String name = nameEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                KiiUser.logIn(new KiiUserCallBack() {
                    @Override
                    public void onLoginCompleted(int token, @Nullable KiiUser user, @Nullable final Exception exception) {
                        if (exception != null) {
                            String message = "Failed to login.";
                            Toast.makeText(MainActivity.this.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            messageText.setText(message);
                            return;
                        }
                        messageText.setText("Login Succeeded!");

                        // In case token is published when no user sign up/in to KiiCloud.
                        // Or different user is signed up/in to Kii Cloud.
                        SharedPreferences spf = getSharedPreferences("FCM", 0);
                        String fcmToken  = spf.getString("FCMToken", "");
                        if (fcmToken != "") {
                            messageText.setText("Installing Push...");
                            KiiUser.pushInstallation().install(fcmToken, new KiiPushCallBack() {
                                @Override
                                public void onInstallCompleted(int taskId, @Nullable Exception e) {
                                    if (exception != null) {
                                        String message = "Failed to install token to Kii Cloud.";
                                        Toast.makeText(MainActivity.this.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                        messageText.setText(message);
                                        return;
                                    }
                                    messageText.setText("Firebase token installation succeeded!");
                                }
                            });
                        }
                    }
                }, name, password);
            }
        });
    }

}
