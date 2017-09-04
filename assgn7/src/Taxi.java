public class Taxi
{
    String taxiName;
    Vertex taxiPosition;
    int startTime=0,endTime=0;

    Taxi(String str, Vertex v)
    {
        taxiName=str;
        taxiPosition=v;
    }
}
