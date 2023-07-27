package dataProviders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import utilities.LoggerLoad;


public class ConfigReader {

	private static Properties properties;
	private static final String propertyFilePath = "src/test/resources/configs/Configuration.properties";

	public static void loadProperty() 
	{
		BufferedReader reader;
		try 
		{
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try 
			{
				properties.load(reader);
				reader.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}
	}
	
	public static String getProperty( String key) {
		return properties.getProperty(key);
	}
	
	public static void setProperty(String key, String value) throws IOException {
		
		FileOutputStream out;
		try {
			out = new FileOutputStream(propertyFilePath);
			properties.setProperty(key, value);;
			properties.store(out, null);
		} catch (FileNotFoundException e) {
			LoggerLoad.logInfo(e.getMessage());
			e.printStackTrace();
		}	 
	}
	
	public static String getInvalidAssignmentId()
	{
		String val = properties.getProperty("assignment.invalidid");
		if (val != null)
			return val;
		else
			throw new RuntimeException("assignment.invalidid not specified in the Configuration.properties file.");
	}
	
	// program urls
	
	public static String getProgramPostUrl()
	{
		String val = properties.getProperty("program.posturl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("program.posturl not specified in the Configuration.properties file.");
	}
	
	public static String getProgramDeleteByIdUrl()
	{
		String val = properties.getProperty("program.deletebyidurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("program.deletebyidurl not specified in the Configuration.properties file.");
	}
	
	// program batch urls
	
	public static String getBatchPostUrl()
	{
		String val = properties.getProperty("batch.posturl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("batch.posturl not specified in the Configuration.properties file.");
	}
	
	public static String getBatchGetAllUrl()
	{
		String val = properties.getProperty("batch.getallurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("batch.getallurl not specified in the Configuration.properties file.");
	}
	
	public static String getBatchGetByBatchIdUrl()
	{
		String val = properties.getProperty("batch.getbybatchidurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("batch.getbybatchidurl not specified in the Configuration.properties file.");
	}
	
	public static String getBatchGetByBatchNameUrl()
	{
		String val = properties.getProperty("batch.getbybatchnameurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("batch.getbybatchnameurl not specified in the Configuration.properties file.");
	}
	
	public static String getBatchGetByProgramIdUrl()
	{
		String val = properties.getProperty("batch.getbyprogramidurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("batch.getbyprogramidurl not specified in the Configuration.properties file.");
	}
	
	public static String getBatchPutBatchUrl()
	{
		String val = properties.getProperty("batch.putbatchurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("batch.putbatchurl not specified in the Configuration.properties file.");
	}
	
	public static String getBatchDeleteByIdUrl()
	{
		String val = properties.getProperty("batch.deletebyidurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("batch.deletebyidurl not specified in the Configuration.properties file.");
	}
	
	// user urls
	
	public static String getUserPostUrl()
	{
		String val = properties.getProperty("user.posturl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("user.posturl not specified in the Configuration.properties file.");
	}
	
	public static String getUserGetAllUrl()
	{
		String val = properties.getProperty("user.getallurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("user.getallurl not specified in the Configuration.properties file.");
	}
	
	public static String getUserGetByUserIdUrl()
	{
		String val = properties.getProperty("user.getbyuseridurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("user.getbyuseridurl not specified in the Configuration.properties file.");
	}
	
	public static String getUserGetAllStaffUrl()
	{
		String val = properties.getProperty("user.getallstaffurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("user.getallstaffurl not specified in the Configuration.properties file.");
	}
	
	public static String getUserGetAllUsersWithRolesUrl()
	{
		String val = properties.getProperty("user.getalluserswithrolesurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("user.getalluserswithrolesurl not specified in the Configuration.properties file.");
	}
	
	public static String getUserDeleteByIdUrl()
	{
		String val = properties.getProperty("user.deletebyidurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("user.deletebyidurl not specified in the Configuration.properties file.");
	}
	
	// assignment urls
	
	public static String getAssignmentPostUrl()
	{
		String val = properties.getProperty("assignment.posturl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("assignment.posturl not specified in the Configuration.properties file.");
	}
	
	public static String getAssignmentGetAllUrl()
	{
		String val = properties.getProperty("assignment.getallurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("assignment.getallurl not specified in the Configuration.properties file.");
	}
	
	public static String getAssignmentGetByAssignmentIdUrl()
	{
		String val = properties.getProperty("assignment.getbyassignmentidurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("assignment.getbyassignmentidurl not specified in the Configuration.properties file.");
	}
	
	public static String getAssignmentGetByBatchIdUrl()
	{
		String val = properties.getProperty("assignment.getbybatchidurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("assignment.getbybatchidurl not specified in the Configuration.properties file.");
	}
	
	public static String getAssignmentPutAssignmentUrl()
	{
		String val = properties.getProperty("assignment.putassignmenturl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("assignment.putassignmenturl not specified in the Configuration.properties file.");
	}
	
	public static String getAssignmentDeleteByIdUrl()
	{
		String val = properties.getProperty("assignment.deletebyidurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("assignment.deletebyidurl not specified in the Configuration.properties file.");
	}
	
	// assignment submit urls
	
	public static String getSubmitPostUrl()
	{
		String val = properties.getProperty("submit.posturl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("submit.posturl not specified in the Configuration.properties file.");
	}
	
	public static String getSubmitGetAllUrl()
	{
		String val = properties.getProperty("submit.getallurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("submit.getallurl not specified in the Configuration.properties file.");
	}
	
	public static String getSubmitGetByAssignmentIdUrl()
	{
		String val = properties.getProperty("submit.getbyassignmentidurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("submit.getbyassignmentidurl not specified in the Configuration.properties file.");
	}
	
	public static String getSubmitGetGradeByStudentIdUrl()
	{
		String val = properties.getProperty("submit.getgradebystudentidurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("submit.getgradebystudentidurl not specified in the Configuration.properties file.");
	}
	
	public static String getSubmitGetGradeByBatchIdUrl()
	{
		String val = properties.getProperty("submit.getgradebybatchidurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("submit.getgradebybatchidurl not specified in the Configuration.properties file.");
	}
	
	public static String getSubmitGetSubmissionByUserIdUrl()
	{
		String val = properties.getProperty("submit.getsubmissionbyuseridurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("submit.getsubmissionbyuseridurl not specified in the Configuration.properties file.");
	}
	
	public static String getSubmitGetSubmissionByBatchIdUrl()
	{
		String val = properties.getProperty("submit.getsubmissionbybatchidurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("submit.getsubmissionbybatchidurl not specified in the Configuration.properties file.");
	}
	
	public static String getSubmitDeleteByIdUrl()
	{
		String val = properties.getProperty("submit.deletebyidurl");
		if (val != null)
			return val;
		else
			throw new RuntimeException("submit.deletebyidurl not specified in the Configuration.properties file.");
	}
}
