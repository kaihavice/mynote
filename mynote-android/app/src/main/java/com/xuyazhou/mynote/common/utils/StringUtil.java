package com.xuyazhou.mynote.common.utils;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xuyazhou.mynote.model.db.CheckListItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

/**
 * Created by dss on 2014/12/4
 */
public class StringUtil {

    private static final String PATTERN_ALPHABETIC_OR_NUMBERIC = "[A-Za-z0-9]*";
    private static final String PATTERN_NUMBERIC = "\\d*\\.{0,1}\\d*";
    private static String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    private static Pattern p = Pattern.compile(str);

    /**
     * 合并字符列表
     *
     * @param separator
     * @param data
     * @return
     */
    public static String implode(String separator, String... data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length - 1; i++) {
            // data.length - 1 => to not add separator at the end
            if (!data[i].matches(" *")) {// empty string are ""; " "; " "; and
                // so on
                sb.append(data[i]);
                sb.append(separator);
            }
        }
        sb.append(data[data.length - 1].trim());
        return sb.toString();
    }

    public static String appendCheckString(ArrayList<CheckListItem> checkList) {

        SpannableStringBuilder sb = new SpannableStringBuilder();
        for (int i = 0; i < checkList.size(); i++) {

            int postion = 0;

            if (checkList.get(i).isChecked()) {
                postion = sb.length();

            }

            if (i == 0) {
                sb.replace(0, 0, " · ");
            } else {
                sb.replace(sb.length(), sb.length(), " · ");
            }


            sb.append(checkList.get(i).getTitle());

            if (checkList.get(i).isChecked()) {

                sb.setSpan(new ForegroundColorSpan(Color.parseColor("#d1d5d4")), postion, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            sb.append("\n");


        }

        return sb.toString();
    }

    public static String appendCheckText(String content, ArrayList<CheckListItem> checkList) {

        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(content).append("\n");
        for (int i = 0; i < checkList.size(); i++) {


            sb.append(checkList.get(i).getTitle());

            sb.append("\n");


        }

        return sb.toString();
    }


    public static int isNumericNow(String str) {

        String[] number = str.split("\\.");

        if (Long.valueOf(number[1]) == 0) {
            return 0;
        } else if (number[1].length() == 1) {
            return 1;
        } else if (number[1].length() == 2) {
            return 2;
        }

        return 0;


    }


    public static String getNoteTilte(String content) {
        for (int i = 0; i < content.length(); i++) {


            if ('\n' == content.charAt(i)) {
                content.substring(0, i - 1);
                return content;
            }
        }
        return content;
    }

    /**
     * 字符串是否由字面或数字组成
     *
     * @param
     * @return
     */
    public static boolean isAlphabeticOrNumberic(EditText editText, int msg) {
        if (Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$").matcher(editText.getText().toString().trim())
                .matches()) {
            return true;
        } else {
            ShowToast.Short(editText.getContext(), msg);
            return false;
        }

    }

    public static boolean passwordFormat(String str) {
        return Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$").matcher(str)
                .matches();
    }

    public static boolean passwordFormat2(String str) {
        return Pattern.compile("^.*(?=.*?[A-Z])(?=.*?[a-z])[/S]*$").matcher(str)
                .matches();
    }

    @NonNull
    public static Boolean passwordFormat2(EditText editText, TextView errorTips) {

        if (Pattern.compile(".*[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]").matcher(editText.getText().toString())
                .matches()) {
            errorTips.setVisibility(View.GONE);
            return true;
        } else {
            errorTips.setVisibility(View.VISIBLE);
            errorTips.setText("密码必须字母和数字的组合");
            return false;
        }
    }

    /**
     * 字符串是否是数组
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        return Pattern.compile(PATTERN_NUMBERIC).matcher(str).matches();
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 判断对象是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(final Object str) {
        return (str == null || str.toString().length() == 0);
    }

    /**
     * 判断一组字符串是否有一个为空
     *
     * @param strs
     * @return
     */
    public static boolean isNullOrEmpty(final String... strs) {
        if (strs == null || strs.length == 0) {
            return true;
        }
        for (String str : strs) {
            if (str == null || str.length() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断子字符串是否有出现在指定字符串中
     *
     * @param str
     * @param c
     * @return
     */
    public static boolean find(String str, String c) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        return str.indexOf(c) > -1;
    }

    public static boolean findIgnoreCase(String str, String c) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        return str.toLowerCase().indexOf(c.toLowerCase()) > -1;
    }

    /**
     * 比较两个字符串是否相
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == str2)
            return true;

        if (str1 == null)
            str1 = "";
        return str1.equals(str2);
    }

    /**
     * 拼接字符串
     *
     * @param strs
     * @return
     */
    public static String concat(String... strs) {
        StringBuffer result = new StringBuffer();
        if (strs != null) {
            for (String str : strs) {
                if (str != null)
                    result.append(str);
            }
        }
        return result.toString();
    }

    /**
     * Helper function for making null strings safe for comparisons, etc.
     *
     * @return (s == null) ? "" : s;
     */
    public static String makeSafe(String s) {
        return (s == null) ? "" : s;
    }

    public static final String EMPTY = "";

    /**
     * 去除字符串首部和尾部的空白字符，返回处理后字符串
     *
     * @param str
     */
    public static String trim(String str) {
        return str == null ? EMPTY : str.trim();
    }

    // 判断手机格式是否正确

    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern

                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

    // 判断email格式是否正确

    public static boolean isEmail(String email) {

        Matcher m = p.matcher(email);

        return m.matches();

    }

    @NonNull
    public static Boolean checkIsNotEmpty(AppCompatEditText editText, int msg) {
        if (isNotEmpty(editText.getText().toString().trim())) {
            return true;
        } else {
            ShowToast.Short(editText.getContext(), msg);
            return false;
        }
    }

    @NonNull
    public static Boolean checkIsNotEmpty(EditText editText) {
        return isNotEmpty(editText.getText().toString().trim());
    }

    @NonNull
    public static Boolean checkIsNotEmpty(EditText editText, TextView errorTips, String content) {

        if (isNotEmpty(editText.getText().toString().trim())) {
            errorTips.setVisibility(View.INVISIBLE);
            return true;
        } else {
            errorTips.setVisibility(View.VISIBLE);
            errorTips.setText(content + "为空");
            return false;
        }
    }

    @NonNull
    public static Boolean checkIsNotEmpty(AppCompatEditText editText, String content) {

        if (isNotEmpty(editText.getText().toString().trim())) {

            return true;
        } else {

            ShowToast.Short(editText.getContext(), content + "为空");
            return false;
        }
    }

    @NonNull
    public static Boolean checkIsNotEmpty(EditText editText, String content) {

        if (isNotEmpty(editText.getText().toString().trim())) {

            return true;
        } else {

            ShowToast.Short(editText.getContext(), content + "为空");
            return false;
        }
    }

    @NonNull
    public static Boolean checkIsNotEmpty(TextView editText, TextView errorTips, String content) {

        if (isNotEmpty(editText.getText().toString().trim())) {
            errorTips.setVisibility(View.INVISIBLE);
            return true;
        } else {
            errorTips.setVisibility(View.VISIBLE);
            errorTips.setText(content + "为空");
            return false;
        }
    }

    @NonNull
    public static Boolean checkIsCheck(CheckBox checkbox, int msg) {
        if (checkbox.isChecked()) {
            return true;
        } else {
            ShowToast.Short(checkbox.getContext(), msg);
            return false;
        }
    }

    @NonNull
    public static Boolean checkLenght(EditText editText, long length, int resid) {

        if (editText.length() == length) {
            return true;
        } else {
            ShowToast.Short(editText.getContext(), resid);
            return false;
        }
    }

    @NonNull
    public static Boolean checkBigLenght(EditText editText, long length) {

        if (editText.length() > length - 1 && editText.length() < 17) {
            return true;
        } else {

            ShowToast.Short(editText.getContext(), "长度必须是6-15个字符");
            return false;
        }
    }

    @NonNull
    public static Boolean checkNumberLenght(EditText editText, TextView errorTips) {

        if (editText.length() == 11) {
            errorTips.setVisibility(View.INVISIBLE);
            return true;
        } else {
            errorTips.setVisibility(View.VISIBLE);
            errorTips.setText("手机号码长度不对");
            return false;
        }
    }

    @NonNull
    public static Boolean checkIdNumberLenght(EditText editText, TextView errorTips) {

        if (editText.length() == 18) {
            errorTips.setVisibility(View.INVISIBLE);
            return true;
        } else {
            errorTips.setVisibility(View.VISIBLE);
            errorTips.setText("身份证号码长度不对");
            return false;
        }
    }

    @NonNull
    public static Boolean checkIsEuql(EditText editText, TextView errorTips) {

        if (editText.length() == 6) {
            errorTips.setVisibility(View.INVISIBLE);
            return true;
        } else {
            errorTips.setVisibility(View.VISIBLE);
            errorTips.setText("长度必须是6数字");
            return false;
        }
    }

    @NonNull
    public static Boolean checkIsEqual(EditText editText, EditText editText2, int msg) {
        if (equals(editText.getText().toString().trim(),
                editText2.getText().toString().trim())) {
            return true;
        } else {
            ShowToast.Short(editText.getContext(), msg);
            return false;
        }
    }

    @NonNull
    public static Boolean checkIsEqual(EditText editText, EditText editText2) {
        return equals(editText.getText().toString().trim(),
                editText2.getText().toString().trim());
    }

    @Nonnull
    public static Boolean ForamtError(EditText editText, String msg) {
        if (isEmail(editText.getText().toString().trim())) {
            return true;
        } else {
            ShowToast.Short(editText.getContext(), msg);
            return false;
        }

    }


    public static String checkUrl(List<String> urlList) {
        String realUrl = null;
        for (String url : urlList) {
            SpannableString s = new SpannableString(url);

            Pattern p = Pattern.compile("ppyun://");

            Matcher m = p.matcher(s);

            if (m.find()) {
                int start = m.start();
                int end = m.end();
                realUrl = url.substring(end, url.length());
                return realUrl;
            } else {
                realUrl = url;
            }

        }

        return realUrl;
    }

    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });

    }


    public static String replaceNumber(String src) {

        String star = "";
        for (int i = 3; i < 7; i++) {
            star += "*";
        }

        return src.substring(0, 3) + star + src.substring(7, 11);
    }

    public static String repaceE(Double src) {
        if (src == null) {
            return "0.00";
        } else {
            DecimalFormat df = new DecimalFormat("0.00");

            return df.format(src) + "";
        }
    }

    public static String formatTosepara(Double src) {
        if (src == null) {
            return "0.00";
        } else {
            DecimalFormat df = new DecimalFormat("#,###");

            return df.format(src) + "";
        }
    }

    public static String repaceEE(Double src) {
        if (src == null) {
            return "0";
        } else {
            DecimalFormat df = new DecimalFormat("0");

            return df.format(src) + "";
        }
    }

    public static String repaceEEE(Double src) {
        if (src == null) {
            return "0.0";
        } else {
            DecimalFormat df = new DecimalFormat("0");

            return df.format(src) + "";
        }
    }


    //根据指定长度生成字母和数字的随机数
    //0~9的ASCII为48~57
    //A~Z的ASCII为65~90
    //a~z的ASCI为97~122

    public static String createRandomCharData(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();//随机用以下三个随机生成器
        Random randdata = new Random();
        int data = 0;
        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(3);
            //目的是随机选择生成数字，大小写字母
            switch (index) {
                case 0:
                    data = randdata.nextInt(10);//仅仅会生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data = randdata.nextInt(26) + 65;//保证只会产生65~90之间的整数
                    sb.append((char) data);
                    break;
                case 2:
                    data = randdata.nextInt(26) + 97;//保证只会产生97~122之间的整数
                    sb.append((char) data);
                    break;
            }
        }
        return sb.toString();
    }

    public static String asciiOrder(String str) {
        char[] ch = str.toCharArray();

        Arrays.sort(ch);

        return String.valueOf(ch);
    }

}
