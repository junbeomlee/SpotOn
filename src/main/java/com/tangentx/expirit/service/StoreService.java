package com.tangentx.expirit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangentx.expirit.api.StoreLegacyApi;
import com.tangentx.expirit.domain.Store;

import retrofit2.Retrofit;
import rx.Observable;

@Service
public class StoreService {

	@Autowired
	Retrofit retrofit;
	
	public Observable<List<Store>> getStoreList() {
		// TODO Auto-generated method stub
			
		return retrofit
				.create(StoreLegacyApi.class)
				.getStoreList();
	}

}
