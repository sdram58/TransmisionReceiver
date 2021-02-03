package com.catata.transmisionreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.catata.transmisionsender.Producto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver br;
    IntentFilter intentFilter;
    PendingIntent pendingIntent;

    private static final int PENDING_REQUEST = 1;

    private static final String CHANNEL_ID = "NOTIFICACION";
    public static final int NOTIFICACION_ID = 1;

    public static final String LOCAL_EXTRA_DATA = "ListaProductos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        br = new MyBroadCastReceiver(new IReceiver() {
            @Override
            public void onReceiveReceiver(ArrayList<Producto> lista) {
                setPendingIntent(lista);
                createNotificacionChannel();
                crearNotificacion(lista.size());
            }
        });

        intentFilter = new IntentFilter(MyBroadCastReceiver.ACTION_RECEIVER);

    }


    private void setPendingIntent(ArrayList<Producto> lista) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putParcelableArrayListExtra(LOCAL_EXTRA_DATA, lista);

        //Para que al dar hacia atrás vaya a la main y no salga (opcional)
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        //Le decimos que somos (MainActivity) la actividad padre para MainActivity2
        stackBuilder.addParentStack(MainActivity2.class);
        stackBuilder.addNextIntent(intent);


        pendingIntent = stackBuilder.getPendingIntent(PENDING_REQUEST, PendingIntent.FLAG_UPDATE_CURRENT);


    }

    //Versiones posteriores a Oreo
    private void createNotificacionChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //Características del Canal
            CharSequence name="Notificacion Normal";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    //Inferiores a Oreo API 26 Android 8.0
    private void crearNotificacion(int num) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_products);
        builder.setContentTitle("Productos");
        builder.setContentText("Han llegado " + num + " productos nuevos");
        //builder.setStyle(new NotificationCompat.BigTextStyle()
        //        .bigText("Texto largo para que no cabe en una única línea"));
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA,1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000, 1000});
        builder.setSound(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.hey));
        //builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID,builder.build());
    }

    @Override
    protected  void onResume(){
        super.onResume();
        registerReceiver(br,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }
}