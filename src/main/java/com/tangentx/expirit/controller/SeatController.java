package com.tangentx.expirit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tangentx.expirit.domain.SeatInfo;
import com.tangentx.expirit.repository.SeatInfoCacheRepository;
import com.tangentx.expirit.service.CacheService;
import com.tangentx.expirit.service.SeatService;
import com.tangentx.expirit.service.StoreService;

import lombok.extern.slf4j.Slf4j;
import rx.Single;

@Slf4j
@RestController
public class SeatController {
	
	@Autowired
	StoreService StoreService;
	
	@Autowired
	SeatService seatService;
	
	@RequestMapping(value = "/{storeName}/seatInfo", method = RequestMethod.GET)
	public Single<SeatInfo> getLatestSeatInfo(@PathVariable("storeName") String storeName) {
		
		return seatService.getLatestSeat(storeName).toSingle();
	}
	
	@RequestMapping(value="/seatInfo",method=RequestMethod.POST)
	public void addSeatInfo(@RequestBody SeatInfo seatInfo){

		seatService.addSeat(seatInfo);
	}

}
