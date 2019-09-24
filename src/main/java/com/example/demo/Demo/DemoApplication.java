package com.example.demo.Demo;

import com.example.demo.annotation.LogParamAnno;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * 将姓名转为 姓**  例如: 韩梅梅 -> 韩**  | 李雷 -> 李*
     * @param name
     * @return
     */
    public static String replaceNameX(String name){

        if (name == null || name.length() == 0) {
            return "";
        }

        String reg = ".{1}";
        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(name);

        int i = 0;
        while(m.find()){
            i++;
            if(i==1)
                continue;
            m.appendReplacement(sb, "*");
        }

        m.appendTail(sb);

        return sb.toString();
    }

    /**
     * 将手机号(11位) 中间四位替换为 ****  例如: 12345678901 -> 123****8901
     * @param phoneNum
     * @return
     */
    public static String replacePhoneNumX(String phoneNum) {

        if (phoneNum == null || phoneNum.length() == 0) {
            return "";
        }

        String phoneNumber = phoneNum.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");

        System.out.println("原号码:"+ phoneNum + ", 加密后:" + phoneNumber);
        System.out.println("============");
        return phoneNumber;
    }

    /**
     * 将身份证号中间替换为**** 例如:123456789987654321 -> 1234****4321
     * @param idCard
     */
    @LogParamAnno
    public static void idCard(String idCard) {

        //$1、$2、……表示正则表达式里面第一个、第二个、……括号里面的匹配内容
        String idCardNumber = idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})","$1****$2");
        System.out.println("身份证号长度："+idCard.length());
        System.out.println("正则idCard中4*：" + idCardNumber);
    }

}
