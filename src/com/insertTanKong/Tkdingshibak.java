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
	   //����ʼ����
       System.out.println("����ʼ����");
       //��ȡʱ��
       DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmmss");
	   Date dat = null;
       Calendar cd = Calendar.getInstance();
       Calendar nowtime = Calendar.getInstance();
       //��ʼʱ��,ע���·�0����1��
       cd.set(2017,5,7,0,0,0);
       //��ֹʱ��
       nowtime.set(2017,5,9,0,0,0);
       //�������ݿ�
       Connection con = null;// ����һ�����ݿ�����
	   PreparedStatement pre = null;// ����Ԥ����������һ�㶼�������������Statement
	   ResultSet result = null;// ����һ�����������
	   try
	   {
	       Class.forName("oracle.jdbc.driver.OracleDriver");// ����Oracle��������
	       System.out.println("��ʼ�����������ݿ⣡");
	       String url = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST =172.19.38.9)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = mydb)))";// 127.0.0.1�Ǳ�����ַ��XE�Ǿ����Oracle��Ĭ�����ݿ���
	       String user = "miss";// �û���,ϵͳĬ�ϵ��˻���
	       String password = "miss1";// �㰲װʱѡ���õ�����
	       con = DriverManager.getConnection(url, user, password);// ��ȡ����
	       System.out.println("172.19.38.9���ӳɹ���");
	        //���浱ǰ�Զ��ύģʽ
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
	            // ��һ������ļ�������رգ���Ϊ���رյĻ���Ӱ�����ܡ�����ռ����Դ
	            // ע��رյ�˳�����ʹ�õ����ȹر�
	            if (result != null)
	                result.close();
	            if (pre != null)
	                pre.close();
	            if (con != null)
	                con.close();
	            System.out.println("���ݿ������ѹرգ�");
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
     
	}
	
	
	/**
	 * һ���ǳ���׼������Oracle���ݿ��ʾ������
	 */
	public  static void insertOracle(ArrayList<String> dataList)
	{
	    Connection con = null;// ����һ�����ݿ�����
	    PreparedStatement pre = null;// ����Ԥ����������һ�㶼�������������Statement
	    ResultSet result = null;// ����һ�����������
	    try
	    {
	        Class.forName("oracle.jdbc.driver.OracleDriver");// ����Oracle��������
	        System.out.println("��ʼ�����������ݿ⣡");
	        String url = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST =172.19.38.9)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = mydb)))";// 127.0.0.1�Ǳ�����ַ��XE�Ǿ����Oracle��Ĭ�����ݿ���
	        String user = "miss";// �û���,ϵͳĬ�ϵ��˻���
	        String password = "miss1";// �㰲װʱѡ���õ�����
	        con = DriverManager.getConnection(url, user, password);// ��ȡ����
	        System.out.println("���ӳɹ���");
	         //���浱ǰ�Զ��ύģʽ
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
	            // ��һ������ļ�������رգ���Ϊ���رյĻ���Ӱ�����ܡ�����ռ����Դ
	            // ע��رյ�˳�����ʹ�õ����ȹر�
	            if (result != null)
	                result.close();
	            if (pre != null)
	                pre.close();
	            if (con != null)
	                con.close();
	            System.out.println("���ݿ������ѹرգ�");
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
	}
	
	
	public  static ArrayList<String> testCimiss(String time) {
	     /*
	       * main����
	       * �磺��ʱ�������������Ҫ�� getSurfEleByTime
	       */
	        /* 1. ���÷����Ĳ������壬����ֵ */
		
	        String params ="userId=BCSY_LNDL_qxxx" /* 1.1 �û���&���� */
	            + "&pwd=dalian82637349"
	            + "&interfaceId=getUparEleByTimeAndStaID" /* 1.2 �ӿ�ID */        
	            + "&dataCode=UPAR_CHN_MUL_FTM" /* 1.3 ��ѡ����������ӿ�ѡ������ */ //���ϣ��й�������Сʱ
	           // + "&elements=Station_Name,Province,City,Cnty,Town,Datetime,REP_CORR_ID,Year_Data,Mon_Data,Day_Data,Hour_Data,Station_Id_C,Station_Id_d,Lat,Lon,Alti,V07030,PRS_Sensor_Alti,HEITH_BALLON,Nation_Code,Year,Mon,Day,Hour,Min,Second,Sensor_type,RSON_Type,SIR_Corr,SYSTAT,SST,CLO_COV_LM,CLO_Height_LoM,CLO_Fome_Low,CLO_FOME_MID,CLO_Fome_High,DATA_CATE,EVSS,Time_Dev_WQ,Lat_Dev,Lon_Dev,PRS_HWC,GPH,Heigh_Alti,TEM,DPT,DTD,WIN_D,WIN_S,WIN_SHE_B1Km,WIN_She_A1Km,Q_Lat_Dev,Q_Lon_Dev,Q_PRS_HWC,Q_GPH,Q_Heigh_Alti,Q_TEM,Q_DPT,Q_DTD,Q_WIN_D,Q_WIN_S,Q_WIN_SHE_B1Km,Q_WIN_She_A1Km" //����Ҫ�أ�վ�š�վ����Сʱ��ˮ����ѹ�����ʪ�ȡ��ܼ��ȡ�2����ƽ�����١�2���ӷ���
	            + "&elements=Station_Name,Province,City,Cnty,Town,Datetime,REP_CORR_ID,Year_Data,Mon_Data,Day_Data,Hour_Data,Station_Id_C,Station_Id_d,Lat,Lon,Alti,V07030,PRS_Sensor_Alti,HEITH_BALLON,Nation_Code,Year,Mon,Day,Hour,Min,Second,Sensor_type,RSON_Type,SIR_Corr,SYSTAT,SST,CLO_COV_LM,CLO_Height_LoM,CLO_Fome_Low,CLO_FOME_MID,CLO_Fome_High,DATA_CATE,EVSS,Time_Dev_WQ,Lat_Dev,Lon_Dev,PRS_HWC,GPH,Heigh_Alti,TEM,DPT,DTD,WIN_D,WIN_S,WIN_SHE_B1Km,WIN_She_A1Km"
	            + "&times=" +time//����ʱ��
	            + "&staIds=54662" //����ʱ��
	            + "&orderby=Station_ID_C:ASC"; //���򣺰���վ�Ŵ�С����
	            //+ "&limitCnt=10"  //��������¼����10
	            //+ "&dataFormat=json" ; /* 1.4 ���л���ʽ */
	                   
	        /* 2. ���ýӿ� */
	        WebsUtil websUtil = new WebsUtil() ;
	       // String rstData = websUtil.getWsString( "callAPI_to_serializedStr", params ) ;
	        String[][] rstData = websUtil.getWsArray( "callAPI_to_Array", params ) ;
	        /* 3.  ������ */
//	        FormatUtil formatUtil = new FormatUtil() ;
//	        formatUtil.outputRstHtml( rstData ) ;
	        //System.out.println(rstData);
	        ArrayList<String> getList = new ArrayList<String>();
	        getList = websUtil.outputRst(rstData) ;
	        return getList;
	       // insertOracle(getList);
	      
	}
}
