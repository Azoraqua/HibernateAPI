package dev.azoraqua.hibernate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface HibernateSetting<T> {
    @NotNull
    String property();

    @Nullable
    T defaultValue();

    /**
     *
     * @return When any values are allowed, return null.
     */
    @Nullable
    T[] options();

    // Helper methods

    static <T> HibernateSetting<T> custom(String property) {
        return new HibernateSetting<T>() {
            @Override
            public @NotNull String property() {
                return property;
            }

            @Override
            public @Nullable T defaultValue() {
                return null;
            }

            @Override
            public @Nullable T[] options() {
                return null;
            }
        };
    }

    static HibernateSetting<String> ofString(String property, String defaultValue, String[] options) {
        return new HibernateSetting<>() {
            @Override
            public @NotNull String property() {
                return property;
            }

            @Override
            public @NotNull String defaultValue() {
                return defaultValue;
            }


            @Nullable
            @Override
            public String[] options() {
                return options;
            }
        };
    }

    static HibernateSetting<Integer> ofInteger(String property, int defaultValue, Integer[] options) {
        return new HibernateSetting<>() {
            @Override
            public @NotNull String property() {
                return property;
            }

            @Override
            public @NotNull Integer defaultValue() {
                return defaultValue;
            }

            @Nullable
            @Override
            public Integer[] options() {
                return options;
            }
        };
    }

    static HibernateSetting<Boolean> ofBoolean(String property, boolean defaultValue) {
        return new HibernateSetting<>() {
            @Override
            public @NotNull String property() {
                return property;
            }

            @Override
            public @NotNull Boolean defaultValue() {
                return defaultValue;
            }

            @Override
            public @NotNull Boolean[] options() {
                return null;
            }
        };
    }
}
