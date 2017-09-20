package com.colpencil.propertycloud.Tools;

import android.content.Context;
import android.view.View;

import com.colpencil.propertycloud.R;

import java.util.HashMap;
import java.util.Map;

public class CreadView {

    private static Map<android.R.integer, View> map = new HashMap<android.R.integer, View>();

    public static View creatView(int position, Context context) {

        View view = null;
        view = map.get(position);
        if (view == null) {
            if (position == 0) {
                view = View.inflate(context, R.layout.item_home_head, null);
            } else if (position == 1) {
                view = View.inflate(context, R.layout.item_home_head2, null);
            } else if (position == 2) {
                view = View.inflate(context, R.layout.item_home_head3, null);
            }  else {
                view = View.inflate(context, R.layout.item_home_head4, null);
            }
        }
        return view;
    }
}
