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
    	String params ="userId=BCSY_LNDL_qxxx" /* 1.1 用户名&密码 */
	            + "&pwd=dalian82637349"
	            + "&interfaceId=getUparEleByTimeAndStaID" /* 1.2 接口ID */        
	            + "&dataCode=UPAR_CHN_MUL_FTM" /* 1.3 必选参数（按需加可选参数） */ //资料：中国地面逐小时
	           // + "&elements=Station_Name,Province,City,Cnty,Town,Datetime,REP_CORR_ID,Year_Data,Mon_Data,Day_Data,Hour_Data,Station_Id_C,Station_Id_d,Lat,Lon,Alti,V07030,PRS_Sensor_Alti,HEITH_BALLON,Nation_Code,Year,Mon,Day,Hour,Min,Second,Sensor_type,RSON_Type,SIR_Corr,SYSTAT,SST,CLO_COV_LM,CLO_Height_LoM,CLO_Fome_Low,CLO_FOME_MID,CLO_Fome_High,DATA_CATE,EVSS,Time_Dev_WQ,Lat_Dev,Lon_Dev,PRS_HWC,GPH,Heigh_Alti,TEM,DPT,DTD,WIN_D,WIN_S,WIN_SHE_B1Km,WIN_She_A1Km,Q_Lat_Dev,Q_Lon_Dev,Q_PRS_HWC,Q_GPH,Q_Heigh_Alti,Q_TEM,Q_DPT,Q_DTD,Q_WIN_D,Q_WIN_S,Q_WIN_SHE_B1Km,Q_WIN_She_A1Km" //检索要素：站号、站名、小时降水、气压、相对湿度、能见度、2分钟平均风速、2分钟风向
	            + "&elements=Station_Name,Province,City,Cnty,Town,Datetime,REP_CORR_ID,Year_Data,Mon_Data,Day_Data,Hour_Data,Station_Id_C,Station_Id_d,Lat,Lon,Alti,V07030,PRS_Sensor_Alti,HEITH_BALLON,Nation_Code,Year,Mon,Day,Hour,Min,Second,Sensor_type,RSON_Type,SIR_Corr,SYSTAT,SST,CLO_COV_LM,CLO_Height_LoM,CLO_Fome_Low,CLO_FOME_MID,CLO_Fome_High,DATA_CATE,EVSS,Time_Dev_WQ,Lat_Dev,Lon_Dev,PRS_HWC,GPH,Heigh_Alti,TEM,DPT,DTD,WIN_D,WIN_S,WIN_SHE_B1Km,WIN_She_A1Km"
	            + "&times=" +time//检索时间
	            + "&staIds=54662" //检索时间
	            + "&orderby=Station_ID_C:ASC"; //排序：按照站号从小到大
	            //+ "&limitCnt=10"  //返回最多记录数：10
	            //+ "&dataFormat=json" ; /* 1.4 序列化格式 */
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
        //开始时间,注意月份0代表1月
        cd.set(2016,5,11,0,0,0);
        //截止时间
        nowtime.set(2017,5,12,0,0,0);
        System.out.println("启动生产者线程！");
        try {
            while (isRunning) {
            	 while(cd.before(nowtime)){
      	    	   cd.add(Calendar.HOUR, 24);
      	    	   String datatime = (dateFormat2.format(cd.getTime()));  
      	    	    System.out.println(datatime); 
      	    	    try{
      	    	    	System.out.println("正在生产数据...");
      	    	    	System.out.println("从省局cimiss调取"+datatime+"数据"); 
      	    	    	Thread.sleep(10);
      	    	    	if(!queue.offer(getDataFromCimiss(datatime))){
      	    	    		System.out.println("日期为:"+datatime+"放入失败");
      	    	    	}
      	    	    }
      	    	    finally{
      	    	    	continue;
      	    	    }
      	          }
            }
        } finally {
            System.out.println("退出生产者线程！");
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