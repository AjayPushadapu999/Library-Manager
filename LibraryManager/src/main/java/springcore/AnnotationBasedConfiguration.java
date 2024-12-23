package springcore;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationBasedConfiguration {
    public static void main(String[] args) {
ApplicationContext context=new ClassPathXmlApplicationContext("Spring.xml");
Address add = (Address) context.getBean("address");

System.out.println("completed");
System.out.println(add.getCity());
}
}