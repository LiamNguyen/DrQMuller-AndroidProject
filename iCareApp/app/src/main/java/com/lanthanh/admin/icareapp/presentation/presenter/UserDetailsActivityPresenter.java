package com.lanthanh.admin.icareapp.presentation.presenter;

import com.lanthanh.admin.icareapp.presentation.presenter.base.Presenter;
import com.lanthanh.admin.icareapp.presentation.view.base.BaseView;

/**
 * Created by ADMIN on 11-Jan-17.
 */

public interface UserDetailsActivityPresenter extends Presenter {
    interface View extends BaseView{
        void navigateToMainActivity();
        void setGenderView(String gender);
        void setDobView(String dob);
        void refreshViews();
        void unlockViews();
        String getStringResource(int id);
        UserDetailsActivityPresenter getMainPresenter();
    }

    void navigateToMainActivity();
    void showGenderDialogFragment();
    void showDobDialogFragment();
    void setName(String name);
    void setAddress(String address);
    void setDob(String dob);
    void setGender(String gender);
    void setEmail(String email);
    void setPhone(String phone);
    String getName();
    String getAddress();
    String getDob();
    String getGender();
    String getEmail();
    String getPhone();
    void updateCustomer();
}
