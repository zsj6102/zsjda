package com.colpencil.tenement.View.Fragments;

import android.R.integer;
import android.app.Activity;
import android.support.v4.app.Fragment;


import com.colpencil.tenement.View.Fragments.OnlineTalk.OnlineTalkListFragment;
import com.colpencil.tenement.View.Fragments.TodayTask.TodayTaskFragment;
import com.colpencil.tenement.View.Fragments.Workbeach.WorkbenchFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LigthWang
 *
 *         fragment的工厂类。
 */
public class FragmentFactory {

	private static Map<integer, Fragment> map = new HashMap<integer, Fragment>();

	public static Fragment creatFragment(int position, Activity homeActivity) {

		Fragment fragment = null;
		fragment = map.get(position);
		if (fragment == null) {
			if (position == 0) {
				fragment = new WorkbenchFragment();
			} else if (position == 1) {
				fragment =  new OnlineTalkListFragment();
			} else if (position == 2) {
				fragment = new TodayTaskFragment();
			} else {
				fragment =  new Fragment4();
			}
		}
		return fragment;
	}
}
