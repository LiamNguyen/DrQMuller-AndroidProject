package DrQMuller.DrQMuller.Service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.airbnb.deeplinkdispatch.DeepLink;

import DrQMuller.R;
import DrQMuller.DrQMuller.Register.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 22-Nov-16.
 */

@DeepLink("icare://210.211.109.180/drmuller/restore")
public class ResetPasswordActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private EmailForResetFragment emailForResetFragment;
    private ResetPasswordFragment resetPasswordFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //Init fragment
        emailForResetFragment = new EmailForResetFragment();
        resetPasswordFragment = new ResetPasswordFragment();
        fragmentManager = getSupportFragmentManager();

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow_2);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get ChooseFragment for when loading Acitivity
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.resetpw_fragment_container, emailForResetFragment, emailForResetFragment.getClass().getName()).commit();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Intent intent = getIntent();
        Uri data = intent.getData();
        if (data!=null) {
            if (data.getPath().equals("/drmuller/restore") && data.getQueryParameterNames().size() == 1 && data.getQueryParameterNames().contains("email")) {
                String queryParameter = data.getQueryParameter("email");
                System.out.println(queryParameter);
                Bundle bundle = new Bundle();
                bundle.putString("email", queryParameter);
                resetPasswordFragment.setArguments(bundle);
                navigateToReset();
            }else{
                navigateToRegister();
            }
        }else {
            navigateToEmail();
        }
        /*if (intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)) {
            Bundle parameters = intent.getExtras();

            if (parameters != null && parameters.getString("email") != null) {
                String queryParameter = parameters.getString("email");
                System.out.println(queryParameter);
                Bundle bundle = new Bundle();
                bundle.putString("email", queryParameter);
                resetPasswordFragment.setArguments(bundle);
                navigateToReset();
            }
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                if (fragmentManager.findFragmentByTag(emailForResetFragment.getClass().getName()).isVisible())
//                    NavUtils.navigateUpFromSameTask(this);
//                else
//                    navigateBack();
//                return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    /*
   *Navigate to Sign Up Screen
   */
    public void navigateToEmail(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        hideAllVisibleFragment(fragmentTransaction);
        if (!emailForResetFragment.isAdded()){
            fragmentTransaction.add(R.id.resetpw_fragment_container, emailForResetFragment, emailForResetFragment.getClass().getName());
        }else{
            fragmentTransaction.show(emailForResetFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    /*
    *Navigate to Sign Up Screen
    */
    public void navigateToReset(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                /*.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                                                        R.anim.slide_in_left, R.anim.slide_out_right);*/
        hideAllVisibleFragment(fragmentTransaction);
        if (!resetPasswordFragment.isAdded()){
            fragmentTransaction.add(R.id.resetpw_fragment_container, resetPasswordFragment, resetPasswordFragment.getClass().getName());
        }else{
            fragmentTransaction.show(resetPasswordFragment);
        }

        fragmentTransaction.addToBackStack(null).commit();
    }

    /*
    *Navigate to last fragment
    */
    public void navigateBack(){
        fragmentManager.popBackStack();

        //Hide soft keyboard if it is open
        hideSoftKeyboard();
    }

    public void navigateToRegister(){
        Intent toRegister = new Intent(this, RegisterActivity.class);
        startActivity(toRegister);
        finish();
    }

    /*
    *Get all visible fragments
    */
    private List<Fragment> getVisibleFragments(){
        // We have 3 fragments, so initialize the arrayList to 3 to optimize memory
        List<Fragment> result = new ArrayList<>(2);

        // Add each visible fragment to the result
        if (emailForResetFragment.isVisible()) {
            result.add(emailForResetFragment);
        }
        if (resetPasswordFragment.isVisible()) {
            result.add(resetPasswordFragment);
        }

        return result;
    }

    /*
    *Hide all visible fragments
    */
    private FragmentTransaction hideAllVisibleFragment(FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : getVisibleFragments()) {
            fragmentTransaction.hide(fragment);
        }

        return fragmentTransaction;
    }

    //Hide SoftKeyBoard when needed
    public void hideSoftKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
