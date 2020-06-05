package com.wroom.searchservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wroom.searchservice.domain.Ad;
import com.wroom.searchservice.domain.Location;
import com.wroom.searchservice.domain.RentRequest;
import com.wroom.searchservice.domain.dto.SearchCriteriaDTO;
import com.wroom.searchservice.domain.enums.RequestStatus;

@Service
public class SearchService {

	private final AdService adService;
	private final RentsService rentsService;

	public SearchService(AdService as, RentsService rs) {
		this.adService = as;
		this.rentsService = rs;
	}

	public List<Ad> search(SearchCriteriaDTO criteria) {
		List<Ad> ads = this.adService.findAll();
		List<Ad> good = new ArrayList<Ad>();
		Location requestedLocation = this.adService.findLocationById(criteria.getLocationId());

		if (requestedLocation == null) {
			return null;
		}
		
		System.out.println(">>> Searching location: " + requestedLocation.getCountry() + ", " + requestedLocation.getCity());

		for (Ad ad : ads) {
//			Check location
			if (ad.getLocation().getId() != criteria.getLocationId()) {
				continue;
			}

//			Check endge-cases
			Date adAvailableFrom = ad.getAvailableFrom();
			Date adAvailableTo = ad.getAvailableTo();
			if (criteria.getFrom().before(adAvailableFrom) || criteria.getTo().after(adAvailableTo)) {
				continue;
			}

//			Check if any existing rent covers chosen dates 
			List<RentRequest> rents = this.rentsService.findByAd(ad);
			System.out.println(">>>> Found " + rents.size() + " rents");
			boolean flag = false;
			for (RentRequest rent : rents) {

//				A) ----|-*---*-|----
				if (criteria.getFrom().after(rent.getFromDate()) && criteria.getTo().before(rent.getToDate())) {
					if (rent.getStatus() == RequestStatus.PAID || rent.getStatus() == RequestStatus.PHYSICALLY_RESERVED
							|| rent.getStatus() == RequestStatus.RESERVED)
						flag = true;
					System.out.println(">>>> Chosen dates are overlapping");
					break;
				}

//				B) -*---|---*---|----
				if (criteria.getFrom().before(rent.getFromDate()) && criteria.getTo().after(rent.getFromDate())) {
					if (rent.getStatus() == RequestStatus.PAID || rent.getStatus() == RequestStatus.PHYSICALLY_RESERVED
							|| rent.getStatus() == RequestStatus.RESERVED)
						flag = true;
					System.out.println(">>>> Chosen dates are overlapping");
					break;
				}

//				C) ----|---*--|--*---
				if (criteria.getFrom().before(rent.getToDate()) && criteria.getTo().after(rent.getToDate())) {
					if (rent.getStatus() == RequestStatus.PAID || rent.getStatus() == RequestStatus.PHYSICALLY_RESERVED
							|| rent.getStatus() == RequestStatus.RESERVED)
						flag = true;
					System.out.println(">>>> Chosen dates are overlapping");
					break;
				}
			}
			if (flag) {
				continue;
			}
			good.add(ad);
		}
		return good;
	}

}
