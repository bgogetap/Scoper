[![Build Status](https://travis-ci.org/bgogetap/Scoper.svg?branch=master)](https://travis-ci.org/bgogetap/Scoper) [ ![Download](https://api.bintray.com/packages/bgogetap/android/Scoper/images/download.svg) ](https://bintray.com/bgogetap/android/Scoper/_latestVersion)

# Scoper - Lightweight Dagger 2 Component Management


## Quick Start
Setup will depend on how you want to build your scopes. The following instructions will assume you have scopes as follows:

Application Scope->Activity Scope->View scope...

Each subsequent scope is a subscope built on top of the previous scope.

Create an `Application` subclass. In it you will initialize the component cache as well as the Application Scope's component.

*The only required step for using this library is instantiating `ScoperCache` and returning it in `getSystemService` as shown*

```java
public class MyApplication extends Application {

  public static final String SCOPE_TAG = "application_scope";
  
  private ScoperCache componentCache;
  
  @Override public void onCreate() {
      super.onCreate();
      componentCache = new ScoperCache();
      Scoper.cacheComponent(this, SCOPE_TAG, DaggerApplicationComponent.builder()
              .applicationModule(new ApplicationModule(this))
              .build());
  }

  @Override public Object getSystemService(String name) {
      if (name.equals(ScoperCache.SERVICE_NAME)) {
          return scoperCache;
      }
      return super.getSystemService(name);
  }
}
```

Next, in each `Activity`, you should override the `attachBaseContext(Context newBase)` method and return an instance of `ScoperContext` as shown. Doing this in a base `Activity` class can simplify the process. The `Activity` should also implement the `Scoped` interface. This has you supply a scope name that will be used to map the component in the cache. The Type Parameter for the `Scoped` interface is the component class for that scope.

```java
public abstract class BaseActivity<T> extends AppCompatActivity implements Scoped<T> {
    
    protected T component;
    
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component = Scoper.createComponent(this, initComponent());
    }
    
    @Override protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ScoperContext(newBase, getScopeName()));
    }
    
    @Override public String getScopeName() {
        return getClass().getName();
    }
    
    protected abstract T initComponent();
    
    @Override protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            Scoper.destroyScope(this);
        }
    }
}
```
```java
final class MainActivity extends BaseActivity<MainComponent> {
  
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component.inject(this);
    }
    
    @Override protected MainComponent initComponent() {
        return Scoper.getComponentForTag<ApplicationComponent>(MyApplication.SCOPE_TAG, this)
                  .plus(new MainModule());
    }
}
```

This setup should be the same whether you use Fragments/Plain Views/Conductor/etc. for the building blocks of your UI.

How you do the next level of scopes will vary depending on your choice of UI management. Demos are provided to show possible solutions (more on the way).

This is just one potential way to set up the boilerplate for scoping your components. `Scoper` has methods like `cacheComponent` and `getComponentForTag` that allow you to manually manage your components without having to implement the `Scoped` interface or use `ScoperContext`.

As mentioned before, the only step you are required to take is instantiating `ScoperCache` in your `Application` subclass, and returning it as shown above in `getSystemService`.

Add to your Gradle dependencies:

```groovy
buildscript {
    repositories {
        jcenter()
    }
}

dependencies {
    compile 'com.brandongogetap:scoper:0.2'
}
```

### Coming soon - Other demos
