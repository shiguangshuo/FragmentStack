package com.xiaoying.fragmentstack;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;


/**
 * Activity基类
 * Created by yinglovezhuzhu@gmail.com on 2015/12/28.
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener {

    protected FragmentManager mFragmentManager;

    protected int mContainerId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 取消异步线程任务
     * @param task
     */
    protected void cancelTask(AsyncTask task) {
        if(null != task && !task.isCancelled()) {
            task.cancel(true);
            task = null;
        }
    }

    /**
     * 入栈(系统默认动画)
     * @param fragment 入栈的Fragment
     */
    public void addFragmentToStack(Fragment fragment) {
        // Add the fragment to the activity, pushing this transaction
        // on to the back stack.
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        final String tag = fragment.getClass().getName() + "@" + fragment.hashCode();
        ft.replace(mContainerId, fragment, tag);
        ft.addToBackStack(tag); // 将name设置成和tag一样，那样就可以在BackStack中通过entryName（tag）来找到Fragment
        ft.commit();
    }

    /**
     * 带自定义动画的入栈
     * @param fragment Fragment对象
     * @param enter 新的Fragment出现动画
     * @param exit 旧的Fragment消失动画
     * @param popEnter 新的Fragment消失动画
     * @param popExit 旧的Fragment出现动画
     */
    public void addFragmentToStackWithAnimation(Fragment fragment,
                                                @android.support.annotation.AnimRes int enter,
                                                @android.support.annotation.AnimRes int exit,
                                                @android.support.annotation.AnimRes int popEnter,
                                                @android.support.annotation.AnimRes int popExit) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.setCustomAnimations(enter, exit, popEnter, popExit);
        final String tag = fragment.getClass().getName() + "@" + fragment.hashCode();
        ft.replace(mContainerId, fragment, tag);
        ft.addToBackStack(tag); // 将name设置成和tag一样，那样就可以在BackStack中通过entryName（tag）来找到Fragment
        ft.commit();
    }

    /**
     * 入栈（无动画）
     * @param fragment Fragment对象
     */
    public void addFragmentToStackWithoutAnimation(Fragment fragment) {
        addFragmentToStackWithAnimation(fragment, 0, 0, 0, 0);
    }

    /**
     * 回退(如果入栈使用了动画，那么出栈自动回有动画)<br/>
     * 当栈内只有一个Fragment的时候，回调{@linkplain #exit()}
     */
    public void popBack() {
        popBack(1);
    }

    /**
     * 获取可回退页面个数总数
     * @return 可回退页面个数总数
     */
    public int getBackStackEntryCount() {
        if(null == mFragmentManager) {
            return 0;
        }
        return mFragmentManager.getBackStackEntryCount();
    }

    /**
     * 回退多级页面(如果入栈使用了动画，那么出栈自动回有动画)<br/>
     * @param level 回退页面个数，当回退的页面个数大于等于栈内Fragment数量，回调{@linkplain #exit()}
     */
    public void popBack(int level) {
        if(mFragmentManager.getBackStackEntryCount() > level) {
            // 栈内的Fragment大于level，回退到倒数第level个fragment
            mFragmentManager.popBackStack(mFragmentManager.getBackStackEntryAt(mFragmentManager.getBackStackEntryCount() - level).getId(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            // 栈内的Fragment小于等于level，退出Activity（Activity中必须要有一个Fragment存活，如果没有了Fragment，Activity销毁）
            exit();
        }
    }

    /**
     * 栈顶Fragment处理Back press事件
     */
    public void handleBackPressedByTopFragment() {
        if(mFragmentManager.getBackStackEntryCount() > 0) {
            // 栈内的Fragment大于0，将onBackPressed事件传递给栈顶的Fragment处理
            FragmentManager.BackStackEntry entry = mFragmentManager.getBackStackEntryAt(mFragmentManager.getBackStackEntryCount() - 1);
            int id = entry.getId();
            final String tag = entry.getName();
            final BaseFragment fragment = (BaseFragment) mFragmentManager.findFragmentByTag(tag);
            fragment.onBackPressed();
        } else {
            // 栈内的Fragment小于1，退出Activity
            exit();
        }
    }

    /**
     * exit
     */
    public void exit() {

    }

    /**
     * 显示一个短Toast
     * @param text
     */
    protected void showShortToast(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个短Toast
     * @param resId
     */
    protected void showShortToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个长Toast
     * @param text
     */
    protected void showLongToast(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个长Toast
     * @param resId
     */
    protected void showLongToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }
}
