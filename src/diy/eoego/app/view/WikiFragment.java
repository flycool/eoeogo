package diy.eoego.app.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import diy.eoego.app.R;
import diy.eoego.app.entity.WikiCategoryListEntity;
import diy.eoego.app.entity.WikiContentItem;
import diy.eoego.app.view.BlogFragment.MyAdapter;
import diy.eoego.app.widget.XListView;

@SuppressLint("ValidFragment")
public class WikiFragment extends BaseListFragment {

	List<WikiContentItem> items_list = new ArrayList<WikiContentItem>();
	private Activity mActivity;
	private WikiCategoryListEntity loadMoreEntity;
	private MyAdapter mAdapter;
	private String more_url;
	private LayoutInflater mInflater;

	public WikiFragment() {}
	
	public WikiFragment(Activity c, WikiCategoryListEntity categorys) {
		mActivity = c;
		if (categorys != null) {
			items_list = categorys.getItems();
			more_url = categorys.getMore_url();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		view = inflater.inflate(R.layout.main, null);
		listView = (XListView) view.findViewById(R.id.list_view);
		mAdapter = new MyAdapter(items_list);
		listView.setAdapter(mAdapter);
		
		return view;
	}
	
	class MyAdapter extends BaseAdapter {
		List<WikiContentItem> mList = new ArrayList<WikiContentItem>();
		
		public MyAdapter(List<WikiContentItem> list) {
			mList.addAll(list);
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
			WikiContentItem item = mList.get(position);
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater
						.inflate(R.layout.wiki_item_layout, null);
				holder.title_ = (TextView) convertView
						.findViewById(R.id.wiki_title);
				holder.title_.setText(item.getTitle());
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
				holder.title_.setText(item.getTitle());
			}
			return convertView;
		}
		
	}
	
	static class ViewHolder {
		public TextView title_;
		public TextView short_;
	}

	@Override
	public void onRefresh() {
		onLoad();
	}

	@Override
	public void onLoadMore() {
		
	}
}
