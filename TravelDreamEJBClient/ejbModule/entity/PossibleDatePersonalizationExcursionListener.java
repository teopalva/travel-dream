package entity;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class PossibleDatePersonalizationExcursionListener {
    @PreUpdate
    @PrePersist
    @PreRemove
    public void setPrimaryKey(PossibleDatePersonalizationExcursion p) {
        Excursion excursion = p.getExcursion();
        DatePersonalization dateP = p.getDatePersonalization();
        PossibleDatePersonalizationExcursionPK pk = new PossibleDatePersonalizationExcursionPK();
        pk.setExcursionId(excursion.getId());
        pk.setDatePersonalizationId(dateP.getId());
        p.setId(pk);
    }
}
