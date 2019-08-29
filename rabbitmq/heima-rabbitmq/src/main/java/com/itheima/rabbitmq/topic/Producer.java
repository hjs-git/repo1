package com.itheima.rabbitmq.topic;

import com.itheima.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 通配符模式： 发送3条消息
        item.insert 、 item.update 、 item.delete
        通配符：‘*’ 可以表示一个单词、'#'可以表示多个单词
 */
public class Producer {
    //交换机名称
    static final String TOPIC_EXCHANGE="topic_exchange";
    //队列名称
    static final String TOPIC_QUEUE_1="topic_queue_1";
    static final String TOPIC_QUEUE_2 = "topic_queue_2";

    public static void main(String[] args) throws Exception{
        //1.创建连接
        Connection connection = ConnectionUtil.getConnection();
        //2.创建频道
        Channel channel = connection.createChannel();
        //3.声明交换机
        channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);
        //4--5 可以在消费者中声明并绑定
        //6.发送消息
        String message = "商品的新增。路由模式 ，路由key为item.insert";
        /**
         参数1：交换机名称，如果没有则使用空字符串
         参数2：路由key，简单模式中可以使用队列名称
         参数3：消息其他属性
         参数4：消息内容
         */
        channel.basicPublish(TOPIC_EXCHANGE,"item.insert",null,message.getBytes());
        System.out.println("已发送消息："+message);

        message = "商品的修改。路由模式 ，路由key为item.update";
        channel.basicPublish(TOPIC_EXCHANGE,"item.update",null,message.getBytes());
        System.out.println("已发送消息："+message);

        message = "商品的删除。路由模式 ，路由key为item.delete";
        channel.basicPublish(TOPIC_EXCHANGE,"item.delete",null,message.getBytes());
        System.out.println("已发送消息："+message);

        //7.关闭资源
        channel.close();
        connection.close();
    }
}
