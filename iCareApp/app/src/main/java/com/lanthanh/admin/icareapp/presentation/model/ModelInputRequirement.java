package com.lanthanh.admin.icareapp.presentation.model;

/**
 * Created by ADMIN on 30-Oct-16.
 */

public class ModelInputRequirement {
    public static final String USERNAME = "^[^.\\-@](?!.*[\\s[^\\w]])[A-Za-z0-9]{4,62}[^.\\-@]$";
    public static final String PASSWORD = "^[^\\s](?=.*[\\d\\W])(?=.*[a-zA-Z]).{6,253}[^\\s]$";
    public static final String NAME = "[\\p{L}\\-.\\s']{0,100}";
    public static final String ADDRESS = "[\\p{L}\\p{N}\\-.\\s,'\"/]{0,50}";
    public static final String EMAIL = "[\\w._\\-]+@[^@]*[^@]$";
    public static final String PHONE = "^[+\\d][()\\d]{1,16}";
}
