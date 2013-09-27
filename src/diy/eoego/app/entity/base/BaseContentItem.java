package diy.eoego.app.entity.base;

public class BaseContentItem {
	
	public int id;
	public String short_content;
	public String detail_url;
	public long time;
	public String title;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShort_content() {
		return short_content;
	}

	public void setShort_content(String short_content) {
		this.short_content = short_content;
	}

	public String getDetail_url() {
		return detail_url;
	}

	public void setDetail_url(String detail_url) {
		this.detail_url = detail_url;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
