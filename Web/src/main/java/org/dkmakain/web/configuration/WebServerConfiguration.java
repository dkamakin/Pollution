package org.dkmakain.web.configuration;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class WebServerConfiguration {

    public JettyConfiguration jetty;
    public ServerType         type;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("jetty", jetty)
                          .add("type", type)
                          .toString();
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
        return Objects.equal(jetty, that.jetty) &&
            type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(jetty, type);
    }
}
