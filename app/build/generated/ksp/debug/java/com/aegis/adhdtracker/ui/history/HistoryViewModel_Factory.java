package com.aegis.adhdtracker.ui.history;

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
public final class HistoryViewModel_Factory implements Factory<HistoryViewModel> {
  private final Provider<LogRepository> repositoryProvider;

  private final Provider<GeminiService> geminiServiceProvider;

  public HistoryViewModel_Factory(Provider<LogRepository> repositoryProvider,
      Provider<GeminiService> geminiServiceProvider) {
    this.repositoryProvider = repositoryProvider;
    this.geminiServiceProvider = geminiServiceProvider;
  }

  @Override
  public HistoryViewModel get() {
    return newInstance(repositoryProvider.get(), geminiServiceProvider.get());
  }

  public static HistoryViewModel_Factory create(Provider<LogRepository> repositoryProvider,
      Provider<GeminiService> geminiServiceProvider) {
    return new HistoryViewModel_Factory(repositoryProvider, geminiServiceProvider);
  }

  public static HistoryViewModel newInstance(LogRepository repository,
      GeminiService geminiService) {
    return new HistoryViewModel(repository, geminiService);
  }
}
