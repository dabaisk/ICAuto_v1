package cn.dabaisk.icauto.service;

import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.dabaisk.icauto.util.HttpRespons;
import cn.dabaisk.icauto.util.HttpUtils;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

public class loginService {
	String loginurl = "http://m.ic5999.com/tools/ssc_ajax.ashx?A=Login&S=ac500&U=username";
	private String username = "dabaisk";
	private String password = "e1b8888b182a4fe08d8c2a6100c2e6ce";// hash加密后的数据

	private void dologin() throws Exception {
//		loginurl+="&Type=Hash&UserName=username&ImgCode=12312&Action=Login&SourceName=MB&Password=password";
		loginurl = loginurl.replaceAll("username", username).replaceAll("password", password);
		System.out.println(loginurl);
//		HttpRequest req = HttpUtil.createPost(loginurl);
		Map<String, String> paramMap=new HashMap<>();
		paramMap.put("Type", "Hash");
		paramMap.put("UserName",username);
		paramMap.put("Action","Login");
		paramMap.put("SourceName","MB");
		paramMap.put("Password",password);
		Map<String, String> headers=new HashMap<>();
		headers.put("Host", "http://m.ic5999.com");
		headers.put("Origin", "http://m.ic5999.com");
		headers.put("Referer", "http://m.ic5999.com/login");
		HttpUtils hu=new HttpUtils();
		 HttpRespons result = hu.send(loginurl,"POST", paramMap, headers);
		System.out.println(result);
	}

	public static void main(String[] args) throws Exception {
		new loginService().dologin();
	}
}
