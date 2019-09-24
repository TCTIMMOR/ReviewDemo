package com.example.demo;

import com.example.demo.Demo.DemoApplication;
import com.example.demo.service.DemoService;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);

    @Test
    public void contextLoads() {
        System.out.println(DemoApplication.replaceNameX("12345"));

        DemoApplication.replacePhoneNumX("15555692255");

        DemoApplication.replacePhoneNumX(null);

        DemoApplication.idCard("123456789987654321");
    }

    /**
     * 给定毫秒数, 输出 x小时x分钟x秒
     */
    @Test
    public void ms2HMs() {
        long s = 7503786 / 1000;
        System.out.println(s);
        long hour = s / (60 * 60);
        long minute = (s - hour * 60 * 60) / 60;
        long seconds = s - hour * 60 * 60 - minute * 60;
        System.out.println("hour: " + hour);
        System.out.println("minute: " + minute);
        System.out.println("seconds: " + seconds);
    }

    /**
     * 毫秒转为时间字符串
     */
    @Test
    public void strTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(1566357736435L));
    }

    /**
     * 生成随机6位验证码
     */
    @Test
    public void vcode() {

        StringBuilder vcode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int random = (int) (Math.random() * 8);
            System.out.println(random);
            vcode.append(random);
        }
        System.out.println(vcode.toString());
    }

    /**
     * 验证日期字符串
     */
    @Test
    public void checkTime() throws Exception {

        String time = "2019-08-26";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println(sdf2.parse(time));

        Long timeMillis =  1567267199000L;
        System.out.println(sdf2.format(timeMillis));

    }

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v          需要四舍五入的数字
     * @param scale      小数点后保留几位
     * @param round_mode 指定的舍入模式
     * @return 以字符串格式返回
     */
    @Test
    public void round() {

        String v = "2.521321";
        int scale = 0;
        int scale2 = 2;
        int round_mode = BigDecimal.ROUND_HALF_UP;

        if (scale < 0) {
            throw new IllegalArgumentException("scale 必须是整数或0");
        }

        BigDecimal b;
        try {
            b = new BigDecimal(v);
        } catch (Exception e) {
            v = null;
            return;
        }

        System.out.println(b.setScale(scale, round_mode));
        System.out.println(b.setScale(scale2, round_mode));
    }

    /**
     * 获取当天是周几
     */
    @Test
    public void day2week() {

        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();

        Date date = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000 * 4);
        System.out.println(date);

        cal.setTime(date);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        System.out.println(w);
        String week = weekDays[w];
        System.out.println("今天是" + week);
    }

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * excel 导入后后读取数据
     * @throws Exception
     */
    @Test
    public void generateCompleteTempList() throws Exception {
        String fileUrl = "classpath:/templates/运输费用模板-更新版.xls";
        Workbook workbook;
        Resource resource = resourceLoader.getResource(fileUrl);
        InputStream inputStream = resource.getInputStream();

        // jxl
        workbook = Workbook.getWorkbook(inputStream);
        Sheet sheet = workbook.getSheet(0);

        int rows = sheet.getRows();
        int columns = sheet.getColumns();
        logger.info("行 = {}, 列 = {}", rows, columns);

        for (int i = 3; i < rows; ++i) {
            try {
                // start provName/cityName/distName
                String startProvName1 = sheet.getCell(1, i).getContents();
                String startProvName2 = sheet.getCell(2, i).getContents();
                String startProvName3 = sheet.getCell(3, i).getContents();
                String startProvName4 = sheet.getCell(4, i).getContents();
                String startProvName5 = sheet.getCell(5, i).getContents();
                String startProvName6 = sheet.getCell(6, i).getContents();
                String startProvName7 = sheet.getCell(7, i).getContents();
                String startProvName8 = sheet.getCell(8, i).getContents();
                String startProvName9 = sheet.getCell(9, i).getContents();
                String startProvName10 = sheet.getCell(10, i).getContents();
                String startProvName11 = sheet.getCell(11, i).getContents();
                String startProvName12 = sheet.getCell(12, i).getContents();
                String startProvName13 = sheet.getCell(13, i).getContents();
                String startProvName14 = sheet.getCell(14, i).getContents();
                String startProvName15 = sheet.getCell(15, i).getContents();
                String startProvName16 = sheet.getCell(16, i).getContents();
                String startProvName17 = sheet.getCell(17, i).getContents();
                String startProvName18 = sheet.getCell(18, i).getContents();
                String startProvName19 = sheet.getCell(19, i).getContents();
                String startProvName20 = sheet.getCell(20, i).getContents();
                String startProvName21 = sheet.getCell(21, i).getContents();
                String startProvName22 = sheet.getCell(22, i).getContents();
                String startProvName23 = sheet.getCell(23, i).getContents();
                String startProvName24 = sheet.getCell(24, i).getContents();
                String startProvName25 = sheet.getCell(25, i).getContents();
                String startProvName26 = sheet.getCell(26, i).getContents();
                String startProvName27 = sheet.getCell(27, i).getContents();
                String startProvName28 = sheet.getCell(28, i).getContents();
                String startProvName29 = sheet.getCell(29, i).getContents();
                String startProvName30 = sheet.getCell(30, i).getContents();

                logger.info("startProvName1:{}", startProvName1);
                logger.info("startProvName2={}", startProvName2);
                logger.info("startProvName3:{}", startProvName3);
                logger.info("startProvName4={}", startProvName4);
                logger.info("startProvName5={}", startProvName5);
                logger.info("startProvName6={}", startProvName6);
                logger.info("startProvName7={}", startProvName7);
                logger.info("startProvName8={}", startProvName8);
                logger.info("startProvName9={}", startProvName9);
                logger.info("startProvName10={}", startProvName10);
                logger.info("startProvName11={}", startProvName11);
                logger.info("startProvName12={}", startProvName12);
                logger.info("startProvName13={}", startProvName13);
                logger.info("startProvName14={}", startProvName14);
                logger.info("startProvName15={}", startProvName15);
                logger.info("startProvName16={}", startProvName16);
                logger.info("startProvName17={}", startProvName17);
                logger.info("startProvName18={}", startProvName18);
                logger.info("startProvName19={}", startProvName19);
                logger.info("startProvName20={}", startProvName20);
                logger.info("startProvName21={}", startProvName21);
                logger.info("startProvName22={}", startProvName22);
                logger.info("startProvName23={}", startProvName23);
                logger.info("startProvName24={}", startProvName24);
                logger.info("startProvName25={}", startProvName25);
                logger.info("startProvName26={}", startProvName26);
                logger.info("startProvName27={}", startProvName27);
                logger.info("startProvName28={}", startProvName28);
                logger.info("startProvName29={}", startProvName29);
                logger.info("startProvName30={}", startProvName30);

            } catch (Exception e) {
                logger.error(e.toString());
            }
        }
    }

    @Test
    public void list2String() {
        Set<String> set = new HashSet<>();
        set.add("整车");
        set.add("零担");
        set.add("汽运");
        set.add("铁路");
        set.add("");
        String join = StringUtils.join(set, ",");
        System.out.println(join);
    }

    /**
     * 判断一个二维数组中是否包含某个整数
     */
    @Test
    public void solution() {
        int target = 8;
        int[][] array = {{1,2,3},{4,5,6},{7,8,9}};

        // 方法一
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] == target) {
                    System.out.println(array[i][j]);
                }
            }
        }

        // 方法二
        int rowCount = array.length;
        int colCount = array[0].length;
        for (int i = rowCount - 1, j = 0; i >= 0 && j < colCount; ) {
            if (target == array[i][j]) {
                System.out.println("i = " + i);
                System.out.println("j = " + j);
                System.out.println(array[i][j]);
                break;
            }

            if (target > array[i][j]) {
                j ++;
                continue;
            }

            if (target < array[i][j]) {
                i -- ;
            }
        }

        for (int i = rowCount - 1, j = 0; i >= 0 && j < colCount;) {
            if (target == array[i][j]) {
                //return true;
            }
            if (target > array[i][j]) {
                j ++;
                continue;
            }
            if (target < array[i][j]) {
                i --;
                continue;
            }
            //return false;
        }
    }

    @Autowired
    private DemoService demoService;

    @Test
    public void testValue() {
        demoService.myDemo();
    }
}
