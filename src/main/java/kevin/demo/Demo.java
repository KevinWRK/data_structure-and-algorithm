package kevin.demo;



import kevin.data_structure.RBTree;

import java.util.HashMap;
import java.util.List;

/**
 * <p>说明:
 * <p>创建时间: 2020/7/24 14:26
 * <p>创  建  人: 华尚科技 —— 锐凯
 **/
public class Demo {
    public static void main(String[] args) throws Exception {
        RBTree<String,Integer> rbTree = new RBTree();
//        rbTree.put("10",10);
//        rbTree.put("9",9);
//        rbTree.put("8",8);
//        rbTree.put("7",7);
//        rbTree.put("6",6);
//        rbTree.put("5",5);
//        rbTree.put("4",4);
//        rbTree.put("3",3);
//        rbTree.put("2",2);
//        rbTree.put("1",1);

//        rbTree.put("1",1);
//        rbTree.put("2",2);
//        rbTree.put("3",3);
//        rbTree.put("4",4);
//        rbTree.put("5",5);
//        rbTree.put("6",6);
//        rbTree.put("7",7);
//        rbTree.put("8",8);
//        rbTree.put("9",9);
//        rbTree.put("10",10);


        rbTree.put("7",1);
        rbTree.put("4",2);
        rbTree.put("5",3);
        rbTree.put("1",4);
        rbTree.put("6",5);
        rbTree.put("10",6);
        rbTree.put("2",7);
        rbTree.put("3",8);
        rbTree.put("8",9);
        rbTree.put("9",10);



        int size = rbTree.size();
        rbTree.traverse();

        System.out.println();

        //  ||符号从左往右执行，只要有一个条件符合就不再执行后面的条件
//        List<String> strings = null;
//        if (null == strings || strings.size() == 1){
//            System.out.println("aaa");
//        }
    }
}
