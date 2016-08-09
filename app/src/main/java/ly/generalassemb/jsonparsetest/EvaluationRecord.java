package ly.generalassemb.jsonparsetest;

/**
 * Created by darrankelinske on 8/9/16.
 */
public class EvaluationRecord {
    private String studentName;
    private String school;
    private String village;
    private String cluster;
    private String block;
    private String evaluationDate;
    private String evaluationType;
    private String level;

    public EvaluationRecord(String studentName, String school, String village, String cluster,
                            String block, String evaluationDate, String evaluationType, String level) {
        this.studentName = studentName;
        this.school = school;
        this.village = village;
        this.cluster = cluster;
        this.block = block;
        this.evaluationDate = evaluationDate;
        this.evaluationType = evaluationType;
        this.level = level;
    }


    public String getEvaluationRow() {
        return studentName + "," + school + "," + village + "," + cluster + "," + block + ","
                +evaluationDate + "," + evaluationType + "," +level;
    }
}
