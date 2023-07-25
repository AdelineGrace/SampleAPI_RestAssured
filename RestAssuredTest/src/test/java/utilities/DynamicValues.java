package utilities;

import org.apache.commons.lang3.RandomStringUtils;

public class DynamicValues {
	
	public static String SerialNumber()
	{
		return RandomStringUtils.randomNumeric(5);
		
	}
	
	public static int PhoneNumber()
	{
		return (int) (Math.floor(Math.random() * 1000000000));
	}

}
