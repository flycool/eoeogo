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
import diy.eoego.app.utils.bitmapfun.ImageFetcher;
import diy.eoego.app.utils.bitmapfun.ImageCache.ImageCacheParams;
import diy.eoego.app.widget.XListView;
import diy.eoego.app.widget.XListView.IXListViewListener;

public abstract class BaseListFragment extends Fragment implements IXListViewListener {
	
	protected XListView listView;
	protected View view;
	LayoutInflater mInflater;
	
	private static final String IMAGE_CACHE_DIR = "thumbs";
	protected ImageFetcher mImageFetcher;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ImageCacheParams cacheParams = new ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);
		
		mImageFetcher = new ImageFetcher(getActivity(), 100);
		mImageFetcher.setLoadingImage(R.drawable.bg_load_default);
		mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);
		
	}
	
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
	
	@Override
	public void onResume() {
		super.onResume();
		mImageFetcher.setExitTasksEarly(false);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mImageFetcher.setPauseWork(false);
		mImageFetcher.setExitTasksEarly(true);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mImageFetcher.clearCache();
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
