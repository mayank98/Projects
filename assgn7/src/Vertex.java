import java.util.*;

public class Vertex
{
    String name;
    LinkedList <Vertex> neighbours=new LinkedList<>();
    LinkedList <Integer> weights=new LinkedList<>();

    Vertex (String str) {name=str;}
    Vertex() {}

    //for dijkstra's algorithm
    Vertex parent;
    Boolean visited;
    int distFromSrc;
}
