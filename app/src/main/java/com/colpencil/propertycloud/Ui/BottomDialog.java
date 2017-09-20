package com.colpencil.propertycloud.Ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.TextView;

import com.colpencil.propertycloud.R;
import com.colpencil.propertycloud.Tools.UtilsLibrary;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;

import java.util.List;

public class BottomDialog {

    protected final Builder mBuilder;

    public int position;//选种的内容

    public final Builder getBuilder() {
        return mBuilder;
    }

    protected BottomDialog(Builder builder) {
        mBuilder = builder;
        mBuilder.bottomDialog = initBottomDialog(builder);
    }

    @UiThread
    public void show() {
        if (mBuilder != null && mBuilder.bottomDialog != null)
            mBuilder.bottomDialog.show();
    }

    @UiThread
    public void dismiss() {
        if (mBuilder != null && mBuilder.bottomDialog != null)
            mBuilder.bottomDialog.dismiss();
    }

    @UiThread
    private Dialog initBottomDialog(final Builder builder) {
        final Dialog bottomDialog = new Dialog(builder.context, R.style.BottomDialogs);
        View view = builder.activity.getLayoutInflater().inflate(R.layout.library_bottom_dialog, null);
        TextView bottom_title = (TextView) view.findViewById(R.id.bottom_title);

        WheelView bottom_wv = (WheelView) view.findViewById(R.id.bottom_wv);
        bottom_wv.setOffset(1);
        bottom_wv.setSeletion(0);
        TextView vNegative = (TextView) view.findViewById(R.id.bottomDialog_cancel);
        TextView vPositive = (TextView) view.findViewById(R.id.bottomDialog_ok);
        bottom_wv.setItems(builder.dataList);
        bottom_wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                ColpencilLogger.e("滑动选中selectedIndex: " + selectedIndex + ", item: " + item);
                position = selectedIndex-1;
            }
        });

        bottom_title.setText(builder.title);
        if (builder.btn_positive != null) {
            vPositive.setVisibility(View.VISIBLE);
            vPositive.setText(builder.btn_positive);
            vPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (builder.btn_positive_callback != null)
                        builder.btn_positive_callback.onClick(BottomDialog.this);
                    bottomDialog.dismiss();
                }
            });
        }

        if (builder.btn_negative != null) {
            vNegative.setVisibility(View.VISIBLE);
            vNegative.setText(builder.btn_negative);
            vNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (builder.btn_negative_callback != null)
                        builder.btn_negative_callback.onClick(BottomDialog.this);
                    bottomDialog.dismiss();
                }
            });
        }
        bottomDialog.setContentView(view);
        bottomDialog.setCancelable(builder.isCancelable);
        return bottomDialog;
    }

    public static class Builder {
        protected Activity activity;
        protected Context context;

        // Bottom Dialog
        protected Dialog bottomDialog;

        // Icon, Title and Content
        protected Drawable icon;
        protected CharSequence title, content;
        protected List<String> dataList;
        // Buttons
        protected CharSequence btn_negative, btn_positive;
        protected ButtonCallback btn_negative_callback, btn_positive_callback;

        // Custom View
        protected View customView;
        protected int customViewPaddingLeft, customViewPaddingTop, customViewPaddingRight, customViewPaddingBottom;

        // Other options
        protected boolean isCancelable;

        public Builder(@NonNull Context context) {
            this.activity = (Activity) context;
            this.context = context;
            this.isCancelable = true;
        }

        public Builder setTitle(@StringRes int titleRes) {
            setTitle(this.context.getString(titleRes));
            return this;
        }

        public Builder setTitle(@NonNull CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setContent(@StringRes int contentRes) {
            setContent(this.context.getString(contentRes));
            return this;
        }

        public Builder setContent(@NonNull CharSequence content) {
            this.content = content;
            return this;
        }

        public Builder setIcon(@NonNull Drawable icon) {
            this.icon = icon;
            return this;
        }

        public Builder setIcon(@DrawableRes int iconRes) {
            this.icon = ResourcesCompat.getDrawable(context.getResources(), iconRes, null);
            return this;
        }

        public Builder setPositiveText(@StringRes int buttonTextRes) {
            setPositiveText(this.context.getString(buttonTextRes));
            return this;
        }

        public Builder setDataList(@NonNull List<String> datas) {
            this.dataList = datas;
            return this;
        }

        public Builder onPositive(@NonNull ButtonCallback buttonCallback) {
            this.btn_positive_callback = buttonCallback;
            return this;
        }

        public Builder setNegativeText(@StringRes int buttonTextRes) {
            setNegativeText(this.context.getString(buttonTextRes));
            return this;
        }

        public Builder setNegativeText(@NonNull CharSequence buttonText) {
            this.btn_negative = buttonText;
            return this;
        }

        public Builder onNegative(@NonNull ButtonCallback buttonCallback) {
            this.btn_negative_callback = buttonCallback;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.isCancelable = cancelable;
            return this;
        }

        public Builder setCustomView(View customView) {
            this.customView = customView;
            this.customViewPaddingLeft = 0;
            this.customViewPaddingRight = 0;
            this.customViewPaddingTop = 0;
            this.customViewPaddingBottom = 0;
            return this;
        }

        public Builder setCustomView(View customView, int left, int top, int right, int bottom) {
            this.customView = customView;
            this.customViewPaddingLeft = UtilsLibrary.dpToPixels(context, left);
            this.customViewPaddingRight = UtilsLibrary.dpToPixels(context, right);
            this.customViewPaddingTop = UtilsLibrary.dpToPixels(context, top);
            this.customViewPaddingBottom = UtilsLibrary.dpToPixels(context, bottom);
            return this;
        }

        public Builder setPositiveText(@NonNull CharSequence buttonText) {
            this.btn_positive = buttonText;
            return this;
        }

        @UiThread
        public BottomDialog build() {
            return new BottomDialog(this);
        }

        @UiThread
        public BottomDialog show() {
            BottomDialog bottomDialog = build();
            bottomDialog.show();
            return bottomDialog;
        }

    }

    public interface ButtonCallback {

        void onClick(@NonNull BottomDialog dialog);
    }

}