package com.lanthanh.admin.icareapp.presentation.model;

/**
 * Created by ADMIN on 30-Oct-16.
 */

public class InputRequirement {
    public static final String USERNAME = "^[A-Za-z0-9\\-\\_]{8,25}$"; //"^[^.\\-@](?!.*[\\s[^\\w]])[A-Za-z0-9]{4,62}[^.\\-@]$";
    public static final String PASSWORD = "^[A-Za-z0-9]{8,30}$"; //"^[^\\s](?=.*[\\d\\W])(?=.*[a-zA-Z]).{6,253}[^\\s]$";
    public static final String NAME = "^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s\\-.]{0,50}$"; //"[\\p{L}\\-.\\s']{0,100}";
    public static final String ADDRESS = "^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ0-9\\s-.\\/]{0,150}$"; //"[\\p{L}\\p{N}\\-.\\s,'\"/]{0,50}";
    public static final String EMAIL =  "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";//"[\\w._\\-]+@[^@]*[^@]$";
    public static final String PHONE =  "^\\+?[0-9]+[0-9\\-\\s]+\\(?[0-9]+\\)?[0-9\\-\\s]{0,12}$"; //"^[+\\d][()\\d]{1,16}";
}
