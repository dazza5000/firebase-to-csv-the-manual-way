package ly.generalassemb.jsonparsetest;

/**
 * Created by darrankelinske on 8/9/16.
 */
public class Cluster {
    private String blockKey;
    private String name;

    public Cluster(String name, String blockKey) {
        this.name = name;
        this.blockKey = blockKey;
    }

    public String getName() {
        return name;
    }

    public String getBlockKey() {
        return blockKey;
    }


}
