package com.cxg.blueboothpinter2;

import com.cxg.blueboothpinter2.pojo.Ztwm004;
import com.cxg.blueboothpinter2.utils.WebServiceUtils;

import org.junit.Test;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void init() throws ParseException {
        Ztwm004 properties = new Ztwm004();
        properties.setILgmng("30.56");
        properties.setMatnr("20300001");
        properties.setMeins("BOX");
        properties.setMenge("2");
        properties.setWerks("2000");
        properties.setZbc("1");
        properties.setZgrdate("2017-06-27");
        properties.setZlinecode("00");
        properties.setIZlocco("000000");
        properties.setZproddate("2017-06-27");
        properties.setItZipcode(null);
        List<Object> list = WebServiceUtils.callWebServiceFor002(WebServiceUtils.URL_007, WebServiceUtils.METHOD_NAME_007, properties);
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Ztwm004 ztwm004 = (Ztwm004)list.get(i);
                System.out.println(ztwm004.getZipcode());
            }
        }
    }

    @Test
    public void init1() {
        HashMap<String, String> properties = new HashMap<>();
        //properties.put("IZkurno","0000");
        properties.put("IMatnr", "20300001");
        List<Object> list = WebServiceUtils.callWebServiceFor001(WebServiceUtils.URL_001, WebServiceUtils.METHOD_NAME_001, properties);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
    }

    @Test
    public void init2() {
        Map<String, String> map = WebServiceUtils.callWebServiceFor004(WebServiceUtils.URL_004, WebServiceUtils.METHOD_NAME_004);
        System.out.println(map);
    }
}