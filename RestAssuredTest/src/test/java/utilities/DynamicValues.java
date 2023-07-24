package utilities;

public class DynamicValues {
	
	public static String SerialNumber()
	{
		return String.valueOf(Math.floor(Math.random() * 100000));
	}
	
	public static int PhoneNumber()
	{
		return (int) (Math.floor(Math.random() * 1000000000));
	}

}
