package se.mlj.uitext.model.user;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@SessionScoped
@Named
public class SessionState implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean showSidebar = true;
	private boolean textEditMode = false;

	public boolean isShowSidebar() {
		return showSidebar;
	}

	public void setShowSidebar(boolean showSidebar) {
		this.showSidebar = showSidebar;
	}
	
	public void toggleSidebar() {
		showSidebar = !showSidebar;
	}

	public boolean isTextEditMode() {
		return textEditMode;
	}

	public void setTextEditMode(boolean textEditMode) {
		this.textEditMode = textEditMode;
	}
	
	public String toggleEditMode() {
		this.textEditMode = !this.textEditMode;
		String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		return viewId + "?faces-redirect=true";
	}

}
