package ly.generalassemb.jsonparsetest;

/**
 * Created by darrankelinske on 8/9/16.
 */
public class Village {

    public String getClusterKey() {
        return clusterKey;
    }

    public String getName() {
        return name;
    }

    private String clusterKey;
    private String name;

    public Village(String name, String clusterKey) {
        this.name = name;
        this.clusterKey = clusterKey;
    }
}
