package apiEngine.routes;

public class ProgramRoutes {
	
	public static String createProgram()
	{
		return "/saveprogram";
	}
	
	public static String deleteProgramById(int programId)
	{
		return "/deletebyprogid/" + programId;
	}

}
