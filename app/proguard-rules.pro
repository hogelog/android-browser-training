-dontobfuscate
-dontoptimize
-verbose
-keepattributes SourceFile, LineNumberTable
-keepparameternames

-keep class org.hogel.naroubrowser.** {
    *;
}

-dontwarn javax.annotation.**
-dontwarn sun.misc.**

## rxandroid
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
   long producerNode;
   long consumerNode;
}

## roboguice
-dontwarn roboguice.activity.*Activity
-dontwarn roboguice.fragment.*Fragment

#-dontwarn roboguice.**
-keep class com.google.inject.Binder
-keepclassmembers class * {
    @com.google.inject.Inject <init>(...);
}
-keep class AnnotationDatabaseImpl

## retrolambda
-dontwarn java.lang.invoke.*

## play-service-ads
-dontwarn com.google.android.gms.internal.**
