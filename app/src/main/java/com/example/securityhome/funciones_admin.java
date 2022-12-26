package com.example.securityhome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class funciones_admin extends AppCompatActivity {
    TextView name, description, user;
    Intent intent;
    RecyclerView recyclerCasas;
    String nombreCasa, usuario;
    List<Casas> cas = new ArrayList<>();
    FloatingActionButton flot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funciones_admin);

        flot= findViewById(R.id.fab);
        flot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AgregarCasa.class);
                startActivity(intent);
            }
        });
        /*Intent vista= getIntent();
        String nombre= vista.getStringExtra("nombre");
        String Descripcion= vista.getStringExtra("descripcion");*/

        name= findViewById(R.id.tvname);
        name.setText("Casas Disponibles");
        description=findViewById(R.id.tvdescription);
        description.setText("Aqui podras ver las casas que haz creado para agregarle diferentes variedades de sensores");
        recyclerCasas= findViewById(R.id.recyclerCasas);
        MostrarCasas();


    }
    private void MostrarCasas() {
        String url="http://54.219.172.193/api/auth/casa/MostrarCasas/{nombre}";
        JsonArrayRequest mosCasas = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i= 0; i<response.length(); i++){
                    try {
                        JSONObject objeto= new JSONObject(response.get(i).toString());
                        nombreCasa=objeto.getString("name");

                        cas.add(new Casas(nombreCasa));
                        recyclerCasas.setLayoutManager(new LinearLayoutManager(funciones_admin.this, LinearLayoutManager.VERTICAL, false));
                        AdaptadorCasas adaptar = new AdaptadorCasas(cas);
                        recyclerCasas.setAdapter(adaptar);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        SingletonRequest.getInstance(this).addToRequestQue(mosCasas);
    }

}