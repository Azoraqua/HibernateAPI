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

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class HibernateInstanceBuilder {
    private final Map<String, Object> properties = new HashMap<>();
    private final Set<Class<? extends Persistable<?>>> entities = new HashSet<>();

    @Contract("_, _ -> this")
    @NotNull
    public <T> HibernateInstanceBuilder withSetting(HibernateSetting<T> setting, T value) {
        validateSetting(setting, value);

        properties.put(setting.property(), value == null
                ? String.valueOf(setting.defaultValue())
                : String.valueOf(value));
        return this;
    }

    @Contract("_, _ -> this")
    @NotNull
    public <T> HibernateInstanceBuilder withCustomSetting(@NotNull String property, @NotNull T value) {
        properties.put(property, String.valueOf(value));
        return this;
    }

    public HibernateInstanceBuilder withEntity(Class<? extends Persistable<?>> entity) {
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

    private void validateSetting(HibernateSetting<?> setting, Object value) {
        if (setting == null) {
            throw new IllegalArgumentException("Setting cannot be null");
        }

        final Object[] options = setting.options();
        final Object defaultValue = setting.defaultValue();

        if (value == null && defaultValue == null) {
            throw new IllegalArgumentException("Setting value cannot be null when there is no default provided");
        }

        if (options != null && Arrays.stream(options).noneMatch(option -> option.equals(value))) {
            throw new IllegalArgumentException("Setting value is not valid");
        }
    }
}
