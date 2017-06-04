import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by David on 25/01/2017.
 */
public class main {
    public static void main(String[] args) throws Exception{
        HashMap<Integer, Country> countryList = new HashMap<Integer, Country>();
        BufferedReader br = reader();
        String line;
        String cName,cCode,cCap,cPopstr;
        Integer cPop = null;
        int index1, index2, index3;
        int i = 0;
        while((line = br.readLine()) != null){
            i++;
            index1 = line.indexOf(",");
            index2 = line.indexOf(",",index1+1);
            index3 = line.indexOf(",",index2+1);
            cName = line.substring(0,index1);
            cCode = line.substring(index1+1,index2);
            cPopstr = line.substring(index2+1,index3);
            cCap = line.substring(index3+1);
            if(!cPopstr.equals("#N/A")){cPop=Integer.parseInt(cPopstr);}
            else{cPop = null;}
            Country temp = new Country(cName, cCode, cCap, cPop);
            countryList.put(i,temp);
        }
        int ran = ThreadLocalRandom.current().nextInt(1, i + 1);
        Country selected = countryList.get(ran);

        TestingLayout Window = new TestingLayout(countryList);

    }
    public static BufferedReader reader () throws Exception{
        String fis = "C:\\Users\\David\\IdeaProjects\\Geo\\src\\TempData.csv";
        InputStream fileName = new FileInputStream(fis);
        InputStreamReader inputStream = new InputStreamReader(fileName, Charset.forName("UTF-8"));
        BufferedReader br =new BufferedReader(inputStream);
        return br;
    }
}


