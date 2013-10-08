package diy.eoego.app.adapter;

import java.util.ArrayList;
import java.util.List;

import diy.eoego.app.entity.BlogsCategoryListEntity;
import diy.eoego.app.entity.CategorysEntity;
import diy.eoego.app.entity.NewsCategoryListEntity;
import diy.eoego.app.view.BlogFragment;
import diy.eoego.app.view.NewsFragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;

public class BasePageAdapter extends FragmentStatePagerAdapter {
	
	public ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	public List<CategorysEntity> tabs = new ArrayList<CategorysEntity>();

	private Activity mAcivity;
	
	public BasePageAdapter(FragmentActivity activity) {
		super(activity.getSupportFragmentManager());
		this.mAcivity = activity;
	}
	
	public void addFragment(List<CategorysEntity> mList, List<Object> listObject) {
		tabs.addAll(mList);
		System.out.println("listObject size====== " + listObject.size());
		for (int i=0; i<listObject.size(); i++) {
			Object object = listObject.get(i);
			if (object instanceof NewsCategoryListEntity) {
				addTab(new NewsFragment(mAcivity, (NewsCategoryListEntity)object));
			} else if (object instanceof BlogsCategoryListEntity) {
				addTab(new BlogFragment(mAcivity, (BlogsCategoryListEntity) object));
			}
		}
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return tabs.get(position).getName();
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
	
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}
	
	public void addNullFragment() {
		CategorysEntity cate = new CategorysEntity();
		cate.setName("connect error");
		tabs.add(cate);
		//addTab(new HttpErrorFragment());
	}
	
	public void addTab(Fragment fragment) {
		fragments.add(fragment);
		notifyDataSetChanged();
	}
	
	public void clear() {
		fragments.clear();
		tabs.clear();
	}

}
