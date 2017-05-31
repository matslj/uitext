package se.mlj.uitext.model.user;

import static org.omnifaces.util.Faces.invalidateSession;
import static org.omnifaces.util.Faces.redirect;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

/**
 * Används för att hålla reda på sessionsinställningar (utom locale, som hanteras av
 * {@link LocaleManager}) för en användare. Just nu finns det bara en sådan inställning den rör
 * ändringsläge av/på för ui-text-redigering.
 *  
 * @author Mats L
 *
 */
@SessionScoped
@Named
public class SessionState implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean textEditMode = false;
	
	@Inject
	Logger logger;

	@PostConstruct
	public void init() {
		textEditMode = false;
	}

	public boolean isTextEditMode() {
		return textEditMode;
	}

	public void setTextEditMode(boolean textEditMode) {
		this.textEditMode = textEditMode;
	}
	
	/**
	 * Ändrar textredigeringsläget.
	 * <ul>
	 * <li>Om man kunde ändra texter, så kan man efter anrop inte ändra texter.
	 * <li>Om man inte kunde ändra texter, så kan man efter anrop ändra texter.
	 * </ul>
	 * Metoden gör efter ändring av textredigeringsläget en redirect till aktuell view.
	 * 
	 * @return jsf view target
	 */
	public String toggleEditMode() {
		this.textEditMode = !this.textEditMode;
		String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		return viewId + "?faces-redirect=true";
	}

}
