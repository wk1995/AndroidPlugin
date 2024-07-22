package custom.android.plugin.base.dependency

object DependencyItem {
    //appcompat中默认引入了很多库，比如activity库、fragment库、core库、annotation库、drawerLayout库、appcompat-resources等
    const val appcompat = "androidx.appcompat:appcompat:1.6.1"

    /**
     * support兼容库
     * */
    const val supportV4 = "androidx.legacy:legacy-support-v4:1.0.0"

    /**
     * core包+ktx扩展函数
     * */
    const val coreKtx = "androidx.core:core-ktx:1.9.0"

    /**
     * activity+ktx扩展函数
     * */
    const val activityKtx = "androidx.activity:activity-ktx:1.8.0"

    /**
     * fragment+ktx扩展函数
     * */
    const val fragmentKtx = "androidx.fragment:fragment-ktx:1.5.1"

    /**
     * 约束布局
     * */
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.4"

    /**
     * 卡片控件
     * */
    const val cardView = "androidx.cardview:cardview:1.0.0"

    /**
     * recyclerView
     * */
    const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"

    const val material = "com.google.android.material:material:1.12.0"

    /**
     * 分包
     * */
    const val multidex = "androidx.multidex:multidex:2.0.1"
    const val junit_junit = "junit:junit:4.13.2"
    const val ext_junit = "androidx.test.ext:junit:1.1.5"
    const val espresso_espresso_core = "androidx.test.espresso:espresso-core:3.5.1"


    private const val room_version = "2.5.0"
    const val database_room_runtime = "androidx.room:room-runtime:$room_version"
    const val database_room_compiler = "androidx.room:room-compiler:$room_version"
    const val database_room_ktx = "androidx.room:room-ktx:$room_version"
    const val database_room_guava = "androidx.room:room-guava:$room_version"
    const val database_room_test = "androidx.room:room-testing:$room_version"
    const val database_room_paging = "androidx.room:room-paging:$room_version"


    const val retrofit2_adapter_rxjava = "com.squareup.retrofit2:adapter-rxjava2:2.5.0"
    const val retrofit2_converter_gson = "com.squareup.retrofit2:converter-gson:2.9.0"
    const val retrofit2_retrofit = "com.squareup.retrofit2:retrofit:2.9.0"

    const val okhttp = "com.squareup.okhttp3:okhttp:4.12.0"
    const val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:4.12.0"


    const val rxjava2_rxjava = "io.reactivex.rxjava2:rxjava:2.2.7"
    const val rxjava2_rxandroid = "io.reactivex.rxjava2:rxandroid:2.1.1"


    const val gson = "com.google.code.gson:gson:2.8.5"

    const val lifecycle_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0"
    const val lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0"

    const val lint_api = "com.android.tools.lint:lint-api:30.0.0"
    const val lint_checks = "com.android.tools.lint:lint-checks:30.0.0"

    const val android_build_gradle = "com.android.tools.build:gradle:8.1.3"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10"
    const val kotlinx_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1"
    const val kotlinx_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1"

    const val auto_service = "com.google.auto.service:auto-service:1.0-rc6"

    const val java_websocket = "org.java-websocket:Java-WebSocket:1.4.0"
    const val lifecycle_process = "androidx.lifecycle:lifecycle-process:2.7.0"
    const val facebook_android_sdk = "com.facebook.android:facebook-android-sdk:15.1.0"
    const val glide = "com.github.bumptech.glide:glide:4.16.0"
    const val lottie = "com.airbnb.android:lottie:4.2.0"
    const val firebase_core = "com.google.firebase:firebase-core:20.1.2"
    const val firebase_config = "com.google.firebase:firebase-config:21.0.2"
    const val firebase_messaging = "com.google.firebase:firebase-messaging:23.0.6"
    const val firebase_analytics = "com.google.firebase:firebase-analytics:20.1.2"
    const val google_play_services_auth = "com.google.android.gms:play-services-auth:20.1.0"
    const val leakcanary_android = "com.squareup.leakcanary:leakcanary-android:2.9.1"
    const val MPAndroidChart = "com.github.PhilJay:MPAndroidChart:v3.1.0"
    const val webkit = "androidx.webkit:webkit:1.2.0-alpha01"


}

