package com.example.securityhome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Admin_Activity extends AppCompatActivity {
    RecyclerView recycler;
    FloatingActionButton flot;
    Intent intent;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        List<administrar> administrador= new ArrayList<>();
        textView=findViewById(R.id.tvuser);

        intent=getIntent();
        String user= intent.getStringExtra("user");
        String email= intent.getStringExtra("email");
        String created= intent.getStringExtra("created");
        textView.setText(user);

        flot= findViewById(R.id.botonflot);
        flot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Activity.this,User_activity.class);
                intent.putExtra("user1",user);
                intent.putExtra("email1",email);
                intent.putExtra("created",created);
                startActivity(intent);
            }
        });

        administrador.add(new administrar("Mostrar Casas", "Aqui podras ver Todas las casas creadas para equipar sensores"));

        recycler =findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        AdaptadorAdministrar admon= new AdaptadorAdministrar(administrador);
        recycler.setAdapter(admon);
    }
}