package diy.eoego.app.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import diy.eoego.app.R;
import diy.eoego.app.utils.ImageUtil;
import diy.eoego.app.utils.ImageUtil.ImageCallback;
import diy.eoego.app.widget.XListView;
import diy.eoego.app.widget.XListView.IXListViewListener;

public abstract class BaseListFragment extends Fragment implements IXListViewListener {
	
	protected XListView listView;
	protected View view;
	LayoutInflater mInflater;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		view = inflater.inflate(R.layout.main, null);
		listView = (XListView) view.findViewById(R.id.list_view);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	protected void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime("just now");
	}
	
	ImageUtil.ImageCallback callback1 = new ImageCallback() {
		@Override
		public void loadImage(Bitmap bitmap, String imagePath) {
			ImageView img = (ImageView) listView.findViewWithTag(imagePath);
			if (img != null) {
				img.setImageBitmap(bitmap);
			}
			
		}
	};
	
}
