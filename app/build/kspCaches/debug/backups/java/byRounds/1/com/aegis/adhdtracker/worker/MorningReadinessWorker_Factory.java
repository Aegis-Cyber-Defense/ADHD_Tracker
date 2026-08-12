package com.aegis.adhdtracker.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.aegis.adhdtracker.data.health.HealthConnectManager;
import com.aegis.adhdtracker.data.remote.GeminiService;
import dagger.internal.DaggerGenerated;
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
public final class MorningReadinessWorker_Factory {
  private final Provider<HealthConnectManager> healthConnectManagerProvider;

  private final Provider<GeminiService> geminiServiceProvider;

  public MorningReadinessWorker_Factory(Provider<HealthConnectManager> healthConnectManagerProvider,
      Provider<GeminiService> geminiServiceProvider) {
    this.healthConnectManagerProvider = healthConnectManagerProvider;
    this.geminiServiceProvider = geminiServiceProvider;
  }

  public MorningReadinessWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, healthConnectManagerProvider.get(), geminiServiceProvider.get());
  }

  public static MorningReadinessWorker_Factory create(
      Provider<HealthConnectManager> healthConnectManagerProvider,
      Provider<GeminiService> geminiServiceProvider) {
    return new MorningReadinessWorker_Factory(healthConnectManagerProvider, geminiServiceProvider);
  }

  public static MorningReadinessWorker newInstance(Context context, WorkerParameters workerParams,
      HealthConnectManager healthConnectManager, GeminiService geminiService) {
    return new MorningReadinessWorker(context, workerParams, healthConnectManager, geminiService);
  }
}
