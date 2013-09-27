package diy.eoego.app.ui;

import diy.eoego.app.R;
import diy.eoego.app.R.layout;
import diy.eoego.app.R.menu;
import diy.eoego.app.adapter.BasePageAdapter;
import diy.eoego.app.indicator.PageIndicator;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private PageIndicator mIndicator;
	private BasePageAdapter mBasePageAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.above_slidingmenu);
		
		initControl();
		initClass();
		initViewPager();
		
	}
	
	
	private void initControl() {
		mViewPager = (ViewPager) findViewById(R.id.above_pager);
		mIndicator = (PageIndicator) findViewById(R.id.above_indicator);
	}
	
	private void initClass() {
		
	}

	private void initViewPager() {
		mBasePageAdapter = new BasePageAdapter(this);
		mViewPager.setOffscreenPageLimit(0);
		mViewPager.setAdapter(mBasePageAdapter);
		mIndicator.setViewPager(mViewPager);
		
		mViewPager.setCurrentItem(0);
	}

}
