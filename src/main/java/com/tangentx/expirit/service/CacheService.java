package com.tangentx.expirit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.tangentx.expirit.domain.SeatInfo;


public class CacheService {

//	private static final String KEY = "SeatInfo";
//	
//	@Autowired
//	private  RedisTemplate<String, SeatInfo> seatInfoTemplate;
//	private HashOperations<String, String, SeatInfo> hashOps;
//	
//	@Autowired
//	public CacheService(RedisTemplate<String, SeatInfo> seatInfoTemplate){
//		this.seatInfoTemplate=seatInfoTemplate;
//		hashOps=seatInfoTemplate.opsForHash();
//	}
//
//	public void addCache(SeatInfo seatInfo){
//		System.out.println("cache added");
//		hashOps.put(KEY, seatInfo.getStoreName(), seatInfo);
//	}
//	
//	public SeatInfo findCache(String key){
//		return (SeatInfo) hashOps.get(KEY, key);
//	}
}
