package com.lanthanh.admin.icareapp.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.Controller.NetworkController;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.api.impl.iCareApiImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.AppointmentManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.CustomerManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.SendEmailManagerImpl;
import com.lanthanh.admin.icareapp.domain.executor.impl.ThreadExecutor;
import com.lanthanh.admin.icareapp.presentation.presenter.MainActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.impl.MainActivityPresenterImpl;
import com.lanthanh.admin.icareapp.presentation.view.adapter.ListPopupWindowAdapter;
import com.lanthanh.admin.icareapp.threading.impl.MainThreadImpl;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 12-Nov-16.
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener, MainActivityPresenter.View{
    public final static boolean isUAT = false;
    public final static int NEWSTAB = 0;
    public final static int BOOKTAB = 1;
    public final static int USERTAB = 2;
    public final static int BOOKTAB_BOOK = 3;
    private MainActivityPresenter mMainPresenter;
    private Drawable cartIcon;
    private ListPopupWindow popupWindow;
    private ListPopupWindowAdapter popupAdapter;
    private MenuItem cart;
    private View removedItem;
    private List<String> cartList;
    private BottomNavigationView bottomNavigationView;

    //Controller
    private NetworkController networkController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            // Do something for lollipop and above versions
            cartIcon = getResources().getDrawable(R.drawable.ic_shopping_cart_white_36dp, null);
        } else{
            // do something for phones running an SDK before lollipop
            cartIcon = getResources().getDrawable(R.drawable.ic_shopping_cart_white_36dp);
        }

        //Toolbar
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Typeface titleFont = Typeface.createFromAsset(getAssets(), "fonts/SourceSansPro-Semibold.ttf");
        TextView title = (TextView) toolBar.findViewById(R.id.toolbar_title);
        title.setTypeface(titleFont);

        //Bottom Navigation View
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.getMenu().getItem(NEWSTAB).setChecked(false);
        bottomNavigationView.getMenu().getItem(BOOKTAB).setChecked(true);
    }

    public void init(){
        mMainPresenter = new MainActivityPresenterImpl(getSharedPreferences("content", Context.MODE_PRIVATE), ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this,
                getSupportFragmentManager(), new AppointmentManagerImpl(iCareApiImpl.getAPI()), new SendEmailManagerImpl(iCareApiImpl.getAPI()), new CustomerManagerImpl(iCareApiImpl.getAPI()));
        //Init cart list for display
        cartList = new ArrayList<>();
        //Init controllers
        networkController = new NetworkController(this);
    }

    public MainActivityPresenter getMainPresenter(){
        return mMainPresenter;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b == null){
            //Check user's privilege to use the app. If false (NOT log in or NOT activate account), return to register
            if (!mMainPresenter.checkPrivilege()) {
                mMainPresenter.navigateToRegisterActivity();
            }else {
                onNavigationItemSelected(bottomNavigationView.getMenu().getItem(BOOKTAB));
            }
        }else {
            if (b.containsKey("isSignedIn")) {
                int n = b.getInt("isSignedIn");
                if (n == 1) {
                    onNavigationItemSelected(bottomNavigationView.getMenu().getItem(BOOKTAB));
                    //bottomNavigationView.getMenu().getItem(1).setChecked(true);
                }
            }else if (b.containsKey("fromUserTab")){
                if (b.getBoolean("fromUserTab"))
                    onNavigationItemSelected(bottomNavigationView.getMenu().getItem(USERTAB));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkController.registerNetworkReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //releaseCartWhenReselect();
        networkController.unregisterNetworkReceiver();
    }

    @Override
    protected void onDestroy() {
        //releaseCartWhenReselect();
        super.onDestroy();
    }

    /* =============================== TOOLBAR ===============================*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        //Attach PopUpWindow to Cart
        cart = menu.findItem(R.id.shopping_cart);
        View v = cart.getActionView();
        createPopUpWindow(v);

        //Set up badge for Cart
        ActionItemBadge.update(this, menu.findItem(R.id.shopping_cart), cartIcon, ActionItemBadge.BadgeStyles.RED, Integer.MIN_VALUE);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        refreshCartIcon();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.shopping_cart:
                popupWindow.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Create shopping cart window
    public void createPopUpWindow(View v){
        int width, height;
        //Get screen width to put PopUpWindow to the right-most
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
        //Get ToolBar height to put PopUpWindow in the middle of ToolBar
        final TypedArray styledAttributes = getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize }
        );
        height = (int) styledAttributes.getDimension(0, 0);
        //Create PopUpWindow
        popupWindow = new ListPopupWindow(this);
        popupAdapter = new ListPopupWindowAdapter(this, R.layout.activity_popup_item, cartList);
        popupWindow.setAdapter(popupAdapter);
        popupWindow.setAnchorView(v);
        popupWindow.setWidth(450);
        popupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
        popupWindow.setHorizontalOffset(width);
        popupWindow.setVerticalOffset(height);
        popupWindow.setModal(true);
        popupWindow.setOnItemClickListener(this);
    }

    @Override
    public void onAddCartItem(String item) {
        cartList.add(item);
        invalidateOptionsMenu();
    }

    @Override
    public void onRemoveCartItem(String item) {
        cartList.remove(item);
        popupAdapter.notifyDataSetChanged();
        if (popupWindow.isShowing())
            popupWindow.getListView().invalidateViews();
        refreshCartIcon();
    }

    @Override
    public void onRemoveCartItemColor(boolean isDone) {
        if (removedItem != null){
            if (isDone){
                ListPopupWindowAdapter.ViewHolder holder = (ListPopupWindowAdapter.ViewHolder) removedItem.getTag();
                TextView currentView = holder.getTextView();
                currentView.setTextColor(getResources().getColor(R.color.colorDarkGray));
            }else{
                ListPopupWindowAdapter.ViewHolder holder = (ListPopupWindowAdapter.ViewHolder) removedItem.getTag();
                TextView currentView = holder.getTextView();
                currentView.setTextColor(getResources().getColor(R.color.colorLightGray));
            }
        }
    }

    @Override
    public void onEmptyCart() {
        cartList.clear();
        invalidateOptionsMenu();
    }

    @Override
    public void refreshCartIcon() {
        if (cartList.size() > 0) {
            ActionItemBadge.update(this, cart, cartIcon, ActionItemBadge.BadgeStyles.RED, cartList.size());
        }else {
            ActionItemBadge.update(this, cart, cartIcon, ActionItemBadge.BadgeStyles.RED, Integer.MIN_VALUE);
        }
    }

    //Response to user click on cart window
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (position != 0) {
            removedItem = view;
            mMainPresenter.removeCartItem((String) adapterView.getAdapter().getItem(position));

        }
    }

    @Override
    public void showFragment(FragmentManager fm, Fragment f, List<Fragment> visibleFrags) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        //Hide all current visible fragment
        hideFragments(fragmentTransaction, visibleFrags);

        if (!f.isAdded()){
            fragmentTransaction.add(R.id.fragment_container, f, f.getClass().getName());
        }else{
            fragmentTransaction.show(f);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    @Override
    public void hideFragments(FragmentTransaction ft, List<Fragment> visibleFrags) {
        for (Fragment fragment : visibleFrags) {
            ft.hide(fragment);
        }
    }

    @Override
    public void navigateActivity(Class activityClass) {
        Intent toActivity = new Intent(this, activityClass);
        startActivity(toActivity);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 110);
        toast.show();
    }

    /* =============================== BOTTOM NAVIGATION VIEW ===============================*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_news:
                break;
            case R.id.action_booking:
                bottomNavigationView.getMenu().getItem(USERTAB).setChecked(false);
                bottomNavigationView.getMenu().getItem(BOOKTAB).setChecked(true);
                mMainPresenter.navigateTab(BOOKTAB);
                break;
            case R.id.action_user:
                bottomNavigationView.getMenu().getItem(BOOKTAB).setChecked(false);
                bottomNavigationView.getMenu().getItem(USERTAB).setChecked(true);
                mMainPresenter.navigateTab(USERTAB);
                break;
            default:
                break;
        }
        return false;
    }

    //Call in case losing network and then connected again
    public void refreshAfterNetworkConnected(){
        this.onPostResume();
    }
}
