# What?

This is an manage queue  Spring Boot based project, that:

1. Is built using maven.
2. Applies standard  plugins for static analysis, which includes both sonar and FindBugs.
3. Uses sonar for Code Coverage reporting.
4. Uses Junit for unit testing.
5. Is coded in the Java language.
6. Uses application.properties for application configuration.

# Why?

So that there is a starting point for anyone that wants to build a new Spring Boot based mid-tier application .


# How?

When run this application runs using a Tomcat container on port 8080, which hosts several endpoints through Spring Actuator:


**Rest Api Testing**
1. createQueue: create queue 
	GET Api
	http://localhost:8080/messagingService/createQueue?queueName=sample&size=2
	
2. deleteQueue: delete queue 
	DELETE Api
	http://localhost:8080/messagingService/deleteQueue?queueName=demoQueue

3. enqueue: Send message to queue 
	POST Api
	http://localhost:8080/messagingService/enqueue
	RequestBody: 
		{
  
         "queueName":"demoQueue",
         "message":"Successfully sent message to rabbitmq"
  
       }
4. dequeue: purge  messages from queue 
	DELETE Api
	http://localhost:8080/messagingService/dequeue?queueName=demoQueue
	
	

This project also demonstrates some of the basics of junit:

* Unit testing 
