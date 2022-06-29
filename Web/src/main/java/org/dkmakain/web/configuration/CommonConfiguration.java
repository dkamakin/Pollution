package org.dkmakain.web.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class CommonConfiguration {

    static final class Token {

        static final String PORT = "port";
    }

    private final Integer port;

    @JsonCreator
    public CommonConfiguration(@JsonProperty(Token.PORT) Integer port) {
        this.port = port;
    }

    @JsonProperty(Token.PORT)
    public Integer getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommonConfiguration that = (CommonConfiguration) o;
        return Objects.equal(port, that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(port);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("port", port)
                          .toString();
    }
}
