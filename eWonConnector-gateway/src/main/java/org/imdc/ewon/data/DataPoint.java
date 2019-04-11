package org.imdc.ewon.data;

import com.inductiveautomation.ignition.common.model.values.BasicQualifiedValue;
import com.inductiveautomation.ignition.common.model.values.QualifiedValue;
import com.inductiveautomation.ignition.common.sqltags.model.types.DataQuality;
import org.imdc.ewon.EwonUtil;

public class DataPoint {
	String date;
	String quality;
	Object value;

	public Object getValue() {
		return value;
	}

	public String getDate() {
		return date;
	}

	public String getQuality() {
		return quality == null ? "good" : quality;
	}

	public QualifiedValue toQVal(){
		return new BasicQualifiedValue(value, "good".equals(getQuality()) ? DataQuality.GOOD_DATA : DataQuality.OPC_BAD_DATA, EwonUtil.toDate(date));
	}
}
