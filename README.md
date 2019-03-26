# Performance-Indicator
A java dependency to calculate execution time.

## Prerequisites
To run the application you need to have java8, git, mvn and fluentd td-agent installed. 

#### FluentD td-agent for MAC:
* Download and install dmg file: https://td-agent-package-browser.herokuapp.com/2/macosx
* Go to td-agent.conf and add following settings:
  ```sh
  $ vi /etc/td-agent/td-agent.conf
  ```
  ```sh
  <match poc.**>
    @type copy
    <store>
      @type file
      path /var/log/td-agent/forward.log
    </store>

    <store>
      @type forward
      heartbeat_type tcp
      #aggregator IP
      host 10.43.5.161
      flush_interval 30s
    </store>

    # secondary host is optional
    # <secondary>
    #    host 192.168.0.12
    # </secondary>
  </match>

  <source>
    @type tail
    path /var/log/student-web-service.log
    pos_file /var/log/td-agent/student.log.pos
    tag poc.student
    format /^(?<time>[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9)]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9].[0-9][0-9][0-9])[ \t]+(?<level>[^ ]*)[ \t]+([0-9]*)[ \t]+(---)[ \t]+\[(?<thread>[^\]]*)\] (?<class>[^ ]*)([\s]*): (?<message>.*)$/

    time_format %Y-%m-%d %H:%M:%S.%L
    timezone +0530
    time_key time
    keep_time_key true
    types time:time
  </source>

  <source>
    @type tail
    path /var/log/course-web-service.log
    pos_file /var/log/td-agent/course.log.pos
    tag poc.course
    format /^(?<time>[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9)]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9].[0-9][0-9][0-9])[ \t]+(?<level>[^ ]*)[ \t]+([0-9]*)[ \t]+(---)[ \t]+\[(?<thread>[^\]]*)\] (?<class>[^ ]*)([\s]*): (?<message>.*)$/

    time_format %Y-%m-%d %H:%M:%S.%L
    timezone +0530
    time_key time
    keep_time_key true
    types time:time
  </source>
  ```
  
#### FluentD td-agent for Windows:
* Download and install file: https://td-agent-package-browser.herokuapp.com/3/windows
* Create folder log inside C:/opt
* Create folder td-agent inside C:/opt/log
* Go to C:/opt/td-agent/etc/td-agent/td-agent.conf and add following settings:
  ```sh
  <match poc.**>
    @type copy
    <store>
      @type file
      path C:/opt/log/td-agent/forward.log
    </store>

    <store>
      @type forward
      heartbeat_type tcp

      #aggregator IP
	  <server>
		host 172.25.109.253
	  </server>
	  <buffer>
		flush_interval 30s
	  </buffer>
    </store>

    # secondary host is optional
    # <secondary>
    #    host 192.168.0.12
    # </secondary>
  </match>

  <source>
    @type tail
    path C:/opt/log/student-web-service.log
    pos_file C:/opt/log/td-agent/student.log.pos
    tag poc.student
    format /^(?<time>[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9)]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9].[0-9][0-9][0-9])[ \t]+(?<level>[^ ]*)[ \t]+([0-9]*)[ \t]+(---)[ \t]+\[(?<thread>[^\]]*)\] (?<class>[^ ]*)([\s]*): (?<message>.*)$/

    time_format %Y-%m-%d %H:%M:%S.%L
    timezone +0530
    time_key time
    keep_time_key true
    types time:time
  </source>

  <source>
    @type tail
    path C:/opt/log/course-web-service.log
    pos_file C:/opt/log/td-agent/course.log.pos
    tag poc.course
    format /^(?<time>[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9)]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9].[0-9][0-9][0-9])[ \t]+(?<level>[^ ]*)[ \t]+([0-9]*)[ \t]+(---)[ \t]+\[(?<thread>[^\]]*)\] (?<class>[^ ]*)([\s]*): (?<message>.*)$/

    time_format %Y-%m-%d %H:%M:%S.%L
    timezone +0530
    time_key time
    keep_time_key true
    types time:time
  </source>
  ```

* Open Td-agent Command Prompt, run and check the logs:
  ```sh
	> fluentd -c C:/opt/td-agent/etc/td-agent/td-agent.conf
  ```

## Run 
* Use terminal and move to your workspace
* Download project using your username:
  ```sh
  $ git clone https://[username]:hiCLC7DDvTZ1g-v-ULaz@innersource.soprasteria.com/kostas.bonis/performance-indicator.git
  ```
* Go to project:
  ```sh
  $ cd performance-indicator
  ```
* Run in command line:
  ```sh
  $ mvn clean install
  ```

## Use
* Add to maven dependencies:
  ```sh
  <dependency>
    <groupId>com.kbonis</groupId>
    <artifactId>performance-indicator</artifactId>
    <version>0.0.1-SNAPSHOT</version>
  </dependency>
  ```

* Calculate performance by adding at the start and end of your block: 

  >Notes:<br>-Parameter 'your-name' for startTiming and endTiming methods should match.<br>-Default logging level is debug, to specify use "info", "warn" or "error".<br>-On endTiming method "description" is not mandatory, if null it will display nothing.

  **Logging level 'debug'**
    ```sh
    LogTimes.startTiming("your-name"); 
    LogTimes.endTiming("your-name"); 
      or 
    LogTimes.endTiming("your-name", "debug");
      or 
    LogTimes.endTiming("your-name", "debug", "your-description");  
    ```
  **Logging level 'info'**
    ```sh
    LogTimes.startTiming("your-name"); 
    LogTimes.endTiming("your-name", "info");
      or 
    LogTimes.endTiming("your-name", "info", "your-description");
    ```
  **Logging level 'warn'**
    ```sh
    LogTimes.startTiming("your-name"); 
    LogTimes.endTiming("your-name", "warn");
      or 
    LogTimes.endTiming("your-name", "warn", "your-description");
    ```
  **Logging level 'error'**
    ```sh
    LogTimes.startTiming("your-name"); 
    LogTimes.endTiming("your-name", "error");
      or
    LogTimes.endTiming("your-name", "error", "your-description");
    ```