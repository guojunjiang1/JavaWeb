package util;

import java.util.UUID;

/**
 * 调用UUID随机字符串工具类
 * 为每一个用户提供一个唯一的激活码
 */
public final class UuidUtil {
	private UuidUtil(){}
	public static String getUuid(){
		return UUID.randomUUID().toString().replace("-","");
	}
}
