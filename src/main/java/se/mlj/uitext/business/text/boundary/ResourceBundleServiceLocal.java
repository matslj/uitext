package se.mlj.uitext.business.text.boundary;

import java.util.List;
import java.util.Locale;

import javax.ejb.Local;

import se.mlj.uitext.business.text.entity.UIText;


/**
 * https://zenidas.wordpress.com/recipes/database-resource-bundle-in-a-jsf-application/
 * 
 * http://blog.eisele.net/2012/08/resource-bundle-tricks-and-best.html
 */
@Local
public interface ResourceBundleServiceLocal {
  public static final String BEAN_NAME = "ResourceBundleServiceImpl";
  
  public List<UIText> getAllTexts(Locale locale);
  
  public List<UIText> getAllTexts();

  public UIText findById(Long id);

  public UIText createUpdateText(UIText text);

  public void deleteUser(UIText text);
}
