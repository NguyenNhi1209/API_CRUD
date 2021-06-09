package com.example.thithuonl;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
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

    Button them;
    ListView lv;
    String url = "https://60ad9d3780a61f00173314a2.mockapi.io/Person/";
    ArrayList<Person> list = new ArrayList<Person>();
    ArrayAdapter<Person> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);
        lv = (ListView)findViewById(R.id.list);

        GetArrayJson(url);
        adapter  = new ArrayAdapter<>(TrangChu.this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);
        them = (Button)findViewById(R.id.them);
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TrangChu.this, ThemPerson.class);
                startActivity(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = ((ListView)parent).getItemAtPosition(position);
                Person p = (Person)o;
                Intent intent = new Intent(TrangChu.this,SuaPerson.class);
                intent.putExtra("id",p.getId());
                intent.putExtra("name",p.getName());
                intent.putExtra("height", p.getHeight());
                intent.putExtra("weight", p.getWeight());
                startActivity(intent);
                Toast.makeText(TrangChu.this,p.getId() + "",Toast.LENGTH_SHORT).show();

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = ((ListView)parent).getItemAtPosition(position);
                Person p = (Person)o;
                int id1 = p.getId();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TrangChu.this);
                alertDialogBuilder.setMessage("Bạn có muốn xóa person này!");
                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // xóa sp đang nhấn giữ
                        DeleteApi(url + id1);
                        //cập nhật lại listview
//                        ArrayList<Person> p = list;
//                        list.clear();
//                        GetArrayJson(url);
                        Intent intent = new Intent(TrangChu.this, TrangChu.class);
                        startActivity(intent);
//                        adapter  = new ArrayAdapter<>(TrangChu.this,android.R.layout.simple_list_item_1,list);
//                        lv.setAdapter(adapter);
                    }
                });
                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //không làm gì
                    }
                });
                alertDialogBuilder.show();

                return true;
            }
        });
    }
    private void DeleteApi(String url){
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(TrangChu.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TrangChu.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                                        adapter  = new ArrayAdapter<>(TrangChu.this,android.R.layout.simple_list_item_1,list);
                                        lv.setAdapter(adapter);

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