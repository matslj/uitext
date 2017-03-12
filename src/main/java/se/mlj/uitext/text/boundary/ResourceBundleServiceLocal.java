package se.mlj.uitext.text.boundary;

import java.util.List;
import java.util.Locale;

import javax.ejb.Local;
import javax.persistence.NoResultException;

import se.mlj.uitext.text.entity.UIText;


/**
 * https://zenidas.wordpress.com/recipes/database-resource-bundle-in-a-jsf-application/
 */
@Local
public interface ResourceBundleServiceLocal {
  public static final String BEAN_NAME = "ResourceBundleServiceImpl";
  
  public List<UIText> getAllTexts(Locale locale);

  public UIText findById(Long id);

  public UIText createUpdateText(UIText text);

  public void deleteUser(UIText text);
}
