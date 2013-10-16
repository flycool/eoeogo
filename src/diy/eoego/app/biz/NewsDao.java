package diy.eoego.app.biz;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

import android.app.Activity;
import diy.eoego.app.config.Constants;
import diy.eoego.app.config.Urls;
import diy.eoego.app.entity.NewsJson;
import diy.eoego.app.entity.NewsMoreResponse;
import diy.eoego.app.entity.NewsResponseEntity;
import diy.eoego.app.utils.RequestCacheUtil;
import diy.eoego.app.utils.Utility;

public class NewsDao extends BaseDao {

	public NewsDao(Activity activity) {
		super(activity);
	}

	private NewsResponseEntity _newsResponse;

	public NewsResponseEntity get_newsResponse() {
		return _newsResponse;
	}

	public void set_newsResponse(NewsResponseEntity _newsResponse) {
		this._newsResponse = _newsResponse;
	}

	public NewsResponseEntity mapperJson(boolean useCache) {
		NewsJson newsJson;
		try {
			String result = RequestCacheUtil.getRequestContent(mActivity,
					Urls.NEWS_LIST + Utility.getScreenParams(mActivity),
					Constants.WebSourceType.Json,
					Constants.DBContentType.Content_list, useCache);
			newsJson = mObjectMapper.readValue(result,
					new TypeReference<NewsJson>() {
					});
			if (newsJson == null) {
				return null;
			}
			this._newsResponse = newsJson.getResponse();
			return _newsResponse;

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public NewsMoreResponse getMore(String more_url) {
		NewsMoreResponse response;
		try {
			String result = RequestCacheUtil.getRequestContent(mActivity,
					more_url + Utility.getScreenParams(mActivity),
					Constants.WebSourceType.Json,
					Constants.DBContentType.Content_list, true);
			response = mObjectMapper.readValue(result,
					new TypeReference<NewsMoreResponse>() {
					});
			return response;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
