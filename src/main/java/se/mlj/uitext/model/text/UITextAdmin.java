package se.mlj.uitext.model.text;

import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import se.mlj.uitext.text.boundary.ResourceBundleServiceLocal;
import se.mlj.uitext.text.entity.UIText;

@ViewScoped
@Named("textAdmin")
public class UITextAdmin {
	
	@Inject
	ResourceBundleServiceLocal textBean;
	
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
	
}
