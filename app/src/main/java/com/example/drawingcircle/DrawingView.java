package com.example.drawingcircle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public
class DrawingView extends View {

    //    생성된 원들을 담아놓는 리스트이다
//    원의 정보가 들어가있는 CircleInfoData(원정보) 를 담고있다
//    원이 생성될 시 drawnCircleList에 원들이 추가된다
//    원을 클릭 한지 확인하기 위해 onTouchEvent - ACTION_MOVE 에서 포문으로 각각의 원들을 불러온다
//    원을 그리기 위해서 onDraw에서 drawnCircleList에서 원의 대한 정보를 가져와 그리도록한다
    private ArrayList<CircleInfoData> drawnCircleList = new ArrayList<>();

    //    원에서 공용으로 사용하기 위한 페인트 , 색상이나 두께를 해당 프로젝트에 바꾸지않기에 한번 만들고 계속 사용하도록한다.
    private Paint paint;



//    유저가 화면을 클릭 했을 때 사용하는 엑티비티 혹은 프래그먼트에 전달하는 리스너
    private ShapeClickInterface clickListener;

    public void setClickListener(ShapeClickInterface listener) {
        this.clickListener = listener;
    }


    public DrawingView(Context context) {
        super(context);
        init();
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);

    }


    /*
     * ACTION_MOVE :
     *  유저가 원 안을 클릭 했을 경우를 확인한다
     *  원내부를 클릭하고 움직일 경우 해당원의 위치를 유저의 드래그를 따라가도록 이동시켜준다
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:

                float x = event.getX();
                float y = event.getY();
                for (int i = 0; i < drawnCircleList.size(); i++) {
//                   시작표시 원을 선택하고 움직인 경우인지 확인
                    float circleXFloat = drawnCircleList.get(i).getCircleX();
                    float circleYFloat = drawnCircleList.get(i).getCircleY();
                    int circleRadius = drawnCircleList.get(i).getRadius();
                    double dx = Math.pow(x - circleXFloat, 2);
                    double dy = Math.pow(y - circleYFloat, 2);
                    if (dx + dy < Math.pow(circleRadius, 2)) {
//                    시작표시 원안에 터치됨
                        moveCircleShape(x, y, i);
                        clickListener.onCircleClick();
                        return true;
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }


//       터치이벤트가 겹쳐져있는 뷰가 없으므로 상위뷰들의 onTouchEvent를 호출하지않기 위해 true로함
        return true;

    }


    //    그리는 곳
    @Override
    protected void onDraw(Canvas canvas) {
//        원을 담고있는 리스트에서 하나씩 돌려서 원을 생성해주도록함
        for (CircleInfoData circle : drawnCircleList) {
            canvas.drawCircle(circle.getCircleX(),
                    circle.getCircleY(),
                    circle.getRadius(),
                    paint);
        }

        super.onDraw(canvas);
    }

    /**
     * 원을 드래그해서 움직이면 동작하는 함수이다
     * circleInfoData (원정보)를 변경시키고 다시 화면을 그리도록함
     *
     * @param x         = 최종 x좌표 - 원의 x값이 된다.
     * @param y         = 최종 y좌표 - 원의 y값이 된다.
     * @param listIndex = drawnCircleList의 들어있는 인덱스값
     */
    private void moveCircleShape(float x, float y, int listIndex) {

        CircleInfoData circleInfoData = drawnCircleList.get(listIndex);

        circleInfoData.setCircleX(Math.round(x));
        circleInfoData.setCircleY(Math.round(y));

        this.invalidate();
    }


    /**
     * 원을 추가해주는 코드
     * 처음 생성된 원은 화면의 정가운대에 추가되며 반지름 값은 5이다.
     * 원의 정보를 drawnCircleList에 담아주고 다시 화면을 로드해 원이 추가된걸 보여주도록한다
     */
    public void createCircle() {
        CircleInfoData circleInfoData = new CircleInfoData(
                getWidth() / 2,
                getHeight() / 2,
                100);

        drawnCircleList.add(circleInfoData);
        this.invalidate();

    }


}
