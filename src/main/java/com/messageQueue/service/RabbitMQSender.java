package com.messageQueue.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.messageQueue.SpringBootMessageServiceApp;
import com.messageQueue.model.QueueDetails;


@Service
public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Autowired
	private AmqpAdmin admin;
	
	@Value("${message.queue.exchange}")
	private String exchange;
	
	@Value("${message.queue.routingkey}")
	private String routingkey;	
	
	private static final Logger logger = LoggerFactory.getLogger(RabbitMQSender.class);
	
	public boolean send(QueueDetails queueDetails) {
		Queue queue = new Queue(queueDetails.queueName, true, false, false);
		Binding binding = new Binding(queueDetails.queueName, Binding.DestinationType.QUEUE, exchange, routingkey, null);
		admin.declareQueue(queue);
		admin.declareBinding(binding);
		amqpTemplate.convertAndSend(exchange, routingkey, queueDetails.message);
		logger.info("Send msg = " + queueDetails.message);
		return true;
	    
	}
	
	
	public boolean createQueue(String queueName, int size) {
		//setting durable while creating a queue
		Queue queue = new Queue(queueName, true, false, false);
		Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, exchange, routingkey, null);
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-max-length", size);
		admin.declareQueue(queue);
		admin.declareBinding(binding);
		return true;
	}
	
	public boolean deleteQueue(String queueName) {
		return admin.deleteQueue(queueName);
		
	}
	
	public boolean deleteMessage(String queueName) {
		admin.purgeQueue(queueName, true);
		return true;
	}
}