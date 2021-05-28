package com.example.drawingcircle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements ShapeClickInterface {

    DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        drawingView = findViewById(R.id.drawing_view);
        drawingView.setClickListener(this);

        Button createCircleBtn = findViewById(R.id.create_circle_btn);
        createCircleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.createCircle();
            }
        });


    }


    @Override
    public void onCircleClick() {
//        화면을 선택 했을 경우 리스너로 넘어오는 곳
//        TODO 드로잉 뷰 클릭했을 시 작업하는 곳 (반지름 크기변경 , 원삭제 등등)

    }

}