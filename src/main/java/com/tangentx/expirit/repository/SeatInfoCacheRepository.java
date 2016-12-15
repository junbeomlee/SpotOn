package com.tangentx.expirit.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.tangentx.expirit.domain.SeatInfo;

@Repository
public class SeatInfoCacheRepository {

	private static final String KEY = "SeatInfo";
	
	@Autowired
	private  RedisTemplate<String, SeatInfo> seatInfoTemplate;
	private HashOperations<String, String, SeatInfo> hashOps;
	
	@Autowired
    public SeatInfoCacheRepository(RedisTemplate<String, SeatInfo> seatInfoTemplate) {
        this.seatInfoTemplate = seatInfoTemplate;
    }
 
    @PostConstruct
    private void init() {
        hashOps = seatInfoTemplate.opsForHash();
    }
    
    public void addCache(SeatInfo seatInfo){
		hashOps.put(KEY, seatInfo.getStoreName(), seatInfo);
	}
	
	public SeatInfo findCache(String key){
		return (SeatInfo) hashOps.get(KEY, key);
	}
}
