package com.example.myapplication.scroll;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Sunlley
 * 2020/12/6
 * --------------
 */
public class LockedNestedPagerAdapter extends FragmentPagerAdapter implements LockableNested{
    public final String TAG = getClass().getSimpleName();

    protected List<LockableNested> lockableNesteds=new ArrayList<>();
    protected List<Fragment> fragments=new ArrayList<>();

    public LockedNestedPagerAdapter(@NonNull FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        testFragments(fragments);
    }

    public LockedNestedPagerAdapter(@NonNull FragmentManager fm, int behavior,List<Fragment> fragments) {
        super(fm, behavior);
        testFragments(fragments);
    }
    private void testFragments(List<Fragment> fragments){
        this.fragments = fragments;
        if (fragments != null && !fragments.isEmpty()) {
            for (int i = 0; i < fragments.size(); i++) {
                Fragment fragment = fragments.get(i);
                if (fragment instanceof LockableNested){
                    lockableNesteds.add((LockableNested) fragment);
                }
            }
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void onLocked(boolean locked) {
        if (lockableNesteds != null) {
            for (int i = 0; i < lockableNesteds.size(); i++) {
                LockableNested lockableNested = lockableNesteds.get(i);
                lockableNested.onLocked(locked);
            }
        }

    }
}
