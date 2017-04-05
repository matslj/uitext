package se.mlj.uitext.model.text;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Event;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import se.mlj.uitext.business.text.boundary.ResourceBundleServiceLocal;
import se.mlj.uitext.business.text.entity.UIText;

@ViewScoped
@Named("textAdmin")
public class UITextAdmin implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	ResourceBundleServiceLocal textBean;
	
	@Inject
	Event<DBResourceBundleReloadEvent> reloadEvent;
	
	@Inject
	Logger log;
	
	private UIText selectedText;
	
	public List<UIText> getTextList() {
		return textBean.getAllTexts();
	}

	public UIText getSelectedText() {
		return selectedText;
	}

	public void setSelectedText(UIText selectedText) {
		if (selectedText == null) {
			this.selectedText = new UIText();
			this.selectedText.setLocale(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		} else {
			this.selectedText = selectedText;
		}
	}
	
	public void setSelectedText(String key, String value) {
		this.selectedText = new UIText();
		this.selectedText.setLocale(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		this.selectedText.setKey(key);
		this.selectedText.setValue(value);
	}
	
	public void updateSelectedText() {
		textBean.createUpdateText(selectedText);
		reloadEvent.fire(new DBResourceBundleReloadEvent(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
	}
	
}
