package com.catata.transmisionreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.catata.transmisionsender.Producto;

import java.util.ArrayList;

public class MyBroadCastReceiver extends BroadcastReceiver {
    public static final String ACTION_RECEIVER = "com.catata.transmisionsender.MainActivity.ACTION_RECEIVER"; //Los mismos que la aplicación que envía
    public static final String EXTRA_DATA = "com.catata.transmisionsender.MainActivity.EXTRA_DATA";

    IReceiver iReceiver;

    public MyBroadCastReceiver(IReceiver iReceiver) {
        this.iReceiver = iReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().compareTo(ACTION_RECEIVER)==0){
            if(intent.hasExtra(EXTRA_DATA)){
                Bundle bundle = intent.getExtras();
                bundle.getParcelableArrayList(EXTRA_DATA);
                //OJO has de crear una clase Producto (igual a la del sender) dentro del mismo paquete que el sender, sinó dará error.
                ArrayList<Producto> listaProductos = intent.getParcelableArrayListExtra(EXTRA_DATA);

                iReceiver.onReceiveReceiver(listaProductos);
            }

        }
    }
}
