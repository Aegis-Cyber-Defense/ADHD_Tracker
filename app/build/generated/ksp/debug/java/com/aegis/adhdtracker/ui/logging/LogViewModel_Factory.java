package com.aegis.adhdtracker.ui.logging;

import com.aegis.adhdtracker.data.health.HealthConnectManager;
import com.aegis.adhdtracker.data.remote.GeminiService;
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

  private final Provider<HealthConnectManager> healthConnectManagerProvider;

  private final Provider<GeminiService> geminiServiceProvider;

  public LogViewModel_Factory(Provider<LogRepository> repositoryProvider,
      Provider<HealthConnectManager> healthConnectManagerProvider,
      Provider<GeminiService> geminiServiceProvider) {
    this.repositoryProvider = repositoryProvider;
    this.healthConnectManagerProvider = healthConnectManagerProvider;
    this.geminiServiceProvider = geminiServiceProvider;
  }

  @Override
  public LogViewModel get() {
    return newInstance(repositoryProvider.get(), healthConnectManagerProvider.get(), geminiServiceProvider.get());
  }

  public static LogViewModel_Factory create(Provider<LogRepository> repositoryProvider,
      Provider<HealthConnectManager> healthConnectManagerProvider,
      Provider<GeminiService> geminiServiceProvider) {
    return new LogViewModel_Factory(repositoryProvider, healthConnectManagerProvider, geminiServiceProvider);
  }

  public static LogViewModel newInstance(LogRepository repository,
      HealthConnectManager healthConnectManager, GeminiService geminiService) {
    return new LogViewModel(repository, healthConnectManager, geminiService);
  }
}
