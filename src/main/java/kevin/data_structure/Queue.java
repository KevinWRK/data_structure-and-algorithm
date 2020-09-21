package kevin.data_structure;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * <p>说明:
 * <p>创建时间: 2020/7/20 9:21
 * <p>创  建  人: 华尚科技 —— 锐凯
 **/
public class Queue {
    public static void main(String[] args) {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
        queue.offer("aaa");
    }
}
