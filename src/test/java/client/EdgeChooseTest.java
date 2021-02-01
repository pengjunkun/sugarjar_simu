package client;

import Client.EdgeChooser;
import org.junit.jupiter.api.Test;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/30.
 */
public class EdgeChooseTest
{
	EdgeChooser edgeChooser=new EdgeChooser(30F,110F);
	/**
	 * test if it is assigned a right edge initially.
	 */
	@Test void testAssignDefaultEdge(){
		edgeChooser.getEdge(1);
	}

}
