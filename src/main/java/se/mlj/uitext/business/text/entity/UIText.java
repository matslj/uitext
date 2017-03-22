package se.mlj.uitext.business.text.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

/**
 * En uitext är en nyckel-värde-behållare för texter som ska visas i det grafiska
 * gränssnittet. Nyckeln är en referens som används av en page author och värdet är det värde
 * som visas för användaren. Nyckeln är unik med avsende på locale (som i nuläget kan vara
 * svenska eller engelska).
 * 
 * @author Mats L
 *
 */
@Entity
@IdClass(UITextId.class)
@NamedQueries({ @NamedQuery(name = UIText.QUERYNAME_FIND_ALL, query = UIText.QUERY_FIND_ALL),
		@NamedQuery(name = UIText.QUERYNAME_FIND_ALL_BY_LOCALE, query = UIText.QUERY_FIND_ALL_BY_LOCALE),
		@NamedQuery(name = UIText.QUERYNAME_FIND_BY_KEY_AND_LOCALE, query = UIText.QUERY_FIND_BY_KEY_AND_LOCALE)})
public class UIText {

	public static final String QUERYNAME_FIND_ALL = "UIText.findAll";
	public static final String QUERY_FIND_ALL = "Select u from UIText u ORDER BY u.key ASC, u.locale DESC";
	
	public static final String QUERYNAME_FIND_ALL_BY_LOCALE = "UIText.findAllByLocale";
	public static final String QUERY_FIND_ALL_BY_LOCALE = "Select u from UIText u WHERE u.locale=:locale";
	
	public static final String QUERYNAME_FIND_BY_KEY_AND_LOCALE = "UIText.findByKeyAndLocale";
	public static final String QUERY_FIND_BY_KEY_AND_LOCALE = "SELECT u from UIText u WHERE u.key=:key AND u.locale=:locale";

	@Id
	private String key;

	private String value;
	
	@Id
	private String locale;
	
	@Version
	@Column(nullable = false)
	private Long version;

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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "UIText [key=" + key + ", value=" + value + ", locale=" + locale + "]";
	}

}
