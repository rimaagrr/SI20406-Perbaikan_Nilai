package stmik_amik.bandung.si20406_proyek2.model;

import java.io.Serializable;

public class DataM implements Serializable {

    private String npm;
    private String name;

    private String key;

    public DataM(){

    }

    public DataM(String npm, String name){
        this.npm = npm;
        this.name = name;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return " " + npm + '\n' +
                " " + name ;
    }
}
