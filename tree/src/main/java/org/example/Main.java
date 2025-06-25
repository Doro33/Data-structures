package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        AVLTree<Integer> avl = new AVLTree<>();
        avl.insert(0);
        avl.insert(1);
        avl.insert(2);
        avl.insert(3);
        avl.insert(4);
        avl.insert(5);
        System.out.println(avl.toString());
        avl.prettyPrint();
        System.out.println("*****************************");
        avl.delete(1);
        avl.prettyPrint();
        System.out.println("*****************************");
        avl.delete(3);
        avl.prettyPrint();
        System.out.println("*****************************");
        avl.delete(2);
        avl.prettyPrint();
        System.out.println("*****************************");
        avl.delete(0);
        avl.prettyPrint();







    }
}