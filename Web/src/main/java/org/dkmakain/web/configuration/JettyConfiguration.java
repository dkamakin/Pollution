package org.dkmakain.web.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JettyConfiguration extends CommonConfiguration {

    @JsonCreator
    public JettyConfiguration(@JsonProperty(Token.PORT) Integer port) {
        super(port);
    }

}
