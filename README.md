# Rtf-log-level Mule Extension

This custom extension allows Anypoint Runtime Manager (ARM) application properties inject changes to log levels
for Mule applications. ARM supports the ability to set log levels dynamically for 
Cloudhub and standalone server deployments, but the *Logging* tab was not implemented for 
Runtime Fabric deployments. 

Without the *Logging* tab in ARM, changing log levels required the developer to create a new Mule
application with a static log4j.xml configuration. This puts a burden on the operations team that may want
to dynamically change log levels without developer intervention. This extension was created to address
this problem until the time that MuleSoft implements the Logging tab in ARM for Runtime Fabric deployments. We
recommend including this extension in every application intended to be deployed to Runtime Fabric so
that the operations team can easily change log levels from ARM.
#### Building the Custom Extension from Source
To build the extension, clone this repository and execute the following maven build command
from the root of the project folder:
```
git clone https://github.com/bhyman-ms/rtf-log-level.git
cd rtf-log-level
mvn clean install -DskipTests
```
This will build and install the Mule extension into your local maven repo. The
artifact can be installed in any maven compatible repo if desired. 

*Developer note: I do intend to implement proper unit tests but need to understand the maven test framework better...*

#### Adding the Extension to Your Mule Application
Add this dependency to your mule application's pom.xml

```
<dependency>
    <groupId>com.mulesoft.consulting</groupId>
    <artifactId>rtf-log-level</artifactId>
    <version>1.0.7</version>
    <classifier>mule-plugin</classifier>
</dependency>
```
This will install the extension in your Mule application. You do not need to do anything else.

#### Example Usage
On startup the Mule application looks for the properties that start 
with *logging.level.* and enable the log level for those components. This only happens at startup, so changing the propert requires an application 
restart for now. If for example the Mule admin wants to turn on HTTP wire tracing for a period of time, simply set a property for the mule application as follows:
```
logging.level.org.mule.service.http.impl.service.HttpMessageLogger=DEBUG
```
You can use this technique to dynamically set any logger's level.




