package diy.eoego.app.biz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

import android.app.Activity;
import diy.eoego.app.config.Constants;
import diy.eoego.app.config.Urls;
import diy.eoego.app.entity.CategorysEntity;
import diy.eoego.app.entity.NewsCategoryListEntity;
import diy.eoego.app.entity.NewsMoreResponse;
import diy.eoego.app.utils.RequestCacheUtil;

public class TopDao extends BaseDao {
	
	private NewsCategoryListEntity newsCategory;
	
	List<CategorysEntity> tabs = new ArrayList<CategorysEntity>();
	
	public TopDao(Activity activity) {
		super(activity);
	}
	
	public List<Object> mapperJson(boolean useCache) {
		List<Object> topCategorys = new ArrayList<Object>();
		tabs.clear();
		try {
		
			String resultNews = RequestCacheUtil.getRequestContent(mActivity, 
					Urls.TOP_NEWS_URL + "",
					Constants.WebSourceType.Json, 
					Constants.DBContentType.Content_list, useCache);
			System.out.println("resultNews====================== " + resultNews);
			NewsMoreResponse newsMoreResponse = mObjectMapper.readValue(resultNews, new TypeReference<NewsMoreResponse>() {});
			if (newsMoreResponse != null) {
				newsCategory = newsMoreResponse.getResponse();
			}
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Collections.addAll(topCategorys, newsCategory);
		
		return topCategorys;
	}
	
	public List<CategorysEntity> getCategorys() {
		CategorysEntity cate1 = new CategorysEntity();
		CategorysEntity cate2 = new CategorysEntity();
		CategorysEntity cate3 = new CategorysEntity();
		cate1.setName("��ѡ��Ѷ");
		cate1.setName("��ѡ����");
		cate1.setName("��ѡ�̳�");
		Collections.addAll(tabs, cate1, cate2, cate3);
		return tabs;
	}
}