package se.mlj.uitext.business.text.boundary;

import java.util.List;
import java.util.Locale;

import javax.ejb.Local;

import se.mlj.uitext.business.text.entity.UIText;

/**
 * Hanterar läsning och skrivning av ui-texter i databas.
 * 
 * @author Mats L
 *
 */
@Local
public interface ResourceBundleServiceLocal {
  public static final String BEAN_NAME = "ResourceBundleServiceImpl";
  
  /**
   * Hämtar alla texter för en viss locale.
   * 
   * @param locale den locale för vilken texter ska hämtas
   * @return alla texter motsvarande param locale (sorterat på stigande nyckel, fallande locale) eller null om resultat saknas
   */
  public List<UIText> getAllTexts(Locale locale);
  
  /**
   * Hämtar alla texter.
   * 
   * @return alla texter (sorterat på stigande nyckel, fallande locale) eller null om resultat saknas
   */
  public List<UIText> getAllTexts();

  /**
   * Hämtar {@link UIText} baserat på key och locale.
   * 
   * @param key
   * @param locale
   * @return textobjekt som motsvarar params.
   */
  public UIText findById(String key, String locale);

  /**
   * Skapar eller uppdaterar en uitext i databasen. En kontroll görs för att se om
   * inkommande ui-text finns sedan tidigare i databasen. Om så är fallet så uppdateras
   * denna instans, annars skapas en ny.
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
