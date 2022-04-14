package com.ljming.table.bind;

/**
 * <p>
 * Title:Dictionary
 * </p>
 * <p>
 * Description:字典接口
 * </p>
 *
 * @author 梁敬明
 * @date 2017年10月14日 下午4:28:26
 */
public interface Dictionary {

	/**
	 * 文本转编码
	 * @param type	字典类型
	 * @param text	字典文本
	 * @return		文本编码
	 */
	String textToCode(String type, String text);
	
	/**
	 * 编码转文本
	 * @param type	字典类型
	 * @param code	文本编码
	 * @return		字典文本
	 */
	String codeToText(String type, String code);

}
