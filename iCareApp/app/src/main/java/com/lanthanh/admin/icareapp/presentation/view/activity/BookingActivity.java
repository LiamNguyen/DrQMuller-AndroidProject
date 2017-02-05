package com.lanthanh.admin.icareapp.presentation.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lanthanh.admin.icareapp.Controller.NetworkController;
import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.api.impl.iCareApiImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.AppointmentManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.CustomerManagerImpl;
import com.lanthanh.admin.icareapp.data.manager.impl.SendEmailManagerImpl;
import com.lanthanh.admin.icareapp.domain.executor.impl.ThreadExecutor;
import com.lanthanh.admin.icareapp.presentation.presenter.BookingActivityPresenter;
import com.lanthanh.admin.icareapp.presentation.presenter.impl.BookingActivityPresenterImpl;
import com.lanthanh.admin.icareapp.presentation.view.adapter.ListPopupWindowAdapter;
import com.lanthanh.admin.icareapp.presentation.view.fragment.booking.BookingSelectFragment;
import com.lanthanh.admin.icareapp.threading.impl.MainThreadImpl;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 24-Jan-17.
 */

public class BookingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, BookingActivityPresenter.View{
    public final static boolean isUAT = false;
    public final static int FIRST_SELECT = 0;
    public final static int SECOND_SELECT = 1;
    public final static int BOOK = 2;
    private BookingActivityPresenter bookingActivityPresenter;
    private Drawable cartIcon;
    private ListPopupWindow popupWindow;
    private ListPopupWindowAdapter popupAdapter;
    private ProgressBar progressBar;
    private MenuItem cart;
    private View removedItem;
    private List<String> cartList;
    //Controller
    private NetworkController networkController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        init();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            // Do something for lollipop and above versions
            cartIcon = getResources().getDrawable(R.drawable.ic_shopping_cart_white_32dp, null);
        } else{
            // do something for phones running an SDK before lollipop
            cartIcon = getResources().getDrawable(R.drawable.ic_shopping_cart_white_32dp);
        }

        //Init view
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    hideProgress();
                return false;
            }
        });

        //Set up Toolbar
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bookingActivityPresenter.navigateTab(FIRST_SELECT);
    }

    public void init(){
        bookingActivityPresenter = new BookingActivityPresenterImpl(
                getSharedPreferences("content", Context.MODE_PRIVATE), ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this,
                getSupportFragmentManager(), new AppointmentManagerImpl(iCareApiImpl.getAPI()),
                new SendEmailManagerImpl(iCareApiImpl.getAPI()), new CustomerManagerImpl(iCareApiImpl.getAPI()));
        //Init cart list for display
        cartList = new ArrayList<>();
        //Init controllers
        networkController = new NetworkController(this);
    }

    @Override
    public BookingActivityPresenter getMainPresenter() {
        return bookingActivityPresenter;
    }

    /* ============================== LIFE CYCLE ==============================*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bookingActivityPresenter.destroy();
    }

    /* =============================== TOOLBAR ===============================*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_appointment, menu);

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
            case android.R.id.home:
                bookingActivityPresenter.onBackPressed();
                return true;
            case R.id.shopping_cart:
                popupWindow.show();
                return true;
            case R.id.abort_booking:
                new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.abort_appointment_dialog))
                        .setPositiveButton(getString(R.string.agree_button), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                bookingActivityPresenter.emptyCart();
                                navigateActivity(MainActivity.class);
                            }
                        }).setNegativeButton(getString(R.string.abort_button), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false).show();
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
        popupWindow.setWidth(500);
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
            bookingActivityPresenter.removeCartItem((String) adapterView.getAdapter().getItem(position));
        }
    }

    /* =============================== END OF TOOLBAR ===============================*/

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

        if (f instanceof BookingSelectFragment)
            fragmentTransaction.commit();
        else
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
        finish();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 110);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        bookingActivityPresenter.onBackPressed();
    }

    //Call in case losing network and then connected again
    public void refreshAfterNetworkConnected(){
        this.onPostResume();
    }

}
