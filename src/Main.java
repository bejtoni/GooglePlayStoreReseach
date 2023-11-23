import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        File inputFile = new File("src/gps1000.csv");

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
                    String developerEmail = lineParts[15].trim();

                    apps.add(new GoogleApps(appName, appId, category, price, developerEmail));
                }
                catch (Exception e){
                    faultyLines.add(currentLine);
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    // Number of apps per category
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

    // Top 100 companies with most apps
    Map <String, Integer> appsPerCompany = new HashMap<>();

    for(GoogleApps t : apps){
        String companyName = t.getAppId().split("\\.")[1].toLowerCase();

        if(!appsPerCompany.containsKey(companyName)){
            appsPerCompany.put(companyName,0);
        }else{
            appsPerCompany.put(companyName, appsPerCompany.get(companyName) + 1);
        }
    }

    for(Map.Entry<String, Integer> entry : appsPerCompany.entrySet()){
        String company = entry.getKey();
        Integer num = entry.getValue();
        System.out.println(company + " " + num);
    }

    // Kraj maina - ispod pisi test kod

        /*for(GoogleApps t : apps){
            System.out.println(t);
        }*/
    }
}

