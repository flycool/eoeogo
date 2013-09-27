package diy.eoego.app.entity;

import java.util.List;

import diy.eoego.app.entity.base.BaseContentList;

public class NewsCategoryListEntity extends BaseContentList {

	private List<NewsContentItem> items;

	public List<NewsContentItem> getItems() {
		return items;
	}

	public void setItems(List<NewsContentItem> items) {
		this.items = items;
	}

}
