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
    <version>1.0.5</version>
    <classifier>mule-plugin</classifier>
</dependency>
```
This will install the extension in your Mule application. You only need to configure
one value to enable the extension. To enable the extension add a global configuration
property **Rtf-log-level** to the project.  

To add this property, navigate to the *Global Elements* tab of your Mule application xml
and click **Create**. Search for the *Rtf-log-level Config* property and hit enter. You must 
provide the string to be searched for by the extension that indicates that the property is intended
to be used for setting log levels. For example, a Property Prepender configured as **MULE_LOG_LEVEL@** indicates that the 
extension will search for any property that starts with the String **MULE_LOG_LEVEL@** and then extract the remainder of
its property key to identify which logger to change. 

Alternatively, you can simply include this XML snippet in your Mule application config file:
```xml
<rtf-log-level:config name="Rtf_log_level_Config" doc:name="Rtf-log-level Config" propertyPrepender="MULE_LOG_LEVEL@" />
```
Change the *propertyPrepender* attribute to whatever value is desired.
#### Example Usage
The Mule admin wants to turn on HTTP wire tracing for a period of time. The Mule applications
that are deployed all have the *Rtf-log-level* extension configured with the
Property Prepender attribute set to **MULE_LOG_LEVEL@**. To enable HTTP wire logging in a mule application you need
to set the logger named *org.mule.service.http.impl.service.HttpMessageLogger* to DEBUG. To
enable this logger simply use ARM set a property for the mule application 
as follows:
```
MULE_LOG_LEVEL@org.mule.service.http.impl.service.HttpMessageLogger=DEBUG
```
You can use this technique to dynamically set any logger's level.




