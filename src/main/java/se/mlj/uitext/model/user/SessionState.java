package se.mlj.uitext.model.user;

import java.io.Serializable;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named
public class SessionState implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean showSidebar = true;

	public boolean isShowSidebar() {
		return showSidebar;
	}

	public void setShowSidebar(boolean showSidebar) {
		this.showSidebar = showSidebar;
	}
	
	public void toggleSidebar() {
		showSidebar = !showSidebar;
	}

	
}
