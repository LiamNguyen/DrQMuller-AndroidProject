package com.lanthanh.admin.icareapp.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.lanthanh.admin.icareapp.data.repository.datasource.LocalStorage;
import com.lanthanh.admin.icareapp.data.restapi.RestClient;
import com.lanthanh.admin.icareapp.data.restapi.impl.RestClientImpl;
import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.domain.repository.UserRepository;
import com.lanthanh.admin.icareapp.presentation.model.UserInfo;
import com.lanthanh.admin.icareapp.presentation.model.dto.DTOAppointment;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author longv
 *         Created on 22-Mar-17.
 */

public class UserRepositoryImpl implements UserRepository {
    private RestClient restClient;
    private LocalStorage localStorage;

    public UserRepositoryImpl(Context context){
        restClient = RestClientImpl.createRestClient();
        localStorage = new LocalStorage(context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE));
    }

    @Override
    public Observable<Boolean> checkUserLoggedIn() {
        UserInfo user = localStorage.getUserFromLocal();
        if (user != null && user.isValid())
            return Observable.just(true);
        return Observable.just(false);
    }

    @Override
    public Observable<RepositorySimpleStatus> checkUserInformationValidity() {
        UserInfo user = localStorage.getUserFromLocal();
        if (user != null) {
            if (user.getStep().equals("none"))
                return Observable.just(RepositorySimpleStatus.MISSING_NAME_AND_ADDRESS);
            else if (user.getStep().equals("basic"))
                return Observable.just(RepositorySimpleStatus.MISSING_DOB_AND_GENDER);
            else if (user.getStep().equals("necessary"))
                return Observable.just(RepositorySimpleStatus.MISSING_EMAIL_AND_PHONE);
            else {
                return Observable.just(RepositorySimpleStatus.VALID_USER);
            }
        }
        return Observable.just(RepositorySimpleStatus.MISSING_USER);
    }

    @Override
    public Observable<Boolean> logout() {
        try {
            localStorage.removeUserFromLocal();
            return Observable.just(true);
        }catch (Exception e) {
            Log.e(this.getClass().getName(), "Error removing user from pref");
            return Observable.just(false);
        }
    }

    @Override
    public Observable<UserInfo> getUserInformation() {
        return Observable.just(localStorage.getUserFromLocal());
    }

    @Override
    public Observable<RepositorySimpleStatus> updateCustomerBasicInfo(String name, String address) {
        UserInfo user = localStorage.getUserFromLocal();
        return restClient.updateBasicInfo(localStorage::saveUserToLocal, user.getToken(), user.getId(), name, address);
    }

    @Override
    public Observable<RepositorySimpleStatus> updateCustomerNecessaryInfo(String dob, String gender) {
        UserInfo user = localStorage.getUserFromLocal();
        return restClient.updateNecessaryInfo(localStorage::saveUserToLocal, user.getToken(), user.getId(), dob, gender);
    }

    @Override
    public Observable<RepositorySimpleStatus> updateCustomerImportantInfo(String email, String phone) {
        UserInfo user = localStorage.getUserFromLocal();
        return restClient.updateImportantInfo(localStorage::saveUserToLocal, user.getToken(), user.getId(), email, phone);
    }

    @Override
    public Observable<RepositorySimpleStatus> updateCustomerInfo(String name, String address, String dob, String gender, String email, String phone) {
        UserInfo user = localStorage.getUserFromLocal();
        return restClient.updateCustomerInfo(localStorage::saveUserToLocal, user.getToken(), user.getId(), name, address, dob, gender, email, phone).map(
            resp -> {
                if (resp == RepositorySimpleStatus.SUCCESS) {
                    List<DTOAppointment> appointmentList = localStorage.getAppointmentsFromLocal();
                    for (DTOAppointment appointment: appointmentList) {
                        appointment.setUser(localStorage.getUserFromLocal());
                    }
                    localStorage.saveAppointmentsToLocal(appointmentList);
                    return RepositorySimpleStatus.SUCCESS;
                }
                return RepositorySimpleStatus.UNKNOWN_ERROR;
            }
        );
    }
}
