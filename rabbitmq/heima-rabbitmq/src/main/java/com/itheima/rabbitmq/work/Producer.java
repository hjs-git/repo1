package com.itheima.rabbitmq.work;

import com.itheima.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 work模式：发送消息
 */
public class Producer {

    static final String QUEUE_NAME="work_queue";

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
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //4.发送消息(30条)
        for (int i = 1; i <= 30; i++) {
            String message = "Hello RabbitMQ。work模式..."+i;

            /**
             参数1：交换机名称，如果没有则使用空字符串
             参数2：路由key，简单模式中可以使用队列名称
             参数3：消息其他属性
             参数4：消息内容
             */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("已发送消息： work模式----"+i);
        }
        //5. 关闭资源
        channel.close();
        connection.close();
    }
}
