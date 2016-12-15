package com.tangentx.expirit.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rx.Observable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatInfo implements Serializable{

	private String storeName;
	private List<Seat> seatInfo = new ArrayList<Seat>();
	private String date;
	public void addSeat(Seat seat) {
		// TODO Auto-generated method stub
		this.seatInfo.add(seat);
	}
	
	public Observable<SeatInfo> toObservable(){
		return Observable.just(this);
	}
}
