package dsa.graphTest;

import java.io.BufferedInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/*
* 旅行商(TSP)
Description
Shrek is a postman working in the mountain, whose routine work is sending mail to n villages. Unfortunately,road between
villages is out of repair for long time, such that some road is one-way road. There are even some villages that can’t
be reached from any other village. In such a case, we only hope as many villages can receive mails as possible.

Shrek hopes to choose a village A as starting point (He will be air-dropped to this location), then pass by as many
villages as possible. Finally, Shrek will arrived at village B. In the travelling process, each villages is only passed
 by once. You should help Shrek to design the travel route.

Input
There are 2 integers, n and m, in first line. Stand for number of village and number of road respectively.

In the following m line, m road is given by identity of villages on two terminals. From v1 to v2. The identity of
village is in range [1, n].

Output
Output maximum number of villages Shrek can pass by.

Example
Input

4 3
1 4
2 4
4 3
Output

3
Restrictions
1 <= n <= 1,000,000

0 <= m <= 1,000,000

These is no loop road in the input.

Time: 2 sec

Memory: 256 MB

https://dsa.cs.tsinghua.edu.cn/oj/problem.shtml?id=1147
* */
public class TSP {

    private static Vertex[] vertices;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in, 1 << 20));
        int sizeOfVertices = scanner.nextInt(), edges = scanner.nextInt();

        vertices =  new Vertex[sizeOfVertices + 1];
        for(int i = 0; i < edges;i++) {
            vertexOf(scanner.nextInt()).addOut(vertexOf(scanner.nextInt()));
        }
        scanner.close();

        System.out.println(doSearch());
    }

    private static int doSearch() {
        int maxVillages = 1;
        LinkedList<Vertex> nodes = new LinkedList<>();

        for(int i = 1, size = vertices.length; i < size; i++) {
            if(vertices[i] != null && vertices[i].inDegree == 0) {
                nodes.push(vertices[i]);
            }
        }

        while(!nodes.isEmpty()) {
            Vertex from = nodes.pop();

            for(Vertex out : from.outs) {
                if(out.villages < from.villages + 1) {
                    out.villages = from.villages + 1;

                    if(maxVillages < out.villages) {
                        maxVillages = out.villages;
                    }
                }

                if(--out.inDegree == 0) {
                    nodes.push(out);
                }
            }
        }

        return maxVillages;
    }

    private static Vertex vertexOf(int i) {
        if(vertices[i] == null) {
            vertices[i] = new Vertex(i);
        }

        return vertices[i];
    }

    private static class Vertex {
        int id;
        int villages = 1;
        int inDegree = 0;//题目给出的图已经确保了无环， 所以inDegree表明了有多少条路径在此汇聚，这是一个突破点，
        // 可以把算法从递归遍历转化成迭代遍历。
        //如果按照基本的DPS算法， 此节点会被多次递归式的重复遍历。这样会造成很大的计算浪费。如果对此用疑惑
        //可以试着动手画一个有向无环图， 并且多条路径在某一点汇聚。
        List<Vertex> outs = new LinkedList<>();

        public Vertex(int index) {
            this.id = index;
        }

        public void addOut(Vertex v) {
            outs.add(v);
            v.inDegree++;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Vertex{");
            sb.append("id=").append(id);
            sb.append(", villages=").append(villages);
            sb.append(", inDegree=").append(inDegree);
            sb.append('}');
            return sb.toString();
        }
    }
}


