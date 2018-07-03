package cn.dabaisk.icauto.service;

import java.util.Map;

import cn.dabaisk.icauto.callback.CallBack;

public class LotterySelectService {
	public String reqId;
	public String status;
	public Object reqObject;
	public Object resObject;

	public	Map<String, String> getReqMap() {
		return (Map<String, String>) reqObject;
	}

	public LotterySelectService() {
		status = "create";
		reqId = (System.currentTimeMillis() + "").substring(5) + (int) (Math.random() * 10000) + "";
	}
}
