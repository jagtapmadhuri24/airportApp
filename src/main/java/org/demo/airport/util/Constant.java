package org.demo.airport.util;

public class Constant {
    public static final String SUCCESS ="Success";
    public static final String[] RUNWAYS_COLUMNS = new String[]{"id","airport_ref","airport_ident","length_ft","width_ft",
            "surface","lighted","closed","le_ident","le_latitude_deg","le_longitude_deg","le_elevation_ft",
            "le_heading_degT","le_displaced_threshold_ft","he_ident",
            "he_latitude_deg","he_longitude_deg",	"he_elevation_ft","he_heading_degT",
            "he_displaced_threshold_ft"};

    public static  final String[] AIRPORT_COLUMNS = new String[]{"id","ident","type",	"name",	"latitude_deg","longitude_deg","elevation_ft","continent"
            , "iso_country","iso_region","municipality","scheduled_service"
            ,"gps_code","iata_code","local_code","home_link","wikipedia_link","keywords"};

    public static final String[] COUNTRIES_COLUMNS = new String[]{"id", "code", "name", "continent", "wikipedia_link", "keywords"};

    public static final String TOP_N_COUNTRIES_WITH_HIGHEST_AIRPORTS ="B";

    public static final String RUNWAYS_FOR_COUNTRY_AIRPORTS ="A";

    public static final String COUNTRY_CODE ="Code";

    public static final String COUNTRY_NAME ="Name";

    public static final String  EMPTY_STRING = "";

    public static final String AIRPORTS_CSV = "AIRPORTS";

    public static final String COUNTRIES_CSV= "COUNTRIES";

    public static final String RUNWAYS_CSV= "RUNWAYS";




}
