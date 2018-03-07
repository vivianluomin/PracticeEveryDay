package com.example.asus1.testmvp.Presenters;

import com.example.asus1.testmvp.Models.Articel;
import com.example.asus1.testmvp.Models.ArticelModel;
import com.example.asus1.testmvp.Models.ArticelModelImpl;
import com.example.asus1.testmvp.Views.ArticleViewInterface;

import java.util.List;

/**
 * Created by asus1 on 2018/3/7.
 */

public class ArticlePresenter {

    //ArticleView接口，代表了View接口角色
    ArticleViewInterface mArticleView;

    //文章数据的Model，也就是Model角色
    ArticelModel mArticleModel = new ArticelModelImpl();

    //从网络上获取文章的API
    ArticleAPI mArticelAPI = new ArticleAPIImpl();


    public ArticlePresenter(ArticleViewInterface viewInterface){
        mArticleView = viewInterface;
    }

    //获取文章，也就是我们的业务逻辑
    public void fetchArticels(){
        mArticleView.showLoading();

        mArticelAPI.fetchAeticel(new DataListener<List<Articel>>() {
            @Override
            public void onComplete(List<Articel> result) {
                mArticleView.showArticel(result);
                mArticleView.hideLoading();

                //储存到数据库
                mArticleModel.saveArticels(result);
            }
        });

    }

    public void loadArticelFromDB(){
        mArticleModel.loadArticelsFromCache(new DataListener<List<Articel>>() {
            @Override
            public void onComplete(List<Articel> result) {
                mArticleView.showArticel(result);
            }
        });
    }

}
