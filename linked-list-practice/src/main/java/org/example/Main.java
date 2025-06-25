package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(2);
        System.out.println(list.isEmpty());
        list.add(3);
        list.add(4);

        list.addFirst(1);
        list.addFirst(0);

        list.addLast(5);
        list.addLast(6);

        list.removeFirst();

        list.removeLast();
        System.out.println(list);
        System.out.println(list.contains(0));
        System.out.println(list.contains(1));
        System.out.println(list.contains(3));
        System.out.println(list.contains(5));
        System.out.println(list.contains(16));

        System.out.println(list.print());
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(3));
        System.out.println(list.get(4));

        System.out.println(list.print());

        System.out.println(list.removeFirstMatch(0));
        System.out.println(list.print());

        System.out.println(list.removeFirstMatch(3));
        System.out.println(list.print());

        list.removeFirstMatch(5);
        System.out.println(list.print());
        System.out.println(list.getFirst());
        System.out.println(list.getLast());

        list.insertAt(0,0);
        list.insertAt(3,3);

        list.insertAt(5,6);

        System.out.println(list.getFirst());
        System.out.println(list.getLast());

        System.out.println(list.reverse().print());

        list.clear();
        System.out.println(list);

    }
}