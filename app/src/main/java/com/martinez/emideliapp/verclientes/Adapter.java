package com.martinez.emideliapp.verclientes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.martinez.emideliapp.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<Cliente> ClienteList;

    public Adapter(ArrayList<Cliente> clienteList){ ClienteList = clienteList;}

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cards,parent,false);
        return new ViewHolder(view);
    }

    @Override // enlasamos los datos
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Cliente cliente = ClienteList.get(position);
        holder.txtNombre.setText(cliente.Nombre);
        holder.txtTipoPedido.setText(cliente.Tipo_pedido);
        holder.txtFechaEntrega.setText(cliente.Fecha_entrega);
        holder.txtDescripcion.setText(cliente.Descripcion);
        holder.txtAbono.setText(cliente.Abono);
        holder.txtTotalPedido.setText(cliente.Total_pedido);
    }

    @Override//contamos cuantos hay para mostrar
    public int getItemCount() {return ClienteList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        //buscamos el campo visual
        TextView txtNombre, txtTipoPedido, txtFechaEntrega, txtDescripcion, txtAbono, txtTotalPedido;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //le damos el recurso visual
            txtNombre = itemView.findViewById(R.id.txtNombreMuestra);
            txtTipoPedido = itemView.findViewById(R.id.txtTipoPedidoMuestra);
            txtFechaEntrega = itemView.findViewById(R.id.txtFechaEntregaMuestra);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcionMuestra);
            txtAbono = itemView.findViewById(R.id.txtAbonoMuestra);
            txtTotalPedido = itemView.findViewById(R.id.txtTipoPedidoMuestra);
        }
    }
}
