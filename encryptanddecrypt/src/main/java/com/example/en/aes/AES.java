package com.example.en.aes;

import android.text.TextUtils;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	/**
	 * 加密
	 *
	 * @param oldcontent
	 *            需要加密的内容
	 * @return
	 */
	public static String encrypt(String oldcontent) {
		String content = getContent(oldcontent);
		try {
			SecretKeySpec key = new SecretKeySpec("aaaaaaaaaa".getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(byteContent);
//			return Base64.encode(result, Base64.DEFAULT); // 加密
			return new String(Base64.encode(result, Base64.DEFAULT));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * @param sSrc:加密字符串
	 * @return 解密数据
	 * @throws Exception
	 */
	public static String decrypt(String sSrc)  {
		if (TextUtils.isEmpty(sSrc)){
			return null;
		}
		try {
			byte[] raw = "aaaaaaaaaa".getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			//先用base64解密
			byte[] encrypted1 = Base64Utils.decode(sSrc);
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original, "utf-8");
				if (TextUtils.isEmpty(originalString)){
					return null;
				}
				originalString = originalString.replace("\0","").trim();
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	/**
	 * 处理待加密字符串，不足16的倍数补足16，汉子加2
	 * @param oldContent
	 * @return
	 */
	public static String getContent(String oldContent) {

		int length = oldContent.length();
		int newlength = length;
		char[] c = oldContent.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (isChinese(c[i])) {
				newlength += 2;
			}
		}
		int temp = newlength % 16;
		StringBuffer buffer = new StringBuffer(oldContent);
		if (length % 16 != 0) {
			for (int i = 0; i < 16 - temp; i++) {
				buffer.append("\0");
			}
		}
		return buffer.toString();

	}

	public static String getNewContent(String oldContent) {
		int length = oldContent.length();
		int temp = length % 16;
		StringBuffer buffer = new StringBuffer(oldContent);
		if (length % 16 != 0) {
			for (int i = 0; i < 16 - temp; i++) {
				buffer.append("\00");
			}
		}
		return buffer.toString();
	}


	/**
	 * 根据Unicode编码判断中文汉字和符号
	 * @param c
	 * @return
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	/**
	 * 完整的判断中文汉字和符号
	 * @param strName
	 * @return
	 */
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

}