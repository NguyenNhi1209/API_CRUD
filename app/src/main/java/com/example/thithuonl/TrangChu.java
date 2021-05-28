package com.example.thithuonl;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrangChu extends AppCompatActivity {
    EditText id;
    Button them,sua;
    ListView lv;
    String url = "https://60ad9d3780a61f00173314a2.mockapi.io/Person/";
    ArrayList<Person> list = new ArrayList<Person>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);
        lv = (ListView)findViewById(R.id.list);

    }

    private void GetArrayJson(String url){
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                list.clear();
                                for(int i=0; i<response.length(); i++){
                                    try {
                                        JSONObject object = (JSONObject) response.get(i);
//                                        tvDisplay.setText(object.getString("height").toString());
                                        Person p = new Person();
                                        p.setId(object.getInt("id"));
                                        p.setName(object.getString("name").toString());
                                        p.setHeight(object.getInt("height"));
                                        p.setWeight(object.getInt("weight"));
                                        list.add(p);
                                        ArrayAdapter<Person> a  = new ArrayAdapter<>(TrangChu.this,android.R.layout.simple_list_item_1,list);

                                        lv.setAdapter(a);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TrangChu.this, "Error by get Json Array!", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}