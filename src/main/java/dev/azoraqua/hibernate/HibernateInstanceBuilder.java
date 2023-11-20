package dev.azoraqua.hibernate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class HibernateInstanceBuilder {
    @NotNull
    private final Map<String, Object> properties = new HashMap<>();
    @NotNull
    private final Set<Class<? extends Persistable<?>>> entities = new HashSet<>();

    @Contract("_, _ -> this")
    @NotNull
    public <T> HibernateInstanceBuilder withSetting(@NotNull HibernateSetting<T> setting, @Nullable T value) {
        validateSetting(setting, value);

        if (value instanceof HibernateStandardSettings.Driver driver) {
            return withCustomSetting(setting.getProperty(), driver.getDriverClass());
        } else if (value instanceof HibernateExtraSettings.HBM2DllAutoMode mode) {
            return withCustomSetting(setting.getProperty(), mode.getValue());
        } else {
            properties.put(setting.getProperty(), value == null
                    ? String.valueOf(setting.getDefaultValue())
                    : String.valueOf(value));
        }

        return this;
    }

    @Contract("_, _ -> this")
    @NotNull
    public <T> HibernateInstanceBuilder withCustomSetting(@NotNull String property, @NotNull T value) {
        properties.put(property, String.valueOf(value));
        return this;
    }

    @Contract("_ -> this")
    @NotNull
    public HibernateInstanceBuilder withEntity(@NotNull Class<? extends Persistable<?>> entity) {
        entities.add(entity);
        return this;
    }

    @Contract("-> new")
    @NotNull
    public HibernateInstance connect() throws HibernateException {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(properties)
                .build();

        final Metadata metadata = new MetadataSources(registry)
                .addAnnotatedClasses(entities.toArray(Class[]::new))
                .buildMetadata();

        final SessionFactory sessionFactory = metadata.buildSessionFactory();

        return new HibernateInstance(entities, sessionFactory);
    }

    private void validateSetting(@Nullable HibernateSetting<?> setting, @Nullable Object value) {
        if (setting == null) {
            throw new IllegalArgumentException("Setting cannot be null");
        }

        final Object[] options = setting.getOptions();
        final Object defaultValue = setting.getDefaultValue();

        if (value == null && defaultValue == null) {
            throw new IllegalArgumentException("Setting value cannot be null when there is no default provided");
        }

        if (options != null && Arrays.stream(options).noneMatch(option -> option.equals(value))) {
            throw new IllegalArgumentException("Setting value is not valid");
        }
    }
}
