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
        properties.setMatnr("20300001");
        properties.setMeins("BOX");
        properties.setMenge("30.56");
        properties.setWerks("2000");
        properties.setZbc("1");
        properties.setZgrdate("2017-06-27");
        properties.setZkurno("000000");
        properties.setZlinecode("00");
        properties.setZproddate("2017-06-27");
        List<Object> list = WebServiceUtils.callWebServiceFor002(WebServiceUtils.URL_002, WebServiceUtils.METHOD_NAME_002, properties);
        if(list.size()!=0) {
            System.out.println(list.get(0));
            System.out.println(list.get(1));
            System.out.println(list.get(2));
        }
    }

    @Test
    public void init1(){
        HashMap<String,String> properties = new HashMap<>();
        //properties.put("IZkurno","0000");
        properties.put("IMatnr","20300001");
        List<Object> list = WebServiceUtils.callWebServiceFor001(WebServiceUtils.URL_001, WebServiceUtils.METHOD_NAME_001, properties);
        System.out.println(list.get(0));
    }

    @Test
    public void init2(){
        Map<String,String> map = WebServiceUtils.callWebServiceFor004(WebServiceUtils.URL_004, WebServiceUtils.METHOD_NAME_004);
        System.out.println(map);
    }
}