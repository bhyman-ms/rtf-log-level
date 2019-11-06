package org.mule.extension.rtf.log.level.internal;

import org.mule.runtime.api.lifecycle.Initialisable;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */

public class RtfloglevelConfiguration implements Initialisable {


  @Parameter
  @Example("MULE_LOG_LEVEL@")
  @DisplayName("Property Prepender")
  @Summary("Identifies the property prepender string that indicates the property is intended to be used to set dynamic log levels")
  private String propertyPrepender;

  public String getPropertyPrepender(){
    return propertyPrepender;
  }

  private static final Logger LOGGER = LoggerFactory.getLogger("org.mule.runtime.core.internal.processor.LoggerMessageProcessor");

  public void initialise() {

      for(String propertyName : System.getProperties().stringPropertyNames())
      {
          if (propertyName.startsWith(this.propertyPrepender)) {

              String loggerName = propertyName.substring(this.propertyPrepender.length());
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