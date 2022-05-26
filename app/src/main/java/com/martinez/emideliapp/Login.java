package com.martinez.emideliapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class Login extends AppCompatActivity {

    EditText txtUsuario, txtPassword;
    Button btnIniciar;
    String Usuario , Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtPassword = findViewById(R.id.txtPassword);
        btnIniciar = findViewById(R.id.btnIniciar);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario=txtUsuario.getText().toString();
                Password=txtPassword.getText().toString();
                if (!Usuario.isEmpty() && !Password.isEmpty()){
                    EjecutarServicio("https://emideli.online/validar_usuarioo.php");
                }else if(Usuario.isEmpty()){
                    new StyleableToast
                            .Builder(getApplicationContext())
                            .text("Falta Correo")
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.RED)
                            .show();
                }else if(Password.isEmpty()){
                    new StyleableToast
                            .Builder(getApplicationContext())
                            .text("Falta Contraseña")
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.RED)
                            .show();
                }

            }
        });
    }

    //Se Controla La Pulsacion Boton Atras
    @Override
    public void onBackPressed() {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("¿Desea Salir de Emideli?").setPositiveButton
                    ("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            Intent intent=new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton
                            ("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.dismiss();
                                }
                            });
            builder.show();
    }

    private void EjecutarServicio (String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GuardarPreferencia();
                if (!response.isEmpty()){
                    Intent AbrirMenu = new Intent(Login.this ,Menu.class);
                    startActivity(AbrirMenu);
                }else{
                    new StyleableToast
                            .Builder(getApplicationContext())
                            .text("Error")
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.RED)
                            .show();
                }
            }
        },new Response.ErrorListener(){
            public void onErrorResponse (VolleyError error) {
                new StyleableToast
                        .Builder(getApplicationContext())
                        .text(Error.class.toString())
                        .textColor(Color.WHITE)
                        .backgroundColor(Color.RED)
                        .show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("usuario", txtUsuario.getText().toString());
                parametros.put("password", txtPassword.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add (stringRequest);
    }

    private void GuardarPreferencia(){
        SharedPreferences preferences=getSharedPreferences("PreferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("usuario",Usuario);
        editor.putString("contrasenia",Password);
        editor.putBoolean("session",true);
        editor.commit();
    }

}
