package com.example.yangxue.textproject.viewmodel;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.example.yangxue.textproject.HttpUtil;
import com.example.yangxue.textproject.global.Global;
import com.example.yangxue.textproject.model.JsonMsgOut;
import com.example.yangxue.textproject.model.MenuItem;
import com.example.yangxue.textproject.model.Movies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewModel extends BaseViewModel {

    private static final String movie_list_view = "getMovieList.do";
    private List<String> tabsData = new ArrayList<>();
    private Map<String, List<MenuItem>> mapGrids = new HashMap<>();

    public MainViewModel(@NonNull Application application){
        super(application);
    }

    public Map<String, List<MenuItem>> getMapGrids(){
        return mapGrids;
    }
    public List<String> getTabsData() {
        return tabsData;
    }

    public void loadMovieAdata() {
        System.out.println(Global.username);
        HttpUtil.post(movie_list_view, Global.username, new MovieLoadCallback(context));
    }

    private class MovieLoadCallback extends HttpUtil.HttpUtilCallback {
        public MovieLoadCallback(Context context) {
            super(context);
        }

        @Override
        public void onSuccess(JsonMsgOut out) {
            if (out != null) {
                List<Movies> list = JSON.parseArray(out.getObj(), Movies.class);
                loadMoviesData(list);
                System.out.println("服务器端返回内容：" + list);
                viewModelData.state = ViewModelData.State.SUCCESS;
                viewModelData.total = list.size();
                viewModelData.object = out.getObj();
                data.setValue(viewModelData);
            }
        }

        @Override
        public void onError(JsonMsgOut error) {
            viewModelData.state = ViewModelData.State.FAIL;
            data.setValue(viewModelData);
        }
    }

    private void loadMoviesData(List<Movies> list) {
        tabsData.clear();
        for(Movies movie: list){
            if (!tabsData.contains(movie.getMovieType())){
                tabsData.add(movie.getMovieType());
            }
        }
        for(String group: tabsData){
            List<MenuItem> itemList = new ArrayList<>();
            for(Movies movie: list){
                if (!movie.getMovieType().equals(group))
                    continue;
                MenuItem item = new MenuItem();
                item.setMenuKey(movie.getKey());
                item.setMenuName(movie.getName());
                item.setMenuType(movie.getMovieType());
                item.setMenuIcon(movie.getIcon());
                itemList.add(item);
            }
            mapGrids.put(group,itemList);
        }

    }
}
