package ujs.mlearn.Utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CommonUtil {
	//header 常量定义//
	private static final String ENCODING_PREFIX = "encoding";
	private static final String NOCACHE_PREFIX = "no-cache";
	private static final String ENCODING_DEFAULT = "UTF-8"; //
	private static final boolean NOCACHE_DEFAULT = true;

	//content-type 定义 //
	private static final String TEXT = "text/plain";
	private static final String JSON = "application/json";
	private static final String XML = "text/xml";
	private static final String HTML = "text/html";
	
	public static void render(final HttpServletResponse response,final String contentType, final String content, final String... headers) {
		try {
			//分析headers参数
			String encoding = ENCODING_DEFAULT;
			boolean noCache = NOCACHE_DEFAULT;
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName, NOCACHE_PREFIX)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else
					throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
			}

			//设置headers参数
			String fullContentType = contentType + ";charset=" + encoding;
			response.setContentType(fullContentType);
			if (noCache) {
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
			}

			PrintWriter writer = response.getWriter();
			writer.write(content);
			writer.flush();
			writer.close();
			System.out.println(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 直接输出文本.
	 * @see #render(String, String, String...)
	 */
	public static void renderText(final HttpServletResponse response,final String text, final String... headers) {
		render(response,TEXT, text, headers);
	}
	
	/**
	 * 直接输出JSON.
	 * 
	 * @param string json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final HttpServletResponse response,final String jsonString, final String... headers) {
		render(response,JSON, jsonString, headers);
	}
	
	/**
	 * 直接输出JSON.
	 * 
	 * @param map Map对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	@SuppressWarnings("unchecked")
	public static void renderJson(final HttpServletResponse response,final Map map, final String... headers) {
		String jsonString = JSONObject.fromObject(map).toString();
		render(response,JSON, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param object Java对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final HttpServletResponse response,final Object object, final String... headers) {
		String jsonString = JSONObject.fromObject(object).toString();
		render(response,JSON, jsonString, headers);
	}
	
	/**
	 * 直接输出JSON.
	 * @param response
	 * @param list
	 * @param headers
	 */
	public static void renderJson(final HttpServletResponse response,final List<?> list, final String... headers) {
		String jsonString = JSONArray.fromObject(list).toString();
		render(response,JSON, jsonString, headers);
	}
}
