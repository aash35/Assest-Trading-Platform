## Assest Trading Platform - Java
CAB302 - Software Development

### Technologies
- Java
- Swing
- Hibernate

The purpose of this program is to create an internal assest trading system for an orginisation. Once the admin has setup the required resource types, organisation branches, organisations resource allocation and so on then different employees within the organisation will be able to trade resources (such as time, computing power, lab space etc.) with the inbuilt credit system.
**An example** -If a member of the Operations staff sold 2 hours of server time and the Development team bought 2 hours of server time. The server would run a script to check if any buy/sell's could be resovled and if so would trade the required resources between the two departments.


User: admin
Password: admin

## Deployment 
### Database Setup
The application comes pre-built with a database connection that is hosted on an Amazon Lightsail instance. This will have all the details you will need to connect and be up running in minutes. This comes preloaded with an admin account with the credential’s admin admin. We would strongly recommend changing this.
All connects are passed through the ORM, hibernate which controls and deals with all queries to and from the database. If you choose to host this database yourself, you can use any database sorted by Java’s JDBC. This can range from mysql, postgres and even MSSQL using the Hibernate connector.
These settings can be changed inside the Hibernate.xml and will need to be recompiled. The reason these are not included in the config file is to ensure that end users can not as easily see the connection string and connect directly to the datastore.
Once these connections are changed, the application’s database will be automatically built by Hibernate adding all the required indexes, uniqueness constraints and relationships. This is actioned on the first start-up of the application where the database is not present at the connection’s location.
For this application’s provided database, the Lightsail instance is running a MariaDB with nightly backups.

### Java Frameworks
The application requires Java to be installed. A version higher than Java 15 is essential for the program’s execution. Our recommendation is running AdoptOpenJDK 16 which is publicly available, however other JREs or JDK’s that target version 15 will be fine. Both are required for the execution of either the client or server.
The client application simply needs to be double clicked once the framework is installed the application will load. It is essential that the settings are set correctly in the config file for both the server and the client to ensure they connect correctly. The server can be run on the client’s machine. The only requirement for the server is the Java Framework preinstallation and the port specified in the server config file being open via your firewall if running outside of the client machine.
The application binds to the interface of the server and accepts incoming requests and returns them on this port.

We would recommend running the server application via command line using the following command line argument:
 Java -jar CAB302_Server.jar
 
If the installation of this service is permanent it would be best to run this as a start up command when the OS starts to ensure the server is ready to accept requests.
To change the IP address and port of either the server or client, navigate to the settings file for either of the applications and change the IP or Port property in the config file. Restart or start-up the application to have the new changes take effect.
If the application and/or server are moved the config files for the respective programs should be kept in the same folder as the jar file.
