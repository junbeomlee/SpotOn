package com.tangentx.expirit.api;

import java.util.List;

import com.tangentx.expirit.domain.Store;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

public interface StoreLegacyApi {

	 @GET("stores")
	 Observable<List<Store>> getStoreList();
}
