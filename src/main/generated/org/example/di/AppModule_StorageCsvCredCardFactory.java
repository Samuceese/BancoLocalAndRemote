package org.example.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import org.example.creditcard.storage.StorageCsvCredCard;

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
    "KotlinInternalInJava",
    "cast"
})
public final class AppModule_StorageCsvCredCardFactory implements Factory<StorageCsvCredCard> {
  private final AppModule module;

  public AppModule_StorageCsvCredCardFactory(AppModule module) {
    this.module = module;
  }

  @Override
  public StorageCsvCredCard get() {
    return storageCsvCredCard(module);
  }

  public static AppModule_StorageCsvCredCardFactory create(AppModule module) {
    return new AppModule_StorageCsvCredCardFactory(module);
  }

  public static StorageCsvCredCard storageCsvCredCard(AppModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.storageCsvCredCard());
  }
}