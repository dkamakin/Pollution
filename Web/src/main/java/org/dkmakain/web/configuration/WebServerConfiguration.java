package org.dkmakain.web.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class WebServerConfiguration {

    static final class Token {

        static final String JETTY = "jetty";
        static final String TYPE  = "type";
    }

    private final JettyConfiguration jetty;
    private final ServerType         type;

    @JsonCreator
    public WebServerConfiguration(@JsonProperty(Token.JETTY) JettyConfiguration jetty,
                                  @JsonProperty(Token.TYPE) ServerType type) {
        this.jetty = jetty;
        this.type  = type;
    }

    @JsonProperty(Token.JETTY)
    public JettyConfiguration getJetty() {
        return jetty;
    }

    @JsonProperty(Token.TYPE)
    public ServerType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WebServerConfiguration that = (WebServerConfiguration) o;
        return Objects.equal(jetty, that.jetty) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(jetty, type);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add(Token.JETTY, jetty)
                          .add(Token.TYPE, type)
                          .toString();
    }
}
