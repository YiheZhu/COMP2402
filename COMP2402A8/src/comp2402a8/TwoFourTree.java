package comp2402a8;

import java.util.List;
import java.util.Stack;

/**
 * The TwoFourTree class is an implementation of the 2-4 tree from notes/textbook.
 * The tree will store Strings as values. 
 * It extends the (modified) sorted set interface (for strings).
 * It implements the LevelOrderTraversal interface. 
 */
public class TwoFourTree extends StringSSet implements LevelOrderTraversal{

		/* your class MUST have a zero argument constructor. All testing will 
		   use this ocnstructor to create a 2-3 tree.
    */
		public Node root = new Node();
		public int size = 0;
        public List<Node> levels = new Stack<>();

		public TwoFourTree(){
		}

    public String levelOrder(){
        String data = new String();
        Node curr = root, prev = NIL;
        prev.parent = NIL;
        if (size == 0)
            return null;
        if (root == NIL)
            return null;

        levels.add(curr);

        while (!levels.isEmpty()) {
            curr = levels.remove(0);
            if (curr == NIL) return data;
            if(prev != NIL) {
                if (curr.data[0].compareTo(prev.data[0]) < 0)
                    data += "|";
                else if (curr.parent != NIL) data += ":";
            }

            for (int i = 0; i < 3; ++i) {
                if (curr.data[i] != EMPTY) {
                    data += curr.data[i];
                    data += ",";
                }
            }
            prev = curr;

            data = data.substring(0,data.length()-1);
            for (Node node: prev.children) {
                if (node != NIL)
                    levels.add(node);
            }
        }
        return data;
    }

    @Override
    public int size() {
        return size;
    }

    public int getLevel(Node x){
		Node currNode = x;
		int level = 1;
		while (currNode != NIL) {
            if (currNode.parent != NIL) {
                level++;
            }
            currNode = currNode.parent;
        }
        return level;
    }

    public int getSize(Node x){
        int count = 0;
        for(int i = 0; i < x.data.length; i++){
            if(x.data[i] != null){
                count++;
            }
        }
        return count;
    }

    public void addless3(Node x, String y){
		if(getSize(x) == 0){
		    x.data[0] = y;
        }else if(getSize(x) == 1){
            if(y.compareTo(x.data[0]) < 0) {
                x.data[1] = x.data[0];
                x.data[0] = y;
                return;
            }
            if(y.compareTo(x.data[0]) > 0){
                x.data[1] = y;
            }
        }else if(getSize(x) == 2){
            if(y.compareTo(x.data[0]) < 0) {
                x.data[2] = x.data[1];
                x.data[1] = x.data[0];
                x.data[0] = y;
                return;
            }
            if(y.compareTo(x.data[0]) > 0 && y.compareTo(x.data[1]) < 0) {
                x.data[2] = x.data[1];
                x.data[1] = y;
                return;
            }
            if(y.compareTo(x.data[0]) > 0 && y.compareTo(x.data[1]) > 0) {
                x.data[2] = y;
            }
        }
    }

    public void pushleft(Node x){
		if(getSize(x) == 2){
		    if(x.data[0] != null && x.data[1] != null){
		        return;
            }
            if(x.data[2] != null && x.data[1] != null){
                x.data[0] = x.data[1];
                x.data[1] = x.data[2];
                x.data[2] = null;
                return;
            }
            if(x.data[2] != null && x.data[0] != null){
                x.data[1] = x.data[2];
                x.data[2] = null;
            }
        }else if(getSize(x) == 1){
		    if(x.data[0] != null){
		        return;
            }
            if(x.data[1] != null){
		        x.data[0] = x.data[1];
		        x.data[1] = null;
		        return;
            }
            if(x.data[2] != null){
		        x.data[0] = x.data[2];
		        x.data[2] = null;
            }
        }
    }

    public int NumPosition(Node x, String y){
		int count = 0;
        for(int i = 0; i < x.data.length; i++){
            if(x.data[i] == null){
                break;
            }
            if(y.compareTo(x.data[i]) > 0){
                count++;
            }
        }
        return count;
    }

    public int getDataPos(Node x, String y){
		int count = 0;
        for(int i = 0; i < x.data.length; i++){
            if(x.data[i] == null){
                break;
            }
            if(y.compareTo(x.data[i]) >= 0){
                count++;
            }
        }
        return count - 1;
    }

    public String getMoveUpData(Node x, String y){
        if(NumPosition(x,y) < 2){
            return x.data[1];
        }else if(NumPosition(x,y) == 2){
            return y;
        }else {
            return x.data[2];
        }
    }

    public String getMoveRightData(Node x, String y){
        if(NumPosition(x,y) < 3){
            return x.data[2];
        }else {
            return y;
        }
    }

    public int countEmptyNodes(Node x){
        int count = 0;
        for(int i = 0; i < x.children.length; i++){
            if(x.children[i] == NIL){
                count++;
            }
        }
        return count;
    }

    public int countNonEmptyNodes(Node x){
        int count = 0;
        for(int i = 0; i < x.children.length; i++){
            if(x.children[i] != NIL){
                count++;
            }
        }
        return count;
    }

    public void split(Node x, String y){
        String tempdataup = y, tempdataright, prevup;
        Node currNode = x;
        Node prevNode = new Node();
        Node tempNode = new Node();
        Node prevtempNode = new Node();
        tempdataup = getMoveUpData(x, y);
        tempdataright = getMoveRightData(x, y);
        prevup = null;

		while (true) {
            if(countEmptyNodes(currNode) == 4) {
                tempNode.data[0] = tempdataright;

                if(NumPosition(currNode,y) < 2){
                    currNode.data[1] = null;
                    currNode.data[2] = null;
                    addless3(currNode,y);
                }else {
                    currNode.data[2] = null;
                }
            }else {
                tempNode.data[0] = tempdataright;
                if(NumPosition(currNode,prevup) == 0){
                    tempNode.children[1] = currNode.children[3];
                    currNode.children[3].parent = tempNode;
                    tempNode.children[0] = currNode.children[2];
                    currNode.children[2].parent = tempNode;
                    currNode.children[3] = NIL;
                    currNode.children[2] = currNode.children[1];
                    currNode.children[1] = prevtempNode;
                    prevtempNode.parent = currNode;
                    currNode.data[1] = null;
                    currNode.data[2] = null;
                    addless3(currNode,prevup);
                }else if(NumPosition(currNode,prevup) == 1){
                    tempNode.children[1] = currNode.children[3];
                    currNode.children[3].parent = tempNode;
                    tempNode.children[0] = currNode.children[2];
                    currNode.children[2].parent = tempNode;
                    currNode.children[3] = NIL;
                    currNode.children[2] = prevtempNode;
                    prevtempNode.parent = currNode;
                    currNode.data[1] = null;
                    currNode.data[2] = null;
                    addless3(currNode,prevup);
                }else if(NumPosition(currNode,prevup) == 2){
                    tempNode.children[1] = currNode.children[3];
                    currNode.children[3].parent = tempNode;
                    tempNode.children[0] = prevtempNode;
                    prevtempNode.parent = tempNode;
                    currNode.children[3] = NIL;
                    currNode.data[2] = null;
                }else if(NumPosition(currNode,prevup) == 3){
                    tempNode.children[1] = prevtempNode;
                    prevtempNode.parent = tempNode;
                    tempNode.children[0] = prevNode;
                    prevNode.parent = tempNode;
                    currNode.children[3] = NIL;
                    currNode.data[2] = null;
                }
            }
            if(currNode.parent == NIL){
                Node r = new Node();
                currNode.parent = r;
                r.data[0] = tempdataup;
                r.children[0] = currNode;
                r.children[1] = tempNode;
                tempNode.parent = r;
                root = r;
                break;
            }
            if(getSize(currNode.parent) < 3){
                if (NumPosition(currNode.parent,tempdataup) == 0) {
                    currNode.parent.children[3] = currNode.parent.children[2];
                    currNode.parent.children[2] = currNode.parent.children[1];
                    currNode.parent.children[1] = tempNode;
                    tempNode.parent = currNode.parent;
                    addless3(currNode.parent, tempdataup);
                    break;
                }else if(NumPosition(currNode.parent,tempdataup) == 1){
                    currNode.parent.children[3] = currNode.parent.children[2];
                    currNode.parent.children[2] = tempNode;
                    tempNode.parent = currNode.parent;
                    addless3(currNode.parent, tempdataup);
                    break;
                }else if(NumPosition(currNode.parent,tempdataup) == 2){
                    currNode.parent.children[3] = tempNode;
                    tempNode.parent = currNode.parent;
                    addless3(currNode.parent, tempdataup);
                    break;
                }
            }
            prevNode = currNode;
            currNode = currNode.parent;
            prevup = tempdataup;
            tempdataright = getMoveRightData(currNode, tempdataup);
            tempdataup = getMoveUpData(currNode, tempdataup);
            prevtempNode = tempNode;
            tempNode = new Node();
        }
    }

    @Override
    public String find(String x) {
        if(root.data[0] == null){
            return null;
        }
        Node currNode = root;
        int position;
        while (true) {
            position = NumPosition(currNode,x);

            if(position < 3) {
                if(currNode.data[position] != null) {
                    if (currNode.data[position].compareTo(x) == 0) {
                        return currNode.data[position];
                    }
                }
            }

            if(currNode.children[position] != NIL){
                currNode = currNode.children[position];
            }else {
                break;
            }
        }

        while (currNode != NIL){
            if(NumPosition(currNode,x) != 3){
                if(currNode.data[NumPosition(currNode,x)] != null) {
                    return currNode.data[NumPosition(currNode,x)];
                }
            }
            if(currNode.parent != NIL){
                currNode = currNode.parent;
            }else {
                return null;
            }
        }
        return null;
    }

    @Override
    public boolean add(String x) {
		if(size == 0){
		    root.data[0] = x;
		    size++;
		    return true;
        }

        if(find(x) != null) {
            if (x.compareTo(find(x)) == 0) {
                return false;
            }
        }

        Node currNode = root;

        while (true) {
            int position = NumPosition(currNode,x);
            if(currNode.children[position] != NIL){
                currNode = currNode.children[position];
            }else {
                if (getSize(currNode) < 3) {
                    addless3(currNode, x);
                    size++;
                    return true;
                } else {
                    split(currNode, x);
                    size++;
                    return true;
                }
            }
        }
    }

    public int GetNodePos(Node x) {
        for(int i = 0; i <= x.parent.children.length; i++) {
            if(x.parent.children[i] == x) {
                return i;
            }
        }
        return -1;
    }

    public void underflow(Node uFNode) {
        // Special case if the root has underflowed
        if(uFNode == root) {
            root = uFNode.children[0];
            root.parent = NIL;
        }
        // Check that uFNode isn't the first child, then check if the left
        // sibling of uFNode has two or more items (permitting a transfer)
        else if(GetNodePos(uFNode) > 0 && getSize(uFNode.parent.children[GetNodePos(uFNode) - 1]) > 1) {
            leftTransfer(uFNode);
        }
        // Check that uFNode isn't the last child, then check if the right
        // sibling of uFNode has two or more items (permitting a transfer)
        else if(GetNodePos(uFNode) < countNonEmptyNodes(uFNode.parent) - 1 && getSize(uFNode.parent.children[GetNodePos(uFNode) + 1]) > 1){
            rightTransfer(uFNode);
        }
        // Check that uFNode isn't the first child, permitting a left fusion
        else if(GetNodePos(uFNode) != 0) {
            leftFusion(uFNode);
        }
        // If uFNode is the first child, perform a right fusion instead
        else  {
            rightFusion(uFNode);
        }
    }

    public void leftTransfer(Node emptyNode) {
        Node parentNode = emptyNode.parent;
        Node siblingNode = parentNode.children[GetNodePos(emptyNode) - 1];
        // Copy down the correct parent item into the newly empty node
        emptyNode.data[0] = parentNode.data[GetNodePos(emptyNode)-1];
        // Move the child at the 0 index into the 1 index
        emptyNode.children[1] = emptyNode.children[0];
        // Set the 0 child of emptyNode to its sibling's largest child
        emptyNode.children[0] = siblingNode.children[getSize(siblingNode)];
        // Set the displaced child to look at its new parent
        siblingNode.children[getSize(siblingNode)].parent = emptyNode;
        // Null the sibling's largest child
        siblingNode.children[getSize(siblingNode)] = NIL;
        // Copy the item from the sibling child up to replace the parent
        parentNode.data[GetNodePos(emptyNode)-1] = siblingNode.data[getSize(siblingNode) - 1];
        // Delete the sibling's largest item
        siblingNode.data[getSize(siblingNode) - 1] = null;
    }

    public void rightTransfer(Node emptyNode) {
        Node parentNode = emptyNode.parent;
        Node siblingNode = parentNode.children[GetNodePos(emptyNode) + 1];
        // Copy down the correct parent item into the newly empty node
        emptyNode.data[0] = parentNode.data[GetNodePos(emptyNode)];
        // Copy the item from the sibling child up to replace the parent
        parentNode.data[GetNodePos(emptyNode)] = siblingNode.data[0];
        // Set the 1 child of emptyNode to its sibling's smallst child
        emptyNode.children[1] = siblingNode.children[0];
        // Set the displaced child to look at its new parent
        siblingNode.children[0] = emptyNode;
        // Shifting delete the sibling's smallest item
        siblingNode.data[0] = null;
        pushleft(siblingNode);
        if(getSize(siblingNode) == 2) {
            siblingNode.children[0] = siblingNode.children[1];
            siblingNode.children[1] = siblingNode.children[2];
            siblingNode.children[2] = siblingNode.children[3];
            siblingNode.children[3] = NIL;
        }else if(getSize(siblingNode) == 1){
            siblingNode.children[0] = siblingNode.children[1];
            siblingNode.children[1] = siblingNode.children[2];
            siblingNode.children[2] = NIL;
        }
    }

    public void leftFusion(Node emptyNode) {
        int emptyIndex = GetNodePos(emptyNode);
        Node parentNode = emptyNode.parent;
        Node siblingNode = parentNode.children[emptyIndex - 1];
        siblingNode.parent = parentNode;
        // Copy down the parent at Node postion - 1 into the left sibling
        siblingNode.data[getSize(siblingNode)] = parentNode.data[emptyIndex - 1];
        // Set the empty node child to belong to the left sibling
        siblingNode.children[2] = emptyNode.children[0];
        // Set the parent of the child to the left sibling
        emptyNode.children[0].parent = siblingNode;
        // Delete the parent item that's now in the left sibling
        parentNode.data[emptyIndex - 1] = null;

        if(emptyIndex == 1){
            pushleft(parentNode);
            parentNode.children[1] = parentNode.children[2];
            parentNode.children[2] = parentNode.children[3];
            parentNode.children[3] = NIL;
        }else if(emptyIndex == 2){
            pushleft(parentNode);
            parentNode.children[2] = parentNode.children[3];
            parentNode.children[3] = NIL;
        }else if(emptyIndex == 3){
            pushleft(parentNode);
            parentNode.children[3] = NIL;
        }
        // Check if we've made the parent underflow and recursively call if so
        if(getSize(parentNode) == 0) {
            underflow(parentNode);
        }
    }

    public void rightFusion(Node emptyNode) {
        Node parentNode = emptyNode.parent;
        Node siblingNode = parentNode.children[GetNodePos(emptyNode) + 1];
        siblingNode.parent = parentNode;
        // Copy down the parent at 0 into the right sibling
        siblingNode.data[1] = siblingNode.data[0];
        siblingNode.data[0] = parentNode.data[0];
        siblingNode.children[2] = siblingNode.children[1];
        siblingNode.children[1] = siblingNode.children[0];
        // Set the empty node child to belong to the right sibling
        siblingNode.children[0] = emptyNode.children[0];
        // Set the parent of the child to the right sibling
        emptyNode.children[0].parent = siblingNode;
        // Delete the parent item that's now in the right sibling
        parentNode.data[0] = null;

        if(getSize(parentNode) == 1){
            pushleft(parentNode);
            parentNode.children[0] = parentNode.children[1];
            parentNode.children[1] = parentNode.children[2];
            parentNode.children[2] = NIL;
        }else if(getSize(parentNode) == 2){
            pushleft(parentNode);
            parentNode.children[0] = parentNode.children[1];
            parentNode.children[1] = parentNode.children[2];
            parentNode.children[2] = parentNode.children[3];
            parentNode.children[3] = NIL;
        }
        // Check if we've made the parent underflow and recursively call if so
        else if(getSize(parentNode) == 0) {
            parentNode.children[0] = parentNode.children[1];
            parentNode.children[1] = NIL;
            underflow(parentNode);
        }
    }

    public Node searchElement(String element){
        Node currNode = root;
        int position;
        while (true) {
            position = NumPosition(currNode,element);

            if(position < 3) {
                if(currNode.data[position] != null) {
                    if (currNode.data[position].compareTo(element) == 0) {
                        return currNode;
                    }
                }
            }
            currNode = currNode.children[position];
        }
    }

    public Node findPredecessorNode(Node x, String remove){
		Node currNode = x;
        if(currNode.children[getDataPos(currNode,remove)] != NIL){
            currNode = currNode.children[getDataPos(currNode,remove)];
        }else {
            return currNode;
        }
		while (true){
		    if(currNode.children[getSize(currNode)] != NIL){
		        currNode = currNode.children[getSize(currNode)];
            }else {
		        break;
            }
        }
        return currNode;
    }

    @Override
    public boolean remove(String x) {
        // If tree is empty throw exception
        if(size == 0) {
            return false;
        }

        if(find(x) == null){
            return false;
        }

        if(find(x).compareTo(x) != 0){
            return false;
        }

        // Find node to delete from
        Node deleteNode = searchElement(x);
        // If leaf, delete and fix sentinels
        if(countEmptyNodes(deleteNode) == 4){
            deleteNode.data[getDataPos(deleteNode,x)] = null;
            pushleft(deleteNode);
        }
        // If internal, move inorder successor to parent
        else {
            Node predecessorNode = findPredecessorNode(deleteNode,x);
            String predecessor = predecessorNode.data[getSize(predecessorNode) - 1];
            // Move the predecessor up to replace the deleted item
            deleteNode.data[getDataPos(deleteNode,x)] = predecessor;
            // Delete the predecessor
            predecessorNode.data[getSize(predecessorNode) - 1] = null;
            // Set deleteNode equal to the node predecessor to check for underflow
            deleteNode = predecessorNode;
        }
        // Check for underflow
        if(getSize(deleteNode) == 0) {
            underflow(deleteNode);
        }
        // Decrement size
        size--;
        return true;
    }

    @Override
    public void clear() {
		root = NIL;
    }

    public static void main(String args[]) {

        TwoFourTree tree = new TwoFourTree();
        String data;
        /*for (int i = 0; i < 10; i++){
            data = "0" + i;
            tree.add(data);
        }*/
        tree.add("05");
        for (int i = 10; i < 70; i+=5){
            data = "" + i;
            tree.add(data);
        }
        tree.add("43");
        tree.add("44");
        //System.out.println(tree.find("217"));
        System.out.println(tree.size());
        System.out.println(tree.levelOrder());
        tree.remove("40");
        System.out.println(tree.size());
        System.out.println(tree.levelOrder());
        tree.remove("25");
        System.out.println(tree.size());
        System.out.println(tree.levelOrder());
        tree.remove("10");
        System.out.println(tree.size());
        System.out.println(tree.levelOrder());
        tree.remove("43");
        System.out.println(tree.size());
        System.out.println(tree.levelOrder());
        tree.remove("35");
        System.out.println(tree.size());
        System.out.println(tree.levelOrder());
        tree.remove("30");
        System.out.println(tree.size());
        System.out.println(tree.levelOrder());
        tree.remove("05");
        System.out.println(tree.size());
        System.out.println(tree.levelOrder());
        tree.remove("15");
        System.out.println(tree.size());
        System.out.println(tree.levelOrder());
    }
}
