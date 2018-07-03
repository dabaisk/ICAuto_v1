package cn.dabaisk.icauto.brower;

import java.awt.BorderLayout;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Map;

import javax.swing.JFrame;

import com.alibaba.fastjson.JSONObject;
import com.teamdev.jxbrowser.chromium.BeforeURLRequestParams;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.BytesData;
import com.teamdev.jxbrowser.chromium.DataReceivedParams;
import com.teamdev.jxbrowser.chromium.FormData;
import com.teamdev.jxbrowser.chromium.LoadURLParams;
import com.teamdev.jxbrowser.chromium.MultipartFormData;
import com.teamdev.jxbrowser.chromium.NetworkService;
import com.teamdev.jxbrowser.chromium.ResponseStartedParams;
import com.teamdev.jxbrowser.chromium.TextData;
import com.teamdev.jxbrowser.chromium.UploadData;
import com.teamdev.jxbrowser.chromium.UploadDataType;
import com.teamdev.jxbrowser.chromium.WebStorage;
import com.teamdev.jxbrowser.chromium.az;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultNetworkDelegate;

import cn.dabaisk.icauto.callback.CallBack;
import cn.dabaisk.icauto.service.AnalysisRecommend;
import cn.dabaisk.icauto.service.LotterySelectService;
import cn.dabaisk.icauto.util.Jsonutil;
import cn.dabaisk.icauto.window.LotterySelect;

public class ChromeBrower implements CallBack {

	BrowserContext browserContext = BrowserContext.defaultContext();
	Browser browser = new Browser(browserContext);
	NetworkService networkService = browserContext.getNetworkService();
	BrowserView view = new BrowserView(browser);
	JFrame frame = new JFrame();
	String UserName = "";
	LotterySelect lotterySelect = null;
	LotterySelectService service = null;

	private void doNet() {

		networkService.setNetworkDelegate(new DefaultNetworkDelegate() {
			@Override
			public void onDataReceived(DataReceivedParams params) {
				loadURL(params);
				// System.out.println(params.getURL());
				// System.out.println(params.getResponseCode());
			}

			@Override
			public void onResponseStarted(ResponseStartedParams responseStartedParams) {
				// System.out.println(params.getURL());
				// System.out.println(params.getResponseCode());
			}

			@Override
			public void onBeforeURLRequest(BeforeURLRequestParams params) {
				if ("DEODAJ".equals(params.getMethod())) {
					System.out.println("test");
					UploadData uploadData = params.getUploadData();
					UploadDataType dataType = uploadData.getType();
					if (dataType == UploadDataType.FORM_URL_ENCODED) {
						FormData data = (FormData) uploadData;
						data.setPair("key1", "value1", "value2");
						data.setPair("key2", "value2");
					} else if (dataType == UploadDataType.MULTIPART_FORM_DATA) {
						MultipartFormData data = (MultipartFormData) uploadData;
						data.setPair("key1", "value1", "value2");
						data.setPair("key2", "value2");
						data.setFilePair("file3", "C:\\Test.zip");
					} else if (dataType == UploadDataType.PLAIN_TEXT) {
						TextData data = (TextData) uploadData;
						data.setText("My data");
					} else if (dataType == UploadDataType.BYTES) {
						BytesData data = (BytesData) uploadData;
						data.setData("My data".getBytes());
					}
					// Apply modified upload data that will be sent to a web
					// server.
					params.setUploadData(uploadData);
				}
			}

		});

	}

	/*
	 * 登记回调函数
	 */
	public void askQuestion() {
		System.out.println("选择彩种");
		lotterySelect = new LotterySelect();
		/*
		 * 设置选择之后回调函数
		 */
		this.lotterySelect.call(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CallBack#slove() 响应回调函数
	 */
	public void slove() {
		System.out.println("选择彩种后查询对应开奖号码");
		networkService.setNetworkDelegate(new DefaultNetworkDelegate() {
			@Override
			public void onDataReceived(DataReceivedParams params) {
				loadURLGetChart(params);
				// System.out.println(params.getURL());
				// System.out.println(params.getResponseCode());
			}

			@Override
			public void onBeforeURLRequest(BeforeURLRequestParams params) {
				setUploadData(params);
			}
		});
		// 发起查询请求
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		String url = "http://ic5999.com/tools/ssc_ajax.ashx?A=GetChart&S=ac500&U=user";
		url = url.replace("user", UserName);
		browser.loadURL(new LoadURLParams(url, "抓取测试",
				"content-type: application/x-www-form-urlencoded\nOrigin:http://ic5999.com\\nReferer:http://ic5999.com/trendChart/1303"));

	}

	private void setUploadData(BeforeURLRequestParams params) {
		if (params.getURL().toString().lastIndexOf("A=GetChart") != -1) {
			UploadData uploadData = params.getUploadData();
			String paramSTr = "&LotteryCode=lotteryCode&NumPeriods=numPeriods&Type=type&SourceName=PC&Action=GetChart";
			paramSTr = paramSTr.replace("lotteryCode", "1303").replace("numPeriods", "50").replace("type", "BJPK10");
			// UploadDataType dataType = uploadData.getType();
			// if (dataType == UploadDataType.FORM_URL_ENCODED) {
			System.out.println("设置请求数据");
			FormData data = (FormData) uploadData;
			if (lotterySelect != null) {
				service = lotterySelect.getLotterySelectService();
				if ("ok".equals(service.status)) {
					// 获取已设置的彩种
					Map<String, String> map = service.getReqMap();
					String numPeriods = map.get("numPeriods");
					if (!"30".equals(numPeriods) && !"50".equals(numPeriods) && !"200".equals(numPeriods)) {
						numPeriods = "30";
					}
					data.setPair("LotteryCode", map.get("LotteryCode"));
					data.setPair("numPeriods", numPeriods);
					data.setPair("type", map.get("type"));
					System.out.println(map);
				} else {
					// 获取默认
					data.setPair("LotteryCode", "1303");
					data.setPair("numPeriods", "30");
					data.setPair("type", "BJPK10");
				}
			}
			// data.setPair("LotteryCode", "1303", "value2");
			data.setPair("Action", "GetChart");
			data.setPair("SourceName", "PC");
			// }
			params.setUploadData(uploadData);
		}
	}

	String data = "";

	private void loadURLGetChart(DataReceivedParams params) {
		data += new String(params.getData(), Charset.forName("UTF-8"));
		if (params.getURL().toString().lastIndexOf("A=GetChart") != -1) {
			// 解析json 计算推荐购买点数
			System.out.println("data:" + data);
			if (data.lastIndexOf("}") == data.length() - 1) {
				/**
				 * json字符串结束 可以开始解析json
				 */
				JSONObject json = Jsonutil.toJSONObj(data);
				String Code = json.getString("Code");
				if ("1".equals(Code)) {
					/**
					 * 获取到开奖号码，解析中最佳购买
					 */
					AnalysisRecommend ar = new AnalysisRecommend(service.getReqMap(), json.getJSONObject("BackData"));
					System.out.println(ar.getResult());
				}
				// 清空缓存json信息
				data = "";
			}
		}
	}

	private void loadURL(DataReceivedParams params) {
		String data = new String(params.getData(), Charset.forName("UTF-8"));
		if (params.getURL().toString().lastIndexOf("A=Login") != -1) {
			JSONObject json = Jsonutil.toJSONObj(data);
			String Code = json.getString("Code");
			if ("1".equals(Code)) {
				WebStorage webStorage = browser.getLocalWebStorage();
				UserName = webStorage.getItem("UserName");
				System.out.println("登录用户：" + UserName);
				/**
				 * 登陆成功 唤醒选择彩种窗体
				 */
				askQuestion();
			}
		}
	}

	public static void main(String[] args) {
		new ChromeBrower().openBrower();
	}

	static {
		try {
			/**
			 * 6.16 ay.class <br/>
			 * 6.4 aq.class <br/>
			 * 6.18 az.class
			 */
			Field e = az.class.getDeclaredField("e");
			e.setAccessible(true);
			Field f = az.class.getDeclaredField("f");
			f.setAccessible(true);
			Field modifersField = Field.class.getDeclaredField("modifiers");
			modifersField.setAccessible(true);
			modifersField.setInt(e, e.getModifiers() & ~Modifier.FINAL);
			modifersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
			e.set(null, new BigInteger("1"));
			f.set(null, new BigInteger("1"));
			modifersField.setAccessible(false);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public void openBrower() {
		String url = "http://ic5999.com/login";
		String title = "login";
		// 禁用close功能
		// frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);HIDE_ON_CLOSE
		// 不显示标题栏,最大化,最小化,退出按钮
		frame.setUndecorated(true);
		frame.add(view, BorderLayout.CENTER);
		frame.setSize(700, 600);
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// frame.setLocationByPlatform(true);
		frame.setVisible(true);
		frame.setTitle(title);
		browser.loadURL(url);
		WebStorage webStorage = browser.getLocalWebStorage();
		System.out.println(webStorage.toString());
		// 监听网络请求
		doNet();
	}
}
