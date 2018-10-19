package com.example.yangxue.textproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.yangxue.textproject.R;
import com.example.yangxue.textproject.adapter.Item;
import com.example.yangxue.textproject.adapter.ItemClickListener;
import com.example.yangxue.textproject.adapter.Section;
import com.example.yangxue.textproject.adapter.SectionedExpandableLayoutHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ItemClickListener{

    @BindView(R.id.menu_list)
    RecyclerView menuListView;
    private SectionedExpandableLayoutHelper sectionedExpandableLayoutHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        menuListView.setItemAnimator(new DefaultItemAnimator());
        sectionedExpandableLayoutHelper = new SectionedExpandableLayoutHelper(this,
                menuListView, this, 2);

        sectionedExpandableLayoutHelper.addSection(getString(R.string.top_rated_movies_topic), getTopRatedList());
        sectionedExpandableLayoutHelper.addSection(getString(R.string.most_popular_movies_topic), getPopularList());
        sectionedExpandableLayoutHelper.notifyDataSetChanged();
    }

    private ArrayList<Item> getTopRatedList() {
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(getResources()
                .getStringArray(R.array.top_rated_movies)));

        ArrayList<Item> movieList = new ArrayList<>();

        for (String str : arrayList) {
            movieList.add(new Item(str));
        }
        return movieList;
    }

    private ArrayList<Item> getPopularList(){
        List<String> arryList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.most_popular_movies)));
        ArrayList<Item> movieList = new ArrayList<>();

        for (String str : arryList) {
            movieList.add(new Item(str));
        }
        return movieList;
    }

    private void forwardActivity(String value) {
        if(value.equals("The Shawshank Redemption")){
            Intent intent = new Intent(this,OtherActivity.class);
            startActivityForResult(intent,1);

        }
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
    public void itemClicked(Item item) {
        forwardActivity(item.getName());
    }

    @Override
    public void itemClicked(Section section) {

    }
}
