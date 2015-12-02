package edu.isi.karma;

import edu.isi.karma.kr2rml.Prefix;
import edu.isi.karma.kr2rml.mapping.KR2RMLMapping;
import edu.isi.karma.rep.Workspace;
import edu.isi.karma.support.ApplyHistorySupport;
import edu.isi.karma.support.ApplyMappingSupport;
import edu.isi.karma.webserver.KarmaException;
import org.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * Created by 4535992 on 30/11/2015.
 * @author 4535992.
 * @version 2015-11-30.
 */
public class ModelWebKarmaSupport {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ModelWebKarmaSupport.class);

    private ApplyMappingSupport aMapping;

    protected ModelWebKarmaSupport() {
        aMapping = ApplyMappingSupport.getInstance();
    }

    protected ModelWebKarmaSupport(boolean isOffline) {
        aMapping = ApplyMappingSupport.getInstance(isOffline);
    }

    private static ModelWebKarmaSupport instance = null;

    public static ModelWebKarmaSupport getInstance(){
        if(instance == null)instance = new ModelWebKarmaSupport();
        return instance;
    }

    public static ModelWebKarmaSupport getInstance(boolean isOffline){
        if(instance == null) instance = new ModelWebKarmaSupport(isOffline);
        return instance;
    }

    //-----------------------------------------------
    //Methods
    //-------------------------------------------------
    public KR2RMLMapping prepareKR2RMLMapping(Workspace workspace, String worksheetId, File r2rmlModelFile){
        KR2RMLMapping mapping;
        try {
            mapping = aMapping.prepareKR2RMLMappingFromModel(workspace,worksheetId,r2rmlModelFile);
        } catch (IOException e) {
            logger.error("The path to the MOdel file is errate",e);
            mapping = null;
        } catch (KarmaException e) {
            logger.error("Karma has thrown some exception", e);
            mapping = null;
        }
        return mapping;
    }

    public KR2RMLMapping prepareKR2RMLMapping(String tableName, File r2rmlModelFile){
        KR2RMLMapping mapping;
        try {
            mapping = aMapping.prepareKR2RMLMappingFromModel(tableName,r2rmlModelFile);
        } catch (IOException e) {
            logger.error("The path to the MOdel file is errate",e);
            mapping = null;
        } catch (KarmaException e) {
            logger.error("Karma has thrown some exception", e);
            mapping = null;
        }
        return mapping;
    }

    public KR2RMLMapping prepareKR2RMLMapping(HttpServletRequest request,Workspace workspace){
        KR2RMLMapping mapping;
        try {
            mapping = aMapping.prepareKR2RMLMappingFromRequest(request, workspace);
        } catch (IOException e) {
            logger.error("The path to the MOdel file is errate",e);
            mapping = null;
        } catch (KarmaException e) {
            logger.error("Karma has thrown some exception", e);
            mapping = null;
        }
        return mapping;
    }

    public JSONArray extractWorkSheetHistory(String tableName,File r2rmlModelFile) throws KarmaException {
        return aMapping.extractWorkSheetHistory(tableName,r2rmlModelFile);
    }

    public JSONArray extractWorkSheetHistory(HttpServletRequest request) throws KarmaException {
        return aMapping.extractWorkSheetHistory(request);
    }

    public JSONArray extractWorkSheetHistory(HttpServletRequest request,Workspace workspace) throws KarmaException {
        return aMapping.extractWorkSheetHistory(request, workspace);
    }

    public void updtaColumnName(String columname,JSONArray historyJson)  {
        aMapping.updtaColumnName(columname,historyJson);
    }

    public String getPrefixes(){ return aMapping.getPrefixes();}

    public void getAuxInfo(){
//        km-dev:sourceName "infodocument_2015_09_18" ;

//        km-dev:modelPublicationTime "1436370842550"^^xsd:long ;

//        km-dev:modelVersion "1.7" ;

//        km-dev:hasInputColumns "[[{\"columnName\":\"doc_id\"}],[{\"columnName\":\"url\"}],[{\"columnName\":\"regione\"}],[{\"columnName\":\"provincia\"}],[{\"columnName\":\"city\"}],[{\"columnName\":\"indirizzo\"}],[{\"columnName\":\"iva\"}],[{\"columnName\":\"email\"}],[{\"columnName\":\"telefono\"}],[{\"columnName\":\"fax\"}],[{\"columnName\":\"edificio\"}],[{\"columnName\":\"latitude\"}],[{\"columnName\":\"longitude\"}],[{\"columnName\":\"nazione\"}],[{\"columnName\":\"description\"}],[{\"columnName\":\"indirizzoNoCAP\"}],[{\"columnName\":\"postalCode\"}],[{\"columnName\":\"indirizzoHasNumber\"}],[{\"columnName\":\"identifier\"}],[{\"columnName\":\"name_location\"}]]" ;
//        km-dev:hasOutputColumns "[[{\"columnName\":\"doc_id\"}],[{\"columnName\":\"url\"}],[{\"columnName\":\"regione\"}],[{\"columnName\":\"provincia\"}],[{\"columnName\":\"city\"}],[{\"columnName\":\"indirizzo\"}],[{\"columnName\":\"iva\"}],[{\"columnName\":\"email\"}],[{\"columnName\":\"telefono\"}],[{\"columnName\":\"fax\"}],[{\"columnName\":\"edificio\"}],[{\"columnName\":\"latitude\"}],[{\"columnName\":\"longitude\"}],[{\"columnName\":\"nazione\"}],[{\"columnName\":\"description\"}],[{\"columnName\":\"indirizzoNoCAP\"}],[{\"columnName\":\"postalCode\"}],[{\"columnName\":\"indirizzoHasNumber\"}],[{\"columnName\":\"identifier\"}],[{\"columnName\":\"name_location\"}]]" ;
//        km-dev:hasModelLabel "infodocument_2015_09_18" ;
//        km-dev:hasBaseURI "http://purl.org/goodrelations/v1#" ;
    }





}
