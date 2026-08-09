package com.aegis.adhdtracker.ui.logging;

import com.aegis.adhdtracker.data.repository.LogRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class LogViewModel_Factory implements Factory<LogViewModel> {
  private final Provider<LogRepository> repositoryProvider;

  public LogViewModel_Factory(Provider<LogRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public LogViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static LogViewModel_Factory create(Provider<LogRepository> repositoryProvider) {
    return new LogViewModel_Factory(repositoryProvider);
  }

  public static LogViewModel newInstance(LogRepository repository) {
    return new LogViewModel(repository);
  }
}
