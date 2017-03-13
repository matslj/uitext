package se.mlj.uitext.model.text;

import java.io.Serializable;

public class DBResourceBundleReloadEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String locale;

	public DBResourceBundleReloadEvent(String locale) {
		super();
		this.locale = locale;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}
}
