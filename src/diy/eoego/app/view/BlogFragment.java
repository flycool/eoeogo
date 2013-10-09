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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import diy.eoego.app.R;
import diy.eoego.app.entity.BlogContentItem;
import diy.eoego.app.entity.BlogsCategoryListEntity;
import diy.eoego.app.widget.XListView;

@SuppressLint("ValidFragment")
public class BlogFragment extends BaseListFragment {
	
	List<BlogContentItem> items_list = new ArrayList<BlogContentItem>();
	private Activity mActivity;
	private String more_url;
	private BlogsCategoryListEntity loadMoreEntity;
	private MyAdapter mAdapter;
	private LayoutInflater mInflater;
	
	public BlogFragment() {}
	
	public BlogFragment(Activity c, BlogsCategoryListEntity categorys) {
		this.mActivity = c;
		if (categorys != null) {
			more_url = categorys.getMore_url();
			this.items_list = categorys.getItems();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		mAdapter = new MyAdapter(items_list);
		view = inflater.inflate(R.layout.main, null);
		listView = (XListView) view.findViewById(R.id.list_view);
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
		List<BlogContentItem> mList = new ArrayList<BlogContentItem>();

		public MyAdapter(List<BlogContentItem> list) {
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
			BlogContentItem item = mList.get(position);
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.blogs_item_layout,
						null);
				holder.header = (TextView) convertView
						.findViewById(R.id.tx_header_title);
				holder.title = (TextView) convertView
						.findViewById(R.id.txt_title);
				holder.short_ = (TextView) convertView
						.findViewById(R.id.txt_short_content);
				holder.img_thu = (ImageView) convertView
						.findViewById(R.id.img_thu);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.header.setText(item.getName());
			holder.title.setText(item.getTitle());
			holder.short_.setText(item.getShort_content());
			String url = item.getHead_image_url().replaceAll("=small", "=middle");
			
			if (url == null || "".equals(url)) {
				holder.img_thu.setVisibility(View.GONE);
			} else {
				holder.img_thu.setVisibility(View.VISIBLE);
				/*ImageUtil.setThumbnailView(url, holder.img_thu, mActivity,
						callback1, false);*/
			}
			
			return convertView;
		}
		
	}
	
	static class ViewHolder {
		public TextView header;
		public TextView title;
		public TextView short_;
		public ImageView img_thu;
	}
}
