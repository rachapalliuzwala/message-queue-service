package com.messageQueue.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.messageQueue.model.QueueDetails;
import com.messageQueue.service.RabbitMQSender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQSenderTestSpec {
	
	@Mock
	private AmqpTemplate amqpTemplate;
	
	@Mock
	private AmqpAdmin admin;
	
	@InjectMocks
	RabbitMQSender rabbitMQSender;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@SuppressWarnings("unused")
	@Test
	public void testCreateQueue() {
		Queue queue = new Queue("testQueue", true, false, false);
		Binding binding = new Binding("testQueue", Binding.DestinationType.QUEUE, "ascent.exchange", "ascent.manage.routingkey", null);
		when(admin.declareQueue(queue)).thenReturn("success");
		boolean response = rabbitMQSender.createQueue("testQueue", 10);
		assertEquals(true, response);

	}
	
	@SuppressWarnings("unused")
	@Test
	public void testSend() {
		QueueDetails queueDetails = new QueueDetails();
		queueDetails.queueName = "testQueue";
		Queue queue = new Queue("testQueue", true, false, false);
		Binding binding = new Binding("testQueue", Binding.DestinationType.QUEUE, "ascent.exchange", "ascent.manage.routingkey", null);
		when(admin.declareQueue(queue)).thenReturn("success");
		boolean response = rabbitMQSender.send(queueDetails);
		assertEquals(true, response);

	}
	
	@SuppressWarnings("unused")
	@Test
	public void testDeleteQueue() {
		when(admin.deleteQueue("testQueue")).thenReturn(true);
		boolean response = rabbitMQSender.deleteQueue("testQueue");
		assertEquals(true, response);

	}
	
	
	@SuppressWarnings("unused")
	@Test
	public void testDeleteMessage() {
		boolean response = rabbitMQSender.deleteMessage("testQueue");
		assertEquals(true, response);
	}
	
	
	
	

}
