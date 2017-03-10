package se.mlj.uitext.text.boundary;

import java.util.Locale;

import se.mlj.uitext.text.entity.ResourceBundle;

public class ResourceBundleServiceImpl implements ResourceBundleServiceLocal {
	
	@Inject
    EntityManager em;

    @Inject
    Logger logger;

    /**
     * Hämtar alla användare från databasen.
     * 
     * @return alla användare i databasen
     */
    public List<Anvandare> getAllUsers() {
        try {
            return em.createNamedQuery("anvandare.listAll", Anvandare.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Hämtar användaren mha primary-key (id). Obs det är inte userId utan en syntetisk nyckel-id.
     * 
     * @param id
     *            primary-key för användaren i databasen
     * @return användaren om den hittas, annars kastas ett unchecked exception
     */
    public Anvandare findById(Long id) {
        return em.find(Anvandare.class, id);
    }

    /**
     * Hämtar användaren mha userId.
     * 
     * @param userId
     *            användarens userId
     * @return null om användaren inte hittades i databasen, annars användaren med angivet userId
     */
    public Anvandare findUserByUserId(String userId) {
        try {
            return em.createNamedQuery("anvandare.findByUserId", Anvandare.class).setParameter("userId", userId).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Skapar eller uppdaterar en användare i databasen.
     * 
     * @param user
     *            användar-instansen som ska skapas/uppdateras
     * @return det skapade användarobjektet
     */
    @LoggStartStop
    public Anvandare createUpdateUser(Anvandare user) {
        return em.merge(user);
    }

    /**
     * Radera användare från databasen.
     * 
     * @param user
     *            användarinstansen som ska raderas
     */
    @LoggStartStop
    public void deleteUser(Anvandare user) {
        user = findById(user.getId());
        em.remove(user);
    }

    /**
     * @param facesContext
     *            Behövs för att ta reda på användarnamnet.
     * 
     * @return användarinstansen för sessionen.
     */
    public Anvandare getCurrentUser(FacesContext facesContext) {
        String userId = facesContext.getExternalContext().getUserPrincipal().getName();
        return this.findUserByUserId(userId);
    }


	@Override
	public ResourceBundle findResourceBundle(Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
