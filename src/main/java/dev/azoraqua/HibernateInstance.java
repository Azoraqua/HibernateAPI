package dev.azoraqua;

import jakarta.persistence.RollbackException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@SuppressWarnings("deprecation")
public final class HibernateInstance implements AutoCloseable {
    private final Map<String, Object> properties = new HashMap<>();
    private final List<Class<? extends Persistable<?>>> entities = new ArrayList<>();
    private SessionFactory sessionFactory;

    @Contract("_, _ -> this")
    @NotNull
    public <T> HibernateInstance withSetting(HibernateSetting<T> setting, T value) {
        validateSetting(setting, value);

        properties.put(setting.property(), value == null
                ? String.valueOf(setting.defaultValue())
                : String.valueOf(value));
        return this;
    }

    @Contract("_, _ -> this")
    @NotNull
    public <T> HibernateInstance withCustomSetting(@NotNull String property, @NotNull T value) {
        properties.put(property, String.valueOf(value));
        return this;
    }

    public HibernateInstance withEntity(Class<? extends Persistable<?>> entity) {
        entities.add(entity);
        return this;
    }

    @Contract("-> this")
    @NotNull
    public HibernateInstance connect() throws HibernateException {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(properties)
                .build();

        final Metadata metadata = new MetadataSources(registry)
                .addAnnotatedClasses(entities.toArray(Class[]::new))
                .buildMetadata();

        this.sessionFactory = metadata.buildSessionFactory();
        return this;
    }

    @Override
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public void save(Persistable<?> entity) throws RollbackException {
        if (!entities.contains(entity.getClass())) {
            throw new IllegalStateException("Entity type is not registered.");
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(entity);
            session.getTransaction().commit();
        }
    }

    public void delete(Persistable<?> entity) throws RollbackException {
        if (!entities.contains(entity.getClass())) {
            throw new IllegalStateException("Entity type is not registered.");
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }
    }

    @Nullable
    public <ID, T extends Persistable<ID>> T find(Class<T> clazz, ID id) {
        if (!entities.contains(clazz)) {
            throw new IllegalStateException("Entity type is not registered.");
        }

        try (Session session = sessionFactory.openSession()) {
            return session.find(clazz, id);
        }
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
