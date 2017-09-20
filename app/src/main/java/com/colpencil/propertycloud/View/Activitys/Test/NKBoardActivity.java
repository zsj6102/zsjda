package com.colpencil.propertycloud.View.Activitys.Test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.colpencil.propertycloud.R;
import com.property.colpencil.colpencilandroidlibrary.Ui.NumKeyBoard.NumKeyboard;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 陈宝 on 2016/6/28.
 */
public class NKBoardActivity extends AppCompatActivity {

    @Bind(R.id.test_linearLayout)
    NumKeyboard board;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nkboard);
        ButterKnife.bind(this);
        board.setInputOver(new NumKeyboard.InputFinishListener() {

            @Override
            public void inputHasOver(String text) {
                Toast.makeText(NKBoardActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
