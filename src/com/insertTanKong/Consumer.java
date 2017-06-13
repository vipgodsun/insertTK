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
 * 消费者线程
 * 
 * @author syc
 */
public class Consumer implements Runnable {
 
    public Consumer(BlockingQueue<ArrayList<String>> queue) {
        this.queue = queue;
    }
 
    public void run() {
        System.out.println("启动消费者线程！");
        Random r = new Random();
        boolean isRunning = true;
        Connection con = null;// 创建一个数据库连接
        PreparedStatement pre = null;
        ResultSet result = null;
        try {
            while (isRunning) {
                System.out.println("正从队列获取数据...");
                //连接数据库
                
               try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				 System.out.println("开始尝试连接数据库！");
	     	     String url = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST =)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = mydb)))";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
	     	     String user = "";// 用户名,系统默认的账户名
	     	     String password = "";// 你安装时选设置的密码
	     	     con = DriverManager.getConnection(url, user, password);// 获取连接
	     	     System.out.println("连接成功！");
	     	        //保存当前自动提交模式
	     	      Statement stmt = con.createStatement(); 
	     	     ArrayList<String> data = queue.poll(2, TimeUnit.SECONDS);
	     	      if(null != data){
	     	    	  System.out.println("正在拿到数据");
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
			}// 加载Oracle驱动程序
              catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           // ArrayList<String> data = queue.poll(2, TimeUnit.SECONDS);
//                if (null != data) {
//                    System.out.println("拿到数据：" + data);
//                    System.out.println("正在消费数据：" + data);
//                    for(int i=0;i<data.size();i++){
//        	        	stmt.addBatch(data.get(i));
//        	        }
//        	        stmt.executeBatch(); 
//                    Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));
//                } else {
//                    // 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
//                    isRunning = false;
//                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
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
        	catch(Exception e){
        		e.printStackTrace();
        	}
            System.out.println("退出消费者线程！");
        }
    }
 
    private BlockingQueue<ArrayList<String>> queue;
    private static final int      DEFAULT_RANGE_FOR_SLEEP = 1000;
}
