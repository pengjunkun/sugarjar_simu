package tools;

import java.io.OptionalDataException;
import java.util.ArrayList;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/28.
 */
public class MyConf
{
	//latency(ms)
	public static final int HIT_LATENCY = 5;
	public static final int MISS_LATENCY = 500;
	//MiB
	public static final int FILE_SIZE = 1;
	public static final int UPDATE_THRESHOLD = 3;
	//-1 means all in one type, without class info
	public static int[] types = new int[202];
	static {
		for (int i =-1;i<201;i++){
			types[i+1]=i;
		}
	}

	//edges:{id,latitude,longitude,size(MiB)}
	//    public static String[][] edgesInfo = { {"1", "40.1", "116.1", "108"}};
	public static String[][] edgesInfo = { { "1", "40.26661944186046", "116.1324861627907", "100" },
			{ "2", "39.858501698630135", "116.53054126027398", "100" },
			{ "3", "39.79028236809816", "116.21845173006135", "100" },
			{ "4", "40.153276293478264", "116.76262652173914", "100" },
			{ "5", "39.99474954767726", "116.37059531051345", "100" } };

	//define some special video type in Integer
	public final static int NOT_REPLACEMENT = -101;
	public final static int WITHOUT_TYPE = -102;
}
