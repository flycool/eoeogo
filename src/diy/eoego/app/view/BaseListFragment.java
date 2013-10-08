package diy.eoego.app.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import diy.eoego.app.R;
import diy.eoego.app.widget.XListView;

public class BaseListFragment extends Fragment {
	
	protected XListView listView;
	protected View view;
	LayoutInflater mInflater;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		view = inflater.inflate(R.layout.main, null);
		listView = (XListView) view.findViewById(R.id.list_view);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
}
