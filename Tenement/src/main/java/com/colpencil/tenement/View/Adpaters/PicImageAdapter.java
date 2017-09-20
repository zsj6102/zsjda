package com.colpencil.tenement.View.Adpaters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.colpencil.tenement.R;
import com.colpencil.tenement.View.Activitys.Workbeach.UpLoadRecordActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.PhotoBitmapUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.List;

/**
 * @author 汪 亮
 * @Description: 照相机 选择后的适配器
 * @Email DramaScript@outlook.com
 * @date 2016/10/16
 */
public class PicImageAdapter extends BaseAdapter {

    private List<String> imageList;
    private UpLoadRecordActivity context;

    public PicImageAdapter(List<String> imageList, UpLoadRecordActivity context) {
        this.imageList = imageList;
        this.context = context;
    }


    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        if (imageList.size() == 0) {
            return 1;
        } else {
            return imageList.size() + 1;
        }
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (imageList.size() == 0) {
            return 0;
        } else {
            return position;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_image, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ColpencilLogger.e("position=" + position + "size=" + imageList.size());
            if (position == imageList.size()) {
                viewHolder.iv_image.setImageDrawable(context.getResources().getDrawable(R.mipmap.selector_image_add));
//                viewHolder.iv_image.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        context.getImage();
//                        ColpencilLogger.e("-------------1---size="+imageList.size()+"position="+position);
//                    }
//                });
            } else {
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeFile(imageList.get(position), option);
                int bitmapDegree = PhotoBitmapUtils.getBitmapDegree(imageList.get(position));
                Bitmap bitmap1 = PhotoBitmapUtils.rotateBitmapByDegree(bitmap, bitmapDegree);
                viewHolder.iv_image.setImageBitmap(bitmap1);
                ColpencilLogger.e("-------------2");
            }
        return convertView;
    }

    class ViewHolder {
        public ImageView iv_image;
    }
}