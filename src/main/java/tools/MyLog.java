package tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.*;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/12.
 */
public class MyLog
{
	private static String MYTAG = "JackTag";
	public static Logger logger;
	public static BufferedWriter tagWriter;

	//this is a writerList
	//	public static ArrayList<BufferedWriter> writers = new ArrayList<>();
	//	static
	//	{
	//		try
	//		{
	//
	//			for (int i = 0; i < lambdas.length; i++)
	//			{
	//				writers.add(new BufferedWriter(
	//						new FileWriter("./Lam" + lambdas[i] + ".csv")));
	//			}
	//
	//		} catch (IOException e)
	//		{
	//			e.printStackTrace();
	//		}
	//		logger = Logger.getLogger("clientLog");
	//		FileHandler fh = null;
	//		try
	//		{
	//			fh = new FileHandler("client.log");
	//		} catch (IOException e)
	//		{
	//			e.printStackTrace();
	//		}
	//		fh.setLevel(Level.INFO);
	//		fh.setFormatter(new SimpleFormatter());
	//		ConsoleHandler ch = new ConsoleHandler();
	//		ch.setLevel(Level.ALL);
	//		ch.setFormatter(new SimpleFormatter());
	//		logger.addHandler(fh);
	//		logger.addHandler(ch);
	//	}
	//

	public static void initTagWriter(String str)
	{
		MYTAG=str;
		try
		{
			tagWriter = new BufferedWriter(new FileWriter("result/"+MYTAG + ".txt"));
//			tagWriter = new BufferedWriter(new FileWriter("/home/pengjk/result/"+MYTAG + ".txt"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public static void tagWriter(String cont)
	{
		try
		{
			tagWriter.write(cont);
			tagWriter.newLine();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void closeTagWriter()
	{
		try
		{
			tagWriter.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void jack(String s)
	{
				System.out.println(s);
	}

	public static void jackJoint(String s)
	{
		System.out.print(s);
	}

	//	public static void writeByIndicator(String cont)
	//	{
	//		try
	//		{
	//			writers.get(MyConf.LAMBDAINDICATOR).write(cont + " ");
	//			//			writers.get(MyConf.LAMBDAINDICATOR).newLine();
	//		} catch (IOException e)
	//		{
	//			e.printStackTrace();
	//		}
	//	}

}
