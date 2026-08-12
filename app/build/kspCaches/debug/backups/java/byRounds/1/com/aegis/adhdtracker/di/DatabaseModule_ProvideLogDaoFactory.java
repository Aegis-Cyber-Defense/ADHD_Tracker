package com.aegis.adhdtracker.di;

import com.aegis.adhdtracker.data.local.AppDatabase;
import com.aegis.adhdtracker.data.local.LogDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DatabaseModule_ProvideLogDaoFactory implements Factory<LogDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideLogDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public LogDao get() {
    return provideLogDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideLogDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideLogDaoFactory(dbProvider);
  }

  public static LogDao provideLogDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideLogDao(db));
  }
}
