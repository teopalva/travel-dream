package entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class PossibleDatePersonalizationFlightListener {
    @PreUpdate
    @PrePersist
    public void setPrimaryKey(PossibleDatePersonalizationFlight p) {
        Flight flight = p.getFlight();
        DatePersonalization dateP = p.getDatePersonalization();
        PossibleDatePersonalizationFlightPK pk = new PossibleDatePersonalizationFlightPK();
        pk.setFlightId(flight.getId());
        pk.setDatePersonalizationId(dateP.getId());
        p.setId(pk);
    }
}
