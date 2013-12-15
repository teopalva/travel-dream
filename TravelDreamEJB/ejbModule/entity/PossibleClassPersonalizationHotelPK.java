package entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the POSSIBLE_CLASS_PERSONALIZATION_HOTEL database table.
 * 
 */
@Embeddable
public class PossibleClassPersonalizationHotelPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int hotelId;

	@Column(insertable=false, updatable=false)
	private int classPersonalizationId;

	public PossibleClassPersonalizationHotelPK() {
	}
	public int getHotelId() {
		return this.hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public int getClassPersonalizationId() {
		return this.classPersonalizationId;
	}
	public void setClassPersonalizationId(int classPersonalizationId) {
		this.classPersonalizationId = classPersonalizationId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PossibleClassPersonalizationHotelPK)) {
			return false;
		}
		PossibleClassPersonalizationHotelPK castOther = (PossibleClassPersonalizationHotelPK)other;
		return 
			(this.hotelId == castOther.hotelId)
			&& (this.classPersonalizationId == castOther.classPersonalizationId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.hotelId;
		hash = hash * prime + this.classPersonalizationId;
		
		return hash;
	}
}