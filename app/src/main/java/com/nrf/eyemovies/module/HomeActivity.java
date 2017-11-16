package com.nrf.eyemovies.module;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nrf.eyemovies.R;
import com.nrf.eyemovies.module.base.BaseActivity;
import com.nrf.eyemovies.module.category.fragment.CategoryFragment;
import com.nrf.eyemovies.module.feed.fragment.FeedFragment;
import com.nrf.eyemovies.module.follow.fragment.FollowFragment;
import com.nrf.eyemovies.module.profile.fragment.ProfileFragment;

import butterknife.BindView;

/**
 * @author Administrator
 */
public class HomeActivity extends BaseActivity {

    @BindView(R.id.bottom)
    RadioGroup mBottom;
    @BindView(R.id.frag_feed)
    RadioButton mFragFeed;
    private Fragment currentFragment = new Fragment();
    private FeedFragment mFeedFragment = new FeedFragment();
    private FollowFragment mFollowFragment = new FollowFragment();
    private CategoryFragment mCategoryFragment = new CategoryFragment();
    private ProfileFragment mProfileFragment = new ProfileFragment();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        //初始化显示首页
        switchFragment(mFeedFragment).commit();
        mFragFeed.setChecked(true);
    }

    @Override
    protected void bindListener() {
        mBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.frag_feed:
                        switchFragment(mFeedFragment).commit();
                        break;

                    case R.id.frag_follow:
                        switchFragment(mFollowFragment).commit();
                        break;

                    case R.id.frag_category:
                        switchFragment(mCategoryFragment).commit();
                        break;

                    case R.id.frag_profile:
                        switchFragment(mProfileFragment).commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }


    private FragmentTransaction switchFragment(Fragment targetFragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.container, targetFragment, targetFragment.getClass().getName());

        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment);


        }
        currentFragment = targetFragment;
        return transaction;
    }



}
