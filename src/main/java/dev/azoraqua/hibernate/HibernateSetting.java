package dev.azoraqua.hibernate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface HibernateSetting<T> {
    @NotNull
    String getProperty();

    @Nullable
    T getDefaultValue();

    /**
     *
     * @return When any values are allowed, return null.
     */
    @Nullable
    T[] getOptions();

    // Helper methods

    static <T> HibernateSetting<T> custom(@NotNull String property) {
        return new HibernateSetting<T>() {
            @Override
            public @NotNull String getProperty() {
                return property;
            }

            @Override
            public @Nullable T getDefaultValue() {
                return null;
            }

            @Override
            public @Nullable T[] getOptions() {
                return null;
            }
        };
    }

    static <T extends Enum<?>> HibernateSetting<T> ofEnum(@NotNull String property, @NotNull Class<T> clazz) {
        return new HibernateSetting<T>() {
            @Override
            public @NotNull String getProperty() {
                return property;
            }

            @Override
            public @Nullable T getDefaultValue() {
                return null;
            }

            @Override
            public @Nullable T[] getOptions() {
                return clazz.getEnumConstants();
            }
        };
    }

    static HibernateSetting<String> ofString(@NotNull String property, @Nullable String defaultValue, @Nullable String[] options) {
        return new HibernateSetting<>() {
            @Override
            public @NotNull String getProperty() {
                return property;
            }

            @Override
            public @Nullable String getDefaultValue() {
                return defaultValue;
            }


            @Nullable
            @Override
            public String[] getOptions() {
                return options;
            }
        };
    }

    static HibernateSetting<Integer> ofInteger(@NotNull String property, int defaultValue, @Nullable Integer[] options) {
        return new HibernateSetting<>() {
            @Override
            public @NotNull String getProperty() {
                return property;
            }

            @Override
            public @NotNull Integer getDefaultValue() {
                return defaultValue;
            }

            @Nullable
            @Override
            public Integer[] getOptions() {
                return options;
            }
        };
    }

    static HibernateSetting<Boolean> ofBoolean(@NotNull String property, boolean defaultValue) {
        return new HibernateSetting<>() {
            @Override
            public @NotNull String getProperty() {
                return property;
            }

            @Override
            public @NotNull Boolean getDefaultValue() {
                return defaultValue;
            }

            @Override
            public @NotNull Boolean[] getOptions() {
                return null;
            }
        };
    }
}
