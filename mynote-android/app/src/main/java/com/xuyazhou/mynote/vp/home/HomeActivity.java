package com.xuyazhou.mynote.vp.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.config.Constant;
import com.xuyazhou.mynote.common.utils.ActivityUtil;
import com.xuyazhou.mynote.common.utils.FingerprintUtils;
import com.xuyazhou.mynote.common.utils.RecyclerViewUtils;
import com.xuyazhou.mynote.common.widget.IconTextView;
import com.xuyazhou.mynote.common.widget.MyNoteSwipeRefreshLayout;
import com.xuyazhou.mynote.model.db.Note;
import com.xuyazhou.mynote.vp.adapter.NoteGridListAdapter;
import com.xuyazhou.mynote.vp.base.BaseActivity;
import com.xuyazhou.mynote.vp.home.detail.NoteDetailActivity;
import com.xuyazhou.mynote.vp.home.member.LoginIndexActivity;
import com.xuyazhou.mynote.vp.home.member.UserinfoActvity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.xuyazhou.mynote.common.utils.FingerprintUtils.initFingerprint;


public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View,
        NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.refreshLayout)
    MyNoteSwipeRefreshLayout refreshLayout;
    @Inject
    NoteGridListAdapter adapter;
    private ViewHolder viewHolder;
    private String listType;
    TextView accountEmail;
    ImageView avatar;


    @Override
    protected void setupActivityComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.initPresmission();
        listType = presenter.initListType();

        presenter.addSub();
        initUI();


    }

    @Override
    public void initUI() {
        setSupportActionBar(toolbar);
        View titleView = LayoutInflater.from(this).inflate(R.layout.action_bar_note_list_layout, null);

        viewHolder = new ViewHolder(titleView);

        toolbar.addView(titleView);
        viewHolder.title.setText("所有");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        View headView = navigationView.getHeaderView(0);
        accountEmail = headView.findViewById(R.id.account_email);
        avatar = headView.findViewById(R.id.photo);


        headView.findViewById(R.id.header_layout).setOnClickListener(v -> {

            drawerLayout.closeDrawer(GravityCompat.START);
            if (presenter.getUserSetting().isLogin()) {
                startActivity(new Intent(this, UserinfoActvity.class));

            } else {
                startActivity(new Intent(this, LoginIndexActivity.class));
            }

        });
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRecyclerView(recyclerView);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary_light);
        refreshLayout.setDistanceToTriggerSync(400);

        setRecyclerView();

        initFingerprint(this);
    }

    private void initHeadView(TextView accountEmail, ImageView avatar) {
        if (presenter.getUserSetting().isLogin()) {
            accountEmail.setText(presenter.getUser().getEmail());
            if (!presenter.getUser().getAvatar().equals("")) {
                Glide.with(this).load(presenter.getUser().getAvatar())
                        .bitmapTransform(new CropCircleTransformation(
                                this))
                        .error(R.mipmap.default_photo_light)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(avatar);
            }

        } else {
            accountEmail.setText("登陆或注册");
            Glide.with(this).load(R.mipmap.default_photo_light)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(avatar);
        }
    }


    private void setRecyclerView() {


        if (listType.equals(Constant.GRID)) {
            RecyclerViewUtils.setGridRecyclerView(this, recyclerView, adapter, 8, () -> presenter.loadMore());


        } else {
            RecyclerViewUtils.setRecyclerView(this, recyclerView, adapter, 0, () -> presenter.loadMore());

        }
    }

    @Override
    public void showData(ArrayList<Note> noteList) {
        adapter.setData(noteList, listType);
    }

    @Override
    public void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_share) {
            // ActivityUtil.moveToActivity(this, MynotePreferenceActivity.class);
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mynotes_list, menu);


        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHeadView(accountEmail, avatar);
        presenter.refresh();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.Unsubscribe();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem viewType = menu.findItem(R.id.viewType);
        MenuItem list_view_type = menu.findItem(R.id.list_view_type);

        if (listType.equals(Constant.GRID)) {

            viewType.setTitle("列表");
            list_view_type.setVisible(false);

        } else {
            if (listType.equals(Constant.LISTONLYTITLE)) {
                list_view_type.setTitle("显示摘要");
            } else {
                list_view_type.setTitle("仅显示标题");
            }
            viewType.setTitle("缩略图");
            list_view_type.setVisible(true);


        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.viewType:

                if (listType.equals(Constant.GRID)) {
                    listType = Constant.LIST;
                } else {
                    listType = Constant.GRID;

                }
                presenter.SetListType(listType);
                setRecyclerView();
                adapter.setList(listType);


                break;

            case R.id.list_view_type:
                if (listType.equals(Constant.LIST)) {
                    listType = Constant.LISTONLYTITLE;

                } else {
                    listType = Constant.LIST;
                }

                presenter.SetListType(listType);
                setRecyclerView();
                adapter.setList(listType);
                break;
            case R.id.lock:
                FingerprintUtils.FingerprintShow(getFragmentManager());
                break;
            case R.id.sync:

               presenter.syncNoteData();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRefresh() {
        presenter.refresh();
        new Handler().postDelayed(() -> refreshLayout.setRefreshing(false), 1000); // 5秒后发送消息，停止刷新

    }


    @OnClick(R.id.fab)
    public void fabOnclick() {
        ActivityUtil.moveToActivity(this, NoteDetailActivity.class);
    }


    static class ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.progress)
        ProgressBar progress;
        @BindView(R.id.sync_error)
        IconTextView syncError;
        @BindView(R.id.sync)
        IconTextView sync;
        @BindView(R.id.search)
        IconTextView search;
        @BindView(R.id.option_btns)
        LinearLayout optionBtns;
        @BindView(R.id.content_view)
        RelativeLayout contentView;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}