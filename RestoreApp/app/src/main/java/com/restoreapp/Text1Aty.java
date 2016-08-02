package com.restoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Text1Aty extends BaseActivity {

    private TextView tip_tv;
    private Button jump_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tip_tv = (TextView) findViewById(R.id.tip_tv);
        tip_tv.setText(this.getClass().getSimpleName() +
                "\n| 点击Home按键 杀掉进程 进入 跳转到本页");

        jump_btn = (Button) findViewById(R.id.jump_btn);
        jump_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Text1Aty.this, Text2Aty.class);
                intent.putExtra("post_id", 10023);
                intent.putExtra("user_name", "ZHAGNSAN");
                startActivity(intent);
            }
        });
    }
}
