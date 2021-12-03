package myproject;

import java.util.ArrayList;

public class GeneticAlgorithm
{
    int[][] distanceArr;
    int populationSize;
    double mutChance;

    public GeneticAlgorithm(int[][] distanceArr, int populationSize, double mutChance) {
        this.distanceArr = distanceArr;
        this.populationSize = populationSize;
        this.mutChance = mutChance;

    }

    public Individ start(int iterationsAmount)
    {
        Individ bestInd;
        ArrayList<Individ> population = generateStartPopulation();

        for (int i = 0; i < iterationsAmount; i++)
        {
            population = selection(population);
            population = cross(population);
            mutation(population);
            improve(population);
        }
        bestInd = findBest(population);

        return bestInd;
    }

    private ArrayList<Individ> generateStartPopulation()
    {
        ArrayList<Individ> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++)
        {
            Individ individ = new Individ(generateRandomPath());
            individ.calc(distanceArr);
            population.add(individ);
        }
        return population;
    }

    private ArrayList<Integer> generateRandomPath()
    {
        ArrayList<Integer> path = new ArrayList<>();
        ArrayList<Integer> tempArr = new ArrayList<>();

        for (int i = 0; i < distanceArr.length; i++)
            tempArr.add(i);
        for (int i = distanceArr.length; i > 0; i--)
        {
            int remIndex = (int)(Math.random()*i);
            path.add(tempArr.remove(remIndex));
        }
        return path;
    }

    //Турнирная селекция из троих
    private ArrayList<Individ> selection(ArrayList<Individ> population)
    {
        ArrayList<Individ> newPopulation = new ArrayList<>();
        for (int i = 0; i < population.size(); i++)
        {
            int ind1 = (int)(Math.random()*population.size());
            int ind2 = (int)(Math.random()*population.size());
            int ind3 = (int)(Math.random()*population.size());
            while(ind1 == ind2 || ind2==ind3 || ind1==ind3) //выбор 3 разных индексов
            {
                ind2 = (int)(Math.random()*population.size());
                ind3 = (int)(Math.random()*population.size());
            }

            Individ bestIndivid = population.get(ind1);

            if(bestIndivid.distance > population.get(ind2).distance)
                bestIndivid = population.get(ind2);

            if(bestIndivid.distance > population.get(ind3).distance)
                bestIndivid = population.get(ind3);

            int dist = bestIndivid.distance;
            bestIndivid = new Individ(new ArrayList<>(bestIndivid.path));
            bestIndivid.distance = dist;
            newPopulation.add(bestIndivid);
        }
        return newPopulation;
    }

    //упорядоченный кроссовер 50 на 50;
    private ArrayList<Individ> cross(ArrayList<Individ> population)
    {
        ArrayList<Individ> newPopulation = new ArrayList<>();

        for (int i = 0; i < populationSize; i++)
        {
            ArrayList childPath = new ArrayList();

            Individ par1;
            Individ par2;
            if(i == populationSize-1)
            {
                par1 = population.get(i);
                par2 = population.get(0);
            }
            else
            {
                par1 = population.get(i);
                par2 = population.get(i+1);
            }

            int skipped = 0;
            for(int j = 0; j < distanceArr.length; j++)
            {
                if(j< distanceArr.length/2)
                    childPath.add(par1.path.get(j));
                else
                {
                    boolean isAdded = false;
                    while(!isAdded)
                    {
                        if(childPath.contains(par2.path.get(skipped)))
                            skipped++;
                        else
                        {
                            childPath.add(par2.path.get(skipped));
                            skipped++;
                            isAdded = true;
                        }
                    }
                }
            }
            Individ child = new Individ(childPath);
            child.calc(distanceArr);
            newPopulation.add(child);
        }
        return newPopulation;
    }

    //замена местами 2-ух случайных городов
    private void mutation(ArrayList<Individ> population)
    {
        for (Individ individ : population)
        {
            if(Math.random() < mutChance)
            {
                int ind1 = (int)(Math.random()* distanceArr.length);
                int ind2 = (int)(Math.random()* distanceArr.length);
                while(ind1 == ind2)
                    ind2 = (int)(Math.random()* distanceArr.length);

                int tmp1 = individ.path.get(ind1);
                int tmp2 = individ.path.get(ind2);

                individ.path.set(ind1, tmp2);
                individ.path.set(ind2, tmp1);
                individ.calc(distanceArr);
            }
        }
    }

    private void improve(ArrayList<Individ> population)
    {
        for (Individ individ : population)
        {
            int ind1 = (int)(Math.random()* distanceArr.length);
            int ind2 = (int)(Math.random()* distanceArr.length);
            while(ind1 == ind2)
                ind2 = (int)(Math.random()* distanceArr.length);

            int tmp1 = individ.path.get(ind1);
            int tmp2 = individ.path.get(ind2);
            int tmpDist = individ.distance;

            individ.path.set(ind1, tmp2);
            individ.path.set(ind2, tmp1);
            individ.calc(distanceArr);

            if(individ.distance > tmpDist)
            {
                individ.path.set(ind1, tmp1);
                individ.path.set(ind2, tmp2);
                individ.distance = tmpDist;
            }
        }
    }

    private Individ findBest(ArrayList<Individ> population)
    {
        Individ bestIndivid = population.get(0);
        for (Individ individ: population)
        {
            if(individ.distance < bestIndivid.distance)
                bestIndivid = individ;
        }
        return bestIndivid;
    }

}
