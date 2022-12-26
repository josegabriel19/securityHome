package com.example.securityhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class AgregarCasa extends AppCompatActivity {

    EditText edInsertar;
    Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent vista= getIntent();
        String nombre= vista.getStringExtra("nombre");
        String Descripcion= vista.getStringExtra("descripcion");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_casa);
        edInsertar= findViewById(R.id.edInsertar);
        btnAgregar= findViewById(R.id.btnAgregar);

      btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name= edInsertar.getText().toString();

                String url="http://54.219.172.193/api/auth/casa/AñadirCasa/"+ name;
                JsonObjectRequest add= new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(),name+" Agregada", Toast.LENGTH_SHORT).show();
                        Intent ag= new Intent(getApplicationContext(), funciones_admin.class);
                        startActivity(ag);
                        finish();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                SingletonRequest.getInstance(getApplicationContext()).addToRequestQue(add);
            }
        });

    }
}