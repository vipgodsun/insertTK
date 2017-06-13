package com.insertTanKong;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
 
/**
 * �������߳�
 * 
 * @author syc
 */
public class Consumer implements Runnable {
 
    public Consumer(BlockingQueue<ArrayList<String>> queue) {
        this.queue = queue;
    }
 
    public void run() {
        System.out.println("�����������̣߳�");
        Random r = new Random();
        boolean isRunning = true;
        Connection con = null;// ����һ�����ݿ�����
        PreparedStatement pre = null;
        ResultSet result = null;
        try {
            while (isRunning) {
                System.out.println("���Ӷ��л�ȡ����...");
                //�������ݿ�
                
               try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				 System.out.println("��ʼ�����������ݿ⣡");
	     	     String url = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST =172.19.38.9)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = mydb)))";// 127.0.0.1�Ǳ�����ַ��XE�Ǿ����Oracle��Ĭ�����ݿ���
	     	     String user = "miss";// �û���,ϵͳĬ�ϵ��˻���
	     	     String password = "miss1";// �㰲װʱѡ���õ�����
	     	     con = DriverManager.getConnection(url, user, password);// ��ȡ����
	     	     System.out.println("172.19.38.9���ӳɹ���");
	     	        //���浱ǰ�Զ��ύģʽ
	     	      Statement stmt = con.createStatement(); 
	     	     ArrayList<String> data = queue.poll(2, TimeUnit.SECONDS);
	     	      if(null != data){
	     	    	  System.out.println("�����õ�����");
	     	    	  for(int i=0;i<data.size();i++){
	     	    		  stmt.addBatch(data.get(i));
	     	    	  }
	     	    	  stmt.executeBatch();
	     	    	 //Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));
	     	      }
	     	      else{
	     	    	  isRunning = false;
	     	      }
	     	      
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// ����Oracle��������
              catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           // ArrayList<String> data = queue.poll(2, TimeUnit.SECONDS);
//                if (null != data) {
//                    System.out.println("�õ����ݣ�" + data);
//                    System.out.println("�����������ݣ�" + data);
//                    for(int i=0;i<data.size();i++){
//        	        	stmt.addBatch(data.get(i));
//        	        }
//        	        stmt.executeBatch(); 
//                    Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));
//                } else {
//                    // ����2s��û���ݣ���Ϊ���������̶߳��Ѿ��˳����Զ��˳������̡߳�
//                    isRunning = false;
//                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
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
        	catch(Exception e){
        		e.printStackTrace();
        	}
            System.out.println("�˳��������̣߳�");
        }
    }
 
    private BlockingQueue<ArrayList<String>> queue;
    private static final int      DEFAULT_RANGE_FOR_SLEEP = 1000;
}