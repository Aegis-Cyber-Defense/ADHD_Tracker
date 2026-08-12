package com.aegis.adhdtracker.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MorningReadinessWorker_AssistedFactory_Impl implements MorningReadinessWorker_AssistedFactory {
  private final MorningReadinessWorker_Factory delegateFactory;

  MorningReadinessWorker_AssistedFactory_Impl(MorningReadinessWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public MorningReadinessWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<MorningReadinessWorker_AssistedFactory> create(
      MorningReadinessWorker_Factory delegateFactory) {
    return InstanceFactory.create(new MorningReadinessWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<MorningReadinessWorker_AssistedFactory> createFactoryProvider(
      MorningReadinessWorker_Factory delegateFactory) {
    return InstanceFactory.create(new MorningReadinessWorker_AssistedFactory_Impl(delegateFactory));
  }
}
