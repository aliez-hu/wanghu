package com.hu.wang;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;

/**
 * @author hwang30
 * @date 2024/6/5 14:48
 */
public class CreateData {

    /**
     * 开启线程池
     * */
    static Vector<Connection> pools = new Vector<Connection>();


    /**
     *
     * 连接数据库
     *
     * @return*/
    public static Connection getConnection() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        //用户信息和url
        String url = "jdbc:mysql://localhost:3306/wanghu?useUnicode=true&characterEncoding=utf8&useSSL=false";
        String username="root";
        String password="wanghu@01";
        //连接成功，数据库对象 Connection
        Connection connection = DriverManager.getConnection(url,username,password);
        connection.setAutoCommit(false);
        return connection;
    }



    public static void main(String[] args)  {
        long startTimes = System.currentTimeMillis();//记录开始时间
        String[] names = new String[]{"线程1","线程2","线程3","线程4","线程5","线程6","线程7","线程8","线程9","线程10"};

        int threadCount = 10;//线程数量
        int total = 1000;//插入的数据条数
        int every = total/threadCount;//每个线程执行条数
        //线程计数器，当执行完一个线程后，计数器值-1，初始值为线程数量
        final CountDownLatch latch = new CountDownLatch(threadCount);
        //循坏开十个线程
        for(int i=0;i<threadCount;i++){
            new Thread(
                    new Worker(latch,names[i],i*every,(i+1)*every)
            ).start();
        }
        try {
            latch.await();//等待计数器变为0，即等待所有异步线程执行完毕
            long endTimes = System.currentTimeMillis();
            System.out.println("所有线程执行完毕:" + (endTimes - startTimes));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static synchronized Connection getPool() throws Exception {
        if(pools != null && pools.size() > 0){
            int last_ind = pools.size() -1;
            return pools.remove(last_ind);
        }else{
            return getConnection();
        }
    }


    /**
     *批量创建（插入）数据到数据库指定表格
     * */
    public static void createDataToDatabase() throws Exception {
        Connection conn = getPool();

        try {
            String sql = "insert into user_info (userName,age,gender,phone,address," +
                    "attribute1,attribute2,attribute3,attribute4,attribute5,attribute6,attribute7,attribute8,attribute9,attribute10," +
                    "attribute11,attribute12,attribute13,attribute14,attribute15,attribute16,attribute17,attribute18,attribute19,attribute20," +
                    "attribute21,attribute22,attribute23,attribute24,attribute25) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement sql1 = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = sql1.getGeneratedKeys();

            int i = 1, j = 1;
            for (int k = 0; k < 1000; k++) {

                sql1.setString(1, "王虎" + j);
                sql1.setString(2, "26");
                sql1.setString(3, "1");
                sql1.setString(4, String.valueOf((1988500000 + j)));
                sql1.setString(5, "凯里市"+j );
                sql1.setString(6, "属性字段1_" + j);
                sql1.setString(7, "属性字段2_"+j );
                sql1.setString(8, "属性字段3_" +j);
                sql1.setString(9, "属性字段4_" +j);
                sql1.setString(10, "属性字段5_" +j);
                sql1.setString(11, "属性字段6_" +j);
                sql1.setString(12, "属性字段7_" +j);
                sql1.setString(13, "属性字段8_" +j);
                sql1.setString(14, "属性字段9_" +j);
                sql1.setString(15, "属性字段10_"+j);
                sql1.setString(16, "属性字段11_"+j);
                sql1.setString(17, "属性字段12_"+j);
                sql1.setString(18, "属性字段13_"+j);
                sql1.setString(19, "属性字段14_"+j);
                sql1.setString(20, "属性字段15_"+j);
                sql1.setString(21, "属性字段16_"+j);
                sql1.setString(22, "属性字段17_"+j);
                sql1.setString(23, "属性字段18_"+j);
                sql1.setString(24, "属性字段19_"+j);
                sql1.setString(25, "属性字段20_"+j);
                sql1.setString(26, "属性字段21_"+j);
                sql1.setString(27, "属性字段22_"+j);
                sql1.setString(28, "属性字段23_"+j);
                sql1.setString(29, "属性字段24_"+j);
                sql1.setString(30, "属性字段25_"+j);
                sql1.addBatch();
                i++;
                j++;
                if (i >= 10) {
                    sql1.executeBatch();
                    sql1.clearBatch();
                    i = 0;
                    conn.commit();
                }
            }
            if (i > 0) {
                sql1.executeBatch();
                sql1.clearBatch();
                conn.commit();
            }
            conn.commit();
            conn.close();
            conn = null;
        }catch (SQLException e){
            System.out.println("初始化数据出错，出错原因：" + e.getMessage());
            try{
                conn.rollback();
                conn = null;
            }catch (SQLException e1){
                e1.getStackTrace();
            }
        }

    }



}





class Worker implements Runnable{

    int start = 0;
    int end = 0;
    String name = "";
    CountDownLatch latch;
    public Worker(CountDownLatch latch,String name, int start,int end){
        this.start = start;
        this.end = end;
        this.name = name;
        this.latch = latch;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            System.out.println("线程："+Thread.currentThread().getName()+ "正在执行。。");
            try {
                CreateData.createDataToDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        latch.countDown();
    }

}
