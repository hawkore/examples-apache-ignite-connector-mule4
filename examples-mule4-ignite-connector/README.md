## Samples for Hawkore's Apache Ignite connector for Mule 4

This maven project contains sample source code for [Hawkore's Apache Ignite connector for Mule 4
](https://docs.hawkore.com/private/apache-ignite-connector-mule4).

**NOTE**: This application is configured to starts as server node for testing purposes, if you want to start it as Apache Ignite as client node you must start Apache Ignite Servers before running this samples. See [README.md](../ignite-server-node-test/README.md) for more info.

### Steps for testing

- 1. Download the latest version of Anypoint Studio 7.5+ and import this project as an `Anypoint Studio project from File System`. After finishing it's compilation, run it as a `Mule Application`.

- 2. Test operations with [Postman](https://www.getpostman.com/apps). 

    - Open Postman and send below requests (view code for more samples). For your convenience, you can import [apache_ignite_connector.postman_collection.json](./apache_ignite_connector.postman_collection.json) into Postman.
    
    - Change port if you have modified the HTTP Listener Config (Global Configuration)

	- Prepare some data:
		- Data ingestion into CACHE: GET http://localhost:8080/caches/ingestPois?cc=ES&count=10000&initialId=0
		- Data ingestion into QUEUE: GET http://localhost:8080/queues/ingestEntities?count=10000&initialId=0 (received data on queue will be stored into CACHE2 check how cache size is increased by: GET http://localhost:8080/caches/size?cache=CACHE2)
		
	- Some **cache** operations (you will see on client terminals traces):
		- Put entry: PUT http://localhost:8080/caches/put?key=1&value=theValue
		- Get entry: GET http://localhost:8080/caches/get?key=1
		- Delete entry: DELETE http://localhost:8080/caches/del?key=1
		- Cache size: GET http://localhost:8080/caches/size?cache=CACHE2
		- Clear cache: GET http://localhost:8080/caches/clear?cache=CACHE2

	- Some **queue** operations (you will see on client terminals traces):
		- publish message: POST http://localhost:8080/queues/publish?message=aMessage
		- publish-consume message: POST http://localhost:8080/queues/publishConsume?message=aMessage
		
	- Some **query** operations (you will see on client terminals traces):
	    - Get poi by id: GET http://localhost:8080/queries/pois/10?lang=en
	    - Delete poi by id: DELETE http://localhost:8080/queries/pois/10
		- Search pois by geo search (Advanced Lucene Query): GET http://localhost:8080/queries/pois/sqlGeoSearch?radius=20km&limit=30&lang=es
		- Search pois by text (SqlQuery): GET http://localhost:8080/queries/pois/sqlSearch?query=airport&limit=30&lang=en
		- Search pois by text (TextQuery): GET http://localhost:8080/queries/pois/textSearch?query=airport&limit=30&lang=en

	- Some **topic** operations (you will see on client terminals traces):
		- publish message to topic subscribers: POST http://localhost:8080/topics/publish?message=aMessage

	- Some **scheduler** operations:
		- scheduled tasks: GET http://localhost:8080/scheduler/tasks
		- scheduled tasks executions: GET http://localhost:8080/scheduler/executions?id=df042632-3d04-3bea-bc9d-06ad607b92b9
		- stop scheduler (use id from scheduled tasks list): POST http://localhost:8080/scheduler/stop?id=df042632-3d04-3bea-bc9d-06ad607b92b9
		- start scheduler (use id from scheduled tasks list): POST http://localhost:8080/scheduler/start?id=df042632-3d04-3bea-bc9d-06ad607b92b9
		- run scheduled task - one shot (use id from scheduled tasks list): POST http://localhost:8080/scheduler/run?id=df042632-3d04-3bea-bc9d-06ad607b92b9
		- re-schedule (use id from scheduled tasks list) (Fixed frequency dispatch every second, uses default values): POST http://localhost:8080/scheduler/reschedule?id=df042632-3d04-3bea-bc9d-06ad607b92b9
		- re-schedule (use id from scheduled tasks list) (CRON expression dispatch every minute): POST [http://localhost:8080/scheduler/reschedule?id=df042632-3d04-3bea-bc9d-06ad607b92b9&cron=* * * * *](http://localhost:8080/scheduler/reschedule?id=df042632-3d04-3bea-bc9d-06ad607b92b9&cron=*%20*%20*%20*%20*)

	- Some **lock** operations:	
		- 1. run task within lock scope with 1 second wait timeout (simulated task execution duration 5 seconds): GET http://localhost:8080/locks/runTask?timeout=1&timeUnit=SECONDS					
		- 2. run task within lock scope with no wait: GET http://localhost:8080/locks/runTask?timeout=0&timeUnit=SECONDS	
		- 3. run task within lock scope with 8 seconds timeout: GET http://localhost:8080/locks/runTask?timeout=8&timeUnit=SECONDS
		- 4. run task within lock scope waiting until lock is acquired: GET http://localhost:8080/locks/runTask?timeout=-1&timeUnit=SECONDS

	- Some **filesystem** operations:	
		- Create file: POST [http://localhost:8080/filesystem/create?path=/sample.txt&content=hello world!](http://localhost:8080/filesystem/create?path=/sample.txt&content=hello%20world!)
		- Create directories: POST http://localhost:8080/filesystem/mkdirs?path=/mydir/mysubdir
		- Download file: GET http://localhost:8080/filesystem/download?path=/sample.txt
		- File/directory size (bytes): GET http://localhost:8080/filesystem/size?path=/sample.txt
		- List root directory files: GET http://localhost:8080/filesystem/ls?path=/
		- File/directory summary: GET http://localhost:8080/filesystem/summary?path=/
		- Delete file: DELETE http://localhost:8080/filesystem/rm?path=/sample.txt
		- Delete directories: DELETE http://localhost:8080/filesystem/rm?path=/mydir

		
				
Visit [Hawkore's home page](https://www.hawkore.com).
