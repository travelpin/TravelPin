//package db.mysql;
//
//
//public class SLList {
//    public IntNode sentinel;
//
//    public SLList() {
//        this.sentinel = new IntNode();
//    }
//
//
//
//    public void reverse() {
//        IntNode curr = sentinel;
//        IntNode temp = curr.prev;
//
//        while (temp != sentinel) {
//            curr.prev = curr.next;
//            curr.next = temp;
//            curr = curr.next;
//            temp = curr.prev;
//        }
//    }
//
//    public class IntNode {
//        public int item;
//        public IntNode next;
//        public IntNode prev;
//
//        public IntNode() {
//            this.item = 0;
//            this.next = null;
//            this.prev = null;
//        }
//    }
//
//
//    public static void main(String[] args) {
//        SLList lst = new SLList();
//        //System.out.println(lst.sentinel.item);
//        lst.sentinel.next = new IntNode();
//        lst.reverse();
//    }
//
//
//
//}


