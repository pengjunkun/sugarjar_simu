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
		//in PengCheng
//		sendController.readFileAndSent("C:\\Users\\HP\\work\\project\\SugarJar\\simu\\data\\54.csv");
		//in Tsinghua
		sendController.readFileAndSent("C:\\Users\\lenovo\\work\\project\\sugarJar\\simu\\data\\54.csv");

		
	}

}
