package com.messageQueue.controller.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.messageQueue.config.RabbitMQConfig;
import com.messageQueue.controller.RabbitMQWebController;
import com.messageQueue.model.QueueDetails;
import com.messageQueue.service.RabbitMQSender;

public class RabbitMQWebControllerIntgTestSpec {
	

	@Mock
	private AmqpTemplate amqpTemplate;
	
	@Mock
	private AmqpAdmin admin;
	
	@Mock
	RabbitTemplate rabbitTemplate;
	
	@Mock
	RabbitMQConfig rabbitMQConfig;
	
	@InjectMocks
	RabbitMQSender rabbitMQSender;

	@Autowired
	RabbitMQWebController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new RabbitMQWebController();
	}
	
	@Test
	public void testCreateQueue() {
		List<String> queuesList = new ArrayList<String>();
		queuesList.add("testQueue");
		Queue queue = new Queue("testQueue", true, false, false);
		Binding binding = new Binding("testQueue", Binding.DestinationType.QUEUE, "ascent.exchange", "ascent.manage.routingkey", null);
		when(admin.declareQueue(queue)).thenReturn("success");
		String response = controller.createQueue(queuesList, 10);
		assertEquals("Queues Created In  RabbitMQ  Successfully", response);

	}
	

	

}
