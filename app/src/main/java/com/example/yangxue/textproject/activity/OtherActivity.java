package com.example.yangxue.textproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.yangxue.textproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        ButterKnife.bind(OtherActivity.this);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("text","otheractivity");
                setResult(1,intent);
                OtherActivity.this.finish();
            }
        });
    }


}
