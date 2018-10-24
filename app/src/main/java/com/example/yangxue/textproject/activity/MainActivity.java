package com.example.yangxue.textproject.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.yangxue.textproject.R;
import com.example.yangxue.textproject.adapter.Item;
import com.example.yangxue.textproject.adapter.ItemClickListener;
import com.example.yangxue.textproject.adapter.Section;
import com.example.yangxue.textproject.adapter.SectionedExpandableLayoutHelper;
import com.example.yangxue.textproject.model.MenuItem;
import com.example.yangxue.textproject.viewmodel.MainViewModel;
import com.example.yangxue.textproject.viewmodel.ViewModelData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ItemClickListener{

    @BindView(R.id.menu_list)
    RecyclerView menuListView;
    private SectionedExpandableLayoutHelper sectionedExpandableLayoutHelper;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getViewModelData().observe(this, new LocalObserver());
        viewModel.loadMovieAdata();
    }

    private void forwardActivity(String value) {
        if(value.equals("The Shawshank Redemption")){
            Intent intent = new Intent(this,OtherActivity.class);
            startActivityForResult(intent,1);

        }
    }

    class LocalObserver implements Observer<ViewModelData>{

        @Override
        public void onChanged(@Nullable ViewModelData viewModelData) {
            if (ViewModelData.State.SUCCESS == viewModelData.state){
                initMainView(viewModel.getMapGrids(), viewModel.getTabsData());
            }
        }
    }

    private void initMainView(Map<String, List<MenuItem>> mapGrids, List<String> tabs) {
        int gripSize = mapGrids.size();
        menuListView.setItemAnimator(new DefaultItemAnimator());
        sectionedExpandableLayoutHelper = new SectionedExpandableLayoutHelper(this,
                menuListView, this, gripSize);
        for (String tabTitle: tabs){
            sectionedExpandableLayoutHelper.addSection(tabTitle, mapGrids.get(tabTitle));
        }
        sectionedExpandableLayoutHelper.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            Toast.makeText(this, data.getStringExtra("text"), Toast.LENGTH_SHORT).show();
        }else if(requestCode == 2){
        }

    }

    @Override
    public void itemClicked(MenuItem item) {
        forwardActivity(item.getMenuName());
    }

    @Override
    public void itemClicked(Section section) {

    }
}
