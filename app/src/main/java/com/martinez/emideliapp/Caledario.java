package com.martinez.emideliapp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.martinez.emideliapp.verclientes.Adapter;
import com.martinez.emideliapp.verclientes.AdapterArray;
import com.martinez.emideliapp.verclientes.Cliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Caledario extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Cliente>clientes = new ArrayList<Cliente>();
    Adapter adapter = new Adapter(clientes);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_registro);
        recyclerView = findViewById(R.id.RecycleCliente);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        obtenerClientes();
        recyclerView.setAdapter(adapter);
    }

    public void obtenerClientes() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://emideli.online/ObtenerPedidos.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray responseArray = new JSONArray(response);
                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject row = responseArray.getJSONObject(i);
                                Cliente cliente = new Cliente(
                                        row.getString("title"),
                                        row.getString("tipo_pedido"),
                                        row.getString("end"),
                                        row.getString("descripcion"),
                                        row.getString("abono"),
                                        row.getString("total"));
                                clientes.add(cliente);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Cliente", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
