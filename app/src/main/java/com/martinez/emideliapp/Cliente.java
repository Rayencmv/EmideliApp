package com.martinez.emideliapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class Cliente extends AppCompatActivity {

    EditText txtNombre, txtDireccion, txtNumero;
    Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        txtNombre = findViewById(R.id.txtNombre);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtNumero = findViewById(R.id.txtNumero);
        btnAgregar = findViewById(R.id.btnIngresarC);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EjecutarServicio("https://emideli.online/addCliente.php");
//                txtNombre.setText("");
//                txtNumero.setText("");
//                txtDireccion.setText("");
            }
        });


    }

    private void EjecutarServicio (String URL){
    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            new StyleableToast
                    .Builder(getApplicationContext())
                    .text("Clientito Ingresadito")
                    .textColor(Color.BLACK)
                    .backgroundColor(Color.TRANSPARENT)
                    .show();
        }
    },new Response.ErrorListener(){
            public void onErrorResponse (VolleyError error) {
                new StyleableToast
                        .Builder(getApplicationContext())
                        .text(Error.class.toString())
                        .textColor(Color.BLACK)
                        .backgroundColor(Color.TRANSPARENT)
                        .show();
            }
        })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("nombre", txtNombre.getText().toString());
                    parametros.put("contacto", txtNumero.getText().toString());
                    parametros.put("direccion", txtDireccion.getText().toString());
                    return parametros;
                }
            };
                    RequestQueue requestQueue= Volley.newRequestQueue(this);
                    requestQueue.add (stringRequest);
        }


    }


