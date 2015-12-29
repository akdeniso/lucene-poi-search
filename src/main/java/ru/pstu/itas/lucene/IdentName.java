package ru.pstu.itas.lucene;

public final class IdentName {
	public static final char IDENT_NAME_SEPARATOR = '@';

	public final String ident;
	public final String name;

	public IdentName(String fullName) {
		int separatorIdx = fullName.indexOf(IDENT_NAME_SEPARATOR);
		if (separatorIdx == -1) {
			this.name = fullName;
			this.ident = "";
		} else {
			this.ident = fullName.substring(0, separatorIdx);
			this.name = fullName.substring(separatorIdx + 1);
		}
	}

	@Override
	public String toString() {
		return "IdentName [ident=" + ident + ", name=" + name + "]";
	}
}
