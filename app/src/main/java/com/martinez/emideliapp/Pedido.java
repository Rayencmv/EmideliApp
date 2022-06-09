package com.martinez.emideliapp;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.*;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import io.github.muddz.styleabletoast.StyleableToast;

public class Pedido extends AppCompatActivity {


    private Bitmap bitmap;
    ImageView ImagenPedido;
    AsyncHttpClient pedido;
    Button btnAgregarP, btnImagen, btnFecha;
    EditText txtFecha, txtTotal, txtNombreC, txtAbono, txtDescripcion;
    CheckBox cbNuevo;
    Spinner spTipoPedido, spCliente;
    private  int dia,mes,ano, PICK_IMAGE_REQUEST=1;

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
        ImagenPedido = findViewById(R.id.ImagenPedido);
        pedido = new AsyncHttpClient();
        txtFecha.setEnabled(false);
        txtNombreC.setEnabled(false);
        CargarTipoPedido();
        LlenarSpinner();
        ClienteNuevo();
        GuardarPedido();
        Fecha();
        BuscarImagen();


    }


    //Funcion del boton guardar y conexion con el web service
    private void GuardarPedido(){
        btnAgregarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EjecutarServicio("https://emideli.online/Carga.php");
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
        final ProgressDialog loading = ProgressDialog.show(this,"Subiendo...","Espere por favor...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    Toast.makeText(Pedido.this, "Pedido Ingresado", Toast.LENGTH_SHORT).show();
                }
            },new Response.ErrorListener(){
                public void onErrorResponse (VolleyError error) {
                    loading.dismiss();
                    Toast.makeText(Pedido.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    String Imagen = getStringImagen(bitmap);
                    Map<String, String> parametros = new HashMap<String, String>();
                    if (cbNuevo.isChecked()==true) {
                        parametros.put("TipoPedido", spTipoPedido.getSelectedItem().toString());
                        parametros.put("NombreC", txtNombreC.getText().toString());
                        parametros.put("Abono", txtAbono.getText().toString());
                        parametros.put("Fecha", txtFecha.getText().toString());
                        parametros.put("Imagen", Imagen);
                        parametros.put("Total", txtTotal.getText().toString());
                        parametros.put("Descripcion", txtDescripcion.getText().toString());
                    }else {
                        parametros.put("TipoPedido", spTipoPedido.getSelectedItem().toString());
                        parametros.put("NombreC", spCliente.getSelectedItem().toString());
                        parametros.put("Abono", txtAbono.getText().toString());
                        parametros.put("Fecha", txtFecha.getText().toString());
                        parametros.put("Imagen", Imagen);
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

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar Imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Configuración del mapa de bits en ImageView
                ImagenPedido.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void BuscarImagen() {

        btnImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

    }


}

