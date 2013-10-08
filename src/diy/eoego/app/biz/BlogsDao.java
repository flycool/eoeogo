package diy.eoego.app.biz;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

import android.app.Activity;
import diy.eoego.app.config.Constants;
import diy.eoego.app.config.Urls;
import diy.eoego.app.entity.BlogsJson;
import diy.eoego.app.entity.BlogsResponseEntity;
import diy.eoego.app.utils.RequestCacheUtil;
import diy.eoego.app.utils.Utility;

public class BlogsDao extends BaseDao {
	
	private BlogsResponseEntity blogsResponse;
	
	public BlogsResponseEntity getBlogsResponse() {
		return blogsResponse;
	}

	public void setBlogsResponse(BlogsResponseEntity blogsResponse) {
		this.blogsResponse = blogsResponse;
	}

	public BlogsDao(Activity activity) {
		super(activity);
	}
	
	public BlogsResponseEntity mapperjson(boolean useCache) {
		BlogsJson blogsJson;
		try {
			String result = RequestCacheUtil.getRequestContent(mActivity, 
				Urls.BLOGS_LIST + Utility.getScreenParams(mActivity),
				Constants.WebSourceType.Json, Constants.DBContentType.Content_list, useCache);
			blogsJson = mObjectMapper.readValue(result,
					new TypeReference<BlogsJson>() {
					});
			
			if (blogsJson == null) return null;
			
			blogsResponse = blogsJson.getResponse();
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return blogsResponse;
	}
	
}
