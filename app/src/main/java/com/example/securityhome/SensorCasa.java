package com.example.securityhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SensorCasa extends AppCompatActivity {
    TextView  sensor;
    String namecasona, nameSensor, valorsen;
    Intent vista;
    EditText namecasa;
    List<Sensores> senCasa = new ArrayList<>();
    List<Casas> nCasa = new ArrayList<>();
    RecyclerView recyclerView;
    FloatingActionButton flot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_casa);

        vista= getIntent();
        namecasona= vista.getStringExtra("nombre");
        namecasa= findViewById(R.id.tvnameCasa);
        namecasa.setText(namecasona);
        recyclerView= findViewById(R.id.recyclerSCasa);
        flot= findViewById(R.id.fab2);

        namecasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ns= namecasa.getText().toString();
                String Url= "http://54.219.172.193/api/auth/casa/ModificarCasa/"+namecasona+"/"+ns;

                JsonObjectRequest nsen= new JsonObjectRequest(Request.Method.PUT, Url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(SensorCasa.this, "Editado", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(SensorCasa.this, Admin_Activity.class);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                SingletonRequest.getInstance(SensorCasa.this).addToRequestQue(nsen);
            }
        });
        flot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Agregar_Sensor.class);
                intent.putExtra("nombrecasa",namecasona);
                startActivity(intent);
                finish();
            }
        });
        //sensor= findViewById(R.id.tvSensor);
        MostrarSensor();
    }

    private void MostrarSensor() {

        String url= "http://54.219.172.193/api/auth/sensor/MostrarSensores/"+namecasona;

        JsonArrayRequest versensores= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int x=0; x<response.length(); x++){
                    try {
                        JSONObject objeto= new JSONObject(response.get(x).toString());
                        nameSensor= objeto.getString("name");
                        String valor= valorsen;
                        senCasa.add(new Sensores(nameSensor));
                        nCasa.add(new Casas(namecasona));

                        recyclerView.setLayoutManager(new LinearLayoutManager(SensorCasa.this, LinearLayoutManager.VERTICAL, false));
                        AdaptadorSensores sensores = new AdaptadorSensores(senCasa,nCasa);
                        recyclerView.setAdapter(sensores);

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
        SingletonRequest.getInstance(this).addToRequestQue(versensores);
    }

}