package diy.eoego.app.entity;

import java.util.List;

import diy.eoego.app.entity.base.BaseContentList;

public class BlogsCategoryListEntity extends BaseContentList {
	private List<BlogContentItem> items;

	public List<BlogContentItem> getItems() {
		return items;
	}

	public void setItems(List<BlogContentItem> items) {
		this.items = items;
	}

}
