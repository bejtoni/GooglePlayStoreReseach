public class GoogleApps {
    private final String appName;
    private final String appId;
    private final String category;
    private final Double developerEmail;
    private final String price;

    public GoogleApps(String AppName, String AppId, String Category, Double DeveloperEmail, String Price) {

        appName = AppName;
        appId = AppId;
        category = Category;
        developerEmail = DeveloperEmail;
        price = Price;
    }

    public String getCategory() {
        return category;
    }

    public String getAppId() {
        return appId.toString();
    }

    @Override
    public String toString() {
        return "GoogleApps{" +
                "appName='" + appName + '\'' +
                ", appId='" + appId + '\'' +
                ", category='" + category + '\'' +
                ", developerEmail=" + developerEmail +
                ", price='" + price + '\'' +
                '}';
    }
}


// AppName, AppId, Category, DeveloperEmail, Price