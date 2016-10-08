# OkHttp UI Test Sample
This repository shows how to test requests/responses via OkHttp in the context of UI Test.
 
## How to execute the test
Execute OkHttpTest.java on GUI, or 
```
$ cd /path/to/okhttp-uitest-sample
$ ./gradlew connectedAndroidTest -PtestSize=large
```

## Sample app and it's test
The sample app has only a button, which just executes `GET http://example.com`.  
The UI test intercepts the request/response logs from OkHttpClient, then assert that they are generated and logged correctly.

## Motivation
Why do we need such a sort of tests?  

More complicated the developed app becomes, more frequently features tend to get degraded.  
Even a simple feature such as a connection to somewhere by pushing a button could get degraded by a casual change for other issues.  
If connections are important and tend to consume time to get tested, you better consider to implement such UI tests and execute them regularly on CI.

## Reference
This test refers to [the test of OkHttpLoggingInterceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/src/test/java/okhttp3/logging/HttpLoggingInterceptorTest.java).
