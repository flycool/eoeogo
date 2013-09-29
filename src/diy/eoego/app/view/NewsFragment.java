package diy.eoego.app.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import diy.eoego.app.entity.NewsCategoryListEntity;
import diy.eoego.app.entity.NewsContentItem;

@SuppressLint("ValidFragment")
public class NewsFragment extends BaseListFragment {
	
	private Activity mActivity;
	private String more_url;
	private List<NewsContentItem> items_list = new ArrayList<NewsContentItem>();
	
	private MyAdapter mAdapter;

	public NewsFragment() {}
	
	
	public NewsFragment(Activity activity, NewsCategoryListEntity categorys) {
		mActivity = activity;
		if (categorys != null) {
			items_list = categorys.getItems();
			more_url = categorys.getMore_url();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		mAdapter = new MyAdapter(items_list);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
			}
			
		});
		
		return view;
	}
	
	class MyAdapter extends BaseAdapter {
		List<NewsContentItem> mList = new ArrayList<NewsContentItem>();
		
		public MyAdapter(List<NewsContentItem> list) {
			mList = list;
			notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = new TextView(mActivity);
			tv.setText("hello eoe");
			
			return tv;
		}
		
	}
	
	static class ViewHolder {
		public TextView title_;
		public TextView short_;
		public ImageView img_thu;
	}
	
}
