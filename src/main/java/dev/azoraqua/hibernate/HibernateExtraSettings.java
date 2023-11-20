package dev.azoraqua.hibernate;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Data
public final class HibernateExtraSettings<T> implements HibernateSetting<T> {
    private final @NotNull String property;
    private final @Nullable T defaultValue;
    private final @Nullable T[] options;

    @NotNull
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
    public static final HibernateSetting<HBM2DllAutoMode> HBM2DLL_AUTO = HibernateSetting.ofEnum(
            "hibernate.hbm2ddl.auto",
            HBM2DllAutoMode.class,
            HBM2DllAutoMode.UPDATE
    );

    public static enum HBM2DllAutoMode {
        CREATE, UPDATE, CREATE_DROP, DROP, NONE
    }
}
