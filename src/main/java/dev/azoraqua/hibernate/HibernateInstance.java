package dev.azoraqua.hibernate;

import jakarta.persistence.RollbackException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@SuppressWarnings("deprecation")
public final class HibernateInstance implements AutoCloseable {
    private final Set<Class<? extends Persistable<?>>> entities;
    private final SessionFactory sessionFactory;

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

    @Override
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
