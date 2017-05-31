package se.mlj.uitext.model;

import static org.omnifaces.util.Faces.invalidateSession;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class RenderPhaseListener implements PhaseListener {
	
	private static final String LOGOUT_XHTML = "/logout.xhtml";

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		ExternalContext ex = context.getExternalContext();
		if (LOGOUT_XHTML.equals(context.getViewRoot().getViewId())) {
			invalidateSession();
		} else {
			ex.getSessionMap().put("splashShown", true);
		}
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
