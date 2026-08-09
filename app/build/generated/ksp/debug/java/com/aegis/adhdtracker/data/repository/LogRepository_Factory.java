package com.aegis.adhdtracker.data.repository;

import com.aegis.adhdtracker.data.local.LogDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class LogRepository_Factory implements Factory<LogRepository> {
  private final Provider<LogDao> logDaoProvider;

  public LogRepository_Factory(Provider<LogDao> logDaoProvider) {
    this.logDaoProvider = logDaoProvider;
  }

  @Override
  public LogRepository get() {
    return newInstance(logDaoProvider.get());
  }

  public static LogRepository_Factory create(Provider<LogDao> logDaoProvider) {
    return new LogRepository_Factory(logDaoProvider);
  }

  public static LogRepository newInstance(LogDao logDao) {
    return new LogRepository(logDao);
  }
}
