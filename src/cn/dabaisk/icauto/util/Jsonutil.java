package cn.dabaisk.icauto.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;

public class Jsonutil {
	/**
	 * fastJson 解析json串
	 * 
	 * @param json
	 * @return
	 */
	public static ArrayList<Map> jsonToList(String json) {
		JSONReader reader = new JSONReader(new StringReader(json));// 已流的方式处理，这里很快
		reader.startArray();
		ArrayList<Map> rsList = new ArrayList<Map>();
		Map<String, String> map = null;
		int i = 0;
		while (reader.hasNext()) {
			i++;
			reader.startObject();// 这边反序列化也是极速
			map = new HashMap<String, String>();
			while (reader.hasNext()) {
				String arrayListItemKey = reader.readString();
				String arrayListItemValue = reader.readObject().toString();
				map.put(arrayListItemKey, arrayListItemValue);
			}
			rsList.add(map);
			reader.endObject();
		}
		reader.endArray();
		return rsList;
	}



	/**
	 * fastJson 解析json串
	 * 
	 * @param json
	 * @return
	 */
	public static JSONObject toJSONObj(String json) {

		return JSONArray.parseObject(json);
	}
}
