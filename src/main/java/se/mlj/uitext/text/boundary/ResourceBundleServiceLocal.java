package se.mlj.uitext.text.boundary;

import java.util.Locale;

import javax.ejb.Local;

import se.mlj.uitext.text.entity.ResourceBundle;


/**
 * https://zenidas.wordpress.com/recipes/database-resource-bundle-in-a-jsf-application/
 */
@Local
public interface ResourceBundleServiceLocal {
  public static final String BEAN_NAME = "ResourceBundleServiceImpl";
  
  public ResourceBundle findResourceBundle(Locale locale);
}
