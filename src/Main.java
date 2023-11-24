import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        File inputFile = new File("src/velki.csv");
        ArrayList<GoogleApps> apps = new ArrayList<>();

        try {
            Scanner s1 = new Scanner(inputFile);

            if(s1.hasNextLine()){
                s1.nextLine();
            }

            ArrayList<String> faultyLines = new ArrayList<>();

            while (s1.hasNextLine()){
                String currentLine  = s1.nextLine();
                String[] lineParts = currentLine.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");

                try {
                    String appName = lineParts[0].trim();
                    String appId = lineParts[1].trim();
                    String category = lineParts[2].trim();
                    Boolean isFree = Boolean.parseBoolean(lineParts[8].trim());
                    Double price = Double.parseDouble(lineParts[9].trim());
                    String developerId = lineParts[13].trim();
                    String developerEmail = lineParts[15].trim();

                    apps.add(new GoogleApps(appName, appId, category, price, developerId, developerEmail, isFree));
                }
                catch (Exception e){
                    faultyLines.add(currentLine);
                }
            }
            s1.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }


    // $$$ 1. Number of apps per category
    Map<String, Integer> appsPerCategoryMap = new HashMap<>();
    for(GoogleApps t : apps){
        String category = t.getCategory();

        if(appsPerCategoryMap.containsKey(category)){
            appsPerCategoryMap.put(category, appsPerCategoryMap.get(category) + 1);
        }
        else {
            appsPerCategoryMap.put(category, 1);
        }
    }

    // $$$ 2. Top 100 companies with most apps
    Map <String, Integer> appsPerCompanyMap = new HashMap<>();
    ArrayList <Map.Entry<String, Integer>> appsPerCompanySorted = new ArrayList<>();

    for(GoogleApps t : apps){
        String companyName = t.getAppId().split("\\.")[1].toLowerCase();

        if(!appsPerCompanyMap.containsKey(companyName)){
            appsPerCompanyMap.put(companyName,1);
        }else{
            appsPerCompanyMap.put(companyName, appsPerCompanyMap.get(companyName) + 1);
        }
    }

    // Push u array, onda sortiraj
    for(Map.Entry<String, Integer> entry : appsPerCompanyMap.entrySet()){
        appsPerCompanySorted.add(entry);
    }
    appsPerCompanySorted.sort((a, b) -> b.getValue() - a.getValue());


    // $$$ 3. Top 3 developers not working for the company of the released app
    Map <String, Integer> developerAppCountMap = new HashMap<>();
    ArrayList <Map.Entry<String, Integer>> developerAppCountSorted = new ArrayList<>();

    for(GoogleApps t : apps){
        try{
            String companyName = t.getAppId().split("\\.")[1] + "." + t.getAppId().split("\\.")[0];
            String mailChecker = t.getDeveloperEmail().split("@")[1].toLowerCase().trim();
            String devId = t.getDeveloperId();

            if(!companyName.contains(mailChecker)){
                if(!developerAppCountMap.containsKey(devId)){
                    developerAppCountMap.put(devId, 1);
                }
                else{
                    developerAppCountMap.put(devId, developerAppCountMap.get(devId) + 1);
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    for(Map.Entry<String, Integer> entry : developerAppCountMap.entrySet()){
        developerAppCountSorted.add(entry);
    }

    developerAppCountSorted.sort((a, b) -> b.getValue() - a.getValue());


    /* 4. Number of apps that can be bought for 1k and 10k */
    ArrayList<GoogleApps> appsSortedByPrice = new ArrayList<>(apps);
    appsSortedByPrice.sort((a, b) -> Double.compare(b.getPrice(), a.getPrice()));

    Integer milja = totalAppCountUsingBudget(1000.0, appsSortedByPrice);
    Integer desetmilja = totalAppCountUsingBudget(10000.0, appsSortedByPrice);

    /* 5. Total number free vs paid apps */
    Map <String, Integer> freeVsPaidMap = new HashMap<>();
    freeVsPaidMap.put("Free", 0);
    freeVsPaidMap.put("Paid", 0);

    for(GoogleApps t : apps){
        if(t.getFreeCheck())
            freeVsPaidMap.put("Free", freeVsPaidMap.get("Free") + 1);
        else
            freeVsPaidMap.put("Paid", freeVsPaidMap.get("Paid") + 1);
    }

    /* Report generator */
    writeReport1(appsPerCategoryMap);
    writeReport2(appsPerCompanySorted);
    writeReport3(developerAppCountSorted);
    writeReport4(milja, desetmilja);
    writeReport5(freeVsPaidMap);

    // KRAJ args[]
    }

    private static void writeReport5(Map<String, Integer> freeVsPaidMap) {
        try {
            FileWriter fw = new FileWriter("Report 5 - Total number of Free apps vs Paid apps.txt");

            fw.write("Number of Free apps --> " + freeVsPaidMap.get("Free") + "\n");
            fw.write("Number of Free apps --> " + freeVsPaidMap.get("Paid") + "\n");

            fw.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void writeReport4(Integer milja, Integer desetmilja) {
        try {
            FileWriter fw = new FileWriter("Report 4 - Apps bought with 1000$ and 10000$.txt");

            fw.write("Number of apps that can be bought with 1000$ including Free apps : " + milja + "\n");
            fw.write("Number of apps that can be bought with 10000$ including Free apps : " + desetmilja + "\n");

            fw.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void writeReport3(ArrayList<Map.Entry<String, Integer>> developerAppCountSorted) {
        try {
            FileWriter fw = new FileWriter("Report 3 - Top 3 developers not working for the company that released the app.txt");
            fw.write("Top 3 developers : \n");

            for(int i = 0; i < 3; i++){
                fw.write("DeveloperID: " + developerAppCountSorted.get(i).getKey() + " - Number of apps: " + developerAppCountSorted.get(i).getValue() + "\n");
            }

            fw.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void writeReport2(ArrayList<Map.Entry<String, Integer>> appsPerCompanySorted) {
        try {
            FileWriter fw = new FileWriter("Report 2 - Top 100 companies with most apps.txt");
            fw.write("Place | Company | Number of apps\n");

            for(int i = 0; i < 100; i++){
                fw.write((i+1) + "  |  " + appsPerCompanySorted.get(i).getKey() + "  |  " + appsPerCompanySorted.get(i).getValue() + "\n");
            }
            fw.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    private static void writeReport1(Map<String, Integer> appsPerCategoryMap) {
        try {
            FileWriter fw = new FileWriter("Report 1 - Apps per category.txt");
            fw.write("Category | Number of apps\n");

            for (Map.Entry<String, Integer> entry : appsPerCategoryMap.entrySet()) {
                String category = entry.getKey();
                int numberOfApps = entry.getValue();
                fw.write(category + " | " + numberOfApps + "\n");
            }
            fw.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static Integer totalAppCountUsingBudget(Double budget, ArrayList<GoogleApps> appSorted) {
        int appNum = 0;

        for(GoogleApps obj : appSorted) {
            if (obj.getFreeCheck()) {
                appNum++;
            } else {
                if (budget - obj.getPrice() >= 0) {
                    budget -= obj.getPrice();
                    appNum++;
                    //System.out.println("Name :" + obj.getAppName() + ", price : " + obj.getPrice() + ", budget : " + budget + ", total count : " + appNum);
                }
            }
        }
        return appNum;
    }
}

