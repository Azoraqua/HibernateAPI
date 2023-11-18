package dev.azoraqua.hibernate;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Data
public final class HibernateStandardSettings<T> implements HibernateSetting<T> {
   private final @NotNull String property;
   private final @Nullable T defaultValue;
   private final @Nullable T[] options;

   @NotNull
   public static final HibernateSetting<Driver> DRIVER = HibernateSetting.ofEnum("hibernate.connection.driver_class", Driver.class);

   @NotNull
   public static final HibernateSetting<String> URL = HibernateSetting.ofString("hibernate.connection.url", null, null);

   @NotNull
   public static final HibernateSetting<String> USERNAME = HibernateSetting.ofString("hibernate.connection.username", null, null);

   @NotNull
   public static final HibernateSetting<String> PASSWORD = HibernateSetting.ofString("hibernate.connection.password", null, null);

   void t() {

   }

   @RequiredArgsConstructor
   @Getter
   public enum Driver {
      /**
       * @implNote Use {@link Driver#MARIADB} when the url starts with <pre>jdbc:mariadb://</pre> otherwise it will result in an error.
       */
      MYSQL("com.mysql.jdbc.Driver"),

      /**
       * @implNote Use {@link Driver#MYSQL} when the url starts with <pre>jdbc:mysql://</pre> otherwise it will result in an error.
       */
      MARIADB("org.mariadb.jdbc.Driver"),

      POSTGRESQL("org.postgres.Driver");

      private final String driverClass;
   }
}
