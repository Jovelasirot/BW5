package team5.BW5.translate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
    public static void main(String[] args) throws IOException {
        String path="src/main/java/team5/BW5/translate/comuni-italiani.csv";
        String line="";
        BufferedReader br=new BufferedReader(new FileReader(path));
        System.out.println("hi i'm the csv reader");
        while((line=br.readLine())!=null){
//            System.out.println(line);
            String [] values= line.split(";");
            System.out.println(values[2]);

        }
    }
}
