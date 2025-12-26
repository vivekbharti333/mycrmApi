package com.common.helper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ngo.helper.DonationHelper;
import com.ngo.object.request.DashboardReportObject;
import com.ngo.object.request.DonationRequestObject;



@Service
public class TestHelper {

	@Autowired
	private DonationHelper donationHelper;
	
	
	private LocalDate localDate = LocalDate.now();
	private LocalDate nextday = localDate.plus(1, ChronoUnit.DAYS);
	private LocalDate preday = localDate.minus(1, ChronoUnit.DAYS);
	private LocalDate firstDateOfMonth = localDate.withDayOfMonth(1);
	private LocalDate lastDateOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth());

	private Date todayDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	private Date tomorrowDate = Date.from(nextday.atStartOfDay(ZoneId.systemDefault()).toInstant());
	private Date previousDate = Date.from(preday.atStartOfDay(ZoneId.systemDefault()).toInstant());
	private Date firstDateMonth = Date.from(firstDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
	private Date lastDateMonth = Date.from(lastDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
	
	
	@SuppressWarnings("unchecked")
	public String checkIt(DonationRequestObject donationRequest) {
	    System.out.println("hjhlh");
	    // Input data with count and amount as distinct fields
//        Object[][] inputData = {
//            {"Mangalore International Airport", 63, 59375L, null, "INR", "Mangalore International"},
//            {"Dabolim International Airport", 26, 29326L, null, "INR", "Dabolim"},
//            {"Dabolim International Airport", 6, 4585L, null, "MUR", "Dabolim"},
//            {"Ahmedabad International Airport", 25, 26276L, null, "INR", "Ahmedabad International"},
//            {"Ahmedabad International Airport", 15, 2560L, null, "USD", "Ahmedabad International"}
//        };

        Object[][] inputData = donationHelper.getDonationCountAndAmountGroupByNameNew(donationRequest, previousDate, todayDate);
        System.out.println("inputData : "+inputData.toString());
        // Map to store the processed data
        Map<String, Object> airportData = new LinkedHashMap<>();

        for (Object[] record : inputData) {
        	 System.out.println("record 0 : "+record[0]);
        	 System.out.println("record 1 : "+record[1]);
        	 System.out.println("record 2 : "+record[2]);
        	 System.out.println("record 3 : "+record[3]);
        	 System.out.println("record 4 : "+record[4]);
//        	 System.out.println("record 5 : "+record[5]);
            String airportName = (String) record[0];
            Object count = (Object) record[1];  // Count as an integer
            Object amount = (Object) record[2];  // Amount as a long
            String currency = (String) record[3];  // Currency (INR, USD, etc.)
            String displayName = (String) record[4];  // Display name

//            // Data entry: include count, amount, and currency
            Object[] dataEntry = {count, amount, currency};

            // If the airport is already in the map, append the data
            if (airportData.containsKey(airportName)) {
                List<Object[]> dataList = (List<Object[]>) ((Map<String, Object>) airportData.get(airportName)).get("details");
                dataList.add(dataEntry);
            } 
            else {
                // Create a new entry if the airport does not exist in the map
                Map<String, Object> airportDetails = new HashMap<>();
                airportDetails.put("details", new ArrayList<>(Collections.singletonList(dataEntry)));
                airportDetails.put("displayName", displayName);
                airportData.put(airportName, airportDetails);
            }
        }
        
        System.out.println("airportData : "+airportData.toString());
        
        for (Map.Entry<String, Object> entry : airportData.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + ": " + value);
        }
        
        System.out.println("###################");
        
        for (Map.Entry<String, Object> entry : airportData.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.print(key + ": {displayName=" + ((Map<String, Object>) value).get("displayName"));

            // Extract and print the details
            List<Object[]> details = (List<Object[]>) ((Map<String, Object>) value).get("details");
            System.out.print(", details=[");

            for (Object[] detail : details) {
                System.out.print("[");
                for (Object obj : detail) {
                    System.out.print(obj + " ");
                }
                System.out.print("] ");
            }

            System.out.println("}");
        }

        // Print the formatted data
//        airportData.forEach((key, value) -> {
//            System.out.println(key + ": " + value);
//        });

	    return null;
	}
	

}
