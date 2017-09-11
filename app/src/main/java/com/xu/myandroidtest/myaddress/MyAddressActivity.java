package com.xu.myandroidtest.myaddress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.xu.myandroidtest.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAddressActivity extends AppCompatActivity {

    ListView listView;
    Button createAddressBn;
    ArrayList<HashMap<String,String>> myList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        initViews();
        setData();
        setListView();
    }

    private void initViews(){
        listView = (ListView)findViewById(R.id.list);
        createAddressBn = (Button)findViewById(R.id.newAddress);
    };

    private void setData(){
        //test data
        HashMap<String,String> map = new HashMap<>();
        map.put("name","张三");
        map.put("phone","13612345678");
        map.put("address","北京市海淀区西土城路10号北京邮电大学");
        myList.add(map);

        map = new HashMap<>();
        map.put("name","李四");
        map.put("phone","13612345678");
        map.put("address","北京市海淀区西土城路10号北京邮电大学");
        myList.add(map);

    }

    private void setListView(){
        SimpleAdapter adapter = new SimpleAdapter(this,
                myList,
                R.layout.activity_main_list_cell,
                new String[] {"name","phone","address"},
             new int[] {R.id.name,R.id.phone,R.id.address});

        listView.setAdapter(adapter);
    }

    public void createNewAddressClick(View view){
        startActivity(new Intent(MyAddressActivity.this,CreateAddressActivity.class));
    }

}
