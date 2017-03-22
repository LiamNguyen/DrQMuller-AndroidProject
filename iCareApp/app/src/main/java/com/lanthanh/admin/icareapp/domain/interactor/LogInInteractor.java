package com.lanthanh.admin.icareapp.domain.interactor;

import com.lanthanh.admin.icareapp.domain.repository.RepositorySimpleStatus;
import com.lanthanh.admin.icareapp.domain.repository.WelcomeRepository;

import io.reactivex.Observable;

/**
 * @author longv
 * Created on 19-Mar-17.
 */

public class LogInInteractor extends BaseInteractor<RepositorySimpleStatus, LogInInteractor.Params> {
    private WelcomeRepository welcomeRepository;

    public LogInInteractor(WelcomeRepository welcomeRepository){
        super();
        this.welcomeRepository = welcomeRepository;
    }

    @Override
    Observable<RepositorySimpleStatus> buildUseCaseObservable(Params params) {
        return welcomeRepository.login(params.username, params.password);
    }

//    public void login(String username, String password) {
//        RequestBody body = restClient.createRequestBody(new String[]{"username", "password"}, new String[]{username, password});
//        compositeDisposable.add(
//                service.login(body)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<JsonObject>() {
//                            String result;
//
//                            @Override
//                            public void onNext(JsonObject jsonObject) {
//                                try {
//                                    JsonArray array = jsonObject.getAsJsonArray("Select_ToAuthenticate");
//                                    if (array.get(0).getAsJsonObject().get("Status").getAsString().equals("0"))
//                                        result = Service.Status.UNAUTHORIZED;
//                                    else
//                                        result = array.get(1).getAsJsonObject().get("jwt").getAsString();
//                                } catch (Exception e) {
//                                    result = Service.Status.INTERNAL_ERROR;
//                                    Log.e(TAG, "Login status: onNext -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "Login status: onError -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
//                                e.printStackTrace();
//                            }
//
//                            @Override
//                            public void onComplete() {
//                                RegisterActivityPresenterImpl.this.mView.hideProgress();
//                                if (result.equals(Service.Status.UNAUTHORIZED) || result.equals(Service.Status.INTERNAL_ERROR)) {
//                                    if (result.equals(Service.Status.UNAUTHORIZED)) {
//                                        RegisterActivityPresenterImpl.this.mView.showError("Tên Đăng Nhập Hoặc Mật Khẩu Sai");
//                                    }
//                                } else {
//                                    final JWTVerifier verifier = new JWTVerifier("drmuller");
//                                    try {
//                                        final Map<String, String> jwtClaims = (Map<String, String>) verifier.verify(result).get("data");
//                                        ModelUser user = new ModelUser(Integer.parseInt(jwtClaims.get("userId")),
//                                                Integer.parseInt(jwtClaims.get("active")),
//                                                jwtClaims.get("step"),
//                                                jwtClaims.get("userName"),
//                                                jwtClaims.get("userGender"),
//                                                jwtClaims.get("userDob"),
//                                                jwtClaims.get("userAddress"),
//                                                jwtClaims.get("userEmail"),
//                                                jwtClaims.get("userPhone"));
//                                        customerManager.saveLocalUserToPref(sharedPreferences, user);
//                                        if (user.getActive() != 0)
//                                            navigateToMainActivity();
//                                        else
//                                            navigateToUserInfo(user.getID(), user.getStep());
//                                        Log.i(TAG, "Login status: onComplete -> " + Service.Status.SUCCESS);
//                                    } catch (Exception e) {
//                                        Log.e(TAG, "Login status: onComplete -> " + Service.Status.INTERNAL_ERROR + "\n" + e.toString());
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        })
//
//        );
//    }

    public static final class Params{
        private String username, password;

        private Params(String username , String password){
            this.username = username;
            this.password = password;
        }

        public static Params forLogin(String username, String password){
            return new Params(username, password);
        }
    }
}
