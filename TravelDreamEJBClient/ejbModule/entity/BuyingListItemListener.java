package entity;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class BuyingListItemListener {

	@PreUpdate
    @PrePersist
    @PreRemove
    public void setPrimaryKey(BuyingListItem b) {
		Package _package = b.get_package();
		User user = b.getUser();
		BuyingListPK pk = new BuyingListPK();
		pk.setPackageId(_package.getId());
		pk.setUserIdBuyingList(user.getMail());
        b.setId(pk);
    }

}
