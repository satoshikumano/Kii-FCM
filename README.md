# Kii-FCM
Sample of Kii-FCM integration.
GCM (Google Cloud Messaging) has been moved to FCM.
This Sample porject shows
- How to migrate GCM based Kii Cloud application to FCM. 
- How to start Kii Cloud application with FCM integration

## Preparation
Edit [Config.java](app/src/main/java/example/com/kii/firebasestudy/Config.java)
And enter you Kii Cloud app information.
(App ID, App Key, Site can be confirmed on [Kii Developer console](https://developer.kii.com).)

## Migration from GCM.
- Sign up/in to Firebase.
- Click "IMPORT GOOGLE PROJECT" and choose the Google project integrated with Kii CLoud.
- In the Project view, click "ADD APP" -> "Add Firebase to your Android app"
- Enter Packagename, optional signing certificate.
- Download config file (google-services.json) and place it in the root directory of the application.
- Run app and login with existing user, send push notification from [Kii Developer Console](https://developer.kii.com).
  App shows notification with laucher icon (dorid icon) when recieved push. 

## Start with FCM.
- Sign up/in to Firebase.
- Click "CREATE NEW PROJECT".
- In the Project view, click "ADD APP" -> "Add Firebase to your Android app"
- Enter Packagename, optional signing certificate.
- Download config file (google-services.json) and place it in the root directory of the application.
- Check Project Setting -> "Cloud Messaging" Tab. Copy "Server Key" value.
- Go to [Kii Developer Console](https://developer.kii.com) and choose app.
  (If you haven't created app, please create app.)
- Save Server Key copied from Firebase console and as GCM Key in Notification settings.
  (Please see the [guide](http://docs.kii.com/en/samples/push-notifications/push-notifications-android/configure-devportal/) how to set GCM Key in Kii Cloud Console.
- Run app and login with existing user, send push notification from [Kii Developer Console](https://developer.kii.com).
  App shows notification with laucher icon (dorid icon) when recieved push. 

## Sample Code anatomy

### [MyFirebaseInstanceIdService](https://github.com/satoshikumano/Kii-FCM/blob/master/app/src/main/java/example/com/kii/firebasestudy/MyFirebaseInstanceIdService.java)

When FCM token has been published or changed, Need to install it to Kii Cloud.

In Kii Cloud, FCM token is bound to KiiUser.
When no user is sign up/in to Kii Cloud, Just store the token to SharedPreferences.
If user is logged in, Upload it to Kii Cloud.

### [MainActivity](https://github.com/satoshikumano/Kii-FCM/blob/master/app/src/main/java/example/com/kii/firebasestudy/MainActivity.java)

Implement Kii User sign in process.
When the FCM token is stored in SharedPreference, update installation in Kii Cloud.
It is necessary when the FCM token is published on initial app installation or KiiUser has been changed after the app installation.

### [MyFirebaseMessagingService](https://github.com/satoshikumano/Kii-FCM/blob/master/app/src/main/java/example/com/kii/firebasestudy/MyFirebaseMessagingService.java)

Just shows simple notification when Recieved notification from Kii Cloud through FCM. 
