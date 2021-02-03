package com.catata.transmisionsender;

import android.os.Parcel;
import android.os.Parcelable;

public class Producto implements Parcelable {
    String nombre;
    int cantidad;

    public Producto(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public Producto(Parcel in) {
        nombre = in.readString();
        cantidad = in.readInt();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        /*Ha de estar en el mismo nombre que el constructor de parámetro parcel*/
        dest.writeString(this.nombre);
        dest.writeInt(this.cantidad);
    }

    /*CREATOR para poder crear listas de parcelables*/
    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };
}
