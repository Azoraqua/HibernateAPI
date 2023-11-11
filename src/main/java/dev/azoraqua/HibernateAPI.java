package dev.azoraqua;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public final class HibernateAPI {

    public static HibernateInstance init() {
        return new HibernateInstance();
    }
}
