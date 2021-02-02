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
	public static int[] types = { 1, 2, 3, 4, 5, 6, 7 };

	//edges:{id,latitude,longitude,size(MiB)}
	public static String[][] edgesInfo = { { "0", "39.1", "116.1", "1000" }, { "1", "40.1", "116.1", "1005" } };
}
