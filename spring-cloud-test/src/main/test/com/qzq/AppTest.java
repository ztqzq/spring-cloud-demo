package com.qzq;

import com.qzq.beans.User;
import org.junit.Test;

public class AppTest {

    @Test
    public void test01() {
        User user = new User();
        user.setAge(1);
        user.setId(1);
        user.setName("zhangsan");
        System.out.println(user);
    }

    @Test
    public void test02() {
        User user1 = new User();
        User user2 = new User();
        System.out.println(user1.hashCode() + " " + user2.hashCode());
        System.out.println(user1.equals(user2));
    }
}
