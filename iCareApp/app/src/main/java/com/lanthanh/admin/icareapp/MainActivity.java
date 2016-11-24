package com.lanthanh.admin.icareapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lanthanh.admin.icareapp.BookingTab.BookingBookFragment;
import com.lanthanh.admin.icareapp.BookingTab.BookingNotSignedIn;
import com.lanthanh.admin.icareapp.BookingTab.BookingSelectFragment;
import com.lanthanh.admin.icareapp.Model.ModelBookingDetail;
import com.lanthanh.admin.icareapp.Register.RegisterActivity;
import com.lanthanh.admin.icareapp.Service.BookingDetailsActivity;
import com.lanthanh.admin.icareapp.UserTab.UserFragment;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    private BookingNotSignedIn bookingNotSignedIn;
    private BookingSelectFragment bookingSelectFragment;
    private BookingBookFragment bookingBookFragment;

    //UserTab Fragment
    private UserFragment userFragment;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize ModelBookingDetails
        bookingDetails = new ModelBookingDetail();

        //Set up cart resource
        cartList = new ArrayList<>();
        //cartList.add(getString(R.string.empty_cart));
        badgeCount = 0;
        cartIcon = getResources().getDrawable(R.drawable.ic_shopping_cart_white_36dp, null);

        //Get Fragment Manager
        fragmentManager = getSupportFragmentManager();

        //Init Fragment
        bookingNotSignedIn = new BookingNotSignedIn();
        bookingSelectFragment = new BookingSelectFragment();
        bookingBookFragment = new BookingBookFragment();
        userFragment = new UserFragment();

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
        bottomNavigationView.getMenu().getItem(0).setChecked(false);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences sharedPref = this.getSharedPreferences("content", Context.MODE_PRIVATE);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b == null){
            if (sharedPref.getString("tokenID", "").isEmpty()) {
                onNavigationItemSelected(bottomNavigationView.getMenu().getItem(2));
                //bottomNavigationView.getMenu().getItem(2).setChecked(true);
            }
            else {
                onNavigationItemSelected(bottomNavigationView.getMenu().getItem(1));
                //bottomNavigationView.getMenu().getItem(1).setChecked(true);
            }
        }else {
            if (b.containsKey("isSignedIn")) {
                int n = b.getInt("isSignedIn");
                if (n == 1) {
                    onNavigationItemSelected(bottomNavigationView.getMenu().getItem(1));
                    //bottomNavigationView.getMenu().getItem(1).setChecked(true);
                }
            }else if (b.containsKey("isBookingSuccess")){
                int m = b.getInt("isBookingSuccess");
                if (m == 1) {
                    onNavigationItemSelected(bottomNavigationView.getMenu().getItem(1));
                    //bottomNavigationView.getMenu().getItem(1).setChecked(true);
                    new AlertDialog.Builder(this)
                            .setMessage("Quí khách đã đặt lịch hẹn thành công")
                            .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setCancelable(false).show();
                }
            }else if (b.containsKey("fromUserTab")){
                if (b.getBoolean("fromUserTab"))
                    onNavigationItemSelected(bottomNavigationView.getMenu().getItem(2));
            }
        }
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
    public void releaseSelectedItemFromCart(String s){
        cartList.remove(s);
        if (popupListView != null)
            popupListView.invalidateViews();
        badgeCount --;
        invalidateOptionsMenu();
        bookingBookFragment.refreshTimeList(s.substring(0, s.indexOf("-") - 1),s.substring(s.indexOf("-") +2, s.length()));
    }

    //Release cart when user reselect in BookSelectFragment
    public void releaseCartWhenReselect(){
        for (Iterator<String> iter = cartList.iterator(); iter.hasNext(); ) {
            String s = iter.next();
            iter.remove();
            badgeCount --;
            invalidateOptionsMenu();
            bookingBookFragment.refreshTimeList(s.substring(0, s.indexOf("-") - 1),s.substring(s.indexOf("-") +2, s.length()));
        }
    }

    //Empty cart after finished booking
    public void emptyCart(){
        cartList.clear();
        badgeCount = 0;
        invalidateOptionsMenu();
    }

    public int numberOfCartItems(){
        return cartList.size();
    }

    //Response to user click on cart window
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (position != 0)
            releaseSelectedItemFromCart((String) adapterView.getAdapter().getItem(position));
    }

    //Adapter for PopupWindow
    class ListPopupWindowAdapter extends ArrayAdapter<String> {
        private List<String> list;
        public ListPopupWindowAdapter(Activity context, int textViewResourceId, List<String> l) {
            super(context, textViewResourceId, l);
            list = l;
        }

        @Override
        public int getCount() {
            return list.size() + 1;
        }

        @Nullable
        @Override
        public String getItem(int position) {
            return list.get(position-1);
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
            if (list.size() <= 0) {
                label.setText(getString(R.string.empty_cart));
            }else{
                if (position == 0) {
                    label.setText(getString(R.string.list_cart));
                } else {
                    label.setText(getItem(position));
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
    public void navigateToNotSignedIn(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        hideAllBookingTabVisibleFragments(fragmentTransaction);

        if (!bookingNotSignedIn.isAdded()){
            fragmentTransaction.add(R.id.fragment_container, bookingNotSignedIn, bookingNotSignedIn.getClass().getName());
        }else{
            fragmentTransaction.show(bookingNotSignedIn);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    public void navigateToSelect(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        hideAllBookingTabVisibleFragments(fragmentTransaction);

        if (!bookingSelectFragment.isAdded()){
            fragmentTransaction.add(R.id.fragment_container, bookingSelectFragment, bookingSelectFragment.getClass().getName());
        }else{
            fragmentTransaction.show(bookingSelectFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

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

    //Get all visible fragment in Booking Tab
    private List<Fragment> getBookingTabVisibleFragments(){
        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        List<Fragment> result = new ArrayList<>(3);

        // Add each visible fragment to the result IF VISIBLE
        //Add "Booking Select Fragment"
        if (bookingNotSignedIn.isVisible()){
            result.add(bookingNotSignedIn);
        }
        if (bookingSelectFragment.isVisible()) {
            result.add(bookingSelectFragment);
        }
        if (bookingBookFragment.isVisible()) {
            result.add(bookingBookFragment);
        }

        return result;
    }

    //Hide all visible fragment in Booking Tab
    private FragmentTransaction hideAllBookingTabVisibleFragments(FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : getBookingTabVisibleFragments()) {
            fragmentTransaction.hide(fragment);
        }

        return fragmentTransaction;
    }

    /* =============================== USER TAB ===============================*/
    public void navigateToUser(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        hideAllBookingTabVisibleFragments(fragmentTransaction);

        if (!userFragment.isAdded()){
            fragmentTransaction.add(R.id.fragment_container, userFragment, userFragment.getClass().getName());
        }else{
            fragmentTransaction.show(userFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    public void navigateToBookingDetails(){
        Intent test = new Intent(this, BookingDetailsActivity.class);
        startActivity(test);
    }

    private List<Fragment> getUserTabVisibleFragments(){
        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        List<Fragment> result = new ArrayList<>(1);

        // Add each visible fragment to the result IF VISIBLE
        //Add "Booking Select Fragment"
        if (userFragment.isVisible()) {
            result.add(userFragment);
        }

        return result;
    }

    private FragmentTransaction hideAllUserTabVisibleFragments(FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : getUserTabVisibleFragments()) {
            fragmentTransaction.hide(fragment);
        }

        return fragmentTransaction;
    }

    /* =============================== BOTTOM NAVIGATION VIEW ===============================*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SharedPreferences sharedPref = this.getSharedPreferences("content", Context.MODE_PRIVATE);
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
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                bottomNavigationView.getMenu().getItem(2).setChecked(false);
                hideAllUserTabVisibleFragments(fragmentTransaction);
                fragmentTransaction.commit();
                //If user signed in, these are fragments that have to appear. "Booking Select Fragment" is default
                //Initialize Fragment
                if (sharedPref.getString("tokenID", "").isEmpty())
                    navigateToNotSignedIn();
                else
                    navigateToSelect();

                break;
            case R.id.action_user:
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                bottomNavigationView.getMenu().getItem(1).setChecked(false);
                hideAllBookingTabVisibleFragments(fragmentTransaction);
                fragmentTransaction.commit();
                if (!sharedPref.getString("tokenID", "").isEmpty()) {
                    navigateToUser();
                }
                else {
                    Intent toRegister = new Intent(this, RegisterActivity.class);
                    startActivity(toRegister);
                }
                break;
        }
        return false;
    }

     /* =============================== DATA ===============================*/
    public ModelBookingDetail getModelBooking(){
        return bookingDetails;
    }

     /* =============================== CHECK FOR NETWORK ===============================*/
     private boolean haveNetworkConnection() {
         ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo networkInfo = cm.getActiveNetworkInfo();
         return networkInfo != null && networkInfo.isConnected();
     }

}
