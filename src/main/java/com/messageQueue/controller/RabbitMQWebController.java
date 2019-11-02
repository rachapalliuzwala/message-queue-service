package com.messageQueue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.messageQueue.model.QueueDetails;
import com.messageQueue.service.RabbitMQSender;

@RestController
@RequestMapping(value = "/messagingService")
public class RabbitMQWebController {

	@Autowired
	RabbitMQSender rabbitMQSender;

	
	/**
	 * Create multiple queues using this api
	 * @param queueNames
	 * @return
	 */
	@GetMapping(value = "/createQueue")
	public String createQueue(@RequestParam("queueName") List<String> queueNames, @RequestParam("size") int size) {
		queueNames.stream().forEachOrdered(queue -> rabbitMQSender.createQueue(queue, size));  
		return "Queues Created In  RabbitMQ  Successfully";
	}
	/**
	 * Delete multiple queues using this api
	 * @param queueNames
	 * @return
	 */
	@DeleteMapping(value = "/deleteQueue")
	public String deleteQueue(@RequestParam("queueName") List<String> queueNames) {
		queueNames.stream().forEachOrdered(queue -> rabbitMQSender.deleteQueue(queue));
		return "Queue deleted In  RabbitMQ  Successfully";
	}
	/**
	 * Produce  messages to queue  using this api
	 * @param queueDetails
	 * @return
	 */
	@PostMapping(value = "/enqueue", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> producer(@RequestBody QueueDetails queueDetails) {
		rabbitMQSender.send(queueDetails);

		return new ResponseEntity<>(HttpStatus.OK);
	}
	/**
	 * purge  all  messages from queue  using this api
	 * @param queueName
	 * @return
	 */
	
	@DeleteMapping(value = "/dequeue", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteMessage(@RequestParam("queueName") String queueName) {
		rabbitMQSender.deleteMessage(queueName);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}

