import com.alibaba.fastjson.JSON;
import com.kaishengit.TulingUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zh
 * Created by Administrator on 2017/11/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-tuling.xml")
public class TulingAPiTest {

    @Test
    public void duihua(){
        TulingUtil tulingUtil = new TulingUtil();
        String json = tulingUtil.chatBot();
        System.out.println(json);
        Map<String,Object> map = JSON.parseObject(json, HashMap.class);

        System.out.println(map.get("text").toString());
    }
}
