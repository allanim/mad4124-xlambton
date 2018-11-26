package ca.lambton.allan.xlambton.database.model;

public class Mission implements SingleIdEntity<Integer> {

    public static final String TABLE_NAME = "mission";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AGENT_ID = "agentId";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_STATUS = "status";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_AGENT_ID + " INTEGER,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + COLUMN_STATUS + " TEXT"
                    + ")";

    private Integer id;
    private Integer agentId;
    private String description;
    private String date;
    private String status;


    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Mission{" + "id=" + id +
                ", agentId=" + agentId +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}
