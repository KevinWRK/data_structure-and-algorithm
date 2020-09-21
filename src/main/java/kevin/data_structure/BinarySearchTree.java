package kevin.data_structure;

/**
 * @author kevin
 * @date 2020-08-17 21:54
 */
public class BinarySearchTree<K,V> {
//    private BinarySearchTree.Node rootNode;
//    private int size;
//    class Node{
//        private BinarySearchTree.Node parentNode;
//        private BinarySearchTree.Node leftNode;
//        private BinarySearchTree.Node rightNode;
//        private K key;
//        private V val;
//        private int hashKey;
//        private byte color; //0为黑色，1为红色
//
//    }
//    public void put(K key,V val){
//        //参数校验
//        if (null == key)
//            throw new NullPointerException("key值不能为空");
//
//        BinarySearchTree.Node node = new BinarySearchTree.Node();
//        node.val = val;
//        node.color = 1;
//        node.key = key;
//        node.hashKey = key.hashCode();
//        insertNode(rootNode,node);
//    }
//    public V get(K key){
//        BinarySearchTree.Node node = getNode(key);
//        if (null == node) {
//            return null;
//        }
//        return (V) node.val;
//    }
//
//    public void remove(K key){
//        deleteNode(getNode(key));
//    }
//
//    /** 删除节点 */
//    private void deleteNode(BinarySearchTree.Node node){
//        BinarySearchTree.Node parentNode = node.parentNode;
//        //三种情况，无子节点，单子节点，双子节点
//        if (null == node.leftNode && null == node.rightNode){
//            if (null != parentNode){
//                //不确定删除的节点为父节点的右子节点还是左子节点，所以进行判断
//                if (null != parentNode.leftNode)
//                    parentNode.leftNode = parentNode.leftNode.key.equals(node.key)? null : parentNode.leftNode;
//                if (null != parentNode.rightNode)
//                    parentNode.rightNode = parentNode.rightNode.key.equals(node.key)? null : parentNode.rightNode;
//                return;
//            }
//            //删除的节点为根节点时
//            rootNode = null;
//
//
//        }else if (null != node.leftNode && null != node.rightNode){
//            BinarySearchTree.Node successorNode = successorNode(node);
//            //后继节点不为子节点
//            if (node.rightNode != successorNode) {
//                //后继节点右子节点不为空
//                if (null != successorNode.rightNode) {
//                    //后继节点父节点的左子节点指向后继节点的右子节点
//                    successorNode.parentNode.leftNode = successorNode.rightNode;
//                    //后继节点右子节点的父节点指向后继节点的父节点
//                    successorNode.rightNode.parentNode = successorNode.parentNode;
//                }
//                //后继节点的右节点指向被删除节点的右节点
//                successorNode.rightNode = node.rightNode;
//                //被删除节点的右节点的父节点指向后继节点
//                node.rightNode.parentNode = successorNode;
//            }
//
//
//            if (null != parentNode) {
//                //父节点指向后继节点
//                if (null != parentNode.leftNode)
//                    parentNode.leftNode = node.key.equals(parentNode.leftNode.key) ? successorNode : parentNode.leftNode;
//                if (null != parentNode.rightNode)
//                    parentNode.rightNode = node.key.equals(parentNode.rightNode.key)? successorNode : parentNode.leftNode;
//
//                //后继节点指向父节点
//                successorNode.parentNode = parentNode;
//            }else{
//                rootNode = successorNode;
//            }
//
//
//
//            //左子节点的指向后继节点
//            node.leftNode.parentNode = successorNode;
//            //后继节点左子节点指向
//            successorNode.leftNode = node.leftNode;
//
//        }else if (null != node.leftNode){
//            if (null != parentNode) {
//                if (null != parentNode.leftNode)
//                    parentNode.leftNode = node.key.equals(parentNode.leftNode.key)? node.leftNode : parentNode.leftNode;
//                if (null != parentNode.rightNode)
//                    parentNode.rightNode = node.key.equals(parentNode.rightNode.key)? node.leftNode : parentNode.rightNode;
//
//                node.leftNode.parentNode = parentNode;
//                return;
//            }
//            //删除的节点为根节点时
//            rootNode = node.leftNode;
//
//        }else {
//            if (null != parentNode) {
//                if (null != parentNode.rightNode)
//                    parentNode.rightNode = node.key.equals(parentNode.rightNode.key)? node.rightNode : parentNode.rightNode;
//                if (null != parentNode.leftNode)
//                    parentNode.leftNode = parentNode.leftNode.key.equals(node.key)? node.rightNode : parentNode.leftNode;
//
//                node.rightNode.parentNode = parentNode;
//                return;
//            }
//            //删除的节点为根节点时
//            rootNode = node.rightNode;
//
//        }
//    }
//
//    /** 找到后继节点 */
//    private BinarySearchTree.Node successorNode(BinarySearchTree.Node node){
//        if (null == node.rightNode.leftNode)
//            return node.rightNode;
//        return leftEndpoint(node.rightNode.leftNode);
//    }
//
//    /** 找到左子节点的叶子节点 */
//    private BinarySearchTree.Node leftEndpoint(BinarySearchTree.Node node){
//        if (null == node.leftNode){
//            return node;
//        }
//        return leftEndpoint(node.leftNode);
//    }
//    /** 遍历 */
//    public void traverse(){
//        traverse(rootNode);
//    }
//    //使用深度优先法遍历红黑树
//    private void traverse(BinarySearchTree.Node node){
//        if (null == node)
//            return;
//        System.out.println(node.val);
//        if (null != node.leftNode)
//            traverse(node.leftNode);
//        if (null != node.rightNode);
//        traverse(node.rightNode);
//    }
//
//    /** 获取节点 */
//    private BinarySearchTree.Node getNode(K key){
//        return getNode(key,this.rootNode);
//    }
//    private BinarySearchTree.Node getNode(K key, BinarySearchTree.Node node){
//        if (null == node){
//            return null;
//        }
//        if (node.key == key){
//            return node;
//        }
//        if (node.hashKey > key.hashCode()){
//            return getNode(key,node.leftNode);
//        }else{
//            return getNode(key,node.rightNode);
//        }
//    }
//
//    /** 插入节点 */
//    private void insertNode(BinarySearchTree.Node basicNode, BinarySearchTree.Node newNode){
//        insertNode(basicNode,newNode,1);
//    }
//    private void insertNode(BinarySearchTree.Node basicNode, BinarySearchTree.Node newNode, int floor){
//        //根节点为空，则把新节点当作根节点插入
//        if (null == basicNode && floor == 1){
//            this.rootNode = newNode;
//            this.rootNode.color = 0;//将根节点的颜色设置为黑色，符合红黑树第二原则
//            return;
//        }
//        //哈希值相同且key相同则覆盖原值，哈比父节点小的都放再左边，大于等于的节点都放在右边
//        if (basicNode.hashKey == newNode.hashKey && basicNode.key == newNode.key){ //TODO，当前只判断哈希值是否相同，没有排除散列冲突的情况
//            basicNode.val = newNode.val;
//        }else if (basicNode.hashKey > newNode.hashKey){
//            if (null == basicNode.leftNode){
//                basicNode.leftNode = newNode;
//                newNode.parentNode = basicNode;
//                return;
//            }
//            insertNode(basicNode.leftNode,newNode,++floor);
//        }else {
//            if (null == basicNode.rightNode){
//                basicNode.rightNode = newNode;
//                newNode.parentNode = basicNode;
//                return;
//            }
//            insertNode(basicNode.rightNode,newNode,++floor);
//        }
//    }
}
