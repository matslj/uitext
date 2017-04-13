package se.mlj.uitext.common.bundle;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.mlj.uitext.business.text.boundary.ResourceBundleServiceLocal;
import se.mlj.uitext.business.text.entity.UIText;

public class DBControl extends Control {
	private Logger logger = LoggerFactory.getLogger(DBControl.class);

	/**
	 * Endast vårt påhittade 'db' format stöds.
	 */
	@Override
	public List<String> getFormats(String baseName) {
		if (baseName == null) {
			throw new NullPointerException();
		}
		return Arrays.asList("db");
	}

	/**
	 * Serves a database bound resource bundle to the resource bundle system.
	 */
	@Override
	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
			throws IllegalAccessException, InstantiationException, IOException {
		if ((baseName == null) || (locale == null) || (format == null) || (loader == null)) {
			throw new NullPointerException();
		}
		ResourceBundle bundle = null;
		if ("db".equals(format)) {
			bundle = new ListDBResourceBundle(locale);
		}
		return bundle;
	}

	/**
	 * A database bound resource bundle.
	 */
	protected class ListDBResourceBundle extends ListResourceBundle {

		private Locale locale;

		/**
		 * ResourceBundle constructor with locale
		 * 
		 * @param locale
		 */
		public ListDBResourceBundle(final Locale locale) {
			this.locale = locale;
		}

		/**
		 * Returns the locale dependent content of a resource bundle from the database as a [n][2] matrix of key value pairs where the
		 * key is the uitext key and value is the value of that key.
		 * 
		 * @return an array of an Object array representing a key-value pair or an empty matrix if no matching key is found.
		 *         This will be rendered as ???key??? by the bundle framework.
		 */
		@Override
		protected Object[][] getContents() {
			ResourceBundleServiceLocal service = null;
			try {
				service = InitialContext.doLookup("java:module/" + ResourceBundleServiceLocal.BEAN_NAME + "!" + ResourceBundleServiceLocal.class.getName());
			} catch (NamingException e) {
				throw new IllegalStateException("Kunde inte hämta instans av " + ResourceBundleServiceLocal.BEAN_NAME + " från jndi.", e);
			}
			if (service != null) {
				final List<UIText> texts = service.getAllTexts(locale);
				if (texts != null && !texts.isEmpty()) {
					Object[][] all = new Object[texts.size()][2];
					int i = 0;
					for (UIText text : texts) {
						all[i] = new Object[] { text.getKey(), text.getValue() };
						i++;
					}
					return all;
				}
			}

			return new Object[][] {};
		}
	}
}
