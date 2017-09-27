package com.joycgj;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class LongPollingServlet extends HttpServlet {
    private Random random = new Random();
    private AtomicLong sequenceId = new AtomicLong();
    private AtomicLong count = new AtomicLong();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("第" + count.incrementAndGet() + "次 long polling");
        int seconds = random.nextInt(100); //随机获取等待时间，来通过sleep模拟服务端是否准备好数据
        System.out.println("wait " + seconds + " second");

        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PrintWriter out = resp.getWriter();
        long value = sequenceId.getAndIncrement();
        out.write(Long.toString(value));
    }
}
