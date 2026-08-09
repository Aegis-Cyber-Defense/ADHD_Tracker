package com.aegis.adhdtracker.di;

import android.content.Context;
import com.aegis.adhdtracker.data.health.HealthConnectManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class HealthModule_ProvideHealthConnectManagerFactory implements Factory<HealthConnectManager> {
  private final Provider<Context> contextProvider;

  public HealthModule_ProvideHealthConnectManagerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public HealthConnectManager get() {
    return provideHealthConnectManager(contextProvider.get());
  }

  public static HealthModule_ProvideHealthConnectManagerFactory create(
      Provider<Context> contextProvider) {
    return new HealthModule_ProvideHealthConnectManagerFactory(contextProvider);
  }

  public static HealthConnectManager provideHealthConnectManager(Context context) {
    return Preconditions.checkNotNullFromProvides(HealthModule.INSTANCE.provideHealthConnectManager(context));
  }
}
