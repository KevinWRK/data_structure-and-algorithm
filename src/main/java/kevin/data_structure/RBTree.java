package kevin.data_structure;

/**
 * <p>说明:
 * <p>创建时间: 2020/7/24 14:05
 * <p>创  建  人: 华尚科技 —— 锐凯
 **/
public class RBTree<K,V> {
    private Node rootNode;
    private int size = 0;
    static final private byte RED = 1;
    static final private byte BLACK = 0;

class Node{
    private Node parentNode;
    private Node leftNode;
    private Node rightNode;
    private K key;
    private V val;
    private int hashKey;
    private byte color; //0为黑色，1为红色

    @Override
    public boolean equals(Object o) {
        //传入对象为空返回false，对象的类型不同返回false
        if (o == null || getClass() != o.getClass()) return false;
        //判断hashKey是否相同，来确定是否为同一节点
        Node node = (Node) o;
        return hashKey == node.hashKey;
    }
}
    /** 新增或修改节点 */
    public void put(K key,V val) throws Exception {
        //参数校验
        if (null == key)
            throw new NullPointerException("key值不能为空");

        Node node = new Node();
        node.val = val;
        node.color = 1;
        node.key = key;
        node.hashKey = key.hashCode();
        insertNode(rootNode,node);
    }
    /** Get节点值 */
    public V get(K key){
        Node node = getNode(key);
        if (null == node) {
            return null;
        }
        return node.val;
    }

    /** 红黑树大小 */
    public int size(){
        return this.size;
    }
    /** 删除节点 */
    public void remove(K key){
        deleteNode(getNode(key));
    }
    private void deleteNode(Node node){
        Node parentNode = node.parentNode;
        //三种情况，无子节点，单子节点，双子节点

        //无子节点
        if (null == node.leftNode && null == node.rightNode){
            if (1 == node.color){  // 删除节点颜色为红色，直接删除
                parentNode.leftNode = parentNode.leftNode == node ? null : parentNode.leftNode;
                parentNode.rightNode = parentNode.rightNode == node ? null : parentNode.rightNode;
            }else {  //删除节点为黑色 , 需要进行平衡操作
                deleteAndBalance(node,1);
            }
            //双子节点
        }else if (null != node.leftNode && null != node.rightNode){
            Node successorNode = successorNode(node); //后继节点
            node.key = successorNode.key;
            node.hashKey = successorNode.hashKey;
            node.val = successorNode.val;
            deleteNode(successorNode); //带入后继节点，情形转换为1，2处理

            //单子节点  删除节点只能为黑色，子节点为红色
        }else {
            if (parentNode.leftNode == node) { //删除节点为父节点的左节点
                parentNode.leftNode = node.leftNode == null ? node.rightNode : node.leftNode; //判断删除节点为父节点的左节点还是右节点
                parentNode.leftNode.parentNode = parentNode;    //修改子节点的父节点
                parentNode.leftNode.color = 0; //重绘子节点为黑色
            } else { //删除节点为父节点的右节点
                parentNode.rightNode = node.rightNode == null ? node.leftNode : node.rightNode;
                parentNode.rightNode.parentNode = parentNode;
                parentNode.rightNode.color = 0; //重绘子节点为黑色
            }
        }
    }
    /** 父结点删除子节点操作 */
    private void parentDeleteChild(Node node){
        Node parentNode = node.parentNode;
        parentNode.leftNode = parentNode.leftNode == node ? null : parentNode.leftNode;
        parentNode.rightNode = parentNode.rightNode == node ? null : parentNode.rightNode;
    }
    /** 删除节点并平衡（无子节点情况）
     * @param isDelete  0不删除，1删除
     * */
    private void deleteAndBalance(Node node,int isDelete){
        Node siblingNode = getBrotherNode(node);
        Node parentNode = node.parentNode;
        // 3.1)D=红(直接删除)
        if (node.color == RED){
            if (isDelete == 1) {
                parentDeleteChild(node);
                node.parentNode = null;
            }
        }
        // 3.2)D=黑：
        // 3.2.1)D为根节点(直接删除)
        if (rootNode == node){
            if (isDelete == 1)
            rootNode = null;
        }
        // 3.2.2)S=红
        if (siblingNode.color == RED){
            //3.2.2.1)D为左节点(P和S颜色对调，P左旋，进入情况3.2.4)
            if (parentNode.leftNode.equals(node)){
                swapColor(parentNode,siblingNode);
                leftRotate(parentNode);
                deleteAndBalance(node,isDelete);
                return;
            //3.2.2.2)D为右节点(P和S颜色对调，P右旋，进入情况3.2.4)
            }else if (parentNode.rightNode.equals(node)){
                swapColor(parentNode,siblingNode);
                leftRotate(parentNode);
                deleteAndBalance(node,isDelete);
                return;
            }
        //3.2.3)S=黑，S不为叶子节点
        }else if (siblingNode.color == BLACK && !isLeafNode(node)){
            Node srNode = getSRNode(node);
            Node slNode = getSLNode(node);
            //3.2.3.1.SR不为空，D为左节点(P和S颜色对调，P左旋，删除D)
            if (srNode != null && parentNode.leftNode.equals(node)) {
                swapColor(parentNode,siblingNode);
                leftRotate(parentNode);
                if (isDelete == 1){
                    parentDeleteChild(node);
                    node.parentNode = null;
                }

            }
            //3.2.3.2.SL不为空，D为右节点(P和S颜色对调，P右旋，删除D)
            if (slNode != null && parentNode.rightNode.equals(node)) {
                swapColor(parentNode,siblingNode);
                rightRotate(parentNode);
                if (isDelete == 1){
                    parentDeleteChild(node);
                    node.parentNode = null;
                }
            }
            //3.2.3.3.SR为空，D为左节点(S和SL颜色对调，S右旋，P与当前右子交换颜色，P左旋，删除D)
            if (srNode == null && parentNode.leftNode.equals(node)) {
                swapColor(siblingNode,siblingNode.leftNode);
                rightRotate(siblingNode);
                swapColor(parentNode,parentNode.rightNode);
                leftRotate(parentNode);
                if (isDelete == 1){
                    parentDeleteChild(node);
                    node.parentNode = null;
                }
            }
            //3.2.3.4.SL为空，D为右节点(S和SR颜色对调，S左旋，P与当前左子交换颜色，P右旋，删除D)
            if (slNode == null && parentNode.rightNode.equals(node)) {
                swapColor(siblingNode,siblingNode.rightNode);
                leftRotate(siblingNode);
                swapColor(parentNode,parentNode.leftNode);
                rightRotate(parentNode);
                if (isDelete == 1){
                    parentDeleteChild(node);
                    node.parentNode = null;
                }
            }

        //3.2.4)P=红，S=黑，S为叶子节点(P和S颜色对调，删除D)
        }else if (parentNode.color == RED && siblingNode.color == BLACK && isLeafNode(node)){
            swapColor(parentNode,siblingNode);
            if (isDelete == 1){
                parentDeleteChild(node);
                node.parentNode = null;
            }

        //3.2.5)P=黑，S=黑，S为叶子节点(S涂红，删除D，并将P作为D节点做平衡操作，将P的P，P的P的P……一直到根节点都做平衡操作)
        }else if (parentNode.color == BLACK && siblingNode.color == BLACK && isLeafNode(node)){
            siblingNode.color = RED;
            parentDeleteChild(parentNode);
            node.parentNode = null;
            deleteAndBalance(parentNode,0);
        }
        if (isDelete == 0)
            deleteAndBalance(node.parentNode,isDelete);

    }
    /** 两节点颜色对调 */
    private void swapColor(Node preNode,Node sufNode){
        byte color = preNode.color;
        preNode.color = sufNode.color;
        sufNode.color = color;
    }


    /** 判断是否为叶子节点 */
    private boolean isLeafNode(Node node){
        return node.leftNode == null && node.rightNode == null;
    }

    /** 获取祖父节点 */
    private Node getGrandParentNode(Node node){
        if (null == node.parentNode) return null;
        return node.parentNode.parentNode;
    }
    /** 获取叔节点 */
    private Node getUncleNode(Node node) {
        if (null == node.parentNode) return null;
        Node grandParentNode = getGrandParentNode(node);
        if (null == grandParentNode) return null;

        return node.parentNode == grandParentNode.leftNode ? grandParentNode.rightNode : grandParentNode.leftNode;
    }
    /** 获取兄弟节点 */
    private Node getBrotherNode(Node node){
        if (null == node.parentNode) return null;
        Node parentNode = node.parentNode;
        return node == parentNode.leftNode ? parentNode.rightNode : parentNode.leftNode;
    }
    /** 获取SR节点 */
    private Node getSRNode(Node node){
        final Node brotherNode = getBrotherNode(node);
        if (brotherNode == null)
            return null;
        return brotherNode.rightNode;
    }
    /** 获取SL节点 */
    private Node getSLNode(Node node){
        final Node brotherNode = getBrotherNode(node);
        if (brotherNode == null)
            return null;
        return brotherNode.leftNode;
    }
    /** 找到后继节点 */
    private Node successorNode(Node node){
        if (null == node.rightNode.leftNode)
            return node.rightNode;
        return leftEndpoint(node.rightNode.leftNode);
    }

    /** 找到左子节点的叶子节点 */
    private Node leftEndpoint(Node node){
        if (null == node.leftNode){
            return node;
        }
        return leftEndpoint(node.leftNode);
    }
    /** 遍历 */
    public void traverse(){
        traverse(rootNode);
    }
    //使用深度优先法遍历红黑树
    private void traverse(Node node){
        if (null == node)
            return;
        System.out.println(node.val);
        if (null != node.leftNode)
            traverse(node.leftNode);
        if (null != node.rightNode);
            traverse(node.rightNode);
    }

    /** 获取节点 */
    private Node getNode(K key){
        return getNode(key,this.rootNode);
    }
    private Node getNode(K key,Node node){
        if (null == node){
            return null;
        }
        if (node.key == key){
            return node;
        }
        if (node.hashKey > key.hashCode()){
            return getNode(key,node.leftNode);
        }else{
            return getNode(key,node.rightNode);
        }
    }

    /** 插入节点 */
    private void insertNode(Node basicNode,Node newNode) throws Exception {
        //根节点为空，则把新节点当作根节点插入
        if (null == basicNode){
            this.rootNode = newNode;
            this.rootNode.color = 0;//将根节点的颜色设置为黑色，符合红黑树第二原则
            spinAndDisColor(newNode);
            this.size++;
            return;
        }
        //哈希值相同且key相同则覆盖原值，哈希值比父节点小的都放再左边，大于等于的节点都放在右边
        if (basicNode.hashKey == newNode.hashKey){ //TODO，当前只判断哈希值是否相同，没有排除散列冲突的情况
         basicNode.val = newNode.val;
        }else if (basicNode.hashKey > newNode.hashKey){
            if (null == basicNode.leftNode){
                basicNode.leftNode = newNode;
                newNode.parentNode = basicNode;
                spinAndDisColor(newNode);
                this.size++;
                return;
            }
            insertNode(basicNode.leftNode,newNode);
        }else {
            if (null == basicNode.rightNode){
                basicNode.rightNode = newNode;
                newNode.parentNode = basicNode;
                spinAndDisColor(newNode);
                this.size++;
                return;
            }
            insertNode(basicNode.rightNode,newNode);
        }
    }
    /**红黑树插入操作后的旋转和变色 */
    private void spinAndDisColor(Node node) throws Exception {
        Node parentNode = node.parentNode;
        Node uncleNode = getUncleNode(node);
        Node grandParentNode = getGrandParentNode(node);

        //  1.插入节点为根节点
        if (null == parentNode){
            node.color = 0; //设置节点颜色为黑色
            return;
        }
        //  2.如果父节点为黑色，则不做处理
        if (0 == parentNode.color)
            return;
        //  3.父节点和叔节点都为红色的情况
        if (1 == parentNode.color && uncleNode != null && 1 == uncleNode.color){
            parentNode.color = 0;//重绘父节点为黑色
            uncleNode.color = 0;//重绘叔节点为黑色
            grandParentNode.color = 1;//重绘祖父节点为红色
            spinAndDisColor(grandParentNode);//将祖父节点当成新添加的节点递归判断
            return;
        }
        // 4和5的情形
        //父节点为祖父节点的左节点
        if (grandParentNode.leftNode == parentNode){
            if (null == uncleNode || uncleNode.color == 0){ //叔节点为空或黑色
                if (node == parentNode.rightNode){ //  4.插入节点为父节点右节点 （需要左旋）
                    leftRotate(parentNode);//父节点左旋
                    spinAndDisColor(node.leftNode);
                    return;
                }
                if (node == parentNode.leftNode){ //  5.插入节点为父节点的左节点 （需要右旋，并重绘父节点和祖父节点）
                    rightRotate(grandParentNode);//祖父节点右旋
                    node.parentNode.color = 0;
                    if (null != getBrotherNode(node)) getBrotherNode(node).color = 1;
                    return;
                }
            }

        }
        //父节点为祖父节点的右节点
        if (getGrandParentNode(node).rightNode == parentNode){
            if (null == uncleNode || uncleNode.color == 0){ //叔节点为空或黑色
                if (node == parentNode.leftNode){ //  4.插入节点为父节点左节点 （需要右旋）
                    rightRotate(parentNode);//父节点右旋
                    spinAndDisColor(node.rightNode);
                    return;
                }
                if (node == parentNode.rightNode){ //  5.插入节点为父节点的右节点 （需要左旋，并重绘父节点和祖父节点）
                    leftRotate(grandParentNode);//祖父节点左旋
                    node.parentNode.color = 0;
                    if (null != getBrotherNode(node)) getBrotherNode(node).color = 1;
                }
            }

        }
    }
    //左旋（自己变为右孩子的左孩子，右孩子的左孩子变为自己的右孩子）
    private void leftRotate(Node node){
        Node parentNode = node.parentNode;
        Node rightNode = node.rightNode;
        //父节点为空，则说明此节点为根节点
        if (null == parentNode){
            rootNode = rightNode;//修改根节点
            rightNode.parentNode = null;//设置右孩子父节点为空
        }else { //父节点不为空
            //判断节点为父节点的左节点还是右节点，并替换为右孩子
            if (parentNode.rightNode == node) parentNode.rightNode = rightNode;
            else parentNode.leftNode = rightNode;
            rightNode.parentNode = parentNode;//修改右孩子父亲指向
        }

        //判断右孩子的左孩子是否为空,不为空则变为自己的右孩子
        if (null != rightNode.leftNode){
            node.rightNode = rightNode.leftNode;
            rightNode.leftNode.parentNode = node;
        }else {
            node.rightNode = null;
        }
        node.parentNode = rightNode;//修改自己的父节点指向
        rightNode.leftNode = node;//右孩子的左孩子指向自己
    }
    //右旋（自己变为左孩子的右孩子，左孩子的右孩子变为自己的左孩子）
    private void rightRotate(Node node){
        Node parentNode = node.parentNode;
        Node leftNode = node.leftNode;

        //父节点为空，则说明此节点为根节点
        if (null == parentNode){
            rootNode = leftNode;//修改根节点
            leftNode.parentNode = null;//设置右孩子父节点为空
        }else { //父节点不为空
            //判断节点为父节点的左节点还是右节点，并替换左孩子
            if (parentNode.rightNode == node) parentNode.rightNode = leftNode;
            else parentNode.leftNode = leftNode;
            leftNode.parentNode = parentNode;//修改右孩子父亲指向
        }

        //判断左孩子的右孩子是否为空,不为空则变为自己的右孩子
        if (null != leftNode.rightNode){
            node.leftNode = leftNode.rightNode;
            leftNode.rightNode.parentNode = node;
        }else {
            node.leftNode = null;
        }
        node.parentNode = leftNode;//修改自己的父节点指向
        leftNode.rightNode = node;//右孩子的左孩子指向自己
    }

}










