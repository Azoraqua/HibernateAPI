package dev.azoraqua;

import org.jetbrains.annotations.NotNull;

public record HibernateExtraSettings<T>(String property, T defaultValue, T[] options) implements HibernateSetting<T> {

    public static final HibernateSetting<Integer> POOL_SIZE = HibernateSetting.ofInteger(
            "hibernate.connection.pool_size",
            10,
            null
    );

    @NotNull
    public static final HibernateSetting<Boolean> AUTO_COMMIT = HibernateSetting.ofBoolean(
            "hibernate.connection.autocommit",
            true
    );

    @NotNull
    public static final HibernateSetting<Boolean> SHOW_SQL = HibernateSetting.ofBoolean(
            "hibernate.show_sql",
            true
    );

    @NotNull
    public static final HibernateSetting<Boolean> FORMAT_SQL = HibernateSetting.ofBoolean(
            "hibernate.format_sql",
            true
    );

    @NotNull
    public static final HibernateSetting<String> HBM2DLL_AUTO = HibernateSetting.ofString(
            "hibernate.hbm2ddl.auto",
            "update",
            new String[] {"create", "update", "create-drop", "drop", "none" }
    );
}
