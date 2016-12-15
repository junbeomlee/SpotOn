package com.tangentx.expirit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tangentx.expirit.cloudStorage.DCloudStorage;
import com.tangentx.expirit.domain.SeatInfo;
import com.tangentx.expirit.repository.SeatInfoCacheRepository;

import rx.Observable;
import rx.Single;

@Service
public class SeatService {

	@Autowired
	DCloudStorage dcloudStorage;
	
	@Autowired
	SeatInfoCacheRepository seatInfoCacheRepository;
	
	public Observable<SeatInfo> getLatestSeat(String containerName){
		
		return Observable
		.just(seatInfoCacheRepository.findCache(containerName))
		.map(findedSeatInfo->{
			if(findedSeatInfo==null){
				Observable<SeatInfo> seatInfo=dcloudStorage.getLatestSeatBlob(containerName);
				seatInfoCacheRepository.addCache(findedSeatInfo);
				findedSeatInfo=seatInfo.toBlocking().single();
			}
			return findedSeatInfo;
		});
	}

	/*
	 * caching하고 cloud에 update하고
	 */
	public void addSeat(SeatInfo seatInfo) {
		// TODO Auto-generated method stub
		seatInfoCacheRepository.addCache(seatInfo);
		dcloudStorage.addSeatInfo(seatInfo);
	}
	
}
