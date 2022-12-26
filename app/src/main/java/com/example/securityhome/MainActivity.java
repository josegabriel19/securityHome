package com.example.securityhome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity{
    Button btnregistro,btnLogin;
    EditText edEmail, edPassword;
    TextView  token;
    String name,email,created;
    public static String SaveToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin=findViewById(R.id.btnLogin);
        edEmail=findViewById(R.id.edEmail);
        edPassword=findViewById(R.id.edPassword);
        btnregistro= findViewById(R.id.btnRegistrarse);
        token= findViewById(R.id.txtToken);

        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), Registro.class);
                startActivity(intent);
            }
        });
    }
    public void Sesion(View view) {
        String url = "http://54.219.172.193/api/login";
        JSONObject jsonBody = new JSONObject();
        String emailI=edEmail.getText().toString() ;
        String password= edPassword.getText().toString();


        try {
            jsonBody.put("email", emailI);
            jsonBody.put("password", password);

        } catch (Exception e) {
        }
        if (emailI.length()==0) { Toast.makeText(MainActivity.this, "Ingresa tu correo", Toast.LENGTH_SHORT).show(); }
        if(password.length()==0){ Toast.makeText(MainActivity.this, "Ingresa tu Contraseña", Toast.LENGTH_SHORT).show();}
        if(emailI.length()!=0 && password.length()!=0){
            Toast.makeText(this, "Iniciando sesion....", Toast.LENGTH_SHORT).show();
        }
        JsonObjectRequest login= new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               Toast.makeText(getApplicationContext(), "Has iniciado sesion exitosamente", Toast.LENGTH_SHORT).show();

                edEmail.setText ("");
                edPassword.setText("");

                try {
                    SaveToken=response.getString("access_token");
                    JSONObject d= response.getJSONObject("user");
                    name= d.getString("name");
                    email= d.getString("email");
                    created= d.getString("updated_at");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Context context = getApplicationContext();
                SharedPreferences Token= context.getSharedPreferences("token", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= Token.edit();
                try {
                    editor.putString("key", response.getString("access_token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editor.apply();

                Intent intent = new Intent(getApplicationContext(),  Admin_Activity.class);
                intent.putExtra("user", name);
                intent.putExtra("email", email);
                intent.putExtra("created",created);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        SingletonRequest.getInstance(this).addToRequestQue(login);
    }
}