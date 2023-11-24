//Bismilahirahmanirahim
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static String mailChecker;

    public static void main(String[] args) {

        File inputFile = new File("src/velki.csv");

        ArrayList<GoogleApps> apps = new ArrayList<>();

        Map<String, Integer> appsPerCategory = new HashMap<>();

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
                    Double price = Double.parseDouble(lineParts[9].trim());
                    String developerId = lineParts[13].trim();
                    String developerEmail = lineParts[15].trim();

                    apps.add(new GoogleApps(appName, appId, category, price, developerId, developerEmail));
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


    // 1. Number of apps per category
    for(GoogleApps t : apps){
        String category = t.getCategory();

        if(appsPerCategory.containsKey(category)){
            appsPerCategory.put(category, appsPerCategory.get(category) + 1);
        }
        else {
            appsPerCategory.put(category, 1);
        }
    }

    /*for(Map.Entry<String, Integer> entry : appsPerCategory.entrySet()){
            String category = entry.getKey();
            int numberOfApps = entry.getValue();
            System.out.println("Category: " + category + ", number of apps : " + numberOfApps);
    }*/

    // 2. Top 100 companies with most apps
    Map <String, Integer> appsPerCompany = new HashMap<>();
    ArrayList <Map.Entry<String, Integer>> appsPerCompanySorted = new ArrayList<>();

    for(GoogleApps t : apps){
        String companyName = t.getAppId().split("\\.")[1].toLowerCase();

        if(!appsPerCompany.containsKey(companyName)){
            appsPerCompany.put(companyName,1);
        }else{
            appsPerCompany.put(companyName, appsPerCompany.get(companyName) + 1);
        }
    }

    // Push u Array, onda sortiraj
    for(Map.Entry<String, Integer> entry : appsPerCompany.entrySet()){
        appsPerCompanySorted.add(entry);
    }
    appsPerCompanySorted.sort((a, b) -> b.getValue() - a.getValue());

    /*for(int i = 0; i < 100; i++){
        System.out.println("Company: " + appsPerCompanySorted.get(i).getKey() + " - Number of apps: " + appsPerCompanySorted.get(i).getValue());
    }*/

    // 3. Top 3 developers not working for the company of the released app
    Map <String, Integer> developerAppCount = new HashMap<>();
    ArrayList <Map.Entry<String, Integer>> developerAppCountSorted = new ArrayList<>();

    for(GoogleApps t : apps){
        try{
            String companyName = t.getAppId().split("\\.")[1].toLowerCase().trim();
            String mailChecker = t.getDeveloperEmail().split("@")[1].toLowerCase().split("\\.")[0].trim();
            String devId = t.getDeveloperId();

            if(!companyName.contains(mailChecker)){
                if(!developerAppCount.containsKey(devId)){
                    developerAppCount.put(devId, 1);
                }
                else{
                    developerAppCount.put(devId, developerAppCount.get(devId) + 1);
                }
            }
        }
        catch (Exception e){
            continue;
        }
    }

    for(Map.Entry<String, Integer> entry : developerAppCount.entrySet()){
        developerAppCountSorted.add(entry);
    }
    developerAppCountSorted.sort((a, b) -> b.getValue() - a.getValue());

    for(int i = 0; i < 20; i++){
        System.out.println("DeveloperID: " + developerAppCountSorted.get(i).getKey() + " - Number of apps: " + developerAppCountSorted.get(i).getValue());
    }

    /*for(Map.Entry<String, Integer> entry : developerAppCount.entrySet()){
        String deva = entry.getKey();
        int numberOfApps = entry.getValue();
        System.out.println("DeveloperID: " + deva + ", number of apps : " + numberOfApps);
    }*/


    // Kraj maina - ispod pisi test kod

    }
}

