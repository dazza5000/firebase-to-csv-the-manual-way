package ly.generalassemb.jsonparsetest;

/**
 * Created by darrankelinske on 8/9/16.
 */
public class Student {

    public String getSchoolKey() {
        return schoolKey;
    }

    public String getName() {
        return name;
    }

    private String schoolKey;
    private String name;

    public Student(String name, String schoolKey) {
        this.name = name;
        this.schoolKey = schoolKey;
    }
}
