package org.imdc.ewon;

/**
 * These are various constants for the talk2m service, mostly url parameter
 * keys.
 *
 */
public class EwonConsts {
	public static final String URL_DM = "https://data.talk2m.com/";
	public static final String URL_T2M = "https://m2web.talk2m.com/t2mapi/";

	public static final String T2M_ACCOUNT = "t2maccount";
	public static final String T2M_USERNAME = "t2musername";
	public static final String T2M_PASSWORD = "t2mpassword";
	public static final String T2M_DEVKEY = "t2mdevid";
	public static final String T2M_M2W_DEVKEY = "t2mdeveloperid";
	public static final String T2M_DEVICE_USERNAME = "t2mdeviceusername";
	public static final String T2M_DEVICE_PASSWORD = "t2mdevicepassword";

	public static final String T2M_DIR_GET = "get/";
	public static final String T2M_DIR_RCGI = "rcgi.bin/";

	public static final String T2M_CALL_UPDATETAGFORM = "UpdateTagForm";

	public static final String T2M_PARAM_TAGNAME1 = "TagName1";
	public static final String T2M_PARAM_TAGVALUE1 = "TagValue1";

	public static final String DM_CALL_GETEWONS = "getewons";
	public static final String DM_CALL_GETEWON = "getewon";
	public static final String DM_CALL_GETDATA = "getdata";
	public static final String DM_CALL_SYNCDATA = "syncdata";

	public static final String DM_PARAM_ID = "id";
	public static final String DM_PARAM_EWONID = "ewonId";
	public static final String DM_PARAM_TAGID = "tagId";
	public static final String DM_PARAM_LIMIT = "limit";
	public static final String DM_PARAM_FROM = "from";
	public static final String DM_PARAM_CREATE_TRANSACTION = "createTransaction";
	public static final String DM_PARAM_LAST_TID = "lastTransactionId";
}
