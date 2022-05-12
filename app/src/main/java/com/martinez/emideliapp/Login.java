package com.martinez.emideliapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
        //RecuperarPreferencias();


        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario=txtUsuario.getText().toString();
                Toast.makeText(getApplicationContext(), Usuario, Toast.LENGTH_SHORT).show();
                Password=txtPassword.getText().toString();
                if (!Usuario.isEmpty() && !Password.isEmpty()){
                    EjecutarServicio("http://localhost/emideli/validar_usuarioo.php");
                }else if(Usuario.isEmpty()){
                    Toast.makeText(Login.this, "Falta el Correo",Toast.LENGTH_SHORT).show();
                }else if(Password.isEmpty()){
                    Toast.makeText(Login.this, "Falta la Contraseña",Toast.LENGTH_SHORT).show();
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
                //GuardarPreferencia();
                if (!response.isEmpty()){
                    Intent AbrirMenu = new Intent(Login.this ,Menu.class);
                    startActivity(AbrirMenu);
                }else{
                    Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
                //finish();
            }
        },new Response.ErrorListener(){
            public void onErrorResponse (VolleyError error) {
                Toast.makeText(getApplicationContext(), "error.toString()", Toast.LENGTH_SHORT).show();
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

    private void RecuperarPreferencias(){
        SharedPreferences preferences= getSharedPreferences("PreferenciasLogin",Context.MODE_PRIVATE);
        txtUsuario.setText(preferences.getString("usuario","micorreo@gmail.com"));
        txtPassword.setText(preferences.getString("contrasenia","12345678"));

    }

}
