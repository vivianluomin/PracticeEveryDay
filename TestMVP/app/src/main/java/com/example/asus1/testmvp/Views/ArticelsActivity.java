package com.example.asus1.testmvp.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.asus1.testmvp.Models.Articel;
import com.example.asus1.testmvp.Presenters.ArticlePresenter;
import com.example.asus1.testmvp.R;

import java.util.LinkedList;
import java.util.List;

public class ArticelsActivity extends AppCompatActivity implements ArticleViewInterface<Articel>{

    private ListView mListView;
    private ProgressBar mProgressBar;
    private List<Articel> mArticels = new LinkedList<>();
    private ArrayAdapter mAdapter;
    private ArticlePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        //构建ArticelPresenter，与ArticelActivity建立关联
        mPresenter = new ArticlePresenter(this);
        //获取文章数据
        mPresenter.fetchArticels();
    }

    private void initViews(){
        mListView = (ListView)findViewById(R.id.articels_list_view);
        mAdapter = new ArticelAdapter(this,R.layout.view_list_item,mArticels);
        mListView.setAdapter(mAdapter);
        //进度条
        mProgressBar = (ProgressBar)findViewById(R.id.loading_progressbar);

    }

    @Override
    public void showArticel(List<Articel> results) {
        mArticels.clear();
        mArticels.addAll(results);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
