abstract sig User {}

sig TDE extends User {}
sig TDC extends User {
	giftList: one GiftList,
	buyingList: one BuyingList
}

abstract sig BaseProduct {}

sig Excursion extends BaseProduct {}
sig Flight extends BaseProduct {}
sig Hotel extends BaseProduct {}

sig PersonalizedProduct {
	product: one (Excursion + Flight + Hotel)
}

sig Package  {
	products: set PersonalizedProduct
}

abstract sig PackageList {
	packageList: set Package
}

sig GiftList extends PackageList {}
sig BuyingList extends PackageList {}

/*
sig Invitation {
	invite: one TDC,
	invited: one TDC,
	package: one Package
}
*/

//Model became realistic - mandatory constraints
fact every_list_has_one_and_only_one_TDC {
	all p: PackageList | one t: TDC | t.buyingList = p or t.giftList = p
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

pred show {
	#Flight > 1
	#Hotel > 1
	#Excursion > 1
	#PersonalizedProduct > 1
	#Package >= 1
	#TDE > 1
	#TDC > 1
}

run show for 6

//check at_least_one_package_has_no_list for 14