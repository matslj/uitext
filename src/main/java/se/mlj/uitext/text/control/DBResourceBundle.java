package se.mlj.uitext.text.control;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class DBResourceBundle extends ResourceBundle {

	protected static final String BUNDLE_NAME = DBResourceBundle.class.getName();

	public DBResourceBundle() {
		this(Locale.getDefault());
	}

	public DBResourceBundle(final Locale locale) {
		setParent(ResourceBundle.getBundle(BUNDLE_NAME, locale, new DBControl()));
	}

	@Override
	protected Object handleGetObject(String key) {
		return parent.getObject(key);
	}

	@Override
	public Enumeration<String> getKeys() {
		return parent.getKeys();
	}

}
