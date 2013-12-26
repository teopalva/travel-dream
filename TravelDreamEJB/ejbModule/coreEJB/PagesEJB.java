package coreEJB;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

/**
 * Session Bean implementation class PagesEJB
 */
@Stateful
@LocalBean
public class PagesEJB implements PagesEJBLocal {
    private String previousPage = null;

    @Override
    public String getPreviousPage() {
	return previousPage;
    }

    @Override
    public void resetPreviousPage() {
	previousPage = null;
    }

    /**
     * Possible pages: "gift", "invitation", "edit"
     */
    @Override
    public void setPreviousPage(String page) {
	previousPage = page;
    }

}