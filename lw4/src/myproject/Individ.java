package myproject;

import java.util.ArrayList;

public class Individ
{
    ArrayList<Integer> path;
    int distance;

    public Individ(ArrayList<Integer> path) {
        this.path = path;
        this.distance = 0;
    }

    public void calc(int[][] arr)
    {
        for (int i = 0; i < path.size()-1; i++)
        {
            int city1 = path.get(i);
            int city2 = path.get(i+1);
            distance += arr[city1][city2];
        }
    }

    @Override
    public String toString() {
        return "Individ{" +
                "path=" + path +"\n" +
                ", distance=" + distance +
                '}';
    }
}
