package com.martinez.emideliapp;

import java.util.Date;

public class Pedidos {

    int id, Abono,TotalPedido;
    String tipoPedido, nombreCliente,Descripcion;
    Date FechaEntrega;
    byte ImagenReferencial;

    public Pedidos() {

    }

    public Pedidos(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Pedidos(int id, int abono, int totalPedido, String tipoPedido, String nombreCliente, String descripcion, Date fechaEntrega, byte imagenReferencial) {
        this.id = id;
        Abono = abono;
        TotalPedido = totalPedido;
        this.tipoPedido = tipoPedido;
        this.nombreCliente = nombreCliente;
        Descripcion = descripcion;
        FechaEntrega = fechaEntrega;
        ImagenReferencial = imagenReferencial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAbono() {
        return Abono;
    }

    public void setAbono(int abono) {
        Abono = abono;
    }

    public int getTotalPedido() {
        return TotalPedido;
    }

    public void setTotalPedido(int totalPedido) {
        TotalPedido = totalPedido;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Date getFechaEntrega() {
        return FechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        FechaEntrega = fechaEntrega;
    }

    public byte getImagenReferencial() {
        return ImagenReferencial;
    }

    public void setImagenReferencial(byte imagenReferencial) {
        ImagenReferencial = imagenReferencial;
    }

    @Override
    public String toString() {
        return nombreCliente;
    }
}
