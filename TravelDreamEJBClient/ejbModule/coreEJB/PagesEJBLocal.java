package coreEJB;

import javax.ejb.Local;

@Local
public interface PagesEJBLocal {
    public String getPreviousPage();
    public void setPreviousPage(String page);
    
    /**
     * not needed
     */
    public void resetPreviousPage();
}
