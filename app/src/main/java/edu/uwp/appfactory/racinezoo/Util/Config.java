package edu.uwp.appfactory.racinezoo.Util;

/**
 * Created by hanh on 2/11/17.
 */

public class Config {
    public static char[] ALPHABET = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static final String BEACON_LAYOUT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";

    public static final int ANIMAL = 0;
    public static final int EVENT = 1;
    public static final int LOCATION =2;



    public static final String CATEGORY_BIRD ="Birds";
    public static final String CATEGORY_MAMMAL ="Mammals";
    public static final String CATEGORY_REPTILE ="Reptile";
    public static final String CATEGORY_AMPHIBIAN ="Amphibian";


    public static final int DETAIL_TYPE_GENERAL = 0;
    public static final int DETAIL_TYPE_FACT = 1;
    public static final int DETAIL_TYPE_LOCATION = 2;
    public static final int DETAIL_TYPE_INFO = 3;
    public static final int DETAIL_TYPE_CLICKABLE_ANIMAL = 4;


    public static final int INFO_TYPE_HOUR = 1;
    public static final int INFO_TYPE_DIRECT = 2;
    public static final int INFO_TYPE_SHOW = 3;
    public static final int INFO_TYPE_ABOUT = 4;
    public static final int INFO_TYPE_PRIVACY = 5;
    public static final int INFO_TYPE_ADMIN = 6;
    public static final int INFO_TYPE_FOOD = 7;
    public static final int INFO_TYPE_CONTACT = 8;

    public static final String PREF_EXPLORE_MODE = "EXPLORE_MODE";

    //1h
    //public static final long BEACON_WAIT_INTERVAL = 1000*60*60;
    //1 min
    public static final long BEACON_WAIT_INTERVAL = 1000*60;







}
