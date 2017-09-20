package com.colpencil.propertycloud.View.Activitys.CloseManager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.propertycloud.Bean.RxBusMsg;
import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.View.Adapter.NationAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.yinghe.whiteboardlib.bean.EventBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_nation
)
public class NationSelectActivity extends ColpencilActivity {

    @Bind(R.id.ll_left)
    LinearLayout ll_left;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.lv_nation)
    ListView lv_nation;
    private NationAdapter adapter;
    private List<String> arrayList;
    private String content;

    @Override
    protected void initViews(View view) {
        tv_title.setText("民族");
        content = getIntent().getStringExtra("content");
        String[] array = getResources().getStringArray(R.array.nation);
        arrayList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            arrayList.add(array[i]);
        }
        adapter = new NationAdapter(this, arrayList, R.layout.item_nation,content);
        lv_nation.setAdapter(adapter);
        ll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_nation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBean rxBusMsg = new EventBean();
                rxBusMsg.setFlag(3);
                rxBusMsg.setAddress(arrayList.get(position));
                RxBus.get().post("change",rxBusMsg);
                finish();
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
