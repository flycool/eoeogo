package diy.eoego.app.entity;

import diy.eoego.app.entity.base.BaseContentItem;

public class BlogContentItem extends BaseContentItem {
	private String name; // ��������

	private String head_image_url; // ����ͷ��

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHead_image_url() {
		return head_image_url;
	}

	public void setHead_image_url(String head_image_url) {
		this.head_image_url = head_image_url;
	}
}
