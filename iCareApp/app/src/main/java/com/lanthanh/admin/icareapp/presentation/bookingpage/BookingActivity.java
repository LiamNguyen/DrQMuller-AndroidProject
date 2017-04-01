package com.lanthanh.admin.icareapp.presentation.bookingpage;

import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.R;
import com.lanthanh.admin.icareapp.presentation.base.BaseActivity;
import com.lanthanh.admin.icareapp.presentation.adapter.ListPopupWindowAdapter;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ADMIN on 24-Jan-17.
 */

public class BookingActivity extends BaseActivity {
    @BindDrawable(R.drawable.ic_shopping_cart_white_32dp) Drawable cartIcon;
    @BindView(R.id.toolbar) Toolbar toolBar;
    @BindView(R.id.progressbar) ProgressBar progressBar;
    private BookingActivityPresenter bookingActivityPresenter;
    private ListPopupWindow popupWindow;
    private ListPopupWindowAdapter popupAdapter;
    private MenuItem cart;
    private View removedItem;
    private List<String> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        ButterKnife.bind(this);

        init();

        //Set up Toolbar
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bookingActivityPresenter.navigateFragment(BookingSelectFragment.class);
    }

    public void init(){
        bookingActivityPresenter = new BookingActivityPresenter(this);
        //Init cart list for display
        cartList = new ArrayList<>();
    }

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

    public void refreshCartIcon() {
        if (cartList.size() > 0) {
            ActionItemBadge.update(this, cart, cartIcon, ActionItemBadge.BadgeStyles.RED, cartList.size());
        }else {
            ActionItemBadge.update(this, cart, cartIcon, ActionItemBadge.BadgeStyles.RED, Integer.MIN_VALUE);
        }
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
                        .setPositiveButton(
                            getString(R.string.agree_button),
                            (DialogInterface dialog, int which) -> {
                                dialog.dismiss();
                                bookingActivityPresenter.emptyCart(
                                    () -> {
                                        onEmptyCartItem();
                                        finish();
                                    }
                                );
                            }
                        ).setNegativeButton(
                            getString(R.string.abort_button),
                            (DialogInterface dialog, int which) -> dialog.dismiss()
                        )
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
        if (width <= 720)
            popupWindow.setWidth(500);
        else
            popupWindow.setWidth(560);
        popupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
        popupWindow.setHorizontalOffset(width);
        popupWindow.setVerticalOffset(height);
        popupWindow.setModal(true);
        popupWindow.setOnItemClickListener(
            (AdapterView<?> adapterView, View view, int position, long id) -> {
                if (position != 0) {
                    removedItem = view;
                    bookingActivityPresenter.releaseTime(
                        (String) adapterView.getAdapter().getItem(position),
                        item -> {
                            cartList.remove(item);
                            popupAdapter.notifyDataSetChanged();
                            if (popupWindow.isShowing())
                                popupWindow.getListView().invalidateViews();
                            refreshCartIcon();
                            onRemoveCartItemColor(true);
                        }
                    );
                }
            }
        );
    }

    public void onAddCartItem(String item) {
        cartList.add(item);
        invalidateOptionsMenu();
    }

    public void onEmptyCartItem() {
        cartList.clear();
        invalidateOptionsMenu();
    }

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

    /* =============================== END OF TOOLBAR ===============================*/

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        bookingActivityPresenter.onBackPressed();
    }
}
