package com.uranus.economy.nav;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.uranus.economy.R;
import com.uranus.economy.base.BaseFragment;
import com.uranus.economy.fragment.Fragment1;
import com.uranus.economy.fragment.Fragment2;
import com.uranus.economy.fragment.Fragment3;
import com.uranus.economy.fragment.Fragment4;
import com.uranus.economy.fragment.Fragment5;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class NavFragment extends BaseFragment implements View.OnClickListener, View.OnLongClickListener {
    @BindView(R.id.nav_1)
    NavigationButton mNav1;
    @BindView(R.id.nav_2)
    NavigationButton mNav2;
    @BindView(R.id.nav_3)
    NavigationButton mNav3;
    @BindView(R.id.nav_4)
    NavigationButton mNav4;
    @BindView(R.id.nav_5)
    NavigationButton mNav5;
    private Context mContext;
    private int mContainerId;
    private boolean isInit = false;
    private FragmentManager mFragmentManager;
    private NavigationButton mCurrentNavButton;
    private OnNavigationClickListener mOnNavigationClickListener;

    public NavFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nav;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

//        mNav1.init(R.drawable.tab_icon_1,
//                R.string.text1,
//                Fragment1.class);
//
//        mNav2.init(R.drawable.tab_icon_3,
//                R.string.text2,
//                Fragment2.class);
//
//        mNav3.init(R.drawable.tab_icon_2,
//                R.string.text3,
//                Fragment3.class);
//
//        mNav4.init(R.drawable.tab_icon_4,
//                R.string.text4,
//                Fragment4.class);

        mNav1.init(R.drawable.tab_icon_1,
                R.string.text1,
                Fragment1.class);

        mNav2.init(R.drawable.tab_icon_3,
                R.string.text2,
                Fragment2.class);

        mNav3.init(R.drawable.tab_icon_2,
                R.string.text3,
                Fragment3.class);

        mNav4.init(R.drawable.tab_icon_4,
                R.string.text4,
                Fragment4.class);
        mNav5.init(R.drawable.tab_icon_3,
                R.string.text5,
                Fragment5.class);


    }

    @OnClick({R.id.nav_1, R.id.nav_2,
            R.id.nav_3, R.id.nav_4,R.id.nav_5})
    @Override
    public void onClick(View v) {
        if (v instanceof NavigationButton) {
            NavigationButton nav = (NavigationButton) v;
            doSelect(nav);
        }
    }

    @OnLongClick({R.id.nav_1})
    @Override
    public boolean onLongClick(View v) {

        return false;
    }

    public void setup(Context context, FragmentManager fragmentManager, int contentId, OnNavigationClickListener listener) {
        isInit = false;
        mContext = context;
        mFragmentManager = fragmentManager;
        mContainerId = contentId;
        mOnNavigationClickListener = listener;
        // do clear
        clearCacheFragment();
        clearOldFragment();
        // do select first
        doSelect(mNav1);
        isInit = true;
    }

    private void clearCacheFragment() {
//        mCurrentNavButton = null;
        if (mCurrentNavButton!=null){
            mCurrentNavButton.setSelected(false);
        }
        mNav1.clearFragment();
        mNav2.clearFragment();
        mNav3.clearFragment();
        mNav4.clearFragment();
        mNav5.clearFragment();
    }

    @SuppressWarnings("RestrictedApi")
    private void clearOldFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (transaction == null || fragments == null || fragments.size() == 0)
            return;
        boolean doCommit = false;
        for (Fragment fragment : fragments) {
            if (fragment != this && fragment != null) {
                transaction.remove(fragment);
                doCommit = true;
            }
        }
        if (doCommit)
            transaction.commitNow();
    }

    private void doSelect(NavigationButton newNavButton) {
        // If the new navigation is me info fragment, we intercept it
        /*
        if (newNavButton == mNavMe) {
            if (interceptMessageSkip())
                return;
        }
        */

        NavigationButton oldNavButton = null;
        if (mCurrentNavButton != null && isInit) {
            oldNavButton = mCurrentNavButton;
            if (oldNavButton == newNavButton) {
                onReselect(oldNavButton);
                return;
            }
            oldNavButton.setSelected(false);
        }
        newNavButton.setSelected(true);
        doTabChanged(oldNavButton, newNavButton);
        onSelect(newNavButton);
        mCurrentNavButton = newNavButton;
    }

    private void doTabChanged(NavigationButton oldNavButton, NavigationButton newNavButton) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (oldNavButton != null) {
            if (oldNavButton.getFragment() != null) {
                ft.detach(oldNavButton.getFragment());
            }
        }
        if (newNavButton != null) {
            if (newNavButton.getFragment() == null) {
                Fragment fragment = Fragment.instantiate(mContext,
                        newNavButton.getClx().getName(), null);
                ft.add(mContainerId, fragment, newNavButton.getTag());
                newNavButton.setFragment(fragment);
            } else {
                ft.attach(newNavButton.getFragment());
            }
        }
        ft.commit();
    }

    /**
     * 拦截底部点击，当点击个人按钮时进行消息跳转
     */
    private boolean interceptMessageSkip() {
        if (1 == 2) {
            return true;
        }
        return false;
    }

    private void onReselect(NavigationButton navigationButton) {
        OnNavigationClickListener listener = mOnNavigationClickListener;
        if (listener != null) {
            listener.onReselect(navigationButton);
        }
    }
    private void onSelect(NavigationButton navigationButton) {
        OnNavigationClickListener listener = mOnNavigationClickListener;
        if (listener != null) {
            listener.onSelect(navigationButton);
        }
    }

    public interface OnNavigationClickListener {
        void onReselect(NavigationButton navigationButton);
        void onSelect(NavigationButton navigationButton);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initData() {
        super.initData();
    }
}

