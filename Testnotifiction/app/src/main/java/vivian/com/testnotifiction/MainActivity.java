package vivian.com.testnotifiction;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showAppNotifiction();
        switchToApp();

    }

    void showAppNotifiction(){
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        CharSequence form = "Joe";
        CharSequence message;
        switch ((new Random().nextInt())%3){
            case 0: message = " r u hungry?";
                break;
            case 1:message = "im nearby y";
                break;
            default:
                message = "meet y for dinner";
                break;
        }

//        PendingIntent content = PendingIntent.getActivities(this,0,
//                makeMessageIntentStack(this,form,message),PendingIntent.FLAG_CANCEL_CURRENT);
        Intent intent = new Intent(this, vivian.com.testnotifiction.message.class);
        intent.putExtra("form",form);
        intent.putExtra("message",message);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent content = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = builder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("ddddddd")
                .setTicker(message)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(content)
                .build();
        manager.notify(1,notification);


    }
    void switchToApp() {
        // We will launch the app showing what the user picked.  In this simple
        // example, it is just what the notification gave us.
        CharSequence from = getIntent().getCharSequenceExtra("from");
        CharSequence msg = getIntent().getCharSequenceExtra("message");
        // Build the new activity stack, launch it, and finish this UI.
        Intent[] stack = message.makeMessageIntentStack(this, from, msg);
        startActivities(stack);
        finish();
    }




}
