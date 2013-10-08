package diy.eoego.app.entity;

import java.util.List;

import diy.eoego.app.entity.base.BaseContentList;

public class BlogsCategoryListEntity extends BaseContentList {
	private List<BlogContentItem> items;

	public List<BlogContentItem> getItem() {
		return items;
	}

	public void setItem(List<BlogContentItem> items) {
		this.items = items;
	}
}
