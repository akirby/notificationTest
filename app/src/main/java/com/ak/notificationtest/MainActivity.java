package com.ak.notificationtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String CHANNEL_ID = "AlertChannel";
    private TextView mainText;

    private int notificationId = 1;
    private NotificationManagerCompat notificationManager;

    public BroadcastReceiver approveReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent){
            notificationManager.cancel(notificationId);
            String data = intent.getAction();
            Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG);
            if(data != null && data.equals("com.myapp.Approve")){
                mainText.setText("Approved");
            }
            else{
                mainText.setText("Denied");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.registerReceiver(approveReceiver, new IntentFilter("com.myapp.Approve"));
        this.registerReceiver(approveReceiver, new IntentFilter("com.myapp.Deny"));

        setContentView(R.layout.activity_main);
        mainText = (TextView) findViewById(R.id.mainText);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        notificationManager = NotificationManagerCompat.from(this);
        createNotificationChannel();

       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification();
            }
        });
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
//        }
    }

    public void showNotification(){

//        Intent approveIntent = new Intent(this, MainActivity.class);
//        approveIntent.setData(Uri.parse("Approve"));
//        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, approveIntent, Intent.FILL_IN_ACTION);
//
//        Intent denyIntent = new Intent(this, MainActivity.class);
//        approveIntent.setData(Uri.parse("deny"));
//        PendingIntent denyPendingIntent = PendingIntent.getActivity(MainActivity.this, 0, denyIntent, Intent.FILL_IN_ACTION);
        Context appContext = getApplicationContext();
//        Intent approveIntent = new Intent(this, MainActivity.class);
        Intent approveIntent = new Intent("com.myapp.Approve");
//        approveIntent.setData(Uri.parse("Approve"));
//        approveIntent.setAction("com.myapp.Approve");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, approveIntent, PendingIntent.FLAG_CANCEL_CURRENT);

//        Intent denyIntent = new Intent(this, MainActivity.class);
        Intent denyIntent = new Intent("com.myapp.Deny");
//        approveIntent.setData(Uri.parse("deny"));
//        denyIntent.setAction("com.myapp.Deny");
        PendingIntent denyPendingIntent = PendingIntent.getBroadcast(this, 2, denyIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Test Notification")
                .setContentText("Test notification details")
                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_launcher_foreground, getString(R.string.Approved),
                        pendingIntent)
                .addAction(R.drawable.ic_launcher_foreground, getString(R.string.Deny),
                        denyPendingIntent);
//        Notification myNotification = builder.build();
//        myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
// notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
    }

//    protected void onNewIntent(Intent intent){
//        int x = 1;
//        x = x +1;
//        notificationManager.cancel(notificationId);
//        String data = intent.getDataString();
//        if(data != null && data.equals("Approve")){
//            mainText.setText("Approved");
//        }
//        else{
//            mainText.setText("Denied");
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


