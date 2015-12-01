package edu.isi.karma.support;

import edu.isi.karma.controller.command.Command;
import edu.isi.karma.controller.command.CommandException;
import edu.isi.karma.controller.command.CommandType;
import edu.isi.karma.controller.command.ICommand;
import edu.isi.karma.controller.command.worksheet.ApplyHistoryFromR2RMLModelCommand;
import edu.isi.karma.controller.command.worksheet.ApplyHistoryFromR2RMLModelCommandFactory;
import edu.isi.karma.controller.command.worksheet.ApplyModelFromURLCommand;
import edu.isi.karma.controller.history.HistoryJSONEditor;
import edu.isi.karma.controller.update.ErrorUpdate;
import edu.isi.karma.controller.update.InfoUpdate;
import edu.isi.karma.controller.update.UpdateContainer;
import edu.isi.karma.kr2rml.KR2RMLVersion;
import edu.isi.karma.kr2rml.mapping.KR2RMLMapping;
import edu.isi.karma.kr2rml.mapping.R2RMLMappingIdentifier;
import edu.isi.karma.kr2rml.mapping.WorksheetR2RMLJenaModelParser;
import edu.isi.karma.rep.Worksheet;
import edu.isi.karma.rep.Workspace;
import edu.isi.karma.rep.WorkspaceManager;
import edu.isi.karma.util.FileUtil;
import edu.isi.karma.util.JSONUtil;
import edu.isi.karma.view.VWorkspace;
import edu.isi.karma.webserver.KarmaException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by 4535992 on 01/12/2015.
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
