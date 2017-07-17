package com.ran.test.jms;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.jms.*;
import javax.naming.InitialContext;

public class ChatRoom implements MessageListener {//topic模式
	private TopicConnection connection;
	private TopicSession pubSession;
	private TopicPublisher publisher;//发布
	private String username;		

	public ChatRoom(String topicFactory,String topicName,String username) throws Exception{
		//使用jndi.properties文件获得一个JNDI连接
		InitialContext ctx = new InitialContext();
		//按名称查找工厂并创建连接
		TopicConnectionFactory factory = (TopicConnectionFactory)ctx.lookup(topicFactory);
		TopicConnection connection = factory.createTopicConnection();
		//创建两个JMS会话对象，session
		TopicSession pubSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		TopicSession subSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		//查找一个JMS主题
		Topic chatTopic = (Topic)ctx.lookup(topicName);
		//创建发布者和订阅者
		TopicPublisher publisher = pubSession.createPublisher(chatTopic);
		TopicSubscriber subscriber = subSession.createSubscriber(chatTopic, null, true);//true标明发布者自身不能是消息消费者
		//设置JMS消息监听器
		subscriber.setMessageListener(this);
		//JMS变量初始化
		this.connection=connection;
		this.pubSession=pubSession;
		this.publisher=publisher;
		this.username=username;
		//启动JMS连接
		connection.start();
	}

	@Override
	public void onMessage(Message message) {//接收消息
		// TODO Auto-generated method stub		
		try {
			TextMessage textMsg = (TextMessage)message;
			System.out.println("------textMsg="+textMsg.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void writeMessage(String text)throws JMSException{//发布消息
		TextMessage textMsg = pubSession.createTextMessage();
		textMsg.setText("------publish msg:"+username+","+text);
		publisher.publish(textMsg);
	}
	
	public void close() throws JMSException{//关闭连接		
		connection.close();
	}
	
	public static void main(String[] args) {//运行聊天室客户端	
		args = new String[]{"TopicCF","topic1","fred"};
		try {
			if(args.length!=3){
				System.out.println("------factory topic or username is missing!");
			}
			ChatRoom chatRoom = new ChatRoom(args[0], args[1], args[2]);
			//从命令行读取入参，直到输入exit为止
			BufferedReader commandLine = new BufferedReader(new InputStreamReader(System.in));			
			while(true){				
				String in = commandLine.readLine();
				if("exit".equalsIgnoreCase(in)){
					chatRoom.clone();
					System.exit(0);
				}else{
					chatRoom.writeMessage(in);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
