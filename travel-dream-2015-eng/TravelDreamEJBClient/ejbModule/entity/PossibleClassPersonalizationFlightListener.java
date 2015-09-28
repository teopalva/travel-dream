package entity;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class PossibleClassPersonalizationFlightListener {
    @PreUpdate
    @PrePersist
    @PreRemove
    public void setPrimaryKey(PossibleClassPersonalizationFlight p) {
        Flight flight = p.getFlight();
        ClassPersonalization classP = p.getClassPersonalization();
        PossibleClassPersonalizationFlightPK pk = new PossibleClassPersonalizationFlightPK();
        pk.setFlightId(flight.getId());
        pk.setClassPersonalizationId(classP.getId());
        p.setId(pk);
    }
}
