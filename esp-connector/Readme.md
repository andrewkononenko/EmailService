#ESP Connector
##Overview
ESP Connector is a part of EmailService application, a stub service that talks to ESP.

##Local application run:
 - Requires mongodb and activemq services running, look parent Readme.md for instructions.
 - Create run configuration in IDEA ```Run-Edit Configuration-JAR Application```
 - Set path to compiled JAR
 - Set run argumets ```server esp-connector/sendEmailService.yaml```