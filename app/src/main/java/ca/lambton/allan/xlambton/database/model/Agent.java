package ca.lambton.allan.xlambton.database.model;

public class Agent implements SingleIdEntity<Integer> {

    public static final String TABLE_NAME = "agent";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_AGENCY = "agency";
    public static final String COLUMN_WEB_SITE = "web_site";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_PHONE = "phone";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_LEVEL + " TEXT,"
                    + COLUMN_AGENCY + " TEXT,"
                    + COLUMN_WEB_SITE + " TEXT,"
                    + COLUMN_ADDRESS + " TEXT,"
                    + COLUMN_COUNTRY + " TEXT,"
                    + COLUMN_PHONE + " TEXT"
                    + ")";

    private Integer id;
    private String name;
    private String level;
    private String agency;
    private String webSite;
    private String address;
    private String country;
    private String phone;


    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Agent{" + "id=" + id +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", agency='" + agency + '\'' +
                ", webSite='" + webSite + '\'' +
                ", address='" + address + '\'' +
                ", country='" + country + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
