package com.huypq.balanceservice.utils;

public final class MoreObjects {

    private MoreObjects() {
    }

    public static ToStringHelper toStringHelper(Object self) {
        return new ToStringHelper(self.getClass().getSimpleName());
    }

    public static final class ToStringHelper {

        private final StringBuilder sb = new StringBuilder();
        private boolean first = true;

        private ToStringHelper(String className) {
            sb.append(className).append("{");
        }

        public ToStringHelper add(String name, Object value) {
            appendSeparator();
            sb.append(name).append("=").append(value);
            return this;
        }

        public ToStringHelper addValue(Object value) {
            appendSeparator();
            sb.append(value);
            return this;
        }

        private void appendSeparator() {
            if (!first) {
                sb.append(", ");
            }
            first = false;
        }

        @Override
        public String toString() {
            return sb.append("}").toString();
        }
    }
}
