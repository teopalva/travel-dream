//Signatures

abstract sig User {}

sig TDE extends User {}
sig TDF extends User {
	tdc: lone TDC
}

sig TDC extends User {
	giftList: one GiftList,
	buyingList: one BuyingList,
	invitationList: one InvitationList
}

abstract sig BaseProduct {
	possiblePersonalization: set (DatePersonalization + ClassPersonalization)
}

sig Excursion extends BaseProduct {
} 
fact {		//No class personalization
	Excursion.possiblePersonalization & ClassPersonalization = none
}

sig Flight extends BaseProduct {
}

sig Hotel extends BaseProduct {
}

sig PersonalizedProduct {
	product: one BaseProduct,
	personalization: set (DatePersonalization + ClassPersonalization)
}

sig Package  {
	products: set PersonalizedProduct
}

abstract sig PackageList {
	packageList: set Package
}

sig GiftList extends PackageList {}
sig BuyingList extends PackageList {
	giftedPackage: set Package
}

sig InvitationList {
	invitationList: set Invitation
}

sig Invitation {
	invited: some TDF,
	package: one Package
}

abstract sig Personalization {}

sig DatePersonalization extends Personalization {}
sig ClassPersonalization extends Personalization {}


//Model became realistic - mandatory constraints
fact every_list_has_one_and_only_one_TDC {
	all p: PackageList | one t: TDC | t.buyingList = p or t.giftList = p
}

fact every_InvitationList_has_one_and_only_one_TDC {
	all list: InvitationList | one t: TDC | t.invitationList = list
}

fact different_package_cant_share_personalized_product {
	all disj p1,p2: Package | all p: p1.products | not p in p2.products
}

fact different_list_cant_share_same_package {
	all disj l1,l2: PackageList | all p: l1.packageList | not p in l2.packageList
}

fact every_package_cant_contain_more_than_two_flight {
	all pkg: Package | #(pkg.products.product & Flight) <= 2
}

fact every_personalized_product_contained_in_package {
	all p: PersonalizedProduct | one pkg: Package | p in pkg.products
}

fact TDC_cant_invite_himself {
	all customer:TDC | all invitation:InvitationList.invitationList | all tdf:TDF | (tdf.tdc)	in customer implies invitation.invited != tdf
}

fact TDC_cant_invite_to_a_package_in_a_list {
	all invitation: Invitation | not one i: PackageList | invitation.package in i.packageList
}

fact PersonalizedProduct_can_contains_only_possible_personalization {
	all p: PersonalizedProduct | p.personalization in p.product.possiblePersonalization
}

fact BasicProduct_must_have_a_personalization {
	all b: BaseProduct | #(b.possiblePersonalization) > 0
}

fact Invitation_not_shared_between_invitation_lists {
	all invitation: Invitation | one list:InvitationList | invitation in list.invitationList
}

fact Package_in_list_must_have_all_personalizations {
	all package: (PackageList.packageList+(InvitationList.invitationList).package) | all personalizedProduct: package.products | #(personalizedProduct.personalization&DatePersonalization) > 0 and #(personalizedProduct.personalization&DatePersonalization) < 2 and ((personalizedProduct.product in Hotel) or (personalizedProduct.product in Flight) implies #(personalizedProduct.personalization&ClassPersonalization) > 0 and #(personalizedProduct.personalization&ClassPersonalization) < 2) 
}

fact only_one_TDC_can_buy_a_package_for_another_TDC {
	all p1, p2: BuyingList.giftedPackage | one gpl1: BuyingList.giftedPackage | one gpl2: BuyingList.giftedPackage | p1 in gpl1 and p2 in gpl2 and p1=p2 implies gpl1=gpl2
}

fact every_giftedPackage_must_be_in_a_BuyingList {
	all package: BuyingList.giftedPackage | one b: BuyingList | package in b.packageList 
}

fact TDC_cant_gift_his_own_package {
	all package: BuyingList.giftedPackage | all tdc1: TDC | package in tdc1.buyingList.giftedPackage implies not (package in tdc1.buyingList.packageList)
}

//Some optional constraints - can be omitted

fact every_package_2flight_1hotel_1excursion {
	all pkg: Package | #(pkg.products.product & Flight) = 2
	all pkg: Package | #(pkg.products.product & Hotel) = 1
	all pkg: Package | #(pkg.products.product & Excursion) = 1
	all pkg: Package | all disj p1,p2: pkg.products | p1.product != p2.product 
}


//I want find a counterexample - package created but not inserted in a list
assert at_least_one_package_has_no_list {
	#(PackageList.packageList) = #Package
}

//I don't want to find an example - Base product personalized with option that is not possible
assert BaseProduct_personalized_with_option_that_is_possible_not {
	all p: PersonalizedProduct | p.personalization in p.product.possiblePersonalization
}

pred show {
	#Flight > 1
	#Hotel > 1
	#Excursion > 1
	#PersonalizedProduct > 1
	#Package >= 1
	#TDE = 1
	#TDC = 1
	#TDF = 0
	#Invitation = 0
	#Personalization > 1
	#ClassPersonalization = 1
	#(BaseProduct.possiblePersonalization) < 4
}

run show for 6

//check at_least_one_package_has_no_list for 15
//check BaseProduct_personalized_with_option_that_is_possible_not for 15
