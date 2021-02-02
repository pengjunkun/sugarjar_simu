package remote;

import Remote.OriginalServer;
import org.junit.jupiter.api.Test;
import tools.MyConf;
import tools.MyLog;
import tools.MyTool;

import static org.junit.jupiter.api.Assertions.*;

public class OriginalServerTest {
    OriginalServer server = OriginalServer.getInstance();

    @Test
    void testOri() {

        MyConf.edgesInfo= new String[][]{{"0", "39.1", "116.1", "1000"}, {"1", "40.1", "116.1", "1005"}};
        int e1=server.getDefaultEdge(29F, 116F);
        int e2=server.getDefaultEdge(59F, 116F);
        assertEquals(0,e1);
        assertEquals(1,e2);

    }


    @Test
    void testDistance() {
        //Distance Beijing, CHN -> Tianjing, Tianjin, CHN
        //Distance: 67.26 mi (108.25 km)
        double diff = Math.abs(108.25 - MyTool.distance(39.9075, 116.39723, 39.14222, 117.17667, "K"));
        assertTrue(diff < 0.1);
    }
}
