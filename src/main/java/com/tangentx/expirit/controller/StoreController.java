package com.tangentx.expirit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tangentx.expirit.domain.Store;
import com.tangentx.expirit.service.StoreService;

import lombok.extern.slf4j.Slf4j;
import rx.Single;

@Slf4j
@RestController
public class StoreController {

	@Autowired
	StoreService StoreService;
	
	@RequestMapping(value = "/stores", method = RequestMethod.GET)
	public Single<List<Store>> test() {
		
		return StoreService.getStoreList().toSingle();
	}
}
