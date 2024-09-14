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
     * �����̳߳�
     * */
    static Vector<Connection> pools = new Vector<Connection>();


    /**
     *
     * �������ݿ�
     *
     * @return*/
    public static Connection getConnection() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        //�û���Ϣ��url
        String url = "jdbc:mysql://localhost:3306/wanghu?useUnicode=true&characterEncoding=utf8&useSSL=false";
        String username="root";
        String password="wanghu@01";
        //���ӳɹ������ݿ���� Connection
        Connection connection = DriverManager.getConnection(url,username,password);
        connection.setAutoCommit(false);
        return connection;
    }



    public static void main(String[] args)  {
        long startTimes = System.currentTimeMillis();//��¼��ʼʱ��
        String[] names = new String[]{"�߳�1","�߳�2","�߳�3","�߳�4","�߳�5","�߳�6","�߳�7","�߳�8","�߳�9","�߳�10"};

        int threadCount = 10;//�߳�����
        int total = 1000;//�������������
        int every = total/threadCount;//ÿ���߳�ִ������
        //�̼߳���������ִ����һ���̺߳󣬼�����ֵ-1����ʼֵΪ�߳�����
        final CountDownLatch latch = new CountDownLatch(threadCount);
        //ѭ����ʮ���߳�
        for(int i=0;i<threadCount;i++){
            new Thread(
                    new Worker(latch,names[i],i*every,(i+1)*every)
            ).start();
        }
        try {
            latch.await();//�ȴ���������Ϊ0�����ȴ������첽�߳�ִ�����
            long endTimes = System.currentTimeMillis();
            System.out.println("�����߳�ִ�����:" + (endTimes - startTimes));
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
     *�������������룩���ݵ����ݿ�ָ�����
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

                sql1.setString(1, "����" + j);
                sql1.setString(2, "26");
                sql1.setString(3, "1");
                sql1.setString(4, String.valueOf((1988500000 + j)));
                sql1.setString(5, "������"+j );
                sql1.setString(6, "�����ֶ�1_" + j);
                sql1.setString(7, "�����ֶ�2_"+j );
                sql1.setString(8, "�����ֶ�3_" +j);
                sql1.setString(9, "�����ֶ�4_" +j);
                sql1.setString(10, "�����ֶ�5_" +j);
                sql1.setString(11, "�����ֶ�6_" +j);
                sql1.setString(12, "�����ֶ�7_" +j);
                sql1.setString(13, "�����ֶ�8_" +j);
                sql1.setString(14, "�����ֶ�9_" +j);
                sql1.setString(15, "�����ֶ�10_"+j);
                sql1.setString(16, "�����ֶ�11_"+j);
                sql1.setString(17, "�����ֶ�12_"+j);
                sql1.setString(18, "�����ֶ�13_"+j);
                sql1.setString(19, "�����ֶ�14_"+j);
                sql1.setString(20, "�����ֶ�15_"+j);
                sql1.setString(21, "�����ֶ�16_"+j);
                sql1.setString(22, "�����ֶ�17_"+j);
                sql1.setString(23, "�����ֶ�18_"+j);
                sql1.setString(24, "�����ֶ�19_"+j);
                sql1.setString(25, "�����ֶ�20_"+j);
                sql1.setString(26, "�����ֶ�21_"+j);
                sql1.setString(27, "�����ֶ�22_"+j);
                sql1.setString(28, "�����ֶ�23_"+j);
                sql1.setString(29, "�����ֶ�24_"+j);
                sql1.setString(30, "�����ֶ�25_"+j);
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
            System.out.println("��ʼ�����ݳ�������ԭ��" + e.getMessage());
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
            System.out.println("�̣߳�"+Thread.currentThread().getName()+ "����ִ�С���");
            try {
                CreateData.createDataToDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        latch.countDown();
    }

}
