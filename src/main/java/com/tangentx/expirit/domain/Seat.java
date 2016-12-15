package com.tangentx.expirit.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat implements Serializable{

	private String SeatNum;
	private int available;
}
