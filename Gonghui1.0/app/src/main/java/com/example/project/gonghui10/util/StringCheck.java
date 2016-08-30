package com.example.project.gonghui10.util;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Meng on 2016/8/5.
 */
public class StringCheck {
    // 验证昵称
    private boolean verifyNickname(EditText edt_username) {
        String nickname = edt_username.getText().toString();
        if (nickname == null || nickname.length() == 0) {
            edt_username.setError("不能为空");
            return false;
        }
        int len = 0;
        char[] nickchar = nickname.toCharArray();
        for (int i = 0; i < nickchar.length; i++) {
            if (isChinese(nickchar[i])) {
                len += 2;
            } else {
                len += 1;
            }
        }
        if (len < 4 || len > 15) {
            edt_username.setError("正确的昵称应该为\n1、4-15个字符\n2、2-7个汉字\n3、不能是邮箱和手机号");
            return false;
        }
        return true;
    }

    private boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }


    // 判断是否为手机号
    private boolean isPhone(String inputText) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(inputText);
        return m.matches();
    }

    // 判断格式是否为email
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

}
