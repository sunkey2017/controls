package com.longi.controls.utils;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @CalssName CommonUtil
 * @version 1.0
 * @Author sunke5
 * @Date 2019-11-17 21:43
 */
public class CommonUtil {

    private static final Logger log = Logger.getLogger (CommonUtil.class);


    /**
     * 根据时间类型比较时间大小
     *
     * @param source traget type "YYYY-MM-DD" "yyyyMMdd HH:mm:ss"  类型可自定义
     * @return 0 ：source和traget时间相同
     * 1 ：source在traget之后
     * -1：source比traget之前
     * @throws Exception
     */
    public static int dateCompare(String source, String traget, String type) throws ParseException {
        int ret = 2;
        SimpleDateFormat format = new SimpleDateFormat (type);
        ret = format.parse (source).compareTo (format.parse (traget));
        return ret;
    }

    public static Date timeParse(String date, String pattern) {
        Date dateParsed = new Date ();
        try {
            dateParsed = new SimpleDateFormat (pattern).parse (date);
        } catch (ParseException e) {
            e.printStackTrace ();
        }
        return dateParsed;
    }

    public static String timeFormat(Date date, String pattern) {
        String formatTime = new SimpleDateFormat (pattern).format (date);
        return formatTime;
    }

    public static String getCurrDateForAccess() {
        Date date = new Date ();
        SimpleDateFormat sdf = new SimpleDateFormat ("dd.MM.yyyy-HH:mm:ss");
        return sdf.format (date);
    }

    public static String getCurrDate() {
        Date date = new Date ();
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        return sdf.format (date);
    }

    /**
     * 获取当前时间之前或之后秒钟 second
     */
    public static String getTimeByMinute(int second) {
        Calendar calendar = Calendar.getInstance ();
        calendar.add (Calendar.SECOND, second);
        return new SimpleDateFormat ("dd.MM.yyyy-HH:mm:ss").format (calendar.getTime ());

    }

    /**
     * 获取当前时间之前或之后秒钟 second
     */
    public static String getTimeByMinute(String dateStr, String urFormat, int second) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat ("dd.MM.yyyy-HH:mm:ss");
        Date date = sdf.parse (dateStr);
        Calendar calendar = Calendar.getInstance ();
        calendar.setTime (date);
        calendar.add (Calendar.SECOND, second);
        return new SimpleDateFormat (urFormat).format (calendar.getTime ());
    }

    public static String getHourMinuteByStr(String urFormat, String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat ("dd.MM.yyyy-HH:mm:ss");
        Date date = sdf.parse (dateStr);
        return new SimpleDateFormat (urFormat).format (date);
    }

    /**
     * @return java.util.Date
     * @Description 转化非正常格式时间为正常格式
     * @Date 11:52 2019-7-23
     * @Param [dateStr, originalFormat]
     **/
    public static Date str2Date(String dateStr, String originalFormat) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat (originalFormat);
        SimpleDateFormat sdf2 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        return sdf2.parse (sdf2.format (sdf1.parse (dateStr)));
    }

    /**
     * 获取当前时间之前或之后的几天 day
     */
    public static String getDateBefore(int days) {
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
        Date prevDay = null;
        try {
            Date day = sdf.parse (sdf.format (new Date ()));
            long ms = day.getTime () - days * 24 * 3600 * 1000L;
            prevDay = new Date (ms);
        } catch (ParseException e) {
            log.error ("get yesterday error：" + e);
            e.printStackTrace ();
        }
        return sdf.format (prevDay);

    }

    /**
     * 判断time1在time2之后
     **/
    public static boolean compareTime(String time1, String time2) {
        SimpleDateFormat sdf = new SimpleDateFormat ("dd.MM.yyyy-HH:mm:ss");
        //将字符串形式的时间转化为Date类型的时间
        Date a = null;
        Date b = null;
        try {
            a = sdf.parse (time1);
            b = sdf.parse (time2);
        } catch (ParseException e) {
            log.error ("对比时间报错:" + CommonUtil.getExceptionDetail(e));
        }

        if (a.after (b)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return void
     * @Description 复制文件
     * @Date 17:30 2019-7-9
     * @Param [sourceFile, targetFile]
     **/
    public static void copyFile(File sourceFile, File targetFile)
            throws IOException {
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream (sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream (input);

        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream (targetFile);
        BufferedOutputStream outBuff = new BufferedOutputStream (output);

        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read (b)) != -1) {
            outBuff.write (b, 0, len);
        }
        // 刷新此缓冲的输出流
        outBuff.flush ();

        //关闭流
        inBuff.close ();
        outBuff.close ();
        output.close ();
        input.close ();
    }



    public static void delFile(String filePath) {
        File delFile = new File (filePath);
        boolean delResult = deleteFile (delFile);
        log.info ("删除已经读取的文件,文件路径： " + filePath + ",完成情况：" + delResult + "，当前时间：" + CommonUtil.getCurrDate ());
    }

    /**
     * 删除指定路径文件
     *
     * @param file 文件
     * @return
     */
    public static boolean deleteFile(File file) {
        if (!file.exists ()) {
            return false;
        }

        if (file.isFile ()) {
            log.info ("删除文件:" + file.getName () + ",当前时间：" + CommonUtil.getCurrDate ());
            file.deleteOnExit ();
            return file.delete ();
        } else {
            File[] files = file.listFiles ();
            for (File f : files) {
                deleteFile (f);
            }
            //return file.delete();
            return true;
        }
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @Description 获取制定文件夹下修改时间最新的2个文件
     * @Date 17:17 2019-7-16
     * @Param [fileDir]
     **/
    public static Map<String, String> getLatestFileName(String fileDir, String machineIP, String machineId, String[] mailGroup) {
        log.info ("获取当前时间，路径:" + fileDir + ",最新的数据文件");
        Map<Long, String> fileMap = new HashMap<> ();
        List<Long> modifyList = new ArrayList<> ();
        Map<String, String> resultMap = new HashMap<> ();

        try {
            File file = new File (fileDir);
            File[] files = file.listFiles ();// 获取目录下的所有文件或文件夹
            if (files == null) {// 如果目录为空，直接退出
                return null;
            }
            // 遍历，目录下的所有文件
            for (File f : files) {
                if (f.isFile ()) {
                    if (f.getName ().indexOf (".mdb") > 0) {
                        modifyList.add (f.lastModified ());
                        fileMap.put (f.lastModified (), f.getName ());
                    }
                } else if (f.isDirectory ()) {
                    continue;
                    //readFile(f.getAbsolutePath());
                }
            }
            String latestFileName = fileMap.get (Collections.max (modifyList));
            resultMap.put ("1", latestFileName);
            log.info ("获取当前时间最新的数据文件1：" + latestFileName);
            modifyList.remove (Collections.max (modifyList));
            if (modifyList.size () > 0) {
                String secondFileName = fileMap.get (Collections.max (modifyList));
                resultMap.put ("2", secondFileName);
                log.info ("获取当前时间最新的数据文件2：" + secondFileName);
            }
        } catch (NullPointerException e) {
            log.info ("连接access服务器报错 ：" + e);
            CommonUtil.sendMail (machineIP,mailGroup,machineId,"无法连接，或数据格式有误");
        }
        return resultMap;
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length (); i++) {
            if (!Character.isDigit (str.charAt (i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * 获取异常详细信息，知道出了什么错，错在哪个类的第几行 .
     *
     * @param ex
     * @return
     */
    public static String getExceptionDetail(Exception ex) {
        String ret = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream ();
            PrintStream pout = new PrintStream (out);
            ex.printStackTrace (pout);
            ret = new String (out.toByteArray ());
            pout.close ();
            out.close ();
        } catch (Exception e) {
            log.error (e);
        }
        return ret;
    }

    public static void sendMail(String IP,String[] mailGroup,String machineId,String msg){
        MsgUtil msgUtil = new MsgUtil();
        msgUtil.setSubject("[Offline]" + IP + " can not connection.");
        msgUtil.setToAddress(mailGroup);
        msgUtil.setContent("台机："+machineId+" ，IP ： "+IP + " ，"+msg+"，请及时人工检查处理。");
        msgUtil.sendHtmlMail(msgUtil);
    }
    public static void sendMail2(String[] mailGroup,String msg){
        MsgUtil msgUtil = new MsgUtil();
        msgUtil.setSubject("[oracle Disconnection]   oracle can not connection.");
        msgUtil.setToAddress(mailGroup);
        msgUtil.setContent(msg);
        msgUtil.sendHtmlMail(msgUtil);
    }

    public static void closeResource(ResultSet rs, PreparedStatement psm, Connection con) throws SQLException {
        if(null != rs){
            rs.close ();
        }
        if(null != psm){
            psm.close ();
        }
        if(null != con){
            con.close ();
        }
    }

    /**
     * @Author sunke5
     * @Description  Gson深拷贝
     * @Date 14:26 2019-11-18
     * @Param [original]
     * @return java.util.List<T>
     **/
    public static <T> List<T> clone(List<T> original) {
        Gson gson = new Gson();
        // Convert the Set to JSON String
        String jsonString = gson.toJson(original);
        // Convert the JSON String back to new Set Object and return it
        List<T> copy = gson.fromJson(jsonString, List.class);
        return copy;
    }

}
