package org.dkamakin.common.configuration.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class TestConfiguration {

    static final class Token {

        static final String NUMBER = "number";
        static final String STRING = "string";
    }

    private final Integer number;
    private final String  string;

    @JsonCreator
    public TestConfiguration(@JsonProperty(Token.NUMBER) Integer number,
                             @JsonProperty(Token.STRING) String string) {
        this.number = number;
        this.string = string;
    }

    @JsonProperty(Token.NUMBER)
    public Integer getNumber() {
        return number;
    }

    @JsonProperty(Token.STRING)
    public String getString() {
        return string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TestConfiguration that = (TestConfiguration) o;
        return Objects.equal(number, that.number) &&
            Objects.equal(string, that.string);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number, string);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("number", number)
                          .add("string", string)
                          .toString();
    }
}
