package se.mlj.uitext.business.text.boundary;

import java.util.List;
import java.util.Locale;

import javax.ejb.Local;

import se.mlj.uitext.business.text.entity.UIText;

/**
 * 
 * https://zenidas.wordpress.com/recipes/database-resource-bundle-in-a-jsf-application/
 * 
 * http://blog.eisele.net/2012/08/resource-bundle-tricks-and-best.html
 * 
 * @author ldc-msl
 *
 */
@Local
public interface ResourceBundleServiceLocal {
  public static final String BEAN_NAME = "ResourceBundleServiceImpl";
  
  /**
   * Hämtar alla texter för en viss locale.
   * 
   * @param locale den locale för vilken texter ska hämtas
   * @return alla texter motsvarande param locale sorterat på stigande nyckel, fallande locale
   */
  public List<UIText> getAllTexts(Locale locale);
  
  /**
   * Hämtar alla texter.
   * 
   * @return alla texter sorterat på stigande nyckel, fallande locale
   */
  public List<UIText> getAllTexts();

  /**
   * Hämtar {@link UIText} baserat på id.
   * @param id den syntetiska nyckeln för {@link UIText}.
   * @return textobjekt som motsvarar angivet id.
   */
  public UIText findById(String key, String locale);

  /**
   * Skapar och uppdaterar en uitext i databasen. Tre tillstånd beaktas av metoden:
   * <ul>
   * <li> id är satt - en update av {@link UIText} görs
   * <li> id saknas - kollar om key kombinerat med locale finns i databasen redan; hämtar
   *                  i så fall förekomsten och uppdaterar mha {@link UIText#setValue(String)}.
   * <li> id saknas - key och locale finns inte i db; skapa ny uitext.
   * </ul>
   * 
   * @param text den text som ska skapas/uppdateras
   * @return skapad/uppdaterad {@link UIText}.
   */
  public UIText createUpdateText(UIText text);

  /**
   * Raderar en text.
   * 
   * @param text den text som ska raderas.
   */
  public void deleteText(UIText text);
}
