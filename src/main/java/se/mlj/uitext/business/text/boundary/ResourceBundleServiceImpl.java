package se.mlj.uitext.business.text.boundary;

import java.util.List;
import java.util.Locale;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.slf4j.Logger;

import se.mlj.uitext.business.text.entity.UIText;
import se.mlj.uitext.business.text.entity.UITextId;

/**
 * För information se {@link ResourceBundleServiceLocal}.
 * 
 * @author ldc-msl
 *
 */
@Stateless
public class ResourceBundleServiceImpl implements ResourceBundleServiceLocal {

	@Inject
	EntityManager em;

	@Inject
	Logger log;

	@Override
	public List<UIText> getAllTexts(Locale locale) {
		try {
			return em.createNamedQuery(UIText.QUERYNAME_FIND_ALL_BY_LOCALE, UIText.class)
					.setParameter("locale", locale.getLanguage()).getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<UIText> getAllTexts() {
		try {
			return em.createNamedQuery(UIText.QUERYNAME_FIND_ALL, UIText.class).getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	/**
	 * 
	 */
	public UIText findById(String key, String locale) {
		UITextId compKey = new UITextId(key, locale);
		return em.find(UIText.class, compKey);
	}

	@Override
	public UIText createUpdateText(UIText text) {
		log.info(text.toString());
		UIText t = findById(text.getKey(), text.getLocale());
		if (t != null) {
			t.setValue(text.getValue());
			return em.merge(t);
		}
		return em.merge(text);
	}
	
//	public UIText createUpdateText(UIText text) {
//		if (text.getId() != null) {
//			return em.merge(text);
//		} else {
//			UIText temp = null;
//			try {
//				temp = em.createNamedQuery(UIText.QUERYNAME_FIND_BY_KEY_AND_LOCALE, UIText.class)
//						.setParameter("key", text.getKey()).setParameter("locale", text.getLocale()).getSingleResult();
//				temp.setValue(text.getValue());
//			} catch (NoResultException ne) {
//				// Finns inte sedan tidigare => skapa ny
//				return em.merge(text);
//			}
//			return temp;
//		}
//	}

	@Override
	public void deleteText(UIText text) {
		text = findById(text.getKey(), text.getLocale());
		em.remove(text);
	}

}
