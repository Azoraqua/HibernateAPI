package dev.azoraqua.hibernate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public final class HibernateAPI {

    public static HibernateInstanceBuilder init() {
        return new HibernateInstanceBuilder();
    }
}
