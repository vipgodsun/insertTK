package com.insertTanKong;


import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.xml.namespace.QName;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;


public class WebsUtil {
	  /*
	   * TODO:wsdl地址，依具体环境设置
	   */
	  private final String wsdl = "http://10.86.121.55/cimiss-web/services/ws?wsdl" ;
	  private final String targetNamespace = "http://ws" ;
	  private final long timeoutInMilliSeconds =  1000 * 60 * 2 ; //2 MINUTE
	  /*
	   * web service请求服务，获取数据（返回序列化字符串）
	   */
	  public String getWsString( String method, String params ) {
	    Class[] returnTypes = new Class[] { String.class } ;
	    return (String)this.getWsData(method, params, returnTypes) ;
	  }
	  
	  public String[][] getWsArray( String method, String params ) {
	    Class[] returnTypes = new Class[]{ String[][].class };
	    return (String[][])this.getWsData(method, params, returnTypes) ; 
	  }
	  
	  public ArrayList<String> outputRst( String[][] data ) {
	    if( data.length < 1 ) {
	      return null;
	    }
	    //第1行为各字段名
	    for( int iCol = 0; iCol < data[0].length; iCol ++ ) {
	      System.out.print( data[0][iCol] + "\t" ) ;      
	    } 
	    System.out.println() ;   
	    System.out.println("-----------------------------------------------------------------------------------------") ;
	    //第2行开始为要素值
	    ArrayList<String> getList = new ArrayList<String>(100);
	    for( int iRow = 1; iRow < data.length; iRow ++ ) {
	      ArrayList<String> records = new ArrayList<String>();  
	      for( int iCol = 0; iCol < data[iRow].length; iCol ++ ) {
	    	records.add(data[iRow][iCol]);
	        //System.out.print( data[iRow][iCol] + "\t" ) ;        
	      }
	      getList.add(getRecordSql(records));
	      System.out.println() ;      
	      //DEMO中，最多只输出10行
//	      if( iRow > 10 ) {
//	        System.out.println( "......" ) ;
//	        break ;
//	      }
	    }
	    return getList;
	  }
	  
	  private Object getWsData( String method, String params, Class returnTypes[] ) {
	    Object response = null ;
	    try {
	      RPCServiceClient wsClient = new RPCServiceClient() ;
	      Options options = wsClient.getOptions() ;
	      options.setTimeOutInMilliSeconds( this.timeoutInMilliSeconds ) ; 
	      EndpointReference end = new EndpointReference( wsdl );
	      options.setTo(end);
	      QName qN = new QName( this.targetNamespace, method );
	      String[] param = { params } ;
	      response = wsClient.invokeBlocking( qN, param, returnTypes )[0] ;
	      wsClient.cleanupTransport();
	    } catch (AxisFault e) {
	      e.printStackTrace();
	    }
	    return response;  
	  }
	  
	  public  String getRecordSql(ArrayList<String> recArray) {  
	        if (null==recArray) {  
	            return null;  
	        }  
	       
	       String recordSql = "insert into UPAR_CHN_MUL_FTM_1 values('"+recArray.get(0)+"','"+recArray.get(1)+"','"+recArray.get(2)+"','"+recArray.get(3)+"','"+recArray.get(4)+"',to_date('"+recArray.get(5)+"','YYYY-MM-DD HH24:MI:SS')";
	       for(int i=6;i<recArray.size();i++)
	       {
	    	   recordSql += ",'"+recArray.get(i)+"'";
	       }
	       recordSql +=")";
	       System.out.println(recordSql);
	        return recordSql;  
	    }  
	  
	}