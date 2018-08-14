/*
 *    Copyright 2018 XuJiaji
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.xujiaji.wanandroid.module.main;

import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.xujiaji.mvvmquick.di.ActivityScoped;
import com.xujiaji.mvvmquick.di.FragmentScoped;
import com.xujiaji.wanandroid.R;
import com.xujiaji.wanandroid.adapter.FragmentsPagerAdapter;
import com.xujiaji.wanandroid.base.App;
import com.xujiaji.wanandroid.helper.GlideImageLoader;
import com.xujiaji.wanandroid.helper.ViewHelper;
import com.xujiaji.wanandroid.model.FragmentModel;
import com.xujiaji.wanandroid.module.main.fragment.AccountDrawerFragment;
import com.xujiaji.wanandroid.module.main.fragment.posts.MainBlogPostsFragment;
import com.xujiaji.wanandroid.module.main.fragment.projects.MainProjectsFragment;
import com.xujiaji.wanandroid.module.main.fragment.MainToolsFragment;
import com.xujiaji.wanandroid.module.main.fragment.MenuDrawerFragment;
import com.youth.banner.Banner;

import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * author: xujiaji
 * created on: 2018/6/12 13:44
 * description:
 */
@Module
public abstract class MainModule {

    @ActivityScoped
    @Provides
    public static FragmentsPagerAdapter provideDrawerPagerAdapter(MainActivity context,
                                                                  MenuDrawerFragment menuDrawerFragment,
                                                                  AccountDrawerFragment accountDrawerFragment) {
        return new FragmentsPagerAdapter(context.getSupportFragmentManager(),
                Stream.of(new FragmentModel(context.getString(R.string.menu), menuDrawerFragment),
                        new FragmentModel(context.getString(R.string.profile), accountDrawerFragment))
                        .toList());
    }

    @ActivityScoped
    @Named("Post")
    @Provides
    public static FragmentModel provideBlogModel(MainActivity context,
                                                    MainBlogPostsFragment blogPostsFragment) {
        return new FragmentModel(context.getString(R.string.app_name), blogPostsFragment);
    }


    @ActivityScoped
    @Named("Project")
    @Provides
    public static FragmentModel provideProjectModel(MainActivity context, MainProjectsFragment projectsFragment) {
        return new FragmentModel(context.getString(R.string.project), projectsFragment);
    }

    @ActivityScoped
    @Named("Tool")
    @Provides
    public static FragmentModel provideToolModel(MainActivity context, MainToolsFragment toolsFragment) {
        return new FragmentModel(context.getString(R.string.tool), toolsFragment);
    }

    @ActivityScoped
    @Provides
    public static List<FragmentModel> provideHomeFragModels(@Named("Post") FragmentModel postModel,
                                                            @Named("Project") FragmentModel projectModel,
                                                            @Named("Tool") FragmentModel toolModel) {
        return Stream.of(postModel, projectModel, toolModel).toList();
    }

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MenuDrawerFragment contributeMenuDrawerFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AccountDrawerFragment contributeAccountDrawerFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MainBlogPostsFragment contributeMainBlogPostsFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MainProjectsFragment contributeMainProjectsFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MainToolsFragment contributeMainToolsFragment();


    @Provides
    static Banner provideBanner(MainActivity activity) {
        Banner mBanner = new Banner(activity);
        mBanner.setDelayTime(3000);
        mBanner.setOffscreenPageLimit(5);
        mBanner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewHelper.dpToPx(App.getInstance(), 120)));
        mBanner.setImageLoader(new GlideImageLoader());
        return mBanner;
    }
}
