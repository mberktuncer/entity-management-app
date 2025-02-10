package com.mberktuncer.entity_management_app.constants;

public class Constants {

    private Constants(){}

    public static final String ACCOUNT_TYPE_USD = "USD";
    public static final String ACCOUNT_TYPE_GOLD = "GOLD";
    public static final String ACCOUNT_TYPE_TL = "TL";

    public static final String TRANSACTION_TYPE_SELF = "Self" ;
    public static final String TRANSACTION_TYPE_AA = "Account to Account";

    public static final String QUALIFIER_USD = "UsdTransactionService";
    public static final String QUALIFIER_GOLD = "GoldTransactionService";
    public static final String QUALIFIER_TL = "TurkishLiraTransactionService";


    public static final String REST_GOLD_CURRENCY_URL = "https://rest.altinkaynak.com/Gold.json";

}
