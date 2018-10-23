package com.example.yangxue.textproject.adapter;


import com.example.yangxue.textproject.model.MenuItem;

public interface ItemClickListener {
    void itemClicked(MenuItem item);
    void itemClicked(Section section);
}
