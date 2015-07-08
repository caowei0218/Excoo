package com.eoe.excoo.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class JsonBinder {
	private ObjectMapper mapper;

	@SuppressWarnings("deprecation")
	public JsonBinder(Inclusion inclusion) {
		mapper = new ObjectMapper();
		// 璁剧疆杈撳嚭鍖呭惈鐨勫睘锟�?
		mapper.getSerializationConfig().setSerializationInclusion(inclusion);
		// 璁剧疆杈撳叆鏃跺拷鐣SON瀛楃涓蹭腑瀛樺湪鑰孞ava瀵硅薄瀹為檯娌℃湁鐨勫睘锟�?
		mapper.getDeserializationConfig()
				.set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);
	}

	/**
	 * 鍒涘缓杈撳嚭鍏ㄩ儴灞烇拷?锟藉埌Json瀛楃涓茬殑Binder.
	 */
	public static JsonBinder buildNormalBinder() {
		return new JsonBinder(Inclusion.ALWAYS);
	}

	/**
	 * 鍒涘缓鍙緭鍑洪潪绌哄睘鎬у埌Json瀛楃涓茬殑Binder.
	 */
	public static JsonBinder buildNonNullBinder() {
		return new JsonBinder(Inclusion.NON_NULL);
	}

	/**
	 * 鍒涘缓鍙緭鍑哄垵濮嬶拷?锟借鏀瑰彉鐨勫睘鎬у埌Json瀛楃涓茬殑Binder.
	 */
	public static JsonBinder buildNonDefaultBinder() {
		return new JsonBinder(Inclusion.NON_DEFAULT);
	}

	/**
	 * 濡傛灉JSON瀛楃涓蹭负Null锟�?"null"瀛楃锟�?,杩斿洖Null. 濡傛灉JSON瀛楃涓蹭负"[]",杩斿洖绌洪泦锟�?.
	 * 
	 * 濡傞渶璇诲彇闆嗗悎濡侺ist/Map,涓斾笉鏄疞ist<String>杩欑锟�?鍗曠被鍨嬫椂浣跨敤濡備笅璇彞: List<MyBean>
	 * beanList = binder.getMapper().readValue(listString, new
	 * TypeReference<List<MyBean>>() {});
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * fromJsonToObject<br>
	 * jackjson鎶妀son瀛楃涓茶浆鎹负Java瀵硅薄鐨勫疄鐜版柟锟�?
	 * 
	 * <pre>
	 * return Jackson.jsonToObj(this.answersJson,
	 * 		new TypeReference&lt;List&lt;StanzaAnswer&gt;&gt;() {
	 * 		});
	 * </pre>
	 * 
	 * @param <T>
	 *            杞崲涓虹殑java瀵硅薄
	 * @param json
	 *            json瀛楃锟�?
	 * @param typeReference
	 *            jackjson鑷畾涔夌殑绫诲瀷
	 * @return 杩斿洖Java瀵硅薄
	 */
	public <T> T jsonToObj(String json, TypeReference<T> typeReference) {
		try {
			return mapper.readValue(json, typeReference);
		} catch (JsonParseException e) {
			Log.w("JsonParseException: ", e);
		} catch (JsonMappingException e) {
			Log.w("JsonMappingException: ", e);
		} catch (IOException e) {
			Log.w("IOException: ", e);
		}
		return null;
	}

	/**
	 * fromJsonToObject<br>
	 * json杞崲涓簀ava瀵硅薄
	 * 
	 * <pre>
	 * return Jackson.jsonToObj(this.answersJson, Jackson.class);
	 * </pre>
	 * 
	 * @param <T>
	 *            瑕佽浆鎹㈢殑瀵硅薄
	 * @param json
	 *            瀛楃锟�?
	 * @param valueType
	 *            瀵硅薄鐨刢lass
	 * @return 杩斿洖瀵硅薄
	 */
	public <T> T jsonToObj(String json, Class<T> valueType) {
		try {
			return mapper.readValue(json, valueType);
		} catch (JsonParseException e) {
			Log.w("JsonParseException: ", e);
		} catch (JsonMappingException e) {
			Log.w("JsonMappingException: ", e);
		} catch (IOException e) {
			Log.w("IOException: ", e);
		}
		return null;
	}

	/**
	 * 濡傛灉瀵硅薄涓篘ull,杩斿洖"null". 濡傛灉闆嗗悎涓虹┖闆嗗悎,杩斿洖"[]".
	 */
	public String toJson(Object object) {

		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			Log.w("write to json string error:" + object, e);
			return null;
		}
	}

	/**
	 * 璁剧疆杞崲鏃ユ湡绫诲瀷鐨刦ormat pattern,濡傛灉涓嶈缃粯璁ゆ墦鍗癟imestamp姣锟�?.
	 */
	@SuppressWarnings("deprecation")
	public void setDateFormat(String pattern) {
		if (StringUtils.isNotBlank(pattern)) {
			DateFormat df = new SimpleDateFormat(pattern);
			mapper.getSerializationConfig().setDateFormat(df);
			mapper.getDeserializationConfig().setDateFormat(df);
		}
	}

	/**
	 * String To JSONObject
	 * 
	 * @date:涓婂崍9:50:35
	 */
	public JSONObject stringToJSONObject(String str) throws JSONException {
		JSONObject dataJson = new JSONObject(str);
		return dataJson;
	}

	/**
	 * 鑾峰彇娉涘瀷鐨凜ollection Type
	 * 
	 * @param collectionClass
	 *            娉涘瀷鐨凜ollection
	 * @param elementClasses
	 *            鍏冪礌锟�?
	 * @return JavaType Java绫诲瀷
	 * @since 1.0
	 */
	public JavaType getCollectionType(Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(ArrayList.class,
				elementClasses);
	}

	/**
	 * jsonString杞琹ist
	 * 
	 * @date:涓嬪崍12:26:04
	 */
	@SuppressWarnings("hiding")
	public <Object> Object stringToList(String jsonString,
			Class<?>... elementClasses) throws JsonParseException,
			JsonMappingException, IOException {
		JavaType javaType = getCollectionType(elementClasses);
		return mapper.readValue(jsonString, javaType);
	}

	/**
	 * 鍙栧嚭Mapper鍋氳繘锟�?姝ョ殑璁剧疆鎴栦娇鐢ㄥ叾浠栧簭鍒楀寲API.
	 */
	public ObjectMapper getMapper() {
		return mapper;
	}

}
