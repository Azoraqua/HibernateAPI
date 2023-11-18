package dev.azoraqua.hibernate;

public record HibernateStandardSettings<T>(String property, T defaultValue, T[] options) implements HibernateSetting<T> {
   public static final HibernateSetting<String> URL = HibernateSetting.ofString("hibernate.connection.url", null, null);
   public static final HibernateSetting<String> USERNAME = HibernateSetting.ofString("hibernate.connection.username", null, null);
   public static final HibernateSetting<String> PASSWORD = HibernateSetting.ofString("hibernate.connection.password", null, null);
}
