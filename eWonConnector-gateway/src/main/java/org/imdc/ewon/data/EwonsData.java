package org.imdc.ewon.data;

public class EwonsData extends DMResult {
	EwonData[] ewons;
	boolean moreDataAvailable = false;
	Long transactionId = 0L;
	public EwonData[] getEwons() {
		return ewons;
	}

	public boolean isMoreDataAvailable() {
		return moreDataAvailable;
	}

	public Long getTransactionId(){
		return transactionId;
	}
}
