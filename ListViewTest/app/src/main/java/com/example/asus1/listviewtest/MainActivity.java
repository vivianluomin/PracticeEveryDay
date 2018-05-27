package com.example.asus1.listviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String[] data = {
            "a","b","c","d","e","f","g","h","i","j","k","l","n",
            "o","p","q","r","s","t","u","v","w","s","y","z"
    };
    private List<Fruit> fruitList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                MainActivity.this,android.R.layout.simple_list_item_1,data
//        );
//        ListView listView = (ListView)findViewById(R.id.list_view);
//        listView.setAdapter(adapter);
        initFruits();
//        FruitAdapter adapter = new FruitAdapter(MainActivity.this,R.layout.fruit_item,fruitList);
//        ListView listView = (ListView)findViewById(R.id.list_view);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Fruit fruit = fruitList.get(position);
//                Toast.makeText(MainActivity.this,fruit.getName(),Toast.LENGTH_SHORT).show();
//            }
//        });
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycle_view);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        fruitadapterr adapter = new fruitadapterr(fruitList);
        recyclerView.setAdapter(adapter);

    }
    private  void initFruits(){
        for(int i = 0;i < 3 ;i++){
            Fruit a = new Fruit(getRandomLengthName("mars"),R.drawable.mars);
            fruitList.add(a);
            Fruit b = new Fruit(getRandomLengthName("earth"),R.drawable.earth);
            fruitList.add(b);
            Fruit c = new Fruit(getRandomLengthName("mars"),R.drawable.mars);
            fruitList.add(c);
            Fruit d = new Fruit(getRandomLengthName("mars"),R.drawable.mars);
            fruitList.add(d);
            Fruit e = new Fruit(getRandomLengthName("sun1"),R.drawable.sun);
            fruitList.add(e);
            Fruit f = new Fruit(getRandomLengthName("sun2"),R.drawable.sun);
            fruitList.add(f);
            Fruit g = new Fruit(getRandomLengthName("mars"),R.drawable.mars);
            fruitList.add(g);

            }
    }
    private String getRandomLengthName(String name){
        Random random = new Random();
        int length = random.nextInt(20)+1;
        StringBuilder builder = new StringBuilder();
        for(int i =0;i<length;i++){
            builder.append(name);
        }
        return builder.toString();
    }
}
