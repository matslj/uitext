package se.mlj.uitext.text.control;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.mlj.uitext.common.utils.LookupUtils;
import se.mlj.uitext.text.boundary.ResourceBundleServiceLocal;
import se.mlj.uitext.text.entity.ResourceMessageEntity;
import se.mlj.uitext.text.entity.UIText;

public class DBControl extends Control {
	private Logger logger = LoggerFactory.getLogger(DBControl.class);

	@Override
	public List<String> getFormats(String baseName) {
		if (baseName == null) {
			throw new NullPointerException();
		}
		return Arrays.asList("db");
	}

	@Override
	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
			throws IllegalAccessException, InstantiationException, IOException {
		if ((baseName == null) || (locale == null) || (format == null) || (loader == null)) {
			throw new NullPointerException();
		}
		ResourceBundle bundle = null;
		if (format.equals("db")) {
			bundle = new ListDBResourceBundle(locale);
		}
		return bundle;
	}

	/**
	 * Our own implementation of a resource bundle inspired on the
	 * ListResourceBundle class with a change so that getting a non existing key
	 * does not result in a MissingResourceException being thrown but, instead,
	 * returning the passed in key.
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
		 * Returns an array in which each item is a pair of objects in an Object
		 * array. The first element of each pair is the key, which must be a
		 * String, and the second element is the value associated with that key.
		 * See the class description for details.
		 * 
		 * @return an array of an Object array representing a key-value pair.
		 */
		protected Object[][] getContents() {
			final ResourceBundleServiceLocal resourceBundleService = LookupUtils
					.lookupWithinApp(ResourceBundleServiceLocal.BEAN_NAME, ResourceBundleServiceLocal.class.getName());
			final List<UIText> texts = resourceBundleService.getAllTexts(locale);
			if (texts != null && !texts.isEmpty()) {
				Object[][] all = new Object[texts.size()][2];
				int i = 0;
				for (UIText text : texts) {
					all[i] = new Object[] { text.getKey(), text.getValue() };
					i++;
				}
				return all;
			}

			return new Object[][] {};
		}
	}
}
