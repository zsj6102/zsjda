package com.property.colpencil.colpencilandroidlibrary.Ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @Description: 可以嵌套的grildview
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/1
 */
public class ColpencilGridView extends GridView {

    public ColpencilGridView(Context context) {
        super(context);
    }

    public ColpencilGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColpencilGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //该自定义控件只是重写了GridView的onMeasure方法，使其不会出现滚动条，ScrollView嵌套ListView也是同样的道理，不再赘述。
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
