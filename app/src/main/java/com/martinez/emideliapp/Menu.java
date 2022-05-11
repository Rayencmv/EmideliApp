package com.martinez.emideliapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void CambioCalendario(View view){
        Intent AbrirCalendario = new Intent(Menu.this ,Caledario.class);
        startActivity(AbrirCalendario);
    }

    public void CambioPedido(View view){
        Intent AbrirPedido = new Intent(Menu.this ,Pedido.class);
        startActivity(AbrirPedido);
    }

    public void CambioRegistroC(View view){
        Intent AbrirCliente = new Intent(Menu.this ,Cliente.class);
        startActivity(AbrirCliente);
    }

}