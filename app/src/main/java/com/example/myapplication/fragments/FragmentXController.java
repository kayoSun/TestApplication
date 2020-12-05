package com.example.myapplication.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Sunlley
 * 2020/11/27
 * -----------------
 * fragment 管理器
 * 兼容 inner生命周期
 */
public class FragmentXController {

    public static FragmentXController newInstance(Object obj) {
        if (obj instanceof FragmentActivity) {
            return new FragmentXController((FragmentActivity) obj);
        } else if (obj instanceof Fragment) {
            return new FragmentXController(((Fragment) obj));
        } else {
            throw new IllegalArgumentException("FragmentController need  a FragmentActivity or Fragment");
        }
    }

    public static FragmentXController newInstance(Object obj, ViewPager viewPager) {
        if (obj instanceof FragmentActivity) {
            return new FragmentXController((FragmentActivity) obj, viewPager);
        } else if (obj instanceof Fragment) {
            return new FragmentXController(((Fragment) obj), viewPager);
        } else {
            throw new IllegalArgumentException("FragmentController need  a FragmentActivity or Fragment");
        }
    }

    Object target;
    boolean isViewPager;//是否是viewpager模式
    boolean isChildSupport;//是否是fragment嵌套fragment
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    List<Fragment> addFragments;
    List<Fragment> hideFragments;
    Fragment lastFragment;//上一个fragment
    Fragment currentFragment;//当前的fragment
    ViewPager mViewPager;

    private FragmentXController(FragmentActivity activity) {
        isChildSupport = false;
        mFragmentManager = activity.getSupportFragmentManager();
        init();
    }

    private FragmentXController(Fragment fragment) {
        isChildSupport = true;
        mFragmentManager = fragment.getChildFragmentManager();
        init();
    }

    private FragmentXController(FragmentActivity activity, ViewPager viewPager) {
        isChildSupport = false;
        mFragmentManager = activity.getSupportFragmentManager();
        mViewPager = viewPager;
        init();

    }

    private FragmentXController(Fragment fragment, ViewPager viewPager) {
        isChildSupport = true;
        mFragmentManager = fragment.getChildFragmentManager();
        mViewPager = viewPager;
        init();
    }

    private void init() {
        addFragments = new ArrayList<>();
        hideFragments = new ArrayList<>();
        if (!isViewPager) {
            mFragmentTransaction = this.mFragmentManager.beginTransaction();
        } else {
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (!isChildSupport){
                        return;
                    }
                    lastFragment = currentFragment;
                    List<Fragment> fragments = mFragmentManager.getFragments();
                    Fragment fragment = fragments.get(position);
                    currentFragment = fragment;
                    if (lastFragment != null) {
                        lastFragment.onHiddenChanged(true);
                    }
                    if (currentFragment != null) {
                        currentFragment.onHiddenChanged(false);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

    }

    private void checkTransaction() {
        if (mFragmentTransaction == null) {
            mFragmentTransaction = mFragmentManager.beginTransaction();
        }
    }

    public Fragment getFragment() {
        return currentFragment;
    }

    public FragmentXController add(Fragment fragment, int containerId) {
        if (isViewPager) {
            return this;
        }
        checkTransaction();
        mFragmentTransaction.add(containerId, fragment);
        addFragments.add(fragment);
        return this;
    }

    public FragmentXController show(Fragment fragment) {
        if (isViewPager) {
            return this;
        }
        checkTransaction();
        mFragmentTransaction.show(fragment);
        hideFragments.remove(fragment);
        currentFragment = fragment;
        return this;
    }

    public FragmentXController hide(Fragment fragment) {
        if (isViewPager) {
            return this;
        }
        checkTransaction();
        mFragmentTransaction.hide(fragment);
        hideFragments.add(fragment);
        if (currentFragment == fragment) {
            currentFragment = null;
        }
        return this;
    }

    /**
     * 提交展示
     */
    public void commit() {
        if (isViewPager) {
            return;
        }
        if (mFragmentTransaction != null) {
            mFragmentTransaction.commit();
        }
        mFragmentTransaction = null;
        if (isChildSupport) {
            if (currentFragment != null) {
                currentFragment.onHiddenChanged(false);
            }
            if (hideFragments != null) {
                for (int i = 0; i < hideFragments.size(); i++) {
                    Fragment fragment = hideFragments.get(i);
                    fragment.onHiddenChanged(true);
                }
            }
        }
    }

    public void commitAllowingStateLoss() {
        if (isViewPager) {
            return;
        }
        if (mFragmentTransaction != null) {
            mFragmentTransaction.commitAllowingStateLoss();
        }
        mFragmentTransaction = null;
        if (isChildSupport) {
            if (currentFragment != null) {
//                currentFragment.onHiddenChanged(false);
            }
            if (hideFragments != null) {
//                for (int i = 0; i < hideFragments.size(); i++) {
//                    Fragment fragment = hideFragments.get(i);
//                    if (!fragment.isHidden()) {
//                        fragment.onHiddenChanged(true);
//                    }
//                }
            }
        }
    }


}
