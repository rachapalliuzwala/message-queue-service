package com.messageQueue.controller.test;

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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.messageQueue.config.RabbitMQConfig;
import com.messageQueue.controller.RabbitMQWebController;
import com.messageQueue.model.QueueDetails;
import com.messageQueue.service.RabbitMQSender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQWebControllerTestSpec {

	
	@Mock
	RabbitTemplate rabbitTemplate;
	
	@Mock
	RabbitMQConfig rabbitMQConfig;
	
	@Mock
	RabbitMQSender rabbitMQSender;

	@InjectMocks
	RabbitMQWebController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCreateQueue() {
		List<String> queuesList = new ArrayList<String>();
		queuesList.add("testQueue");
		when(rabbitMQSender.createQueue("testQueue", 10)).thenReturn(true);
		String response = controller.createQueue(queuesList, 10);
		assertEquals("Queues Created In  RabbitMQ  Successfully", response);

	}
	

	@Test
	public void testDeleteQueue() {
		List<String> queuesList = new ArrayList<String>();
		queuesList.add("testQueue");
		when(rabbitMQSender.deleteQueue("testQueue")).thenReturn(true);
		String response = controller.deleteQueue(queuesList);
		assertEquals("Queue deleted In  RabbitMQ  Successfully", response);

	}
	
	@Test
	public void testProduceMessage() {
		QueueDetails queueDetails = new QueueDetails();
		queueDetails.queueName = "testQueue";
		queueDetails.message = "Sending message to testQueue";
		when(rabbitMQSender.send(queueDetails)).thenReturn(true);
		ResponseEntity<String> responseEntity = controller.producer(queueDetails);
		assertEquals(200, responseEntity.getStatusCode().value());

	}
	
	
	@Test
	public void testDeleteMessage() {
		when(rabbitMQSender.deleteMessage("testQueue")).thenReturn(true);
		ResponseEntity<String> responseEntity = controller.deleteMessage("testQueue");
		assertEquals(200, responseEntity.getStatusCode().value());
	}

}
