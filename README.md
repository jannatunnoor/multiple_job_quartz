## Spring Quartz

This module contains articles about Spring with Quartz

### Relevant Articles: 

- [Multiple Job Scheduling in Spring with Quartz](https://abigzero.medium.com/multiple-job-scheduling-in-spring-with-quartz-35951a44a3b4)
- [Scheduling in Spring with Quartz](https://www.baeldung.com/spring-quartz-schedule)
    

## #Scheduling in Spring with Quartz Example Project
This is the first example where we configure a basic scheduler.

##### Spring boot application, Main class


`com.example.quartz.QuartzApplication

##### Configuration in *application.properties*

  - Default: configures scheduler using Spring convenience classes:

    `using.spring.schedulerFactory=true`
   
  - To configure scheduler using Quartz API: 
  
    `using.spring.schedulerFactory=false`
    
    But, quartz scheduling is not working due to db connection.
