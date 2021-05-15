///*
// * Copyright Chris2018998
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package cn.beecp.concurrent;
//
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.concurrent.ConcurrentLinkedQueue;
//import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
//
//import static java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater;
//
///**
// * Concurrent Queue Implementation mock class:{@link java.util.concurrent.ConcurrentLinkedQueue}
// *
// * @author Chris.Liao
// */
//public class BeeConcurrentQueue<E> extends ConcurrentLinkedQueue<E> {
//    private static final class ChainNode<E> {
//        volatile ChainNode<E> next;//update by nextUpd
//        private E v;
//        public ChainNode(E v) {
//            this.v = v;
//        }
//        public boolean contains(Object o) {
//            return (v == o) || v.equals(o);
//        }
//    }
//    private final static AtomicReferenceFieldUpdater<ChainNode, ChainNode> nextUpd =
//            newUpdater(ChainNode.class, ChainNode.class, "next");
//
//    private final ChainNode<E> head;
//    private volatile ChainNode<E> tail;
//    public BeeConcurrentQueue() {
//        this.head = new ChainNode<E>(null);
//        this.tail = head;
//    }
//
//    public final boolean isEmpty() {
//        return head == tail;
//    }
//
//    public E peek() {
//        ChainNode node = head.next;
//        return (node != null) ? (E) node.v : null;
//    }
//
//    public int size() {
//        int size = 0;
//        ChainNode<E> curNode = head.next;
//        while (curNode != null) {
//            size++;
//            curNode = curNode.next;
//        }
//        return size;
//    }
//
//    public boolean offer(E v) {
//        if (v == null) throw new NullPointerException();
//        ChainNode<E> newNode = new ChainNode<E>(v);
//        while (!nextUpd.compareAndSet(tail, null, newNode)) ;
//        this.tail = newNode;
//        return true;
//    }
//
//    public E poll() {
//        while (true) {
//            ChainNode fistNode = head.next;
//            if (fistNode == null) return null;
//            ChainNode secondNode = fistNode.next;
//            if (nextUpd.compareAndSet(head, fistNode, secondNode)) {
//                if (fistNode == tail) {
//                    tail = head;
//                    tail.next = null;
//                }
//                return (E) fistNode.v;
//            }
//        }
//    }
//
//    public boolean remove(Object o) {
//        ChainNode preNode = head;
//        ChainNode curNode = head.next;
//        while (true) {
//            if (curNode == null) return false;
//            ChainNode nextNode = curNode.next;
//            if (curNode.contains(o)) {//found
//                return (nextUpd.compareAndSet(preNode, curNode, nextNode));//removed
//            }
//
//            curNode=nextNode;
//            preNode=curNode;
//        }
//    }
//
//    public boolean contains(Object o) {
//        ChainNode node = head.next;
//        while (node != null) {
//            if (node.contains(o)) return true;
//            ChainNode next = node.next;
//        }
//        return false;
//    }
//
//    public Object[] toArray() {
//        LinkedList<E> al = new LinkedList<E>();
//        ChainNode node = head.next;
//        while (node != null) {
//            al.add((E) node.v);
//            ChainNode next = node.next;
//        }
//        return al.toArray();
//    }
//
//    public <T> T[] toArray(T[] a) {
//        LinkedList<E> al = new LinkedList<E>();
//        ChainNode node = head.next;
//        while (node != null) {
//            al.add((E) node.v);
//            ChainNode next = node.next;
//        }
//        return al.toArray(a);
//    }
//
//    public Iterator<E> iterator() {
//        return new Itr();
//    }
//
//    private class Itr implements Iterator<E> {
//        private ChainNode preNode = head;
//        private ChainNode curNode = null;
//        private ChainNode nextNode = null;
//
//        public boolean hasNext() {
//            return curNode != null && curNode.next != null;
//        }
//
//        public E next() {
//            if (curNode == null || curNode.next == null) throw new IllegalStateException();
//
//            preNode=curNode;
//            curNode=curNode.next;
//            return (E) curNode.v;
//        }
//
//        public void remove() {
//            while (true) {
//                      //@todo
////                if (preNode == null) {//remove by other
////                    curNode = head;
////                    return;
////                } else if (preNode.next == curNode) {
////                    Node nextNode = curNode.next;
////                    if (curNode.next == nextNode && nextUpd.compareAndSet(preNode, curNode, nextNode)) {//removed from chain
////                        curNode.pre = null;
////                        curNode.next = null;
////
////                        if (curNode == tail) {
////                            preNode.next = null;
////                            tail = preNode;
////                        } else {
////                            nextNode.pre = preNode;
////                        }
////                        return;
////                    }
////                }
//          }
//        }
//    }//Iterator
//}
