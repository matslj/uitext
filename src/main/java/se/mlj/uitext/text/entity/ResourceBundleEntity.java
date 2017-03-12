package se.mlj.uitext.text.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import se.mlj.uitext.common.entity.AbstractEntity;

@Entity
@Table(name = "resource_bundle")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = ResourceBundleEntity.QUERYNAME_FIND_ALL, query = ResourceBundleEntity.QUERY_FIND_ALL),
		@NamedQuery(name = ResourceBundleEntity.QUERYNAME_COUNT_ALL, query = ResourceBundleEntity.QUERY_COUNT_ALL),
		@NamedQuery(name = ResourceBundleEntity.QUERYNAME_FIND_BY_LOCALE, query = ResourceBundleEntity.QUERY_FIND_BY_LOCALE) })
public class ResourceBundleEntity extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	public static final String QUERY_PARAM_LOCALE = "locale";

	public static final String QUERYNAME_FIND_ALL = "ResourceBundleEntity.findAll";
	public static final String QUERY_FIND_ALL = "Select r from ResourceBundleEntity r";

	public static final String QUERYNAME_COUNT_ALL = "ResourceBundleEntity.countAll";
	public static final String QUERY_COUNT_ALL = "select count(r) from ResourceBundleEntity r";

	public static final String QUERYNAME_FIND_BY_LOCALE = "ResourceBundleEntity.findByLocale";
	public static final String QUERY_FIND_BY_LOCALE = "select r from ResourceBundleEntity r join fetch r.messages m where r.locale=:" + QUERY_PARAM_LOCALE;

	@Column(name = "locale")
	private String locale;

	@Column(name = "basename")
	private String basename;

	@Column(name = "last_modified")
	private Long lastModified;

	@OneToMany(mappedBy = "bundle")
	private List<ResourceMessageEntity> messages;

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @return the basename
	 */
	public String getBasename() {
		return basename;
	}

	/**
	 * @param basename
	 *            the basename to set
	 */
	public void setBasename(String basename) {
		this.basename = basename;
	}

	/**
	 * @return the lastModified
	 */
	public Long getLastModified() {
		return lastModified;
	}

	/**
	 * @param lastModified
	 *            the lastModified to set
	 */
	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * @return the messages
	 */
	public List<ResourceMessageEntity> getMessages() {
		if (messages == null) {
			messages = new ArrayList();
		}
		return messages;
	}

	/**
	 * @param messages
	 *            the messages to set
	 */
	public void setMessages(List messages) {
		this.messages = messages;
	}
}
