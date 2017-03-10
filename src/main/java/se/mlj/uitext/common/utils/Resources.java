package se.mlj.uitext.common.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence context, to CDI beans
 * 
 * <p>
 * Example injection on a managed bean field:
 * </p>
 * 
 * <pre>
 * &#064;Inject
 * private EntityManager em;
 * </pre>
 */
public class Resources {
	
	public static final String BUNDLE_BASE_NAME = "se.mlj.uitext.messages";

    // use @SuppressWarnings to tell IDE to ignore warnings about field not being referenced directly
    @SuppressWarnings("unused")
    @Produces
    @PersistenceContext(unitName = "primary")
    @Default
    private EntityManager em;

    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }
    
    public static String getMessageFromBundle(String messageKey) {
    	FacesContext facesContext = FacesContext.getCurrentInstance();
    	Locale locale = facesContext.getViewRoot().getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
    	return bundle.getString(messageKey);
    }
}
