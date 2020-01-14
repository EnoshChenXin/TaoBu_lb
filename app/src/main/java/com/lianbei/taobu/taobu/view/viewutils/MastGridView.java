package com.lianbei.taobu.taobu.view.viewutils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.GridView;
import android.widget.LinearLayout;

public class MastGridView extends GridView {
    int size = 10;//列表长度
    int gridviewWidth =10;
    int itemWidth = 10;

    public MastGridView(Context context) {
        super ( context );
        init (  );
    }

    public MastGridView(Context context, AttributeSet attrs) {
        super ( context, attrs );
        init (  );
    }

    public MastGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super ( context, attrs, defStyleAttr );
        init (  );
    }


    private void init() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (
                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT );
        setLayoutParams ( params ); // 设置GirdView布局参数,横向布局的关键
        setColumnWidth ( itemWidth ); // 设置列表项宽
        setHorizontalSpacing (20 ); // 设置列表项水平间距
        setStretchMode ( GridView.NO_STRETCH );
        setNumColumns ( size ); // 设置列数量=列表集合数
        invalidate ();
    }

    public void setDataSize(int dataSize,int gridviewWidth,int itemWidth ) {
        this.size = dataSize;
        this.gridviewWidth = gridviewWidth;
        this.itemWidth = itemWidth;
        Log.e ("size:--" ,"--itemWidth:"+size+"--gridviewWidth:"+gridviewWidth+"--itemWidth:"+itemWidth);
        init (  );
    }
    //该自定义控件只是重写了GridView的onMeasure方法，使其不会出现滚动条，ScrollView嵌套ListView也是同样的道理，不再赘述。
//    @Override
//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(
//                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }
}
