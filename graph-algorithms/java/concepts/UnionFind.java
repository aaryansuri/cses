package concepts;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UnionFind {

    private static int[] parent;
    private static int[] rank;

    public static void main(String[] args) {

        int[] elements = new int[] {1, 2, 3, 4, 5};

        int n = elements.length;

        parent = new int[n + 1];
        rank = new int[n + 1];

        for(int i = 1; i <= n; i++) parent[i] = i;

//        union(1, 2);
//        display();
//
//        union(1, 3);
//        display();
//
//        union(3, 4);
//        display();

        unionByRank(4, 3);
        display();

        unionByRank(2, 1);
        display();

        unionByRank(1, 3);
        display();


    }



    private static void display() {
        System.out.println(Arrays.stream(parent).map(UnionFind::compressedFind).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
    }

    private static int compressedFind(int ele) {
        if(parent[ele] != ele) {
            parent[ele] = compressedFind(parent[ele]);
        }
        return parent[ele];
    }

    private static void unionByRank(int ele1, int ele2) {
        int parentEle1 = compressedFind(ele1);
        int parentEle2 = compressedFind(ele2);

        if(parentEle1 == parentEle2) return;

        if(rank[parentEle1] < rank[parentEle2]) {
            parent[parentEle1] = parentEle2;
        } else if(rank[parentEle2] < rank[parentEle1]) {
            parent[parentEle2] = parentEle1;
        } else {
            parent[parentEle1] = parentEle2;
            rank[parentEle2] += 1;
        }

    }
    private static int find(int ele) {
        if(parent[ele] == ele) return ele;
        return find(parent[ele]);
    }

    private static void union(int ele1, int ele2) {
        int parentEle1 = find(ele1);
        int parentEle2 = find(ele2);
        parent[parentEle2] = parentEle1;
    }
}
