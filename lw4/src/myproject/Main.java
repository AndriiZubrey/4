package myproject;

import java.util.Random;

public class Main {

    public static void main(String[] args)
    {
        int CITIES_AMOUNT = 300;
        int MIN_DISTANCE = 5;
        int MAX_DISTANCE = 150;
        int POPULATION_SIZE = 300;
        double MUTATION_CHANCE = 0.1;
        int ITERATIONS_AMOUNT = 1500;


        int[][] arr = generateDistanceArray(CITIES_AMOUNT,MIN_DISTANCE,MAX_DISTANCE);
        GeneticAlgorithm ga = new GeneticAlgorithm(arr, POPULATION_SIZE, MUTATION_CHANCE);
        System.out.println(ga.start(ITERATIONS_AMOUNT));
    }

    public static int[][] generateDistanceArray(int citiesAmount, int minDistance, int maxDistance)
    {
        Random rnd = new Random(1);
        int[][] distanceArray = new int[citiesAmount][citiesAmount];
        for(int i=0; i< citiesAmount; i++)
        {
            for (int j = i; j < citiesAmount; j++)
            {
                if(i != j)
                {
                    int dist = rnd.nextInt(maxDistance-minDistance+1) + minDistance;
                    distanceArray[i][j] = dist;
                    distanceArray[j][i] = dist;
                }
                else {
                    distanceArray[i][j] = 0;
                }
            }
        }
        return distanceArray;
    }
}
