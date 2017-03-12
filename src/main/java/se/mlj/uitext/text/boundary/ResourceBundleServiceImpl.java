package se.mlj.uitext.text.boundary;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.slf4j.Logger;

import se.mlj.uitext.text.entity.UIText;

public class ResourceBundleServiceImpl implements ResourceBundleServiceLocal {
	
	@Inject
    EntityManager em;

    @Inject
    Logger logger;

    public List<UIText> getAllTexts(Locale locale) {
        try {
            return em.createNamedQuery(UIText.QUERYNAME_FIND_ALL, UIText.class).setParameter("locale", locale.getLanguage()).
            		getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public UIText findById(Long id) {
        return em.find(UIText.class, id);
    }

    public UIText createUpdateText(UIText text) {
        return em.merge(text);
    }

    public void deleteUser(UIText text) {
        text = findById(text.getId());
        em.remove(text);
    }

}
