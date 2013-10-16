package diy.eoego.app.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import diy.eoego.app.R;
import diy.eoego.app.biz.NewsDao;
import diy.eoego.app.entity.NewsCategoryListEntity;
import diy.eoego.app.entity.NewsContentItem;
import diy.eoego.app.entity.NewsMoreResponse;
import diy.eoego.app.utils.ImageUtil;

@SuppressLint("ValidFragment")
public class NewsFragment extends BaseListFragment {
	
	private Activity mActivity;
	private String more_url;
	private List<NewsContentItem> items_list = new ArrayList<NewsContentItem>();
	private MyAdapter mAdapter;
	private NewsCategoryListEntity loadMoreEntity;
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case 0:
				more_url = loadMoreEntity.getMore_url();
				mAdapter.appendToList(loadMoreEntity.getItems());
				break;
			}
			onLoad();
		}
		
	};
	
	
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
		
		listView.setXListViewListener(this);
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
		
		public void appendToList(List<NewsContentItem> lists) {
			if(lists == null) return;
			mList.addAll(lists);
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
			ViewHolder holder;
			NewsContentItem item = mList.get(position);
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater
						.inflate(R.layout.news_item_layout, null);
				holder.title_ = (TextView) convertView
						.findViewById(R.id.news_title);
				holder.short_ = (TextView) convertView
						.findViewById(R.id.news_short_content);
				holder.img_thu = (ImageView) convertView
						.findViewById(R.id.img_thu);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.title_.setText(item.getTitle());
			holder.short_.setText(item.getShort_content());
			String img_url = item.getThumbnail_url();
			if (img_url.equals(null) || img_url.equals("")) {
				holder.img_thu.setVisibility(View.GONE);
			} else {
				holder.img_thu.setVisibility(View.VISIBLE);
				ImageUtil.setThumbnailView(img_url, holder.img_thu, mActivity,
						callback1, false);
			}
			return convertView;
		}
		
	}
	
	static class ViewHolder {
		public TextView title_;
		public TextView short_;
		public ImageView img_thu;
	}

	@Override
	public void onRefresh() {
		onLoad();
	}


	@Override
	public void onLoadMore() {
		if (more_url == null || "".equals(more_url)) {
			mHandler.sendEmptyMessage(1);
			return;
		} else {
			new Thread() {
				@Override
				public void run() {
					NewsMoreResponse response = new NewsDao(mActivity).getMore(more_url);
					if (response != null) {
						loadMoreEntity = response.getResponse();
						mHandler.sendEmptyMessage(0);
					}
				}
				
			}.start();
		}
	}
	
}
