
# Final Year Project

Built with AndroidX Support | Requires Android Studio Arctic Fox | 2020.3.1 or higher | Current Kotlin Version 1.7.20


### SDK Versions

compileSdkVersion 34 | buildToolsVersion "30.0.3" | minSdkVersion 26 | targetSdkVersion 34


### Figma Prototype 
https://www.figma.com/proto/693ID80HNeW9KQIJfSEozK/Wireframing-for-Fleez-(Mobile-App)?node-id=1-304&scaling=scale-down&page-id=0%3A1&starting-point-node-id=1%3A304&mode=design&t=Ltw6C1h2CZVkKSf8-1


### Libraries

1. Retrofit- REST API Call
https://square.github.io/retrofit/
2. Glide - Image Loading and caching.
https://github.com/bumptech/glide
3. Material Design Components - Google's latest Material Components.
https://material.io/develop/android
4. koin - Dependency Injection
https://insert-koin.io/



### Package Structure


```
├── appcomponents       
│ ├── di                 - Dependency Injection Components 
│ │ └── MyApp.kt
│ ├── network            - REST API Call setup
│ │ ├── ResponseCode.kt
│ │ └── RetrofitProvider.kt
│ └── ui                 - Data Binding Utilities
│     └── CustomBindingAdapter.kt
├── constants            - Constant Files
│ ├── IntegerConstants.kt
│ └── StringConstants.kt
├── extensions           - Kotlin Extension Function Files
│ └── Strings.kt
├── modules              - Application Specific code
│ └── example            - A module of Application 
│  ├── ui                - UI handling classes
│  └── data              - Data Handling classes
│    ├── viewmodel       - ViewModels for the UI
│    └── model           - Model for the UI
└── network              - REST API setup
  ├── models             - Request/Response Models
  ├── repository         - Network repository
  ├── resources          - Common classes for API
  └── RetrofitService.kt
```
