package org.dkmakain.web.configuration;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class CommonConfiguration {

    public Integer port;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("port", port)
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
        CommonConfiguration that = (CommonConfiguration) o;
        return Objects.equal(port, that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(port);
    }
}
