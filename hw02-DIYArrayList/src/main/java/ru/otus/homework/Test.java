package ru.otus.homework;

import ru.otus.homework.DIYCollections.DIYArrayList;
import java.util.ArrayList;
import java.util.Collections;

public class Test {
    public static void main(String[] args) {

        ArrayList<Integer> test = new ArrayList<>();
        Collections.addAll(test, 1, 2, 3, 4, 44, 6, 7, 8, 666, 10, 11, 12, 13, 13, 13, 13, 17, 18, 19, 20, 21, 21, 55, 1, 2, 3, 4, 44, 6, 7, 8, 666, 10, 11, 12, 13, 13, 13, 13, 17, 18, 19, 20, 21, 21, 55, 1, 2, 3, 4, 44, 6, 7, 8, 666, 10, 11, 12, 13, 13, 13, 13, 17, 18, 19, 20, 21, 21, 55);

        DIYArrayList<Integer> diyArrayList = new DIYArrayList<>();
        Collections.addAll(diyArrayList, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 21, 22, 1, 2, 3, 4, 44, 6, 7, 8, 666, 10, 11, 12, 13, 13, 13, 13, 17, 18, 19, 20, 21, 21, 55, 1, 2, 3, 4, 44, 6, 7, 8, 666, 10, 11, 12, 13, 13, 13, 13, 17, 18, 19, 20, 21, 21, 55);
        Collections.copy(diyArrayList, test);
        Collections.sort(diyArrayList);

        System.out.println();
        for (int i = 0; i <= diyArrayList.size() - 1; i++) {
            System.out.println(diyArrayList.get(i));
        }


    }
}
