public class GoogleApps {
    private final String appName;
    private final String appId;
    private final String category;
    private final String developerId;
    private final String developerEmail;
    private final Double price;

    public GoogleApps(String AppName, String AppId, String Category, Double Price, String developerId, String DeveloperEmail) {

        appName = AppName;
        appId = AppId;
        category = Category;
        this.developerId = developerId;
        developerEmail = DeveloperEmail;
        price = Price;
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
