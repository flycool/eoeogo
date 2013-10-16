package diy.eoego.app.entity.base;
import java.util.List;

import diy.eoego.app.entity.CategorysEntity;

/**
 * 返回的大的json的封�?基类
 *
 */
public abstract class BaseResponseData {
	 
	private long date;				//创建时间
	private List<CategorysEntity> categorys;	//分类
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public List<CategorysEntity> getCategorys() {
		return categorys;
	}
	public void setCategorys(List<CategorysEntity> categorys) {
		this.categorys = categorys;
	}
	

}
