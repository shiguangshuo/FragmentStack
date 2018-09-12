# 项目名称FragmentStack

## 介绍
项目实现Fragment栈管理，实现Fragment页面跳转管理，添加新页面，回退上一个页面（回退上N个页面），回到首页面。页面跳转也支持添加动画。

## 实现方式
详情参考demo示例。
### 1. `Activity`继承`BaseActivity`类

```java
public class MainActivity extends BaseActivity {
}
```

### 2. 设置`Fragment`容器容器
1. `Activity`布局中需要包含一个`Fragment`容器，容器必须是`FrameLayout`

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/tv_title"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:maxLines="1"
        app:layout_constraintTop_toTopOf="parent"
        android:textSize="18sp"
        android:gravity="center"
        android:padding="8dp"
        android:textColor="@android:color/black"
        android:text="Hello World!"/>
    <View
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:background="@android:color/darker_gray"
        app:layout_constraintTop_toBottomOf="@+id/tv_title"/>
    <!-- 容器类使用FrameLayout -->
    <FrameLayout
        android:id="@+id/fl_container"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@id/tv_title" >

    </FrameLayout>

</android.support.constraint.ConstraintLayout>
```

2. 在`Activity中`设置容器id
```java
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 设置容器id
        setContainerId(R.id.fl_container);
    }
```

### 3. 往容器中添加`Fragment`页面

```java
// 第一根参数是继承自BaseFragment的Fragment对象
// 第二个参数是是否加入到回退栈中，true 添加到回退栈，可以通过回退返回上一个Fragment； false 不添加到回退栈，不可回退到上一个Fragment。
addFragmentToStackWithoutAnimation(TestFragment.newInstance(null), true);
```

### 4. `Fragment`继承`BaseFragment`
 `Fragment`继承`BaseFragment`，并在Fragment中实现页面内容。
```java
public class TestFragment extends BaseFragment {
}
```

## 接口介绍

### `BaseActivity`中接口

1. `public void addFragmentToStack(Fragment fragment, boolean addToBackStack)`入栈(系统默认动画)
2. `public void addFragmentToStackWithAnimation(Fragment fragment, boolean addToBackStack,
                                                    @android.support.annotation.AnimRes int enter,
                                                    @android.support.annotation.AnimRes int exit,
                                                    @android.support.annotation.AnimRes int popEnter,
                                                    @android.support.annotation.AnimRes int popExit)`入栈（自定义动画）
3. `public void addFragmentToStackWithoutAnimation(Fragment fragment, boolean addToBackStack)`入栈（无动画）
4. `public void popBack()` 回退(如果入栈使用了动画，那么出栈自动回有动画)，当栈内只有一个Fragment的时候，会回调`exit()`方法
5. `public void popBack(int level)` 回退多级页面(如果入栈使用了动画，那么出栈自动回有动画)，当回退的页面个数大于等于栈内Fragment数量，回调`exit()`
6. `public int getBackStackEntryCount()` 获取栈内Fragment总数
7. `public void handleBackPressedByTopFragment()` 栈顶`Fragment`处理`onBackPress`事件，根据需要在`Activity`的`onBackPressed()`中调用，会将时间传递到当前显示的`Fragment`的`onBackPressed`中
8. `exit()` 退出，`BaseActivity`中此方法是空方法，需要使用者自己实现退出功能（比如销毁Activity）


### `BaseFragment`中接口
1. `protected void setResult(int resultCode)` 设置结果，用于`Activity.startActivityForResult(Intent, int)`返回结果
2. `protected void setResult(int resultCode, Intent data)` 设置结果（带数据），用于`Activity.startActivityForResult(Intent, int)`返回结果
3. `protected void exit()` 退出，会回调`Activity`中的`exit()`
4. `public void onBackPressed()` 返回相应（条件：当前Fragment在栈顶，`Activity`中的`onBackPressed()`方法中调用了`public void handleBackPressedByTopFragment()`）
5. `protected void back()` 返回上一级，如果没有上一级，回调`exit()`
6. `protected void back(int level)` 返回上level个级页面，如果栈中Fragment的数量小于等于level个，回调`exit()`
7. `backToHome()` 回到第一个页面
8. `protected void openNewFragment(Fragment fragment, boolean addToBackStack)` 打开新的`Fragment`（系统默认动画）
9. `protected void openNewFragmentWithAnimation(Fragment fragment, boolean addToBackStack,
                                                               @android.support.annotation.AnimRes int enter,
                                                               @android.support.annotation.AnimRes int exit,
                                                               @android.support.annotation.AnimRes int popEnter,
                                                               @android.support.annotation.AnimRes int popExit)` 打开新的`Fragment`(带动画)
10. `protected void openNewFragmentWithoutAnimation(Fragment fragment)` 打开新的`Fragment`(无动画)
