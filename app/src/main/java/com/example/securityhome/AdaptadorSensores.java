package com.example.securityhome;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorSensores extends RecyclerView.Adapter<AdaptadorSensores.SensoresViewHolder> {

    List<Sensores> sensores;
    List <Casas> casas;

    public AdaptadorSensores(List<Sensores> sensores,List <Casas> casas){
        this.sensores=sensores;
        this.casas= casas;
    }


    @NonNull
    @Override
    public SensoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsensores,parent,false);
        return new SensoresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorSensores.SensoresViewHolder holder, int position) {
        holder.bind(sensores.get(position));
        holder.bind2(casas.get(position));
    }
    @Override
    public int getItemCount() {
        return sensores.size();
    }

    public class SensoresViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvnameSensor, tvnamecasa;
        String nomSensor, namecasa;
        public SensoresViewHolder(@NonNull View itemView) {
            super(itemView);
            tvnameSensor= itemView.findViewById(R.id.tvnameSensor);
            tvnamecasa= itemView.findViewById(R.id.TVnamecasa);
            itemView.setOnClickListener(this);
        }

        public void bind(Sensores s) {
            tvnameSensor.setText(s.getNombre());
            this.nomSensor= s.getNombre();
        }
        public void bind2(Casas c) {
            tvnamecasa.setText(c.getNomCasa());
            this.namecasa= c.getNomCasa();
        }

        @Override
        public void onClick(View view) {
            Intent vistasen = new Intent(view.getContext(), Datos_Sensores.class);
            vistasen.putExtra("nombresen", this.nomSensor.toString());
            vistasen.putExtra("nombreC", this.namecasa.toString());
            view.getContext().startActivity(vistasen);
        }
    }
}
