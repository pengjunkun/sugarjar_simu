import tools.MyLog;
import tools.MyTool;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/28.
 */
public class Main
{
    static SendController sendController;
	public static void main(String[] args){
		MyLog.jack("write");
		sendController=new SendController();
//		sendController.readFileAndSent("/c/Users/HP/work/project/SugarJar/simu/data/54.csv");
		sendController.readFileAndSent("C:\\Users\\HP\\work\\project\\SugarJar\\simu\\data\\54.csv");

		
	}

}
