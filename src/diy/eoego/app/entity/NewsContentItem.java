package diy.eoego.app.entity;

import diy.eoego.app.entity.base.BaseContentItem;

public class NewsContentItem extends BaseContentItem {

	private String title;
	private String thumbnail_url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumbnail_url() {
		return thumbnail_url;
	}

	public void setThumbnail_url(String thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}

}
