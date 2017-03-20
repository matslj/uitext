package se.mlj.uitext.business.text.boundary;

import java.util.List;
import java.util.Locale;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.slf4j.Logger;

import se.mlj.uitext.business.text.entity.UIText;

@Stateless
public class ResourceBundleServiceImpl implements ResourceBundleServiceLocal {

	@Inject
	EntityManager em;

	@Inject
	Logger logger;

	public List<UIText> getAllTexts(Locale locale) {
		try {
			return em.createNamedQuery(UIText.QUERYNAME_FIND_ALL_BY_LOCALE, UIText.class)
					.setParameter("locale", locale.getLanguage()).getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public List<UIText> getAllTexts() {
		try {
			return em.createNamedQuery(UIText.QUERYNAME_FIND_ALL, UIText.class).getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public UIText findById(Long id) {
		return em.find(UIText.class, id);
	}

	public UIText createUpdateText(UIText text) {
		if (text.getId() != null) {
			return em.merge(text);
		} else {
			UIText temp = null;
			try {
				temp = em.createNamedQuery(UIText.QUERYNAME_FIND_BY_KEY_AND_LOCALE, UIText.class)
						.setParameter("key", text.getKey()).setParameter("locale", text.getLocale()).getSingleResult();
				temp.setValue(text.getValue());
			} catch (NoResultException ne) {
				// Finns inte sedan tidigare => skapa ny
				return em.merge(text);
			}
			return temp;
		}
	}

	public void deleteUser(UIText text) {
		text = findById(text.getId());
		em.remove(text);
	}

}
