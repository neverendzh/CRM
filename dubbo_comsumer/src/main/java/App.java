import com.neverend.service.CommodityService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author zh
 * Created by Administrator on 2017/12/8.
 */
public class App {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dubbo-consumer.xml");
        CommodityService commodityService = (CommodityService) context.getBean("rpcCommodityService");
        String hello = commodityService.sayHelloDubbo("dubbo");
        System.out.println(hello);
        System.in.read();
    }
}