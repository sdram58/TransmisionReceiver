package com.catata.transmisionreceiver;

import com.catata.transmisionsender.Producto;

import java.util.ArrayList;

public interface IReceiver {
    public void onReceiveReceiver(ArrayList<Producto> lista);
}
