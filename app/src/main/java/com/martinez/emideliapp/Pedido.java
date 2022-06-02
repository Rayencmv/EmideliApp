package com.martinez.emideliapp;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import io.github.muddz.styleabletoast.StyleableToast;

public class Pedido extends AppCompatActivity {

    AsyncHttpClient pedido;
    Button btnAgregarP, btnImagen;
    EditText txtFecha, txtTotal, txtNombreC, txtAbono;
    CheckBox cbNuevo;
    Spinner spTipoPedido, spCliente;

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

        pedido = new AsyncHttpClient();
        CargarTipoPedido();
        LlenarSpinner();
        ClienteNuevo();

    }

    //llena los datos del spinner con el nombre del cliente
    private void LlenarSpinner (){
        String url = "https://emideli.online/obtenerDatos.php";
        pedido.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    CargarSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }



    //controla si el cliente es nuevo o ya esta agregado en la base de datos
    private void ClienteNuevo(){
        cbNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( cbNuevo.isChecked()==false )
                {
                    spCliente.setEnabled(true);
                    txtNombreC.setFreezesText(false);

                }else{
                    spCliente.setEnabled(false);
                    txtNombreC.setFreezesText(true);
                }
            }
        });
    }

    //seleccionar tipo de pedido si es personalizado o si es predeterminado
    private void CargarTipoPedido(){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.TipoPedido, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spTipoPedido.setAdapter(adapter);
    }

    // se carga el spinner de tipo de pedido
    private void CargarSpinner(String respuesta){
        ArrayList<Pedidos> lista = new ArrayList<Pedidos>();

        try {
            String s = "Seleccione";
            JSONArray jsonArreglo = new JSONArray(respuesta);
            Pedidos p1= new Pedidos();
            lista.add(new Pedidos("Seleccione"));
            for (int i=0;i<jsonArreglo.length();i++){
                Pedidos p2= new Pedidos();
                p2.setNombreCliente(jsonArreglo.getJSONObject(i).getString("Nombre"));
                lista.add(p2);
            }
            ArrayAdapter<Pedidos> a = new ArrayAdapter<Pedidos>(this, android.R.layout.simple_dropdown_item_1line,lista);
            spCliente.setAdapter(a);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}