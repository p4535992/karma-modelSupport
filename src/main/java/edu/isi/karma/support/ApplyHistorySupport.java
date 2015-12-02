package edu.isi.karma.support;

import edu.isi.karma.controller.history.HistoryJSONEditor;
import edu.isi.karma.controller.history.HistoryJsonUtil;
import edu.isi.karma.rep.Workspace;
import edu.isi.karma.rep.WorkspaceManager;


import edu.isi.karma.webserver.KarmaException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.File;


/**
 * Created by 4535992 on 30/11/2015.
 * @author 4535992.
 * @version 2015-11-30.
 */
@SuppressWarnings("unused")
public class ApplyHistorySupport extends ApplyMappingSupport {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApplyHistorySupport.class);

    private JSONArray historyJson;

    protected ApplyHistorySupport() {
        if(isOffline  && !isSetted){
            try {
                setupKarmaMetadata();
            } catch (KarmaException e) {
                logger.error("Error occurred while initializing the Karma MetaData...");
                System.exit(-1);
            }
        }
    }

    protected  ApplyHistorySupport(boolean isOffline) {
        this.isOffline = isOffline;
        new ApplyHistorySupport();
    }

    private static  ApplyHistorySupport instance = null;

    public static  ApplyHistorySupport getInstance(){
        if(instance == null) {
            instance = new  ApplyHistorySupport();
        }
        return instance;
    }

    public static  ApplyHistorySupport getInstance(boolean isOffline){
        if(instance == null) {
            instance = new  ApplyHistorySupport(isOffline);
        }
        return instance;
    }

    /**
     * Method to READ a WorkSheetHistory from a R2RML Model.
     * @param tableName the String name of The Table.
     * @param r2rmlModelFile the File turtle R2RML of the Table @tableName.
     * @return the JSON Array of the WorkSheetHistory.
     * @throws KarmaException throw if any error is occurred with Web-Karma.
     */
    protected JSONArray extractWorkSheetHistoryFromModel(String tableName,File r2rmlModelFile) throws KarmaException {
        try {
            /*
            ApplyHistoryFromR2RMLModelCommandFactory fac = new ApplyHistoryFromR2RMLModelCommandFactory();
            ApplyHistoryFromR2RMLModelCommand co =
                    (ApplyHistoryFromR2RMLModelCommand) fac.createCommandFromFile(workSheet.getId(), R2RML, workspace, override);
            */
            historyJson = prepareKR2RMLMappingFromModel(tableName, r2rmlModelFile).getWorksheetHistory();
            HistoryJSONEditor editor = new HistoryJSONEditor(new JSONArray(historyJson.toString()), workspace, currentWorkSheet.getId());
            if (historyJson.length() == 0) {
                throw new Exception(new Throwable("No history found in R2RML Model!"));
            }else{
               return historyJson;
            }
        }catch(Exception e){
            logger.error( "Error occured while applying history!", e);
        }
        return historyJson;
    }


    /**
     * Method to READ a WorkSheetHistory from a Rquest by the graphic interface of the Server.
     * @param request HttpServletRequest the request from the server.
     * @return the JSON Array of the WorkSheetHistory.
     * @throws KarmaException throw if any error is occurred with Web-Karma.
     */
    protected JSONArray extractWorkSheetHistoryFromRequest(HttpServletRequest request) throws KarmaException {
        this.workspace = WorkspaceManager.getInstance().createWorkspace();
        return extractWorkSheetHistoryFromRequest(request,workspace);
    }

    /**
     * Method to READ a WorkSheetHistory from a Rquest by the graphic interface of the Server.
     * @param request HttpServletRequest the request from the server.
     * @param workspace Workspace in use on the Server.
     * @return the JSON Array of the WorkSheetHistory.
     * @throws KarmaException throw if any error is occurred with Web-Karma.
     */
    protected JSONArray extractWorkSheetHistoryFromRequest(HttpServletRequest request,Workspace workspace) throws KarmaException {
        try {
            /*
            ApplyHistoryFromR2RMLModelCommandFactory fac = new ApplyHistoryFromR2RMLModelCommandFactory();
            ApplyHistoryFromR2RMLModelCommand co =
                    (ApplyHistoryFromR2RMLModelCommand) fac.createCommand(request, workspace);
            */
            historyJson = prepareKR2RMLMappingFromRequest(request, workspace).getWorksheetHistory();
            HistoryJSONEditor editor = new HistoryJSONEditor(new JSONArray(historyJson.toString()), workspace, currentWorkSheet.getId());
            if (historyJson.length() == 0) {
                throw new Exception(new Throwable("No history found in R2RML Model!"));
            }else{
                return historyJson;
            }
        }catch(Exception e){
            logger.error( "Error occured while applying history!", e);
        }
        return historyJson;
    }

  /*  protected updateInputparameters(JSONArray historyJson){
        mapping.getWorksheetHistory().Arr
    }*/



   /* public static void main(String args[]) throws IOException, KarmaException {
        File r2rml = new File("" +
                "C:\\Users\\tenti\\Desktop\\Marco Utility\\TESI 2015-09-30\\Web-Karma-20151130\\karma-modelSupport\\src\\main\\java\\edu\\isi\\karma\\test\\R2RML_infodocument-model_2015-07-08.ttl");
        ApplyHistorySupport support =  ApplyHistorySupport.getInstance(true);
        JSONArray array = support.extractWorkSheetHistoryFromModel("infodocument_2015_09_18", r2rml);

        File outputJson = new File(
                "C:\\Users\\tenti\\Desktop\\Marco Utility\\TESI 2015-09-30\\Web-Karma-20151130\\karma-modelSupport\\src\\main\\java\\edu\\isi\\karma\\test\\output.json");

        FileUtil.writePrettyPrintedJSONObjectToFile(new JSONObject().put("WorkSheetHistory", (Object) array), outputJson);
        //JSONArray array2 = support.extractWorkSheetHistory("infodocument_2015_09_18", r2rml);
    }*/

}
