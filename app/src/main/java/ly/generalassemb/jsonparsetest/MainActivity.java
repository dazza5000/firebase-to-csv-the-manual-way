package ly.generalassemb.jsonparsetest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainAct";
    private HashMap<String, Block> blockHashMap = new HashMap<>();
    private HashMap<String, Cluster> clusterHashMap = new HashMap<>();
    private HashMap<String, Village> villageHashMap = new HashMap<>();
    private HashMap<String, School> schoolHashMap = new HashMap<>();
    private HashMap<String, Student> studentHashMap = new HashMap<>();
    private HashMap<String, Math> mathHashMap = new HashMap<>();
    private HashMap<String, Language> languageHashMap = new HashMap<>();
    private ArrayList<EvaluationRecord> evaluationRecords = new ArrayList<>();

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://nonprofit-app.firebaseio.com/.json")
                .build();


        // Get a handler that can be used to post to the main thread
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    //Log.i(TAG, "onResponse: " +response.body().string());
                    String jsonString = response.body().string();
                    try {
                        JSONObject topLevelJsonObject = new JSONObject(jsonString);

                        // Create HashMap of block objects
                        JSONObject blockObject = topLevelJsonObject.getJSONObject("block");
                        Iterator keys = blockObject.keys();


                        while (keys.hasNext()) {
                            Object key = keys.next();
                            JSONObject value = blockObject.getJSONObject((String) key);
                            String blockName = value.getString("name");
                            blockHashMap.put((String) key, new Block(blockName));
                        }


                        Log.i(TAG, "The block HashMap size is: " + blockHashMap.size());

                        // Create HashMap of block objects
                        JSONObject clusterArray = topLevelJsonObject.getJSONObject("cluster");
                        Iterator clusterKeys = clusterArray.keys();
                        while (clusterKeys.hasNext()) {
                            Object key = clusterKeys.next();
                            if (null != clusterArray.getJSONObject((String) key)) {
                                JSONObject value = clusterArray.getJSONObject((String) key);
                                String clusterName = value.getString("name");
                                String blockKey = null;
                                if (!value.isNull("blockKey")) {
                                    blockKey = value.getString("blockKey");
                                }
                                if (null != clusterName && null != blockKey) {
                                    clusterHashMap.put((String) key, new Cluster(clusterName, blockKey));
                                }

                            }
                        }

                        Log.i(TAG, "The cluster HashMap size is: " + clusterHashMap.size());

                        // Create HashMap of village objects
                        JSONObject villageArray = topLevelJsonObject.getJSONObject("village");
                        Iterator villageKeys = villageArray.keys();
                        while (villageKeys.hasNext()) {
                            Object key = villageKeys.next();
                            if (null != villageArray.getJSONObject((String) key)) {
                                JSONObject value = villageArray.getJSONObject((String) key);
                                String villageName = value.getString("name");
                                String clusterKey = null;
                                if (!value.isNull("clusterKey")) {
                                    clusterKey = value.getString("clusterKey");
                                }
                                if (null != villageName && null != clusterKey) {
                                    villageHashMap.put((String) key, new Village(villageName, clusterKey));
                                }

                            }
                        }

                        Log.i(TAG, "The village HashMap size is: " + villageHashMap.size());


                        // Create HashMap of school objects
                        JSONObject schoolArray = topLevelJsonObject.getJSONObject("school");
                        Iterator schoolKeys = schoolArray.keys();
                        while (schoolKeys.hasNext()) {
                            Object key = schoolKeys.next();
                            if (null != schoolArray.getJSONObject((String) key)) {
                                JSONObject value = schoolArray.getJSONObject((String) key);
                                String schoolName = value.getString("name");
                                String villageKey = null;
                                if (!value.isNull("villageKey")) {
                                    villageKey = value.getString("villageKey");
                                }
                                if (null != schoolName && null != villageKey) {
                                    schoolHashMap.put((String) key, new School(schoolName, villageKey));
                                }

                            }
                        }

                        Log.i(TAG, "The school HashMap size is: " + schoolHashMap.size());


                        // Create HashMap of student objects
                        JSONObject studentArray = topLevelJsonObject.getJSONObject("student");

                        Iterator studentKeys = studentArray.keys();

                        while (studentKeys.hasNext()) {
                            Object key = studentKeys.next();

                            if (null != studentArray.getJSONObject((String) key)) {


                                JSONObject studentObject = studentArray.getJSONObject((String) key);
                                String studentName = studentObject.getString("name");
                                String schoolKey = null;


                                if (!studentObject.isNull("schoolKey")) {
                                    schoolKey = studentObject.getString("schoolKey");
                                }


                                if (null != studentName && null != schoolKey) {
                                    studentHashMap.put((String) key, new Student(studentName, schoolKey));
                                }

                            }
                        }

                        Log.i(TAG, "The student HashMap size is: " + studentHashMap.size());

                        // Create HashMap of math objects
                        JSONObject mathArray = topLevelJsonObject.getJSONObject("math");

                        Iterator mathKeys = mathArray.keys();
                        while (mathKeys.hasNext()) {
                            Object key = mathKeys.next();
                            if (null != mathArray.getJSONObject((String) key)) {
                                JSONObject value = mathArray.getJSONObject((String) key);
                                String evaluationDate = value.getString("evaluationDate");
                                String evaluationType = value.getString("evaluationType");
                                String level = value.getString("level");
                                String studentKey = null;
                                if (!value.isNull("studentKey")) {
                                    studentKey = value.getString("studentKey");
                                }
                                if (null != level && null != studentKey) {
                                    mathHashMap.put((String) key, new Math(evaluationDate, evaluationType, level, studentKey));
                                }

                            }
                        }

                        Log.i(TAG, "The math HashMap size is: " + mathHashMap.size());

                        // Create HashMap of language objects
                        JSONObject languageArray = topLevelJsonObject.getJSONObject("language");
                        Iterator languageKeys = languageArray.keys();

                        while (languageKeys.hasNext()) {
                            Object key = languageKeys.next();

                            if (null != languageArray.getJSONObject((String) key)) {
                                JSONObject value = languageArray.getJSONObject((String) key);
                                String evaluationDate = value.getString("evaluationDate");
                                String level = value.getString("level");
                                String studentKey = null;
                                if (!value.isNull("studentKey")) {
                                    studentKey = value.getString("studentKey");
                                }
                                if (null != level && null != studentKey) {
                                    languageHashMap.put((String) key, new Language(evaluationDate, "language", level, studentKey));
                                }

                            }
                        }

                        Log.i(TAG, "The language HashMap size is: " + languageHashMap.size());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                for (Map.Entry<String, Math> mathEntry : mathHashMap.entrySet()) {

                    String studentName = null;
                    String schoolName = null;
                    String villageName = null;
                    String clusterName = null;
                    String blockName = null;
                    String evaluationType = null;
                    String mathLevel = null;
                    String evaluationDate = null;

                    Student currentStudent =
                            studentHashMap.get(mathEntry.getValue().getStudentKey());

                    studentName = currentStudent.getName();

                    School currentSchool = schoolHashMap.get(currentStudent.getSchoolKey());
                    schoolName = currentSchool.getName();

                    Village currentVillage = villageHashMap.get(currentSchool.getVillageKey());
                    villageName = currentVillage.getName();

                    Cluster currentCluster = clusterHashMap.get(currentVillage.getClusterKey());
                    clusterName = currentCluster.getName();

                    Block currentBlock = blockHashMap.get(currentCluster.getBlockKey());
                    blockName = currentBlock.getName();

                    evaluationDate = mathEntry.getValue().getEvaluationDate();
                    evaluationType = mathEntry.getValue().getEvaluationType();
                    mathLevel = mathEntry.getValue().getLevel();

                    EvaluationRecord evaluationRecord =
                            new EvaluationRecord(studentName, schoolName, villageName, clusterName,
                                    blockName, evaluationDate, evaluationType, mathLevel);

                    evaluationRecords.add(evaluationRecord);


                    //System.out.println(mathEntry.getKey() + "/" + mathEntry.getValue());
                }

                for (Map.Entry<String, Language> languageEntry : languageHashMap.entrySet()) {

                    String studentName = null;
                    String schoolName = null;
                    String villageName = null;
                    String clusterName = null;
                    String blockName = null;
                    String evaluationType = null;
                    String languageLevel = null;
                    String evaluationDate = null;

                    Student currentStudent =
                            studentHashMap.get(languageEntry.getValue().getStudentKey());
                    studentName = currentStudent.getName();

                    School currentSchool = schoolHashMap.get(currentStudent.getSchoolKey());
                    schoolName = currentSchool.getName();

                    Village currentVillage = villageHashMap.get(currentSchool.getVillageKey());
                    villageName = currentVillage.getName();

                    Cluster currentCluster = clusterHashMap.get(currentVillage.getClusterKey());
                    clusterName = currentCluster.getName();

                    Block currentBlock = blockHashMap.get(currentCluster.getBlockKey());
                    blockName = currentBlock.getName();

                    evaluationDate = languageEntry.getValue().getEvaluationDate();
                    evaluationType = languageEntry.getValue().getEvaluationType();
                    languageLevel = languageEntry.getValue().getLevel();

                    EvaluationRecord evaluationRecord =
                            new EvaluationRecord(studentName, schoolName, villageName, clusterName,
                                    blockName, evaluationDate, evaluationType, languageLevel);

                    evaluationRecords.add(evaluationRecord);

                    //System.out.println(mathEntry.getKey() + "/" + mathEntry.getValue());
                }

                String stringToWrite = "";

                for (EvaluationRecord record : evaluationRecords) {
                    Log.i(TAG, record.getEvaluationRow());
                    stringToWrite.concat(record.getEvaluationRow() + "\n");
                }


                File file = null;
                File root = Environment.getExternalStorageDirectory();
                if (root.canWrite()) {
                    File dir = new File(root.getAbsolutePath() + "/EvaluationData");
                    dir.mkdirs();
                    file = new File(dir, "Evaluations.csv");
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.write(stringToWrite.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Uri uriToSend = null;
                uriToSend = Uri.fromFile(file);

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Evaluation Records");
                sendIntent.putExtra(Intent.EXTRA_STREAM, uriToSend);
                sendIntent.setType("text/html");
                startActivity(sendIntent);




//                writeToFile(stringToWrite, MainActivity.this);
//
//                String filename="csv.txt";
//                File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
//                Uri path = Uri.fromFile(filelocation);
//                Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                // set the type to 'email'
//                emailIntent .setType("vnd.android.cursor.dir/email");
//                String to[] = {"darran.kelinske@gmail.com"};
//                emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
//                // the attachment
//                emailIntent .putExtra(Intent.EXTRA_STREAM, path);
//                // the mail subject
//                emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Subject");
//                startActivity(Intent.createChooser(emailIntent , "Send email..."));
//            }

            }
        });

//    private void writeToFile(String data,Context context) {
//        try {
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("csv.txt", Context.MODE_PRIVATE));
//            outputStreamWriter.write(data);
//            outputStreamWriter.close();
//        }
//        catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
//    }

}
}
