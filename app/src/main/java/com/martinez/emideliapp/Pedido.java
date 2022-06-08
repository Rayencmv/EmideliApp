package com.martinez.emideliapp;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import com.loopj.android.http.*;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import io.github.muddz.styleabletoast.StyleableToast;

public class Pedido extends AppCompatActivity {

    AsyncHttpClient pedido;
    Button btnAgregarP, btnImagen, btnFecha;
    EditText txtFecha, txtTotal, txtNombreC, txtAbono, txtDescripcion;
    CheckBox cbNuevo;
    Spinner spTipoPedido, spCliente;
    private  int dia,mes,ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        btnAgregarP = findViewById(R.id.btnIngresarP);
        txtFecha = findViewById(R.id.txtEntrega);
        txtTotal = findViewById(R.id.txtTotal);
        txtNombreC = findViewById(R.id.txtNombreC);
        txtAbono = findViewById(R.id.txtAbono);
        cbNuevo = findViewById(R.id.cbNuevo);
        spTipoPedido = findViewById(R.id.spTipoPedido);
        spCliente = findViewById(R.id.spCliente);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        btnImagen = findViewById(R.id.btnImagen);
        btnFecha = findViewById(R.id.btnFecha);

        pedido = new AsyncHttpClient();
        txtFecha.setEnabled(false);
        txtNombreC.setEnabled(false);
        CargarTipoPedido();
        LlenarSpinner();
        ClienteNuevo();
        GuardarPedido();
        Fecha();


    }


    //Funcion del boton guardar y conexion con el web service
    private void GuardarPedido(){
        btnAgregarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EjecutarServicio("https://emideli.online/addPedido.php");
            }
        });
    }

    //llena los datos del spinner con el nombre del cliente
    private void LlenarSpinner() {
        String url = "https://emideli.online/obtenerDatos.php";
        pedido.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    CargarSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    //controla si el cliente es nuevo o ya esta agregado en la base de datos
    private void ClienteNuevo() {
        cbNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbNuevo.isChecked() == false) {
                    spCliente.setEnabled(true);
                    txtNombreC.setEnabled(false);


                } else {
                    spCliente.setEnabled(false);
                    txtNombreC.setEnabled(true);

                }
            }
        });
    }

    //seleccionar tipo de pedido si es personalizado o si es predeterminado
    private void CargarTipoPedido() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.TipoPedido, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spTipoPedido.setAdapter(adapter);
    }

    // se carga el spinner de tipo de pedido
    private void CargarSpinner(String respuesta) {
        ArrayList<Pedidos> lista = new ArrayList<Pedidos>();

        try {
            String s = "Seleccione";
            JSONArray jsonArreglo = new JSONArray(respuesta);
            Pedidos p1 = new Pedidos();
            lista.add(new Pedidos("Seleccione"));
            for (int i = 0; i < jsonArreglo.length(); i++) {
                Pedidos p2 = new Pedidos();
                p2.setNombreCliente(jsonArreglo.getJSONObject(i).getString("Nombre"));
                lista.add(p2);
            }
            ArrayAdapter<Pedidos> a = new ArrayAdapter<Pedidos>(this, android.R.layout.simple_dropdown_item_1line, lista);
            spCliente.setAdapter(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Enviar los datos del pedido al web service para ingresar a la base de datos
    private void EjecutarServicio(String URL) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    new StyleableToast
                            .Builder(getApplicationContext())
                            .text("Pedido Ingresado")
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
                    if (cbNuevo.isChecked()==true) {
                        parametros.put("TipoPedido", spTipoPedido.getSelectedItem().toString());
                        parametros.put("NombreC", txtNombreC.getText().toString());
                        parametros.put("Abono", txtAbono.getText().toString());
                        parametros.put("Fecha", txtFecha.getText().toString());
                        parametros.put("Imagen", btnImagen.getText().toString());
                        parametros.put("Total", txtTotal.getText().toString());
                        parametros.put("Descripcion", txtDescripcion.getText().toString());
                    }else {
                        parametros.put("TipoPedido", spTipoPedido.getSelectedItem().toString());
                        parametros.put("NombreC", spCliente.getSelectedItem().toString());
                        parametros.put("Abono", txtAbono.getText().toString());
                        parametros.put("Fecha", txtFecha.getText().toString());
                        parametros.put("Imagen", btnImagen.getText().toString());
                        parametros.put("Total", txtTotal.getText().toString());
                        parametros.put("Descripcion", txtDescripcion.getText().toString());
                    }

                    return parametros;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add (stringRequest);


        }

        //Funcion del boton para ingresar la fecha de entrega
        private void Fecha() {
            btnFecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v == btnFecha) {
                        final Calendar c = Calendar.getInstance();
                        dia = c.get(Calendar.DAY_OF_MONTH);
                        mes = c.get(Calendar.MONTH);
                        ano = c.get(Calendar.YEAR);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(Pedido.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txtFecha.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }
                                , ano, mes, dia);

                        datePickerDialog.show();
                    }
                }
            });
        }


}

