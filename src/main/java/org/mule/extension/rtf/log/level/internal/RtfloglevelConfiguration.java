package org.mule.extension.rtf.log.level.internal;

import org.mule.runtime.api.lifecycle.Initialisable;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Operations(RtfloglevelOperations.class)
@ConnectionProviders(RtfloglevelConnectionProvider.class)
public class RtfloglevelConfiguration implements Initialisable {

  @Parameter
  private String configId;

  public String getConfigId(){
    return configId;
  }

  private static final Logger LOGGER = LoggerFactory.getLogger("org.mule.runtime.core.internal.processor.LoggerMessageProcessor");

  public void initialise() {
      final String LOGGER_PREFIX = "MULE_LOG_LEVEL-";

      for(String propertyName : System.getProperties().stringPropertyNames())
      {
          if (propertyName.startsWith(LOGGER_PREFIX)) {

              String loggerName = propertyName.substring(LOGGER_PREFIX.length());
              String levelName = System.getProperty(propertyName, "");
              Level level = Level.toLevel(levelName); // defaults to DEBUG
              if (!"".equals(levelName) && !levelName.toUpperCase().equals(level.toString())) {
                  LOGGER.info("MULE_LOG_LEVEL: logger " + loggerName + " invalid Level: " + levelName);
                  continue;
              }
              LOGGER.info("MULE_LOG_LEVEL: logger " + loggerName + " to " + levelName);

              Configurator.setLevel(loggerName, level);

            }
        }
  }
}