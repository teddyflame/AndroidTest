package com.xu.myandroidtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xu.myandroidtest.asynctask.AsyncTaskTest;
import com.xu.myandroidtest.myaddress.MyAddressActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<HashMap<String,String>> myList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button asyncTaskBn = (Button)findViewById(R.id.asyncTask);
        asyncTaskBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent asyncTaskIntent = new Intent(MainActivity.this,AsyncTaskTest.class);
                startActivity(asyncTaskIntent);
            }
        });

        listView = (ListView)findViewById(R.id.list);

        setData();
        setListView();
    }

    private void setListView(){
        SimpleAdapter adapter = new SimpleAdapter(this,
                myList,
                R.layout.activity_main_list_cell,
                new String[] {"name","source","content"},
                new int[] {R.id.name,R.id.phone,R.id.address});

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String actionString = myList.get(i).get("name");
                switch (actionString){
                    case "asyncTack":
                        startActivity(new Intent(MainActivity.this,AsyncTaskTest.class));
                        break;
                    case "myaddress":
                        startActivity(new Intent(MainActivity.this,MyAddressActivity.class));
                        break;
                    default:break;
                }

            }
        });
    }

    private void setData(){
        HashMap<String,String> map = new HashMap<>();
        map.put("name","asyncTask");
        map.put("source","crazy");
        map.put("content","no content");
        myList.add(map);


        map = new HashMap<>();
        map.put("name","myaddress");
        map.put("source","work");
        map.put("content","no content");
        myList.add(map);
    }
}
