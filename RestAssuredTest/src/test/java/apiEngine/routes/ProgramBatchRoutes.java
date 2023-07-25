package apiEngine.routes;

public class ProgramBatchRoutes {
	
	public static String createBatch()
	{
		return "/batches";
	}
	
	public static String getAllBatches(String dataKey)
	{
		String endpoint = null;
		if("Invalid".equals(dataKey))
			endpoint = "/batchesss";
		else 
			endpoint = "/batches";	

		
		System.out.println("endpoint in "+endpoint);
		return endpoint;
		
	}
	
	public static String getBatchById(Integer batchId,String dataKey)
	{
		String endpoint = null;
		if("Invalid".equals(dataKey))
			endpoint = "/batches/batchId/0000" ;
		else 
			endpoint = "/batches/batchId/" + batchId;;	
		return endpoint;
	}
	
	public static String getBatchByBatchName(String batchName,String dataKey)
	{
		String endpoint = null;
		if("Invalid".equals(dataKey))
			endpoint = "/batches/batchName/0000" ;
		else 
			endpoint ="/batches/batchName/" + batchName;
		return endpoint;
	}
	
	public static String getBatchByProgramId(Integer programId,String dataKey)
	{
		String endpoint = null;
		if("Invalid".equals(dataKey))
			endpoint = "/batches/program/0000" ;
		else 
			endpoint ="/batches/program/" + programId;
		return endpoint;
		
	}
	
	public static String updateBatch(Integer batchId,String dataKey)
	{
		String endpoint = null;
		if("Put_Batch_Invalid".equals(dataKey))
			endpoint = "/batches/0000" ;
		else 
			endpoint = "/batches/" + batchId;;	
		return endpoint;
		
	}
	
	public static String deleteBatch(int batchId,String dataKey)
	{
		String endpoint = null;
		if("Invalid".equals(dataKey))
			endpoint = "/batches/0000" ;
		else 
			endpoint ="/batches/" + batchId;
		return endpoint;
	}

}
