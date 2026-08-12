package com.aegis.adhdtracker;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ADHDTrackerApp_MembersInjector implements MembersInjector<ADHDTrackerApp> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public ADHDTrackerApp_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<ADHDTrackerApp> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new ADHDTrackerApp_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(ADHDTrackerApp instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.aegis.adhdtracker.ADHDTrackerApp.workerFactory")
  public static void injectWorkerFactory(ADHDTrackerApp instance, HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
