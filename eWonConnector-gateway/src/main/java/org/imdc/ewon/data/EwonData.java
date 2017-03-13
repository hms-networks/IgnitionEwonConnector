package org.imdc.ewon.data;

import java.util.Date;

import org.imdc.ewon.EwonUtil;

public class EwonData extends DMResult {
	int id;
	String name;
	String lastSynchroDate;
	Tag[] tags;
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLastSync() {
		return lastSynchroDate;
	}
	
	public Date getLastSync_Date(){
		return EwonUtil.toDate(getLastSync());
	}
	
	public Tag[] getTags() {
		return tags;
	}

	@Override
	public String toString() {
		return String.format("%s[id=%d, lastSync=%s]", name, id, lastSynchroDate);
	}
}
