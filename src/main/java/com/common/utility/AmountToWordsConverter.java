package com.common.utility;

import org.springframework.stereotype.Component;

@Component
public class AmountToWordsConverter {

	@SuppressWarnings("unused")
	public String amountInWords(long number) {

	    if (number == 0) return "Zero";

	    String[] units = {
	            "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
	            "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen",
	            "Sixteen", "Seventeen", "Eighteen", "Nineteen"
	    };

	    String[] tens = {
	            "", "", "Twenty", "Thirty", "Forty", "Fifty",
	            "Sixty", "Seventy", "Eighty", "Ninety"
	    };

	    if (number < 20)
	        return units[(int) number];

	    if (number < 100)
	        return tens[(int) (number / 10)] +
	                ((number % 10 != 0) ? " " + units[(int) (number % 10)] : "");

	    if (number < 1000)
	        return units[(int) (number / 100)] + " Hundred" +
	                ((number % 100 != 0) ? " " + amountInWords(number % 100) : "");

	    if (number < 100000)
	        return amountInWords(number / 1000) + " Thousand" +
	                ((number % 1000 != 0) ? " " + amountInWords(number % 1000) : "");

	    if (number < 10000000)
	        return amountInWords(number / 100000) + " Lakh" +
	                ((number % 100000 != 0) ? " " + amountInWords(number % 100000) : "");

	    return amountInWords(number / 10000000) + " Crore" +
	            ((number % 10000000 != 0) ? " " + amountInWords(number % 10000000) : "");
	}

}
