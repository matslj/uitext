package se.mlj.uitext.model.user;

import java.util.Iterator;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Används för att hålla reda på eller ändra aktuell locale.
 * Localen för en sida sätts i default.xhtml-templaten mha
 * <b>&lt;f:view locale="#{localeManager.locale}"&gt;</b> och default locale (om ingen har satts av användaren
 * i sessionen) hämtas från det initiala requestet i samband med att den här javaklassen skapas. Se
 * init-metoden nedan.
 * 
 * @author Mats L
 *
 */
@Named
@SessionScoped
public class LocaleManager implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Locale locale;

	@PostConstruct
	public void init() {
		locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
	}

	public Locale getLocale() {
		return locale;
	}

	public String getLanguage() {
		return locale.getLanguage();
	}

	public String setLanguage(String language) {
		Iterator<Locale> i = FacesContext.getCurrentInstance().getApplication().getSupportedLocales();
        while (i.hasNext()) {
        	Locale l = i.next();
        	if (l.getLanguage().equals(language)) {
        		locale = new Locale(language);
        		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        	}
        }
		String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		return viewId + "?faces-redirect=true";
	}

}
