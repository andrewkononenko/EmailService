#EmailService
##Overview
EmailService is an application that provides easy interface for sending emails, notifications etc.

##Local Development Setup
###Message queue installation:
 - Follow instructions from official page http://activemq.apache.org/getting-started.html
 - If you install it on Linux and have problems with running provider, try to run it from 
  platform specific directory: {activemq_install_directory}/bin/{platform_specific directory}/

###DB configuration:
 - Install mongodb
 - Create database
```
 use MailManagerDB
```
 - Create collections "users" and "envelopes"
```
 db.createCollection("users")
 db.createCollection("envelopes")
```
 - You can create test user with
```
 db.users.insert({username:"testuser@test.test",name:"testuser",surname:"test"})
```

###Local application run:
 - Compile sources ```mvn clean install```
 - Follow instructions in subproject's Readme.md file
 
###Verify
 - You can verify if application configured and run correctly with
```
 curl --request POST "http://localhost:9080/envelope?Subject=qqq&To=testuser2@test.test&From=testuser@test.test&template=123"
```
 You should get such response:
```
{  
   "id":"57849b9ae4b0422110879535",
   "from":{  
      "id":null,
      "username":"testuser@test.test",
      "name":"testuser",
      "surname":"test"
   },
   "to":"testuser2@test.test",
   "subject":"qqq",
   "template":"123",
   "state":"IN_QUEUE"
}
```
