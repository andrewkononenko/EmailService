##EmailService
DB configuration:
 - Install mongodb;
 - Create database "use MailManagerDB";
 - Create collections "users" and "envelopes":
    db.createCollection("users")
    db.createCollection("envelopes")

Run application:
 - "mvn clean install"