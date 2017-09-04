import java.util.*;

public class TaxiService
{
    int INF=1000000007;
    LinkedList <Vertex> vertexSet=new LinkedList<>();
    LinkedList <Taxi> taxiSet=new LinkedList<>();

    public Vertex minVertex()
    {
        int i,n=vertexSet.size();
        Vertex v;
        for(i=0;i<n;i++)
        {
            if (!vertexSet.get(i).visited)
                break;
        }

        if(i==n)
            return null;
        else
            v=vertexSet.get(i);

        for(i=0;i<n;i++)
        {
            if(!vertexSet.get(i).visited)
            {
                if(vertexSet.get(i).distFromSrc < v.distFromSrc)
                    v=vertexSet.get(i);
            }
        }

        return v;
    }

    public void Dijkstra(Vertex source)
    {
        if(source==null)
            return;

        int n=vertexSet.size();
        for(int i=0;i<n;i++)
        {
            vertexSet.get(i).parent=null;
            vertexSet.get(i).distFromSrc=INF;
            vertexSet.get(i).visited=false;
        }
        source.distFromSrc=0;

        while(minVertex()!=null)
        {
            Vertex v=minVertex();
            v.visited=true;
            for(int j=0;j<v.neighbours.size();j++)
            {
                Vertex v2=v.neighbours.get(j);
                if(!v2.visited)
                {
                    if(v.distFromSrc + v.weights.get(j) < v2.distFromSrc)
                    {
                        v2.parent=v;
                        v2.distFromSrc = v.distFromSrc + v.weights.get(j);
                    }
                }
            }
        }
    }

    public void printPathTo(Vertex destination)
    {
        if(destination.distFromSrc < INF)
        {
            Vertex v=destination;
            LinkedList <String> temp=new LinkedList<>();
            while(v!=null)
            {
                temp.add(v.name);
                v=v.parent;
            }
            LinkedList <String> temp2=new LinkedList<>();
            int n=temp.size();
            for(int i=n-1;i>=0;i--)
                temp2.add(temp.get(i));
            for(String str:temp2)
                System.out.print(str+", ");
        }
        else
            System.out.print("the destination can't be reached from the source.");
    }

	public void performAction(String actionMessage)
    {
        System.out.println("action to be performed: " + actionMessage);
        System.out.println();
        Scanner in=new Scanner(actionMessage);
        String str=in.next();

        if(str.equals("printgraph"))
        {
            int i,j;
            for(i=0;i<vertexSet.size();i++)
            {
                Vertex v=vertexSet.get(i);
                System.out.print(v.name+" ---> ");
                for(j=0;j<v.neighbours.size();j++)
                    System.out.print(v.neighbours.get(j).name+"_"+v.weights.get(j)+", ");
                System.out.println();
            }
        }

        if(str.equals("edge"))
        {
            String src=in.next();
            String dst=in.next();
            int t=in.nextInt();
            int i,j;
            int n=vertexSet.size();
            Vertex v1=new Vertex(src),v2=new Vertex(dst);

            for(i=0;i<n;i++)
            {
                if(vertexSet.get(i).name.equals(src))
                {
                    v1=vertexSet.get(i);
                    break;
                }
            }

            for(j=0;j<n;j++)
            {
                if(vertexSet.get(j).name.equals(dst))
                {
                    v2=vertexSet.get(j);
                    break;
                }
            }

            if(i==n)
                vertexSet.add(v1);
            if(j==n)
                vertexSet.add(v2);

            if((i==n)||(j==n))
            {
                v1.neighbours.add(v2);
                v1.weights.add(t);
                v2.neighbours.add(v1);
                v2.weights.add(t);
            }
            else
            {
                int idx1=v1.neighbours.indexOf(v2);
                int idx2=v2.neighbours.indexOf(v1);
                if(idx1<0)
                {
                    v1.neighbours.add(v2);
                    v1.weights.add(t);
                    v2.neighbours.add(v1);
                    v2.weights.add(t);
                }
                else
                {
                    v1.weights.remove(idx1);
                    v1.weights.add(idx1,t);
                    v2.weights.remove(idx2);
                    v2.weights.add(idx2,t);
                }
            }
        }

        if(str.equals("taxi"))
        {
            String taxiName=in.next();
            String taxiPosition=in.next();
            int i;
            int n=vertexSet.size();
            for(i=0;i<n;i++)
            {
                if(vertexSet.get(i).name.equals(taxiPosition))
                    break;
            }

            if(i==n)
                System.out.println("ERROR - No vertex named "+taxiPosition+" exists.");
            else
            {
                Vertex v=vertexSet.get(i);
                taxiSet.add(new Taxi(taxiName,v));
            }
        }

        if(str.equals("customer"))
        {
            String src=in.next();
            String dst=in.next();
            int t=in.nextInt();
            int i;
            int n=vertexSet.size();
            Vertex source=new Vertex();
            Vertex destination=new Vertex();

            for(i=0;i<n;i++)
            {
                if(vertexSet.get(i).name.equals(src))
                {
                    source=vertexSet.get(i);
                    break;
                }
            }

            for(i=0;i<n;i++)
            {
                if(vertexSet.get(i).name.equals(dst))
                {
                    destination=vertexSet.get(i);
                    break;
                }
            }
            Dijkstra(source);

            n=taxiSet.size();
            Taxi chosenTaxi=null;
            int minTime=INF;
            for(i=0;i<n;i++)
            {
                Taxi car=taxiSet.get(i);
                if(t >= car.endTime)
                {
                    if(chosenTaxi==null)
                        chosenTaxi=car;
                    if(car.taxiPosition.distFromSrc < chosenTaxi.taxiPosition.distFromSrc)
                        chosenTaxi=car;
                }
            }
            for(Taxi car:taxiSet)
            {
                if(t >= car.endTime)
                    minTime=Math.min(minTime,car.taxiPosition.distFromSrc);
            }
            if(chosenTaxi==null)
                System.out.println("Sorry, no taxi is available.");
            else
            {
                System.out.println("Available taxis:");
                for(Taxi car:taxiSet)
                {
                    if(t >= car.endTime)
                    {
                        System.out.print("Path of "+car.taxiName+" : ");
                        Dijkstra(car.taxiPosition);
                        printPathTo(source);
                        System.out.println("and time taken is "+source.distFromSrc+" units");
                    }
                }
                System.out.println("*** Chose "+chosenTaxi.taxiName+" to service the customer request ***");
                System.out.print("Path of customer : ");
                Dijkstra(source);
                printPathTo(destination);
                System.out.println("and time taken is "+destination.distFromSrc);
                chosenTaxi.startTime=t;
                chosenTaxi.endTime=t+minTime+destination.distFromSrc;
                chosenTaxi.taxiPosition=destination;
                System.out.println();
            }
        }

        if(str.equals("printTaxiPosition"))
        {
            int t=in.nextInt();
            for(Taxi car:taxiSet)
            {
                //System.out.print(car.endTime);
                if(t >= car.endTime)
                    System.out.println(car.taxiName+" : "+car.taxiPosition.name);
                else
                    System.out.println(car.taxiName+" : the taxi is currently busy.");
            }
            System.out.println();
        }
    }
}
