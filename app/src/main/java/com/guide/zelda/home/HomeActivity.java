package com.guide.zelda.home;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.guide.zelda.R;
import com.guide.zelda.base.BaseActivity;
import com.guide.zelda.base.BaseFragment;
import com.guide.zelda.di.component.ApplicationComponent;
import com.guide.zelda.map.MapFragment;
import com.guide.zelda.widget.TitleView;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final long WAIT_TIME = 2000L;

    @BindView(R.id.title)
    TitleView titleView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private long lastTime = 0;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent component) {

    }

    @Override
    protected void initView() {
        BaseFragment fragment = findFragment(MainQuestFragment.class);
        if (fragment == null) {
            loadRootFragment(R.id.fl_container, MainQuestFragment.newInstance());
        }
        navigationView.setNavigationItemSelectedListener(this);
        titleView.centerTitle(R.string.main_tab_whole_flow);
        titleView.leftImage(R.drawable.ic_drawer);
        titleView.clickLeft(l -> {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START);
            } else {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        View view = navigationView.inflateHeaderView(R.layout.nav_header_home);
        TextView tvWeb = view.findViewById(R.id.tv_web);
        tvWeb.setOnClickListener(l -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse("https://github.com/Mrxxy"));
            startActivity(intent);
        });
    }

    @Override
    protected void init() {

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new FragmentAnimator();
    }

    @Override
    public void onBackPressedSupport() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            ISupportFragment topFragment = getTopFragment();

            if (topFragment instanceof MainQuestFragment) {
                navigationView.setCheckedItem(R.id.nav_whole_flow);
            }

            if (getFragmentManager().getBackStackEntryCount() > 1) {
                pop();
            } else {
                if (System.currentTimeMillis() - lastTime < WAIT_TIME) {
                    finish();
                } else {
                    lastTime = System.currentTimeMillis();
                    Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        final ISupportFragment topFragment = getTopFragment();
        LogUtils.d("TAG", "topFragment: " + topFragment.getClass().getSimpleName());
        if (id == R.id.nav_whole_flow) {
            titleView.centerTitle(R.string.main_tab_whole_flow);
            MainQuestFragment fragment = findFragment(MainQuestFragment.class);
            if (fragment == null) {
                start(MainQuestFragment.newInstance());
            } else {
                start(fragment, SupportFragment.SINGLETASK);
            }
        } else if (id == R.id.nav_newest) {
            titleView.centerTitle(R.string.main_tab_newest);
            UserShareFragment fragment = findFragment(UserShareFragment.class);
            if (fragment == null) {
                start(UserShareFragment.newInstance());
            } else {
                start(fragment, SupportFragment.SINGLETASK);
            }
        } else if (id == R.id.nav_map) {
            titleView.centerTitle(R.string.main_tab_map);
            MapFragment fragment = findFragment(MapFragment.class);
            if (fragment == null) {
                start(MapFragment.newInstance());
            } else {
                popTo(MapFragment.class, false);
            }
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        navigationView.setCheckedItem(id);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
