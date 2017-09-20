package com.property.colpencil.colpencilandroidlibrary.Ui.ViewpagerCycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ImgTool;
import com.property.colpencil.colpencilandroidlibrary.R;

/**
 * ImageView创建工厂
 */
public class ViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @param
	 * @return
	 */
	public static ImageView getImageView(Context context, String url, ImgTool imgTool) {
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		imgTool.loadImgByString(url,imageView);//显示图片
		return imageView;
	}
}
