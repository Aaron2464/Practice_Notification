package com.aaron.practice_notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.send);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                senNotification();
                break;
        }
    }

    private void senNotification() {
        String title = "Notification";
        String content = "Succeed";
        CharSequence name = "渠道名稱1";
        String description = "渠道描述1";
        String channelId = "channelId1";        //渠道id
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        NotificationCompat.Builder builder;                         // NotificationCompat.Builder V.S.   Notification.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //創建通知渠道
            int importance = NotificationManager.IMPORTANCE_HIGH;       //重要性級別
            NotificationChannel mChannel = new NotificationChannel(channelId, name, importance);
            mChannel.setDescription(description);   //渠道描述
            mChannel.enableLights(true);            //是否顯示通知指示燈
            mChannel.enableVibration(true);         //是否振動
            builder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.run)           // small icon must be png, and background must be transparent.
                    .setContentTitle(title)
                    .setContentText(content)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(" NotificationCompat.Builder  Notification.Builder 的差別就是能不能支持 setStyle"))
                    .setChannelId(channelId)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            notificationManager.createNotificationChannel(mChannel);//創建通知渠道
            notificationManager.notify(new Random().nextInt(), builder.build());
        } else {
            builder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.run)
                    .setContentTitle(title)
                    .setContentText(content);
            Notification notification = builder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notificationManager.notify(new Random().nextInt(), notification);  // if id = id , notification just has only one;
        }
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(contentIntent);
    }
}
