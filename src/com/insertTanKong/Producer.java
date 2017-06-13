package com.insertTanKong;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
public class Producer implements Runnable {

    public Producer(BlockingQueue<ArrayList<String>> queue) {
        this.queue = queue;
    }
    
    public  ArrayList<String> getDataFromCimiss(String time){
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
        WebsUtil websUtil = new WebsUtil() ;
        String[][] rstData = websUtil.getWsArray( "callAPI_to_Array", params);
        //BlockingQueue<String> data = new LinkedBlockingQueue<String>(100);
        ArrayList<String> data = new ArrayList<String>();
        data = websUtil.outputRst(rstData) ;
        return data;
    }
    
    public void run() {
        String data = null;
        Random r = new Random();
        DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmmss");
 	    Date dat = null;
        Calendar cd = Calendar.getInstance();
        Calendar nowtime = Calendar.getInstance();
        //��ʼʱ��,ע���·�0����1��
        cd.set(2016,5,11,0,0,0);
        //��ֹʱ��
        nowtime.set(2017,5,12,0,0,0);
        System.out.println("�����������̣߳�");
        try {
            while (isRunning) {
            	 while(cd.before(nowtime)){
      	    	   cd.add(Calendar.HOUR, 24);
      	    	   String datatime = (dateFormat2.format(cd.getTime()));  
      	    	    System.out.println(datatime); 
      	    	    try{
      	    	    	System.out.println("������������...");
      	    	    	System.out.println("��ʡ��cimiss��ȡ"+datatime+"����"); 
      	    	    	Thread.sleep(10);
      	    	    	if(!queue.offer(getDataFromCimiss(datatime))){
      	    	    		System.out.println("����Ϊ:"+datatime+"����ʧ��");
      	    	    	}
      	    	    }
      	    	    finally{
      	    	    	continue;
      	    	    }
      	          }
            }
        } finally {
            System.out.println("�˳��������̣߳�");
        }
    }

    public void stop() {
        isRunning = false;
    }

    private volatile boolean isRunning = true;
    private BlockingQueue<ArrayList<String>> queue;
    private static AtomicInteger count = new AtomicInteger();
    private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;

}