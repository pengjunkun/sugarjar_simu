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
//		sendController.readFileAndSent("C:\\work\\data\\iqiyi\\24h.csv","C:\\work\\data\\iqiyi\\typeResult\\out_24h.csv");
		sendController.readFileAndSent("C:\\work\\data\\iqiyi\\24h.csv");

		
	}

}
