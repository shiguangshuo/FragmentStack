package com.xiaoying.fragmentstack.library;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Fragment基类
 * Created by yinglovezhuzhu@gmail.com on 2015/10/30.
 */
public class BaseFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void onBackPressed() {

    }

    /**
     * 设置结果，用于{@linkplain #startActivityForResult(Intent, int)}返回结果
     * @param resultCode 结果码
     */
    protected void setResult(int resultCode) {
        Activity activity = getActivity();
        if(null != activity) {
            activity.setResult(resultCode);
        }
    }

    /**
     * 设置结果，用于{@linkplain #startActivityForResult(Intent, int)}返回结果
     * @param resultCode 结果码
     * @param data 结果数据
     */
    protected void setResult(int resultCode, Intent data) {
        Activity activity = getActivity();
        if(null != activity) {
            activity.setResult(resultCode, data);
        }
    }

    /**
     * 退出<br/><br/>
     * 回调{@linkplain BaseActivity#exit()}
     */
    protected void exit() {
        Activity activity = getActivity();
        if(activity instanceof BaseActivity) {
            ((BaseActivity) activity).exit();
        }
    }

    /**
     * 返回上一级，如果没有上一级，回调{@linkplain BaseActivity#exit()}
     */
    protected void back() {
        back(1);
    }

    /**
     * 返回上level个级页面，如果栈中Fragment的数量小于等于level个，回调{@linkplain BaseActivity#exit()}
     * @param level 返回页面个数
     */
    protected void back(int level) {
        Activity activity = getActivity();
        if(activity instanceof BaseActivity) {
            ((BaseActivity) activity).popBack(level);
        }
    }

    /**
     * 回到第一个页面
     */
    protected void backToHome() {
        Activity activity = getActivity();
        if(activity instanceof BaseActivity) {
            int entryCount = ((BaseActivity) activity).getBackStackEntryCount();
            if(entryCount > 1) {
                back( entryCount - 1);
            }
        }
    }

    /**
     * 打开新的Fragment（系统默认动画）
     * @param fragment Fragment对象
     * @param addToBackStack 是否加入到回退栈中
     */
    protected void openNewFragment(Fragment fragment, boolean addToBackStack) {
        Activity activity = getActivity();
        if(activity instanceof BaseActivity) {
            ((BaseActivity) activity).addFragmentToStack(fragment, addToBackStack);
        }
    }

    /**
     * 打开新的Fragment(带动画)
     * @param fragment Fragment对象
     * @param addToBackStack 是否加入到回退栈中
     * @param enter 新的Fragment出现动画
     * @param exit 旧的Fragment消失动画
     * @param popEnter 新的Fragment消失动画
     * @param popExit 旧的Fragment出现动画
     */
    protected void openNewFragmentWithAnimation(Fragment fragment, boolean addToBackStack,
                                                               @android.support.annotation.AnimRes int enter,
                                                               @android.support.annotation.AnimRes int exit,
                                                               @android.support.annotation.AnimRes int popEnter,
                                                               @android.support.annotation.AnimRes int popExit) {
        Activity activity = getActivity();
        if(activity instanceof BaseActivity) {
            ((BaseActivity) activity).addFragmentToStackWithAnimation(fragment, addToBackStack, enter, exit, popEnter, popExit);
        }
    }

    /**
     * 打开新的Fragment(无动画)
     * @param fragment Fragment对象
     * @param addToBackStack 是否加入到回退栈中
     */
    protected void openNewFragmentWithoutAnimation(Fragment fragment, boolean addToBackStack) {
        Activity activity = getActivity();
        if(activity instanceof BaseActivity) {
            ((BaseActivity) activity).addFragmentToStackWithAnimation(fragment, addToBackStack, 0, 0, 0, 0);
        }
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
     * 显示一个短Toast
     * @param text
     */
    protected void showShortToast(CharSequence text) {
        Activity activity = getActivity();
        if(null != activity) {
            Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示一个短Toast
     * @param resId
     */
    protected void showShortToast(int resId) {
        Activity activity = getActivity();
        if(null != activity) {
            Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示一个长Toast
     * @param text
     */
    protected void showLongToast(CharSequence text) {
        Activity activity = getActivity();
        if(null != activity) {
            Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示一个长Toast
     * @param resId
     */
    protected void showLongToast(int resId) {
        Activity activity = getActivity();
        if(null != activity) {
            Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取资源id，如果没有找到，返回0
     * @param name
     * @param defType
     * @return
     */
    protected int getIdentifier(String name, String defType) {
        return getResources().getIdentifier(name, defType, getActivity().getPackageName());
    }

    protected int getResourceColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 设置文本不能输入汉字
     */
    protected void forbidCNInput(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                //返回null表示此字符可以输入,返回空字符串表示禁止输入此字符
                if (source.toString().length() != source.toString().getBytes().length) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

}
