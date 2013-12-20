package entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the BUYING_LIST database table.
 * 
 */
@Embeddable
public class BuyingListPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int packageId;

	@Column(insertable=false, updatable=false)
	private String userIdBuyingList;

	public BuyingListPK() {
	}
	public int getPackageId() {
		return this.packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	public String getUserIdBuyingList() {
		return this.userIdBuyingList;
	}
	public void setUserIdBuyingList(String userIdBuyingList) {
		this.userIdBuyingList = userIdBuyingList;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BuyingListPK)) {
			return false;
		}
		BuyingListPK castOther = (BuyingListPK)other;
		return 
			(this.packageId == castOther.packageId)
			&& this.userIdBuyingList.equals(castOther.userIdBuyingList);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.packageId;
		hash = hash * prime + this.userIdBuyingList.hashCode();
		
		return hash;
	}
}