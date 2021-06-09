package com.example.thithuonl;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class SuaPerson extends AppCompatActivity {
    EditText txtName, txtHeight, txtWeight;
    int id ;
    String name;
    int height,weight;
    Button btnLuu;
    String url = "https://60ad9d3780a61f00173314a2.mockapi.io/Person/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them);
        txtName = (EditText) findViewById(R.id.name);
        txtHeight = (EditText) findViewById(R.id.height);
        txtWeight = (EditText) findViewById(R.id.weight);
        btnLuu = (Button)findViewById(R.id.save);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");
        height = intent.getIntExtra("height", 0);
        weight = intent.getIntExtra("weight",0);
        txtName.setText(name + "");
        txtHeight.setText(height + "");
        txtWeight.setText(weight + "");
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PutApi(url + id);
                Intent intent = new Intent(SuaPerson.this, TrangChu.class);
                startActivity(intent);

            }
        });
    }

    private void PutApi(String url){
        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SuaPerson.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SuaPerson.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String name = txtName.getText().toString();
                String height = txtHeight.getText().toString() ;
                String weight = txtWeight.getText().toString() ;
                HashMap<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("height", height);
                params.put("weight", weight);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}