#EmailService

##Overview
EmailService is an application that provides easy interface for sending emails, notifications etc.

##Local Development Setup
###DB configuration:
 - Install mongodb
 - Create database
 ```sh
 use MailManagerDB
 ```
 - Create collections "users" and "envelopes"
 ```sh
 db.createCollection("users")
 db.createCollection("envelopes")
 ```
###Run application:
 - "mvn clean install"