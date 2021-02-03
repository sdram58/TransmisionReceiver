package com.catata.transmisionreceiver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.catata.transmisionsender.Producto;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    RecyclerView lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        lista = (RecyclerView) findViewById(R.id.lista);

        ocultarNotificacion();



        lista.setAdapter(new MyAdapter());
        lista.setLayoutManager(new LinearLayoutManager(this));

    }

    private void ocultarNotificacion() {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        notificationManagerCompat.cancel(MainActivity.NOTIFICACION_ID);
    }

    private ArrayList<Producto> getListFromIntent() {
        ArrayList<Producto> listaProductos;
        Intent i = getIntent();
        if(i.hasExtra(MainActivity.LOCAL_EXTRA_DATA)){
            listaProductos = i.getParcelableArrayListExtra(MainActivity.LOCAL_EXTRA_DATA);
        }else{
            listaProductos = new ArrayList<Producto>();
        }
        return listaProductos;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{

        ArrayList<Producto> listaProductos;

        public MyAdapter() {
            super();
            this.listaProductos =  getListFromIntent();

        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list, parent, false);

            return new MyHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.tvNombre.setText(listaProductos.get(position).getNombre());
            holder.tvCantidad.setText("" + listaProductos.get(position).getCantidad());
        }

        @Override
        public int getItemCount() {
            return listaProductos.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            TextView tvNombre, tvCantidad;
            public MyHolder(@NonNull View itemView) {
                super(itemView);

                tvNombre = (TextView) itemView.findViewById(R.id.tvNombre);
                tvCantidad = (TextView) itemView.findViewById(R.id.tvCantidad);
            }
        }
    }
}