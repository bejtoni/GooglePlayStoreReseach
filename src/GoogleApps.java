public class GoogleApps {
    private final String appName;
    private final String appId;
    private final String category;
    private final String developerId;
    private final String developerEmail;
    private final Double price;
    private final Boolean isFree;

    public GoogleApps(String AppName, String AppId, String Category, Double Price, String developerId, String DeveloperEmail, Boolean isFree) {

        appName = AppName;
        appId = AppId;
        category = Category;
        this.developerId = developerId;
        developerEmail = DeveloperEmail;
        price = Price;
        this.isFree = isFree;
    }

    public String getCategory() {
        return category;
    }

    public String getAppId() {
        return appId.toString();
    }

    public String getDeveloperEmail() {
        return developerEmail;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public Boolean getFreeCheck() {
        return isFree;
    }

    public String getAppName() {
        return appName;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "GoogleApps{" +
                "appName='" + appName + '\'' +
                ", appId='" + appId + '\'' +
                ", category='" + category + '\'' +
                ", developerId='" + developerId + '\'' +
                ", developerEmail='" + developerEmail + '\'' +
                ", price=" + price +
                '}';
    }
}
