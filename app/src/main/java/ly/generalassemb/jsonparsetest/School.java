package ly.generalassemb.jsonparsetest;

/**
 * Created by darrankelinske on 8/9/16.
 */
public class School {

    private String villageKey;
    private String name;

    public School(String name, String villageKey) {
        this.name = name;
        this.villageKey = villageKey;
    }

    public String getVillageKey() {
        return villageKey;
    }

    public String getName() {
        return name;
    }
}
