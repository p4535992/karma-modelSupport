package edu.isi.karma.support;

import edu.isi.karma.controller.command.worksheet.ApplyHistoryFromR2RMLModelCommand;
import edu.isi.karma.controller.command.worksheet.ApplyHistoryFromR2RMLModelCommandFactory;
import edu.isi.karma.controller.history.HistoryJSONEditor;
import edu.isi.karma.controller.update.UpdateContainer;
import edu.isi.karma.rep.Worksheet;
import edu.isi.karma.rep.Workspace;
import edu.isi.karma.rep.WorkspaceManager;
import edu.isi.karma.util.FileUtil;


import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


/**
 * Created by 4535992 on 30/11/2015.
 * @author 4535992.
 * @version 2015-11-30.
 */
public class ApplyHistorySupport {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApplyHistorySupport.class);

    protected  ApplyHistorySupport() {}

    private static  ApplyHistorySupport instance = null;

    public static  ApplyHistorySupport getInstance(){
        if(instance == null) {
            instance = new  ApplyHistorySupport();
        }
        return instance;
    }

    public JSONArray extractWorkSheetHistory(String tableName,File R2RML){
        JSONArray historyJson = new JSONArray();
        Workspace workspace = WorkspaceManager.getInstance().createWorkspace();
        Worksheet workSheet = workspace.getFactory().createWorksheet(tableName,workspace,"UTF-8");
        ApplyHistoryFromR2RMLModelCommandFactory fac = new ApplyHistoryFromR2RMLModelCommandFactory();
        try {
            ApplyHistoryFromR2RMLModelCommand co =
                    (ApplyHistoryFromR2RMLModelCommand) fac.createCommandFromFile(workSheet.getId(), R2RML, workspace, true);

            UpdateContainer c = new UpdateContainer();
            //TODO: extractHistoryFromModel modified from private to public
            historyJson  = co.extractHistoryFromModel(workspace, c);
            HistoryJSONEditor editor = new HistoryJSONEditor(new JSONArray(historyJson.toString()), workspace, workSheet.getId());
            if (null == historyJson || historyJson.length() == 0) {
                throw new Exception(new Throwable("No history found in R2RML Model!"));
            }else{
               return historyJson;
            }
        }catch(Exception e){
            logger.error( "Error occured while applying history!", e);
        }
        return historyJson;
    }


    public JSONArray extractWorkSheetHistory(HttpServletRequest request,Workspace workspace){
        JSONArray historyJson = new JSONArray();
        final String worksheetId = "worksheetId";

        //Workspace workspace = WorkspaceManager.getInstance().createWorkspace();
        ApplyHistoryFromR2RMLModelCommandFactory fac = new ApplyHistoryFromR2RMLModelCommandFactory();
        try {
            String id = request.getParameter(worksheetId);
            ApplyHistoryFromR2RMLModelCommand co =
                    (ApplyHistoryFromR2RMLModelCommand) fac.createCommand(request, workspace);

            UpdateContainer c = new UpdateContainer();
            //TODO: extractHistoryFromModel modified from private to public
            historyJson  = co.extractHistoryFromModel(workspace, c);
            HistoryJSONEditor editor = new HistoryJSONEditor(new JSONArray(historyJson.toString()), workspace, id);
            if (null == historyJson || historyJson.length() == 0) {
                throw new Exception(new Throwable("No history found in R2RML Model!"));
            }else{
                return historyJson;
            }
        }catch(Exception e){
            logger.error( "Error occured while applying history!", e);
        }
        return historyJson;
    }

    public static void main(String args[]) throws IOException {
        File r2rml = new File("" +
                "C:\\Users\\tenti\\Desktop\\Marco Utility\\TESI 2015-09-30\\Web-Karma-20151130\\karma-modelSupport\\src\\main\\java\\edu\\isi\\karma\\test\\R2RML_infodocument-model_2015-07-08.ttl");
        ApplyHistorySupport support =  ApplyHistorySupport.getInstance();
        JSONArray array = support.extractWorkSheetHistory("infodocument_2015_09_18", r2rml);

        File outputJson = new File(
                "C:\\Users\\tenti\\Desktop\\Marco Utility\\TESI 2015-09-30\\Web-Karma-20151130\\karma-modelSupport\\src\\main\\java\\edu\\isi\\karma\\test\\output.json");

        FileUtil.writePrettyPrintedJSONObjectToFile(new JSONObject().put("WorkSheetHistory", (Object) array), outputJson);


        //JSONArray array2 = support.extractWorkSheetHistory("infodocument_2015_09_18", r2rml);
    }

}
