package com.lw.vcs.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author 练威
 * @version 创建时间：2018-01-07 11:08:46 <br/>
 * @类说明 拼音工具
 */
public class PinYinUtil {

	/**
	 * 汉字转拼音
	 * @param str
	 * @return
	 */
	public static String chinese2pinyin(String str) {
		char[] cl_chars = str.trim().toCharArray();
		String hanyupinyin = "";
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 输出拼音全部小写
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		try {
			for (int i = 0; i < cl_chars.length; i++) {
				if (String.valueOf(cl_chars[i]).matches("[\u4e00-\u9fa5]+")) {// 如果字符是中文,则将中文转为汉语拼音
					hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0];
				} else {// 如果字符不是中文,则不转换
					hanyupinyin += cl_chars[i];
				}
			}

			return hanyupinyin;
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			System.out.println("字符不能转成汉语拼音");
		}

		return null;
	}
}
