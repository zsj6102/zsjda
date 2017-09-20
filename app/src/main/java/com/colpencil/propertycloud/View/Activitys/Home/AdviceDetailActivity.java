package com.colpencil.propertycloud.View.Activitys.Home;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.AdviceList;
import com.colpencil.propertycloud.Bean.ListAdvice;
import com.colpencil.propertycloud.Overall.HostUrl;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.PhotoPreview.PhotoActivity;
import com.colpencil.propertycloud.View.Adapter.CurrentPicAdapter;
import com.google.gson.Gson;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpencilGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @Description: 物业意见详情
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/12/3
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_advice_detail
)
public class AdviceDetailActivity extends ColpencilActivity {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.tv_reason)
    TextView tv_reason;

    @Bind(R.id.tv_info)
    TextView tv_info;

    @Bind(R.id.tv_content)
    TextView tv_content;

    @Bind(R.id.gl_pic)
    ColpencilGridView gl_pic;
    private Gson gson;
    private AdviceList adviceList;

    @Override
    protected void initViews(View view) {
        tv_title.setText("物业意见详情");
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String json = getIntent().getStringExtra("json");
        gson = new Gson();
        adviceList = gson.fromJson(json, AdviceList.class);
        tv_reason.setText(adviceList.title);
        tv_content.setText(adviceList.detaildesc);
        tv_info.setText(adviceList.company+"，"+adviceList.sendtm);
        CurrentPicAdapter adapter = new CurrentPicAdapter(this,adviceList.pic,R.layout.item_pic);
        gl_pic.setAdapter(adapter);
        gl_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adviceList.pic.size()!=0){
                    List<String> imageList = new ArrayList<String>();
                    for (int i=0;i<adviceList.pic.size();i++){
                        imageList.add(adviceList.pic.get(i));
                    }
                    int childCount = parent.getChildCount();
                    ArrayList<Rect> rects = new ArrayList<>();
                    for (int i = 0; i < childCount; i++) {
                        Rect rect = new Rect();
                        View child = parent.getChildAt(i);
                        child.getGlobalVisibleRect(rect);
                        rects.add(rect);
                    }
                    Intent intent = new Intent(AdviceDetailActivity.this, PhotoActivity.class);
                    String imageArray[] = new String[imageList.size()];
                    for(int i = 0;i<imageArray.length;i++){
                        imageArray[i] = imageList.get(i);
                    }
                    intent.putExtra("imgUrls", imageArray);
                    intent.putExtra("index", position);
                    intent.putExtra("bounds", rects);
                    startActivity(intent);
                    ColpencilLogger.e("size="+adviceList.pic.size());
                    overridePendingTransition(0, 0);
                }
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
}
