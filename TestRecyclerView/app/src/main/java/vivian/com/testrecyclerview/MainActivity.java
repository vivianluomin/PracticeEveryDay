package vivian.com.testrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdapter adapter;
    List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        adapter = new MyAdapter(data,this);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        //设置布局管理器
        adapter.SetOnItmeClickListener(new MyAdapter.onItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(MainActivity.this,"onClick",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void LongClivk(int position) {
                adapter.remove(position);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        //recyclerView.addItemDecoration(new DividerItemDecaration(this,DividerItemDecaration.VERTICAL_LIST));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));


    }

    private  void init(){
        data = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++)
        {
            data.add("" + (char) i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.itemmenu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.shanchu:
                adapter.remove(1);
                break;
            case R.id.tianjia:
                adapter.addItem(1);
        }
        return true;
    }
}
