package com.martinez.emideliapp.verclientes;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.Date;

public class Cliente {

    public String Nombre ;
    public String Tipo_pedido;
    public String Fecha_entrega ;
    public String Descripcion;
    public String Abono;
    public String Total_pedido;

    public Cliente(String nombre, String tipo_pedido, String fecha_entrega, String descripcion, String abono, String total_pedido) {
        Nombre = nombre;
        Tipo_pedido = tipo_pedido;
        Fecha_entrega = fecha_entrega;
        Descripcion = descripcion;
        Abono = abono;
        Total_pedido = total_pedido;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTipo_pedido() {
        return Tipo_pedido;
    }

    public void setTipo_pedido(String tipo_pedido) {
        Tipo_pedido = tipo_pedido;
    }

    public String getFecha_entrega() {
        return Fecha_entrega;
    }

    public void setFecha_entrega(String fecha_entrega) {
        Fecha_entrega = fecha_entrega;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getAbono() {
        return Abono;
    }

    public void setAbono(String abono) {
        Abono = abono;
    }

    public String getTotal_pedido() {
        return Total_pedido;
    }

    public void setTotal_pedido(String total_pedido) {
        Total_pedido = total_pedido;
    }

   /* @Override
    public String toString() {
        return "Cliente{" +
                "Nombre='" + Nombre + '\'' +
                ", telefono=" + telefono +
                ", fecha=" + fecha +
                '}';
    }*/
}
