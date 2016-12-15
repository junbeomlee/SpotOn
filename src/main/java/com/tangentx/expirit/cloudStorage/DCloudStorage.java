package com.tangentx.expirit.cloudStorage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;
import com.tangentx.expirit.domain.Seat;
import com.tangentx.expirit.domain.SeatInfo;

import rx.Observable;
import rx.Single;
import rx.functions.Func2;

@Service
public class DCloudStorage {
	public static final String storageConnectionString =
	        "DefaultEndpointsProtocol=http;"
	        + "AccountName=cs459;"
	        + "AccountKey=aF+wjDY8OCJGFt5dgFqDrAHuTUMbldLGPX1tepIV5EdU+R7ZHAOPl+LZKkZVPh0odkG3Y1tR0R5+zurp2bETsw==";
	public CloudStorageAccount account;
	public CloudBlobClient serviceClient;
	
	public DCloudStorage() throws StorageException, IOException, URISyntaxException{
		 			
			try {
				account = CloudStorageAccount.parse(storageConnectionString);
				serviceClient = account.createCloudBlobClient();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//		    CloudBlobContainer container = serviceClient.getContainerReference("pc1");
//			container.createIfNotExists();
//			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
//		    Date now = new Date();
//		    String strDate = sdfDate.format(now);
//		    CloudBlockBlob blob = container.getBlockBlobReference(strDate);
//		    Seat seat = new Seat();
//		    seat.setSeatNum("0");
//		    seat.setAvailable(1);
//		    SeatInfo seatInfo = new SeatInfo();
//		    seatInfo.setStoreName("pc1");
//		    seatInfo.addSeat(seat);
//		    seatInfo.setDate(strDate);
//		    ObjectMapper mapper = new ObjectMapper();
//		    String jsonString = mapper.writeValueAsString(seatInfo);
//		    System.out.println(jsonString);
//            blob.uploadFromByteArray(jsonString.toString().getBytes(), 0, jsonString.toString().getBytes().length);         
	}
	
	public Observable<SeatInfo> getLatestSeatBlob(String containerName){
		CloudBlobContainer cloudBlobContainer=null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			cloudBlobContainer=serviceClient.getContainerReference(containerName);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Observable
				.from(Observable.from(cloudBlobContainer.listBlobs()).toList().toBlocking().single())
				.toSortedList(new Func2<ListBlobItem, ListBlobItem, Integer>() {

					@Override
					public Integer call(ListBlobItem t1, ListBlobItem t2) {
						// TODO Auto-generated method stub
						int compare=0;
						try {
							compare=((CloudBlockBlob)t2).getName().compareTo(((CloudBlockBlob)t1).getName());
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return compare;
					}
			    })
				.flatMap(Observable::from)
				.first()
				.map(blobitem->{
					String jsonString=null;
					try {
						jsonString= ((CloudBlockBlob)blobitem).downloadText();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return jsonString;
				}).map(jsonString->{
					SeatInfo seatInfo=null;
					try {
						seatInfo = mapper.readValue(jsonString, SeatInfo.class);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return seatInfo;
				});
	}

	public void addSeatInfo(SeatInfo seatInfo){
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();
		CloudBlobContainer container;
		try {
			container = serviceClient.getContainerReference(seatInfo.getStoreName());
			container.createIfNotExists();
			CloudBlockBlob blob=container.getBlockBlobReference(seatInfo.getDate());
		    String jsonString;
		    jsonString = mapper.writeValueAsString(seatInfo);
			blob.uploadFromByteArray(jsonString.getBytes(), 0, jsonString.getBytes().length);
		} catch (URISyntaxException | StorageException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
