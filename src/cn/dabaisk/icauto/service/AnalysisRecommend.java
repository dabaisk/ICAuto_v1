package cn.dabaisk.icauto.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.dabaisk.icauto.entity.Numbers;
import cn.dabaisk.icauto.util.Jsonutil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

/**
 * 解析推荐购买号码
 * 
 * @author Administrator
 *
 */
public class AnalysisRecommend {
	public String numPeriods;
	public String LotteryCode;
	public String type;
	private ArrayList<Map> numbers;

	public AnalysisRecommend() {
		super();
	}

	public AnalysisRecommend(Map<String, String> map, JSONObject json) {
		super();

		this.numPeriods = map.get("numPeriods");
		this.LotteryCode = map.get("LotteryCode");
		this.type = map.get("type");
		JSONObject jsonBody = json.getJSONObject("Body");
		System.out.println("Body:" + jsonBody.toJSONString());
		String body = jsonBody.getJSONArray("Body").toJSONString();
		System.out.println("body:" + body);
		this.numbers = Jsonutil.jsonToList(body);

	}

	public AnalysisRecommend(String numPeriods, String lotteryCode, String type, ArrayList numbers) {
		super();
		this.numPeriods = numPeriods;
		this.LotteryCode = lotteryCode;
		this.type = type;
		this.numbers = numbers;
	}

	public String[] listTostr() {
		Collections.sort(numbers, new Comparator<Map>() {
			// numbers 最新一期靠前
			@Override
			public int compare(Map o1, Map o2) {
				if (Double.parseDouble(o1.get("No") + "") < Double.parseDouble(o2.get("No") + "")) {
					return 1;
				} else {
					return -1;
				}
			}
			// 获取指定位置的开奖号码
		});
		int numPeriodsInt = Integer.parseInt(numPeriods);
		String[] result = { "", "", "", "", "" };
		for (int i = 0; i < numbers.size(); i++) {
			if (i >= numPeriodsInt) {
				continue;
			}
			Map map = numbers.get(i);
			String[] LotteryOpenNo = map.get("LotteryOpenNo").toString().split(",");
			for (int k = 0; k < LotteryOpenNo.length; k++) {
				/**
				 * for (int j = k; j < LotteryOpenNo.length; j++) 数据重复<br/>
				 * 采用int j = k; j <= k; j++
				 * 
				 */
				for (int j = k; j <= k; j++) {
					if ("10".equals(LotteryOpenNo[j])) {
						LotteryOpenNo[j] = "0";
					}
					result[k] += LotteryOpenNo[j];
				}
			}

		}
		return result;
	}

	private static List<Numbers> total(String str) {
		System.out.println("最近的中奖期号码：" + str + " length：" + str.length());
		List<Numbers> list = new ArrayList<>();
		String[] nums = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		int[] count = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < str.length(); i++) {
			for (int j = 0; j < nums.length; j++) {
				String string = nums[j];
				if (string.equals(str.charAt(i) + "")) {
					count[j] = count[j] + 1;
				}
			}
		}
		for (int i = 0; i < nums.length; i++) {
			list.add(new Numbers(nums[i], count[i]));
		}
		Collections.sort(list, new Numbers());
		System.out.println("建议购买sort：" + list);
		return list;
	}

	public String getResult() {
		String result = "";
		String[] results = listTostr();
		for (int i = 0; i < results.length; i++) {
			String string = results[i];
			if (string != null && !"".equals(string)) {
				List<Numbers> list = total(string);
				result = i + "开奖号码：<font color=red>" + string + "</font> 近" + numPeriods + "期";
				String result1 = "";
				String result2 = "";
				for (int j = 0; j < list.size(); j++) {
					Numbers numbers = list.get(i);
					if (j == list.size() - 1) {
						result1 += numbers.getNumber() + ",";
						result2 += numbers.getCount() + ",";
					} else {
						result1 += numbers.getNumber() + ",";
						result2 += numbers.getCount() + ",";
					}

				}
				result += "\r\n" + i + "推荐购买<font color=green>" + result1 + "</font>";
				result += "\r\n" + i + "开奖次数<font color=green>" + result2 + "</font>";
			}
		}
		return result;
	}

}
