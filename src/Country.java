/**
 * Created by David on 25/01/2017.
 */
public class Country {
    public Country(String cName, String cCode, String cCap, Integer cPop){
        name=cName;
        code=cCode;
        cap=cCap;
        pop=cPop;
    }
    String name;
    String code;
    String cap;
    Integer pop;
    void setName(String in){name=in;}
    void setCode(String in){code=in;}
    void setPop(int in){pop=in;}
    void setCap(String in){cap=in;}
    String getName(){return name;}
    String getCap(){return cap;}
    String getCode(){return code;}
    Integer getPop(){return pop;}
}