package diy.eoego.app.biz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import diy.eoego.app.entity.CategorysEntity;
import diy.eoego.app.entity.NewsCategoryListEntity;

public class TopDao extends BaseDao {
	
	private NewsCategoryListEntity newsCategory;
	
	List<CategorysEntity> tabs = new ArrayList<CategorysEntity>();
	
	public TopDao(Activity activity) {
		super(activity);
	}
	
	public List<Object> mapperJson(boolean useCache) {
		List<Object> topCategorys = new ArrayList<Object>();
		tabs.clear();
		
		
		
		return null;
	}
	
	public List<CategorysEntity> getCategorys() {
		CategorysEntity cate1 = new CategorysEntity();
		CategorysEntity cate2 = new CategorysEntity();
		CategorysEntity cate3 = new CategorysEntity();
		cate1.setName("精选资讯");
		cate1.setName("精选博客");
		cate1.setName("精选教程");
		Collections.addAll(tabs, cate1, cate2, cate3);
		return tabs;
	}
}
