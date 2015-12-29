package ru.pstu.itas.lucene;

final class IndexItem {
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";

	private final Long id;
	private final String title;
	private final String content;

	IndexItem(Long id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}

	Long getId() {
		return id;
	}

	String getTitle() {
		return title;
	}

	String getContent() {
		return content;
	}

	@Override
	public String toString() {
		return "IndexItem [id=" + id + ", title=" + title + "]";
	}
}
