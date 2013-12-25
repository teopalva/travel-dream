package entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the BUYING_LIST database table.
 * 
 */
@Entity
@EntityListeners({BuyingListItemListener.class})
@Table(name="BUYING_LIST_ITEM")
@NamedQuery(name="BuyingList.findAll", query="SELECT b FROM BuyingListItem b")
public class BuyingListItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BuyingListPK id;

	@Temporal(TemporalType.DATE)
	private Date date;

	private byte gifted;

	private byte paid;

	//bi-directional many-to-one association to Package
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PackageId")
	private Package _package;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="UserIdBuyingList")
	private User user;

	public BuyingListItem() {
	}

	public BuyingListPK getId() {
		return this.id;
	}

	public void setId(BuyingListPK id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean getGifted() {
		return this.gifted != 0;
	}

	public void setGifted(boolean gifted) {
		if(gifted)
			this.gifted = new Byte("1");
		else
			this.gifted = new Byte("0");
	}

	public boolean getPaid() {
		return this.paid != 0;
	}

	public void setPaid(boolean paid) {
		if(paid)
			this.paid = new Byte("1");
		else
			this.paid = new Byte("0");
	}

	public Package get_package() {
		return this._package;
	}

	public void set_package(Package _package) {
		this._package = _package;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}