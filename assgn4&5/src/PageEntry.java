/**
 * Created by hp 15 on 9/26/2016.
 */
import java.util.*;
import java.io.*;

public class PageEntry
{
    String pageName;
    PageIndex pageIndex=new PageIndex();
    String contents;

    PageIndex getPageIndex() {return pageIndex;}

    PageEntry(String name)
    {
        pageName=name;
        char punctuations[]=new char[] {39,'{','}','[',']','<','>','(',')','=','.',',',';','"','?','#','!','-',':'};
        Arrays.sort(punctuations);
        String words[]=new String[] {"a","an","the","they","these","this","for","is","are","was","of","or","and","does","will","whose"};
        Arrays.sort(words);
        String file="";
        try
        {
            BufferedReader br=new BufferedReader(new FileReader("webpages/"+pageName));
            int val;
            while((val=br.read())!=-1)
            {
                char c=(char)val;
                if(Arrays.binarySearch(punctuations,c)<0)
                    file=file+c;
                else
                    file=file+" ";
            }
            file=file.toLowerCase();
            contents=file;
            Scanner in=new Scanner(file);
            int pos=0;
            while(in.hasNext())
            {
                String str=in.next();
                pos++;
                str=str.toLowerCase();
                if(str.equals("stacks"))
                    str="stack";
                if(str.equals("applications"))
                    str="application";
                if(str.equals("structures"))
                    str="structure";
                if(Arrays.binarySearch(words,str)>=0)
                    continue;
                Position p=new Position(this,pos);
                pageIndex.addPositionForWord(str,p);
            }
        }
        catch (Exception err)
        {
            System.out.println("the specified webpage doesn't exist.");
        }
    }

    float getRelevanceOfPage(String str[],boolean doTheseWordsRepresentAPhrase)
    {
        String smallWords[]=new String[] {"a","an","the","they","these","this","for","is","are","was","of","or","and","does","will","whose"};
        Arrays.sort(smallWords);
        int i;
        for(i=0;i<str.length;i++)
        {
            str[i]=str[i].toLowerCase();
            if(str[i].equals("stacks"))
                str[i]="stack";
            if(str[i].equals("applications"))
                str[i]="application";
            if(str[i].equals("structures"))
                str[i]="structure";
        }

        float relevance=0;
        if(!doTheseWordsRepresentAPhrase)
        {
            for(i=0;i<str.length;i++)
            {
                Scanner in=new Scanner(contents);
                int pos=0;
                float currRelevance=0;
                while(in.hasNext())
                {
                    String word=in.next();
                    pos++;
                    if(word.equals("stacks"))
                        word="stack";
                    if(word.equals("applications"))
                        word="application";
                    if(word.equals("structures"))
                        word="structure";
                    if(word.equals(str[i]))
                        currRelevance=currRelevance+(float)1/(pos*pos);
                }
                relevance=relevance+currRelevance;
            }
        }

        else
        {
            String temp[]=contents.split("\\s+");
            ArrayList <String> list=new ArrayList<>();
            for(i=0;i<temp.length;i++)
            {
                if(Arrays.binarySearch(smallWords,temp[i])<0)
                    list.add(temp[i]);
            }
            String words[]=list.toArray(new String[0]);
            int pos=0;
            int j;
            for(i=0;i<words.length;i++)
            {
                pos++;
                if(words[i].equals("stacks"))
                    words[i]="stack";
                if(words[i].equals("applications"))
                    words[i]="application";
                if(words[i].equals("structures"))
                    words[i]="structure";
                if(words[i].equals(str[0]))
                {
                    for(j=0;j<str.length;j++)
                    {
                        if(!words[i+j].equals(str[j]))
                            break;
                    }
                    if(j==str.length)
                        relevance=relevance+(float)1/(pos*pos);
                }
            }
        }

        return relevance;
    }
}
