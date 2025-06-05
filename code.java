package org.example.sem;
import java.util.*; //импорт ArrayList, PriorityQueue, Arrays и Comparator

public class Sem_4 {

    //класс ребра (номер вершины, к которой идем и длина коридора до нее
    static class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        int n = 4; // количество комнат
        List<List<Edge>> graph = new ArrayList<>(); //инициализируем список, в котором каждый элемент список объектов типа edge
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>()); //пробегаемся по каждому элементу списка graph и инициализиуем элемент как список
        }

        //добавляем рёбра в граф
        graph.get(0).add(new Edge(1, 4)); //из вершины 0 есть путь в вершину 1 с длиной коридора 4
        graph.get(0).add(new Edge(2, 1));
        graph.get(1).add(new Edge(3, 1));
        graph.get(2).add(new Edge(1, 2));
        graph.get(2).add(new Edge(3, 5));

        int start = 0; //начальная комната
        int end = 3; //комната с сокровищем

        int shortest_way = dijkstra(graph, start, end);

        if (shortest_way == Integer.MAX_VALUE) {
            System.out.println("Сокровище недостижимо");
        } else {
            System.out.println("Длина кратчайшего пути: " + shortest_way);
        }
    }

    public static int dijkstra(List<List<Edge>> graph, int start, int end) {
        int n = graph.size(); //кол-во вершин в графе
        int[] dist = new int[n]; //массив кратчайших расстояний от начальной вершины до всех остальных
        Arrays.fill(dist, Integer.MAX_VALUE); //записывает все расстояния как бесконечность
        dist[start] = 0;

        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1])); //создаем приоритетную очередь содержающую в себе элементы типа int
        //Comparator - интерфейс для сравнения, comparingInt - лямбда-выражение, которое укказывает, что приоритет должен устанавливаться вторым элементом массива (длиной коридора)
        queue.add(new int[]{start, 0}); //добавляем в очередь начальную вершину

        while (!queue.isEmpty()) { //пока очередь не пустая
            int[] current = queue.poll(); //извлекаем элемент с высшим приоритетом из очереди
            int num = current[0]; //номер текущей вершины
            int len = current[1]; //длина коридора от начальной до текущей


            for (Edge edge : graph.get(num)) { //пробегаемся по всем смежным вершинам
                int v = edge.to; //смежная вершина
                int weight = edge.weight; //путь до нее

                if (dist[num] + weight < dist[v]) { //для каждой смежной вершины v текущей вершины num проверяется, можно ли уменьшить расстояние до v
                    dist[v] = dist[num] + weight;
                    queue.add(new int[]{v, dist[v]});
                }
            }
        }

        return dist[end];
    }
}
