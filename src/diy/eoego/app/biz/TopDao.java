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
import diy.eoego.app.entity.BlogsCategoryListEntity;
import diy.eoego.app.entity.BlogsMoreResponse;
import diy.eoego.app.entity.CategorysEntity;
import diy.eoego.app.entity.NewsCategoryListEntity;
import diy.eoego.app.entity.NewsMoreResponse;
import diy.eoego.app.entity.WikiCategoryListEntity;
import diy.eoego.app.entity.WikiMoreResponse;
import diy.eoego.app.utils.RequestCacheUtil;
import diy.eoego.app.utils.Utility;

public class TopDao extends BaseDao {
	
	private NewsCategoryListEntity newsCategorys;
	private BlogsCategoryListEntity blogsCategorys;
	private WikiCategoryListEntity wikiCategorys;
	
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
			NewsMoreResponse newsMoreResponse = mObjectMapper.readValue(resultNews, new TypeReference<NewsMoreResponse>() {});
			if (newsMoreResponse != null) {
				newsCategorys = newsMoreResponse.getResponse();
			}
			
			String resultBlogs = RequestCacheUtil.getRequestContent(mActivity,
					Urls.TOP_BLOG_URL + Utility.getScreenParams(mActivity),
					Constants.WebSourceType.Json,
					Constants.DBContentType.Content_list, useCache);
			BlogsMoreResponse blogsMoreResponse = mObjectMapper.readValue(
					resultBlogs, new TypeReference<BlogsMoreResponse>() {
					});
			if (blogsMoreResponse != null) {
				blogsCategorys = blogsMoreResponse.getResponse();
			}
			
			String resultWiki = RequestCacheUtil.getRequestContent(mActivity,
					Urls.TOP_WIKI_URL + Utility.getScreenParams(mActivity),
					Constants.WebSourceType.Json,
					Constants.DBContentType.Content_list, useCache);
			WikiMoreResponse wikiMoreResponse = mObjectMapper.readValue(
					resultWiki, new TypeReference<WikiMoreResponse>() {
					});
			if (wikiMoreResponse != null) {
				this.wikiCategorys = wikiMoreResponse.getResponse();
			}
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Collections.addAll(topCategorys, newsCategorys, blogsCategorys, wikiCategorys);
		
		return topCategorys;
	}
	
	public List<CategorysEntity> getCategorys() {
		CategorysEntity cate1 = new CategorysEntity();
		CategorysEntity cate2 = new CategorysEntity();
		CategorysEntity cate3 = new CategorysEntity();
		cate1.setName("精选资讯");
		cate2.setName("精选博客");
		cate3.setName("精选教程");
		Collections.addAll(tabs, cate1, cate2, cate3);
		return tabs;
	}
}
