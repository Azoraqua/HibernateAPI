package dev.azoraqua.hibernate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public final class HibernateAPI {

    @Contract("-> new")
    @NotNull
    public static HibernateInstanceBuilder init() {
        return new HibernateInstanceBuilder();
    }
}
