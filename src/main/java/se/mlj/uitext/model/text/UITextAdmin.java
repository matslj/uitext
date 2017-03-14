package se.mlj.uitext.model.text;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Event;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import se.mlj.uitext.text.boundary.ResourceBundleServiceLocal;
import se.mlj.uitext.text.entity.UIText;

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
	
	private UIText selectedText;
	
	public List<UIText> getTextList() {
		return textBean.getAllTexts();
	}

	public UIText getSelectedText() {
		return selectedText;
	}

	public void setSelectedText(UIText selectedText) {
		this.selectedText = selectedText;
	}
	
	public void updateSelectedText() {
		textBean.createUpdateText(selectedText);
		reloadEvent.fire(new DBResourceBundleReloadEvent(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
	}
	
}
