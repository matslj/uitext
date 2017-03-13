package se.mlj.uitext.model.text;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.application.ApplicationResourceBundle;

import se.mlj.uitext.text.control.DBResourceBundle;

/**
 * Lyssnar efter ett {@link DBResourceBundleReloadEvent} och när ett sådant inkommer så rensas
 * ResourceBundle cachen. Denna lösning gör det möjligt för en användare att uppdatera uitexter,
 * och få genomslag på ändringen, utan att man behöver starta om servern.
 * <p>
 * Att bara göra ResourceBundle.cleareCache() fungerar inte eftersom resource bundlet även är cachat
 * av jsf (i alla fall om det är definierat via faces-config.xml istället för f:loadBundle). Lösningen
 * är att mha reflection rensa den interna map som innehåller jsfs resource bundle cache.
 * <p>
 * http://stackoverflow.com/questions/4325164/how-to-reload-resource-bundle-in-web-application
 * 
 * @author Mats L
 *
 */
@Named
@ApplicationScoped
public class DBResourceBundleReloader {
	private static final long serialVersionUID = 1L;

	public void onReload(@Observes final DBResourceBundleReloadEvent bundleReloadEvent) {
		ResourceBundle.clearCache(Thread.currentThread().getContextClassLoader());

		// ApplicationResourceBundle användas av mojarra, men kanske inte av andra jsf implementationer.
		// Byter man implementation så kanske det här inte fungerar längre.
		ApplicationResourceBundle appBundle = ApplicationAssociate.getCurrentInstance().getResourceBundles()
				.get(DBResourceBundle.class.getName());
		Map<Locale, ResourceBundle> resources = getFieldValue(appBundle, "resources");
		resources.clear();
	}
	
	/*
	   Alternativ som bara använder reflection och ingen import.
	 
	   Class<?> applicationAssociateClass = Class.forName("com.sun.faces.application.ApplicationAssociate");
	   Method getCurrentInstance = applicationAssociateClass.getMethod("getCurrentInstance");
	   Object applicationAssociate = getCurrentInstance.invoke(null);
	   Method getResourceBundles = applicationAssociate.getClass().getMethod("getResourceBundles");
	   Map<String, ?> resourceBundles = (Map<String, ?>)getResourceBundles.invoke(applicationAssociate);
	   Object appBundle = resourceBundles.get(name);
	   Map<Locale, ResourceBundle> resources = getFieldValue(appBundle, "resources");
	   resources.clear();
	 */

	@SuppressWarnings("unchecked")
	private static <T> T getFieldValue(Object object, String fieldName) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return (T) field.get(object);
		} catch (Exception e) {
			return null;
		}
	}
}
