package apiEngine.routes;

import dataProviders.ConfigReader;

public class ProgramRoutes {
	
	public static String createProgram()
	{
		return ConfigReader.getProgramPostUrl();
	}
	
	public static String deleteProgramById(int programId)
	{
		return ConfigReader.getProgramDeleteByIdUrl() + programId;
	}

}
