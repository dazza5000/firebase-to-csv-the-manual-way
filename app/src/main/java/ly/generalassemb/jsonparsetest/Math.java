package ly.generalassemb.jsonparsetest;

/**
 * Created by darrankelinske on 8/9/16.
 */
public class Math {

    private String studentKey;
    private String level;
    private String evaluationType;
    private String evaluationDate;

    public Math(String evaluationDate, String evaluationType, String level, String studentKey) {
        this.evaluationDate = evaluationDate;
        this.evaluationType = evaluationType;
        this.level = level;
        this.studentKey = studentKey;
    }

    public String getStudentKey() {
        return studentKey;
    }

    public String getLevel() {
        return level;
    }

    public String getEvaluationType() {
        return evaluationType;
    }

    public String getEvaluationDate() {
        return evaluationDate;
    }

}
