package diy.eoego.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import diy.eoego.app.R;
import diy.eoego.app.adapter.BasePageAdapter;
import diy.eoego.app.biz.BaseDao;
import diy.eoego.app.biz.BlogsDao;
import diy.eoego.app.biz.TopDao;
import diy.eoego.app.entity.CategorysEntity;
import diy.eoego.app.indicator.PageIndicator;

public class MainActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private PageIndicator mIndicator;
	private BasePageAdapter mBasePageAdapter;
	
	private TopDao topDao;
	private BlogsDao blogsDao;
	
	private List<Object> categoryList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.above_slidingmenu);
		
		initControl();
		initClass();
		initViewPager();
		initListView();
		
	}
	
	
	private void initControl() {
		mViewPager = (ViewPager) findViewById(R.id.above_pager);
		mIndicator = (PageIndicator) findViewById(R.id.above_indicator);
	}
	
	private void initClass() {
		topDao = new TopDao(this);
		blogsDao = new BlogsDao(this);
	}

	private void initViewPager() {
		mBasePageAdapter = new BasePageAdapter(this);
		mViewPager.setOffscreenPageLimit(0);
		mViewPager.setAdapter(mBasePageAdapter);
		mIndicator.setViewPager(mViewPager);
		mViewPager.setCurrentItem(0);
		
		new MyTask().execute(topDao);
	}
	
	private void initListView() {
		
	}
	
	public class MyTask extends AsyncTask<BaseDao, String, Map<String, Object>> {
		private boolean mUseCache;
		
		public MyTask() {
			mUseCache = true;
		}
		
		public MyTask(boolean useCache) {
			mUseCache = useCache;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected Map<String, Object> doInBackground(BaseDao... params) {
			BaseDao dao = params[0];
			List<CategorysEntity> categorys = new ArrayList<CategorysEntity>();
            Map<String, Object> map = new HashMap<String, Object>();
            
            if (dao instanceof TopDao) {
            	if ((categoryList = ((TopDao) dao).mapperJson(mUseCache)) != null) {
            		System.out.println("category list size=================== " + categoryList.size());
            		categorys = topDao.getCategorys();
            		map.put("tabs", categorys);
            		map.put("list", categoryList);
            	}
            	
            }
			return map;
		}
		
		@Override
		protected void onPostExecute(Map<String, Object> result) {
			super.onPostExecute(result);
			
			mBasePageAdapter.clear();
			mViewPager.removeAllViews();
			
			if (!result.isEmpty()) {
				mBasePageAdapter.addFragment((List)result.get("tabs"), (List)result.get("list"));
			}
			mViewPager.setVisibility(View.VISIBLE);
            mBasePageAdapter.notifyDataSetChanged();
            mViewPager.setCurrentItem(0);
            mIndicator.notifyDataSetChanged();
		}
		
	}
	

}
