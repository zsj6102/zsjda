package com.colpencil.propertycloud.View.Fragments;

import android.R.integer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.colpencil.propertycloud.View.Fragments.Home.HomeFragment;
import com.colpencil.propertycloud.View.Fragments.Intimate.IntimateFragment;
import com.colpencil.propertycloud.View.Activitys.OpenDoor.OpenDoorActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LigthWang
 *
 *         fragment的工厂类。
 */
public class FragmentFactory {

	private static Map<integer, Fragment> map = new HashMap<integer, Fragment>();

	public static Fragment creatFragment(int position, AppCompatActivity homeActivity) {

		Fragment fragment = null;
		fragment = map.get(position);
		if (fragment == null) {
			if (position == 0) { // 首页
				fragment = new HomeFragment();
			} else if (position == 1) { // 优质生活
				fragment =  new Fragment2();
			}   else if (position == 2) { // 邻里圈
				fragment = new Fragment3();
			} else { // 贴心管家
				fragment =  new IntimateFragment();
			}
		}
		return fragment;
	}
}
