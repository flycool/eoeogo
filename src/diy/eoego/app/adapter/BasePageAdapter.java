package diy.eoego.app.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;

public class BasePageAdapter extends FragmentStatePagerAdapter {

	private Activity mAcivity;
	
	public BasePageAdapter(FragmentActivity activity) {
		super(activity.getSupportFragmentManager());
		this.mAcivity = activity;
	}

	@Override
	public Fragment getItem(int arg0) {
		return new Fragment();
	}

	@Override
	public int getCount() {
		return 3;
	}

}
