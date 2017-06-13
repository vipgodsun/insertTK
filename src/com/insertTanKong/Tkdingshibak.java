package com.insertTanKong;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Tkdingshibak {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	   //程序开始运行
       System.out.println("程序开始运行");
       //获取时间
       DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmmss");
	   Date dat = null;
       Calendar cd = Calendar.getInstance();
       Calendar nowtime = Calendar.getInstance();
       //开始时间,注意月份0代表1月
       cd.set(2017,5,7,0,0,0);
       //截止时间
       nowtime.set(2017,5,9,0,0,0);
       //连接数据库
       Connection con = null;// 创建一个数据库连接
	   PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
	   ResultSet result = null;// 创建一个结果集对象
	   try
	   {
	       Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
	       System.out.println("开始尝试连接数据库！");
	       String url = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST =)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = mydb)))";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
	       String user = "";// 用户名,系统默认的账户名
	       String password = "";// 你安装时选设置的密码
	       con = DriverManager.getConnection(url, user, password);// 获取连接
	       System.out.println("连接成功！");
	        //保存当前自动提交模式
	       Statement stmt = con.createStatement(); 
	       ArrayList<String> dataList = new ArrayList<String>();
	       while(cd.before(nowtime)){
	    	   cd.add(Calendar.HOUR, 24);
	    	   String str = (dateFormat2.format(cd.getTime()));  
	    	    System.out.println(str); 
	    	    try{
	    	    	dataList.addAll(testCimiss(str));
	    	    }
	    	    finally{
	    	    	continue;
	    	    }
	       }
	        //ArrayList<String> dataList = testCimiss();
	        
	        for(int i=0;i<dataList.size();i++){
	        	stmt.addBatch(dataList.get(i));
	        }
	        stmt.executeBatch(); 
	     
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	    finally
	    {
	        try
	        {
	            // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
	            // 注意关闭的顺序，最后使用的最先关闭
	            if (result != null)
	                result.close();
	            if (pre != null)
	                pre.close();
	            if (con != null)
	                con.close();
	            System.out.println("数据库连接已关闭！");
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
     
	}
	
	
	/**
	 * 一个非常标准的连接Oracle数据库的示例代码
	 */
	public  static void insertOracle(ArrayList<String> dataList)
	{
	    Connection con = null;// 创建一个数据库连接
	    PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
	    ResultSet result = null;// 创建一个结果集对象
	    try
	    {
	        Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
	        System.out.println("开始尝试连接数据库！");
	        String url = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST =)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = mydb)))";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
	        String user = "";// 用户名,系统默认的账户名
	        String password = "";// 你安装时选设置的密码
	        con = DriverManager.getConnection(url, user, password);// 获取连接
	        System.out.println("连接成功！");
	         //保存当前自动提交模式
	        Statement stmt = con.createStatement(); 
	        for(int i=0;i<dataList.size();i++){
	        	stmt.addBatch(dataList.get(i));
	        }
	        stmt.executeBatch(); 
	     
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	    finally
	    {
	        try
	        {
	            // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
	            // 注意关闭的顺序，最后使用的最先关闭
	            if (result != null)
	                result.close();
	            if (pre != null)
	                pre.close();
	            if (con != null)
	                con.close();
	            System.out.println("数据库连接已关闭！");
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
	}
	
	
	public  static ArrayList<String> testCimiss(String time) {
	     /*
	       * main方法
	       * 如：按时间检索地面数据要素 getSurfEleByTime
	       */
	        /* 1. 调用方法的参数定义，并赋值 */
		
	        String params ="userId=" /* 1.1 用户名&密码 */
	            + "&pwd="
	            + "&interfaceId=getUparEleByTimeAndStaID" /* 1.2 接口ID */        
	            + "&dataCode=UPAR_CHN_MUL_FTM" /* 1.3 必选参数（按需加可选参数） */ //资料：中国地面逐小时
	           // + "&elements=Station_Name,Province,City,Cnty,Town,Datetime,REP_CORR_ID,Year_Data,Mon_Data,Day_Data,Hour_Data,Station_Id_C,Station_Id_d,Lat,Lon,Alti,V07030,PRS_Sensor_Alti,HEITH_BALLON,Nation_Code,Year,Mon,Day,Hour,Min,Second,Sensor_type,RSON_Type,SIR_Corr,SYSTAT,SST,CLO_COV_LM,CLO_Height_LoM,CLO_Fome_Low,CLO_FOME_MID,CLO_Fome_High,DATA_CATE,EVSS,Time_Dev_WQ,Lat_Dev,Lon_Dev,PRS_HWC,GPH,Heigh_Alti,TEM,DPT,DTD,WIN_D,WIN_S,WIN_SHE_B1Km,WIN_She_A1Km,Q_Lat_Dev,Q_Lon_Dev,Q_PRS_HWC,Q_GPH,Q_Heigh_Alti,Q_TEM,Q_DPT,Q_DTD,Q_WIN_D,Q_WIN_S,Q_WIN_SHE_B1Km,Q_WIN_She_A1Km" //检索要素：站号、站名、小时降水、气压、相对湿度、能见度、2分钟平均风速、2分钟风向
	            + "&elements=Station_Name,Province,City,Cnty,Town,Datetime,REP_CORR_ID,Year_Data,Mon_Data,Day_Data,Hour_Data,Station_Id_C,Station_Id_d,Lat,Lon,Alti,V07030,PRS_Sensor_Alti,HEITH_BALLON,Nation_Code,Year,Mon,Day,Hour,Min,Second,Sensor_type,RSON_Type,SIR_Corr,SYSTAT,SST,CLO_COV_LM,CLO_Height_LoM,CLO_Fome_Low,CLO_FOME_MID,CLO_Fome_High,DATA_CATE,EVSS,Time_Dev_WQ,Lat_Dev,Lon_Dev,PRS_HWC,GPH,Heigh_Alti,TEM,DPT,DTD,WIN_D,WIN_S,WIN_SHE_B1Km,WIN_She_A1Km"
	            + "&times=" +time//检索时间
	            + "&staIds=54662" //检索时间
	            + "&orderby=Station_ID_C:ASC"; //排序：按照站号从小到大
	            //+ "&limitCnt=10"  //返回最多记录数：10
	            //+ "&dataFormat=json" ; /* 1.4 序列化格式 */
	                   
	        /* 2. 调用接口 */
	        WebsUtil websUtil = new WebsUtil() ;
	       // String rstData = websUtil.getWsString( "callAPI_to_serializedStr", params ) ;
	        String[][] rstData = websUtil.getWsArray( "callAPI_to_Array", params ) ;
	        /* 3.  输出结果 */
//	        FormatUtil formatUtil = new FormatUtil() ;
//	        formatUtil.outputRstHtml( rstData ) ;
	        //System.out.println(rstData);
	        ArrayList<String> getList = new ArrayList<String>();
	        getList = websUtil.outputRst(rstData) ;
	        return getList;
	       // insertOracle(getList);
	      
	}
}
