package apiEngine.routes;

import dataProviders.ConfigReader;

public class ProgramBatchRoutes {
	
	public static String createBatch()
	{
		return ConfigReader.getBatchPostUrl();
	}
	
	public static String getAllBatches(String dataKey)
	{
		String endpoint = null;
		if("Invalid".equals(dataKey))
			endpoint = ConfigReader.getBatchGetAllUrl() + "ss";
		else 
			endpoint = ConfigReader.getBatchGetAllUrl();	

		
		System.out.println("endpoint in "+endpoint);
		return endpoint;
		
	}
	
	public static String getBatchById(Integer batchId,String dataKey)
	{
		String endpoint = null;
		if("Invalid".equals(dataKey))
			endpoint = ConfigReader.getBatchDeleteByIdUrl() + "0000" ;
		else 
			endpoint = ConfigReader.getBatchDeleteByIdUrl() + batchId;;	
		return endpoint;
	}
	
	public static String getBatchByBatchName(String batchName,String dataKey)
	{
		String endpoint = null;
		if("Invalid".equals(dataKey))
			endpoint = ConfigReader.getBatchGetByBatchNameUrl() + "0000" ;
		else 
			endpoint = ConfigReader.getBatchGetByBatchNameUrl() + batchName;
		return endpoint;
	}
	
	public static String getBatchByProgramId(Integer programId,String dataKey)
	{
		String endpoint = null;
		if("Invalid".equals(dataKey))
			endpoint = ConfigReader.getBatchGetByProgramIdUrl() + "0000" ;
		else 
			endpoint = ConfigReader.getBatchGetByProgramIdUrl() + programId;
		return endpoint;
		
	}
	
	public static String updateBatch(Integer batchId,String dataKey)
	{
		String endpoint = null;
		if("Put_Batch_Invalid".equals(dataKey))
			endpoint = ConfigReader.getBatchPutBatchUrl() + "0000" ;
		else 
			endpoint = ConfigReader.getBatchPutBatchUrl() + batchId;;	
		return endpoint;
		
	}
	
	public static String deleteBatch(int batchId,String dataKey)
	{
		String endpoint = null;
		if("Invalid".equals(dataKey))
			endpoint = ConfigReader.getBatchDeleteByIdUrl() + "0000" ;
		else 
			endpoint = ConfigReader.getBatchDeleteByIdUrl() + batchId;
		return endpoint;
	}
	
	public static String deleteBatchById(int batchId)
	{
		return ConfigReader.getBatchDeleteByIdUrl() + batchId;
	}

}
