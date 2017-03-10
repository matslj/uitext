package se.mlj.uitext.text.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import se.mlj.uitext.common.entity.AbstractEntity;

@Entity
@Table(name = "resource_message")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = ResourceMessage.QUERYNAME_FIND_ALL, query = ResourceMessage.QUERY_FIND_ALL),
		@NamedQuery(name = ResourceMessage.QUERYNAME_COUNT_ALL, query = ResourceMessage.QUERY_COUNT_ALL) })
public class ResourceMessage extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	public static final String QUERYNAME_FIND_ALL = "ResourceMessage.findAll";
	public static final String QUERY_FIND_ALL = "Select r from ResourceMessage r";

	public static final String QUERYNAME_COUNT_ALL = "ResourceMessage.countAll";
	public static final String QUERY_COUNT_ALL = "select count(r) from ResourceMessage r";

	@Column(name = "message_key")
	private String key;

	@Column(name = "message_value")
	private String value;

	@ManyToOne
	@JoinColumn(name = "key_bundle")
	private ResourceBundle bundle;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the bundle
	 */
	public ResourceBundle getBundle() {
		return bundle;
	}

	/**
	 * @param bundle
	 *            the bundle to set
	 */
	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}
}
