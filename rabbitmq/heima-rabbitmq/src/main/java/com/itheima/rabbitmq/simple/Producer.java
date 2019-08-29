package com.itheima.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 简单模式：用于发送消息
 */
public class Producer {

    static final String QUEUE_NAME="simple_queue";

    public static void main(String[] args) throws Exception {
        //1. 创建连接工厂（设置RabbitMQ的连接参数）
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接参数
        //主机：默认localhost
        connectionFactory.setHost("localhost");
        //连接端口：默认5672
        connectionFactory.setPort(5672);
        //虚机主机：默认/
        connectionFactory.setVirtualHost("/itcast");
        //用户名：默认guest
        connectionFactory.setUsername("heima");
        //密码：默认guest
        connectionFactory.setPassword("heima");

        //2.创建连接
        Connection connection = connectionFactory.newConnection();
        //3.创建频道
        Channel channel = connection.createChannel();
        //4.声明队列
        /**
         参数1：队列名称
         参数2：是否定义持久化队列
         参数3：是否独占本连接
         参数4：是否在不使用的时候队列自动删除
         参数5：其他参数
         */
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //5.发送消息
        String message = "Hello RabbitMq";

        /**
         参数1：交换机名称，如果没有则使用空字符串
         参数2：路由key，简单模式中可以使用队列名称
         参数3：消息其他属性
         参数4：消息内容
         */
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("已发送消息："+message);
        //6.关闭资源
        channel.close();
        connection.close();

    }
}
