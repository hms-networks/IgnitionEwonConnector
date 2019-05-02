package org.imdc.ewon.comm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.imdc.ewon.EwonConsts;
import org.imdc.ewon.EwonUtil;
import org.imdc.ewon.config.SyncMode;
import org.imdc.ewon.data.EwonData;
import org.imdc.ewon.data.EwonsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.inductiveautomation.ignition.common.FormatUtil;


public class CommunicationManger {
	private Logger logger = LoggerFactory.getLogger("Ewon.CommManager");
	// private final OkHttpClient client;
	private final Gson gson = new Gson();
	private AuthInfo authInfo;
	private SyncMode mode = SyncMode.GetData;

	public CommunicationManger() {

	}

	public void setAuthInfo(AuthInfo info) {
		this.authInfo = info;
	}

	protected String buildCall(String type, String function, String... params) {
		StringBuilder sb = new StringBuilder(type);
		sb.append(function).append("?");
		sb.append(authInfo.toGetString());
		if (params != null) {
			for (int i = 0; i < params.length; i += 2) {
				sb.append("&").append(params[i]);
				if (params[i + 1] != null) {
					sb.append("=").append(params[i + 1]);
				}
			}
		}
		String ret = sb.toString();
		logger.debug("Generated call: {}", ret);
		return ret;
	}

	protected String buildDMCall(String function, String... params) {
		return buildCall(EwonConsts.URL_DM, function, params);
	}

	protected String buildT2MCall(String directory, String function, String... params) {
		return buildCall((EwonConsts.URL_T2M + directory), function, params);
	}

	protected void logResults(long start, String call, String body){
		logger.debug("[{}] Call finished in {}", call, FormatUtil.formatDurationSince(start));
		logger.trace("[{}] Results: {}", call, body);
	}

	public EwonsData queryEwonDevices() throws Exception {
		long start = System.currentTimeMillis();
		String body = EwonUtil.httpGet(buildDMCall(EwonConsts.DM_CALL_GETEWONS));
		logResults(start, EwonConsts.DM_CALL_GETEWONS, body);
		return gson.fromJson(body, EwonsData.class);
	}

	public EwonData queryEwon(Integer id) throws Exception {
		long start = System.currentTimeMillis();
		String body = EwonUtil.httpGet(buildDMCall(EwonConsts.DM_CALL_GETEWON, EwonConsts.DM_PARAM_ID, id.toString()));
		logResults(start, EwonConsts.DM_CALL_GETEWON, body);
		return gson.fromJson(body, EwonData.class);
	}

	public EwonsData getData(Integer ewonId, Integer tagId, Integer limit, Date fromTime) throws Exception {
		long start = System.currentTimeMillis();
		List<String> params = new ArrayList<>();

		if(ewonId != null){
			params.add(EwonConsts.DM_PARAM_EWONID);
			params.add(ewonId.toString());
		}

		if(tagId != null){
			params.add(EwonConsts.DM_PARAM_TAGID);
			params.add(tagId.toString());
		}

		if(limit != null){
			params.add(EwonConsts.DM_PARAM_LIMIT);
			params.add(limit.toString());
		}

		if(fromTime != null){
			params.add(EwonConsts.DM_PARAM_FROM);
			params.add(EwonUtil.toString(fromTime).replace(":", "%3A"));
		}

		String body = EwonUtil
		        .httpGet(buildDMCall(EwonConsts.DM_CALL_GETDATA, params.size()>0 ? params.toArray(new String[params.size()]) : null));

		logResults(start, EwonConsts.DM_CALL_GETDATA, body);
		return gson.fromJson(body, EwonsData.class);
	}

	public EwonsData syncData(Long transactionId) throws Exception {
		long start = System.currentTimeMillis();
		String body = EwonUtil.httpGet(transactionId == null
		        ? buildDMCall(EwonConsts.DM_CALL_SYNCDATA, EwonConsts.DM_PARAM_CREATE_TRANSACTION, null)
		        : buildDMCall(EwonConsts.DM_CALL_SYNCDATA, EwonConsts.DM_PARAM_CREATE_TRANSACTION, null,
		                EwonConsts.DM_PARAM_LAST_TID, transactionId.toString()));
		logResults(start, EwonConsts.DM_CALL_SYNCDATA, body);
		return gson.fromJson(body, EwonsData.class);
	}

	public EwonsData sync(Object token) throws Exception {
		if (mode == SyncMode.GetData) {
			return getData(null, null, null, (Date)token);
		}else{
			return syncData((Long)token);
		}
	}

}
