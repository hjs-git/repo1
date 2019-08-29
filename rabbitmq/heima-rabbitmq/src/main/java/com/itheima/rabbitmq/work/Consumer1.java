package com.itheima.rabbitmq.work;

import com.itheima.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 work模式：消费者接收消息
 */
public class Consumer1 {
    public static void main(String[] args) throws Exception {
        //1.创建连接
        Connection connection = ConnectionUtil.getConnection();
        //2.创建频道
        Channel channel = connection.createChannel();
        //3.声明队列
        /**
         参数1：队列名称
         参数2：是否定义持久化队列
         参数3：是否独占本连接
         参数4：是否在不使用的时候队列自动删除
         参数5：其他参数
         */
        channel.queueDeclare(Producer.QUEUE_NAME, true, false, false, null);
        //每次可以预取多少条消息
        channel.basicQos(1);
        //4.创建消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    //路由key
                    System.out.println("路由key为："+envelope.getRoutingKey());
                    //交换机
                    System.out.println("交换机为："+envelope.getExchange());
                    //消息id
                    System.out.println("消息id为："+envelope.getDeliveryTag());
                    //接收到的消息
                    System.out.println("消费者1---接收到的消息为："+new String(body,"utf-8"));

                    //休眠1秒
                    Thread.sleep(1000);
                    //确认消息
                    /**
                     参数1：消息id
                     参数2：false表示只有当前这条被处理，
                     */
                    channel.basicAck(envelope.getDeliveryTag(),false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //5.监听队列
        channel.basicConsume(Producer.QUEUE_NAME,true,defaultConsumer);
    }
}
