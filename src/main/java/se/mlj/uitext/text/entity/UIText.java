package se.mlj.uitext.text.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import se.mlj.uitext.common.entity.AbstractEntity;

@Entity
@NamedQueries({ @NamedQuery(name = UIText.QUERYNAME_FIND_ALL, query = UIText.QUERY_FIND_ALL)})
public class UIText extends AbstractEntity {

	public static final String QUERYNAME_FIND_ALL = "UIText.findAll";
	public static final String QUERY_FIND_ALL = "Select r from UIText r WHERE r.locale=:locale";

	private String key;

	private String value;
	
	private String locale;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

}
