package the.flash.redisClient;

import org.apache.commons.lang3.StringUtils;

public class RedisUtils {

	public static final String ROW_START = "*";
	public static final String COMMOND_END_STR = "\r\n";
	public static final String SUBCOMMOND_START = "$";
	public static final String SERVER_INFO_START = "#";
	public static final String SELECT_DB_COMMOND = "select";
	public static final String AUTH_DB_COMMOND = "auth";

	public static String getCommond(String commond) {
		String[] cmds = StringUtils.split(commond);
		int row = cmds.length;
		StringBuffer sb = new StringBuffer();
		// 先计算命令行数:*[命令行数]\r\n
		String start = String.format(ROW_START + "%s" + COMMOND_END_STR, row);
		sb.append(start);
		for (int i = 0; i < row; i++) {
			// 处理每一条命令:$[命令长度]\r\n
			sb.append(String.format(SUBCOMMOND_START + "%s" + COMMOND_END_STR + "%s" + COMMOND_END_STR,
					cmds[i].length(), cmds[i]));
		}
		return sb.toString();
	}


}
