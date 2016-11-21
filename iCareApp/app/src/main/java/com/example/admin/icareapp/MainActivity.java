package com.example.admin.icareapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.admin.icareapp.BookingTab.BookingBookFragment;
import com.example.admin.icareapp.BookingTab.BookingSelectFragment;
import com.example.admin.icareapp.Model.ModelBookingDetail;
import com.example.admin.icareapp.Register.RegisterActivity;
import com.example.admin.icareapp.UserTab.UserFragment;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ADMIN on 12-Nov-16.
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener{
    private FragmentManager fragmentManager;
    private int badgeCount;
    private Drawable cartIcon;
    private ListPopupWindow popupWindow;
    private List<String> cartList;
    private ListView popupListView;
    private ModelBookingDetail bookingDetails;
    //BookingTab Fragment
    private BookingSelectFragment bookingSelectFragment;
    private BookingBookFragment bookingBookFragment;

    //UserTab Fragment
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize ModelBookingDetails
        bookingDetails = new ModelBookingDetail();

        //Set up cart resource
        cartList = new ArrayList<>();
        cartList.add(getString(R.string.empty_cart));
        badgeCount = 0;
        cartIcon = getResources().getDrawable(R.drawable.ic_shopping_cart, null);

        //Get Fragment Manager
        fragmentManager = getSupportFragmentManager();
        bookingSelectFragment = new BookingSelectFragment();

        bookingBookFragment = new BookingBookFragment();
        userFragment = new UserFragment();

        //Toolbar
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        //Bottom Navigation View
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    /* =============================== TOOLBAR ===============================*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        //Attach PopUpWindow to Cart
        MenuItem cart = menu.findItem(R.id.shopping_cart);
        View v = cart.getActionView();
        createPopUpWindow(v);

        //Set up badge for Cart
        ActionItemBadge.update(this, menu.findItem(R.id.shopping_cart), cartIcon, ActionItemBadge.BadgeStyles.RED, Integer.MIN_VALUE);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (badgeCount > 0) {
            ActionItemBadge.update(this, menu.findItem(R.id.shopping_cart), cartIcon, ActionItemBadge.BadgeStyles.RED, badgeCount);
        }else {
            ActionItemBadge.update(this, menu.findItem(R.id.shopping_cart), cartIcon, ActionItemBadge.BadgeStyles.RED, Integer.MIN_VALUE);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.shopping_cart:
                popupWindow.show();
                popupListView = popupWindow.getListView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Create shopping cart window
    public void createPopUpWindow(View v){
        int width, height;
        //Get screen width to put PopUpWindow to the left-most
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
        ListPopupWindowAdapter popupAdapter = new ListPopupWindowAdapter(this, R.layout.activity_popup_item, cartList);
        popupWindow.setAdapter(popupAdapter);
        popupWindow.setAnchorView(v);
        popupWindow.setWidth(450);
        popupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
        popupWindow.setHorizontalOffset(width);
        popupWindow.setVerticalOffset(height);
        popupWindow.setModal(true);
        popupWindow.setOnItemClickListener(this);
    }

    //Add item to cart when time is selected
    public void addSelectedItemToCart(String s){
        cartList.add(s);
        badgeCount ++;
        invalidateOptionsMenu();
    }

    //Remove item from cart when user click on one of the items in cart
    public void removeSelectedItemFromCart(String s){
        cartList.remove(s);
        popupListView.invalidateViews();
        badgeCount --;
        invalidateOptionsMenu();
        bookingBookFragment.refreshTimeList(s.substring(0, s.indexOf("-") - 1),s.substring(s.indexOf("-") +2, s.length()));
    }

    public void emptyCart(){
        cartList.clear();
        cartList.add(getString(R.string.empty_cart));
        badgeCount = 0;
        invalidateOptionsMenu();
    }

    public int numberOfCartItems(){
        return cartList.size() - 1;
    }

    //Response to user click on cart window
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (position != 0)
            removeSelectedItemFromCart(cartList.get(position));
    }

    //Adapter for PopupWindow
    class ListPopupWindowAdapter extends ArrayAdapter<String> {
        private List<String> list;
        public ListPopupWindowAdapter(Activity context, int textViewResourceId, List<String> l) {
            super(context, textViewResourceId, l);
            list = l;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.activity_popup_item, parent, false);
                holder = new ViewHolder(row);
                row.setTag(holder);
            }else
                holder = (ViewHolder) row.getTag();

            TextView label = holder.getTextView();
            if (list.size() == 1) {
                label.setText(list.get(position));
            }else{
                if (position == 0) {
                    label.setText(getString(R.string.list_cart));
                } else {
                    label.setText(list.get(position));
                    ImageView icon = holder.getImageView();
                    icon.setImageResource(R.drawable.ic_delete_item);
                }
            }

            return row;
        }

        private class ViewHolder{
            private View v;
            private TextView tv;
            private ImageView iv;

            ViewHolder(View base){
                v = base;
            }

            public TextView getTextView(){
                if (tv == null)
                    tv = (TextView) v.findViewById(R.id.selected_item);
                return tv;
            }

            public ImageView getImageView(){
                if (iv == null)
                    iv = (ImageView) v.findViewById(R.id.delete_item);
                return iv;
            }
        }
    }

    /* =============================== BOOKING TAB ===============================*/
    /* ------------- ALREADY LOGIN ------------- */
    //After select, move to "Book Fragment" to book date
    public void navigateToBook(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        hideAllBookingTabVisibleFragments(fragmentTransaction);

        if (!bookingBookFragment.isAdded()){
            fragmentTransaction.add(R.id.fragment_container, bookingBookFragment, bookingBookFragment.getClass().getName());
        }else{
            fragmentTransaction.show(bookingBookFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    //Get all visible fragment in User Tab
    private List<Fragment> getBookingTabVisibleFragments(){
        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        List<Fragment> result = new ArrayList<>(2);

        // Add each visible fragment to the result IF VISIBLE
        //Add "Booking Select Fragment"
        if (bookingSelectFragment.isVisible()) {
            result.add(bookingSelectFragment);
        }
        if (bookingBookFragment.isVisible()) {
            result.add(bookingBookFragment);
        }

        return result;
    }

    //Hide all visible fragment in User Tab
    private FragmentTransaction hideAllBookingTabVisibleFragments(FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : getBookingTabVisibleFragments()) {
            fragmentTransaction.hide(fragment);
        }

        return fragmentTransaction;
    }

    /* =============================== BOTTOM NAVIGATION VIEW ===============================*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (item.getItemId()) {
            case R.id.action_news:
                /*
                if (!userFragment.isAdded()){
                    fragmentTransaction.add(R.id.fragment_container, userFragment, userFragment.getClass().getName());
                }else{
                    fragmentTransaction.show(userFragment);
                }

                fragmentTransaction.addToBackStack(null).commit();*/

                break;
            case R.id.action_booking:
                //Hide ALL fragments from OTHER TABS
                hideAllBookingTabVisibleFragments(fragmentTransaction);

                //If user signed in, these are fragments that have to appear. "Booking Select Fragment" is default
                //Initialize Fragment

                if (!bookingSelectFragment.isAdded()){
                    fragmentTransaction.add(R.id.fragment_container, bookingSelectFragment, bookingSelectFragment.getClass().getName());
                }else{
                    fragmentTransaction.show(bookingSelectFragment);
                }

                fragmentTransaction.addToBackStack(null).commit();
                break;
            case R.id.action_user:
                Intent toRegister = new Intent(this, RegisterActivity.class);
                startActivity(toRegister);
                break;
        }
        return false;
    }

     /* =============================== DATA ===============================*/
    public ModelBookingDetail getModelBooking(){
        return bookingDetails;
    }
}
