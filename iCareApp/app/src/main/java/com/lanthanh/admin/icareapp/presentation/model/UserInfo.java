package com.lanthanh.admin.icareapp.presentation.model;

/**
 * @author longv
 * Created on 20-Mar-17.
 */

public class UserInfo {
    private String id;
    private String name;
    private String address;
    private String dob;
    private String gender;
    private String phone;
    private String email;
    private String token;
    private String step;
    private int active;


    public UserInfo(String id, String name, String dob, String gender, String phone, String address, String email, String token, String step, int active){
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.token = token;
        this.step = step;
        this.active = active;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getDateOfBirth(){
        return dob;
    }

    public String getGender(){
        return gender;
    }

    public String getPhone(){
        return phone;
    }

    public String getAddress(){
        return address;
    }

    public String getEmail(){
        return email;
    }

    public String getToken(){
        return token;
    }

    public String getStep() {
        return step;
    }

    public int getActive() {
        return active;
    }
    /**
     * Check whether user is valid (all information is provided)
     * @return boolean True if valid, else false
     * @see #isBasicInfoValid()}
     * @see #isNecessaryInfoValid()
     * @see #isImportantInfoValid()
     */
    public boolean isValid() {
        boolean valid = false;
        try {
            valid = !name.isEmpty() && !dob.isEmpty() && !gender.isEmpty() && !phone.isEmpty() && !address.isEmpty() && !email.isEmpty()
                    && !id.isEmpty() && !token.isEmpty();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return valid;
    }

//    /**
//     * Check whether user's basic information is valid
//     * @return boolean True if valid, else false
//     * @see #isValid()
//     * @see #isNecessaryInfoValid()
//     * @see #isImportantInfoValid()
//     */
//    public boolean isBasicInfoValid() {
//        boolean valid = false;
//        try {
//            valid = !name.isEmpty() && !address.isEmpty() && !id.isEmpty() && !token.isEmpty();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//        return valid;
//    }
//
//    /**
//     * Check whether user's necessary information is valid
//     * @return boolean True if valid, else false
//     * @see #isValid()
//     * @see #isBasicInfoValid()
//     * @see #isImportantInfoValid()
//     */
//    public boolean isNecessaryInfoValid() {
//        boolean valid = false;
//        try {
//            valid = !dob.isEmpty() && !gender.isEmpty() && !id.isEmpty() && !token.isEmpty();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//        return valid;
//    }
//
//    /**
//     * Check whether user's important information is valid
//     * @return boolean True if valid, else false
//     * @see #isValid()
//     * @see #isBasicInfoValid()
//     * @see #isNecessaryInfoValid()
//     */
//    public boolean isImportantInfoValid() {
//        boolean valid = false;
//        try {
//            valid = !phone.isEmpty() && !email.isEmpty() && !id.isEmpty() && !token.isEmpty();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//        return valid;
//    }
}
