package edu.isi.karma.support;

import com.jayway.jsonpath.Configuration;
import edu.isi.karma.config.ModelingConfiguration;
import edu.isi.karma.controller.update.UpdateContainer;
import edu.isi.karma.er.helper.PythonRepository;
import edu.isi.karma.kr2rml.*;
import edu.isi.karma.kr2rml.mapping.KR2RMLMapping;
import edu.isi.karma.kr2rml.mapping.R2RMLMappingIdentifier;
import edu.isi.karma.kr2rml.mapping.WorksheetR2RMLJenaModelParser;
import edu.isi.karma.kr2rml.planning.TriplesMap;
import edu.isi.karma.kr2rml.template.TemplateTerm;
import edu.isi.karma.kr2rml.template.TemplateTermSet;
import edu.isi.karma.metadata.KarmaMetadataManager;
import edu.isi.karma.metadata.PythonTransformationMetadata;
import edu.isi.karma.metadata.UserConfigMetadata;
import edu.isi.karma.metadata.UserPreferencesMetadata;
import edu.isi.karma.modeling.semantictypes.SemanticTypeUtil;
import edu.isi.karma.rep.Worksheet;
import edu.isi.karma.rep.Workspace;
import edu.isi.karma.util.FileUtil;
import edu.isi.karma.util.SupportUtil;
import edu.isi.karma.webserver.KarmaException;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.collections.map.MultiKeyMap;
import org.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 4535992 on 30/11/2015.
 * @author 4535992.
 * @version 2015-11-30.
 */
@SuppressWarnings("unused")
public class ApplyMappingSupport {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApplyMappingSupport.class);

    protected static KR2RMLMapping mapping;
    protected Workspace workspace;
    protected Worksheet currentWorkSheet;
    protected boolean override;
    protected File r2rmlModelFile;
    protected boolean isOffline = true;
    protected boolean isSetted = false;
    protected Configuration jsonConfiguration;

    protected ApplyMappingSupport() {
        if(isOffline && !isSetted){
            try {setupKarmaMetadata();}
            catch (KarmaException e) {
                logger.error("Error occurred while initializing the Karma MetaData...");
                System.exit(-1);
            }
        }
    }

    protected ApplyMappingSupport(boolean isOffline) {
        this.isOffline = isOffline;
        new ApplyMappingSupport();
    }

    private static ApplyMappingSupport instance = null;

    public static ApplyMappingSupport getInstance(){
        if(instance == null)instance = new ApplyMappingSupport();
        return instance;
    }

    public static ApplyMappingSupport getInstance(boolean isOffline){
        if(instance == null) instance = new ApplyMappingSupport(isOffline);
        return instance;
    }

    /**
     * Set all your Web-Karma direcotries for work offline.
     * @throws KarmaException throw if any error is occurred with Web-Karma.
     */
    protected void setupKarmaMetadata() throws KarmaException {
        if(!isSetted) {
            UpdateContainer uc = new UpdateContainer();
            KarmaMetadataManager userMetadataManager = new KarmaMetadataManager();
            userMetadataManager.register(new UserPreferencesMetadata(), uc);
            userMetadataManager.register(new UserConfigMetadata(), uc);
            userMetadataManager.register(new PythonTransformationMetadata(), uc);
            PythonRepository.disableReloadingLibrary();
            SemanticTypeUtil.setSemanticTypeTrainingStatus(false);
            ModelingConfiguration.setLearnerEnabled(false); // disable automatic learning
            this.isSetted = true;
        }
    }

    /**
     * Method to get a KR2RMLMapping object of Web-Karma.
     * @param workspace the current Workspace where we are working.
     * @param worksheetId the String id of the current Worksheet where we are working.
     * @param r2rmlModelFile the File Turtle R2RML model fo Web-Karma.
     * @throws IOException
     * @throws KarmaException
     */
    public KR2RMLMapping prepareKR2RMLMappingFromModel(Workspace workspace, String worksheetId, File r2rmlModelFile)
            throws IOException, KarmaException {
        this.override = true;
        return prepareKR2RMLMapping(workspace, worksheetId, r2rmlModelFile);
    }

    public KR2RMLMapping prepareKR2RMLMappingFromModel(String tableName, File r2rmlModelFile)
            throws IOException, KarmaException {
        this.workspace = SupportUtil.createNewWorkspace();
        return prepareKR2RMLMappingFromModel(
                workspace, SupportUtil.createNewWorkSheet(tableName, workspace).getId(), r2rmlModelFile);
    }

    private KR2RMLMapping prepareKR2RMLMapping(Workspace workspace,String worksheetId,File r2rmlModelFile)
            throws KarmaException, IOException {
        Worksheet ws = workspace.getFactory().getWorksheet(worksheetId);
        R2RMLMappingIdentifier id = new R2RMLMappingIdentifier(ws.getTitle(), r2rmlModelFile.toURI().toURL());
        WorksheetR2RMLJenaModelParser parser = new WorksheetR2RMLJenaModelParser(id);
        mapping = parser.parse();
        this.currentWorkSheet = ws;
        this.r2rmlModelFile = r2rmlModelFile;
        return mapping;
        //this.workSheetHistory = mapping.getWorksheetHistory();
    }

    /**
     * Method to get a KR2RMLMapping object of Web-Karma.
     * @param request HttpServletRequest the request from the server.
     * @param workspace Workspace in use on the Server.
     * @throws IOException
     * @throws KarmaException
     */
    public KR2RMLMapping prepareKR2RMLMappingFromRequest(HttpServletRequest request,Workspace workspace)
            throws IOException, KarmaException {
        final String eWorkSheetId = "worksheetId";
        final String eOverride = "override";
        String worksheetId = request.getParameter(eWorkSheetId);
        File uploadableFile = FileUtil.downloadFileFromHTTPRequest(request);
        this.override = Boolean.parseBoolean(request.getParameter(eOverride));
        return prepareKR2RMLMapping(workspace, worksheetId, uploadableFile);
    }

    //-----------------------------------------------------------------
    // METHOD ApplyHistorySupport
    //-----------------------------------------------------------------

    public JSONArray extractWorkSheetHistory(String tableName,File r2rmlModelFile) throws KarmaException {
        return ApplyHistorySupport.getInstance(true).extractWorkSheetHistoryFromModel(tableName, r2rmlModelFile);
    }

    public JSONArray extractWorkSheetHistory(HttpServletRequest request) throws KarmaException {
        return ApplyHistorySupport.getInstance(false).extractWorkSheetHistoryFromRequest(request);
    }

    public JSONArray extractWorkSheetHistory(HttpServletRequest request,Workspace workspace) throws KarmaException {
        return ApplyHistorySupport.getInstance(false).extractWorkSheetHistoryFromRequest(request, workspace);
    }

    //-----------------------------------------------------------------
    // METHOD ApplySubjectMapSupport
    //-----------------------------------------------------------------

    public boolean hasSubject(String name){
        return ApplySubjectMapSupport.getInstance().hasSubjectImpl(name);
    }

    public SubjectMap readSubjectMapByKey(String key){
        return ApplySubjectMapSupport.getInstance().readSubjectMapByKeyImpl(key);
    }

    public SubjectMap readSubjectMapByName(String name){
        return ApplySubjectMapSupport.getInstance().readSubjectMapByNameImpl(name);
    }


    public TemplateTerm readTemplateTerm(int indexSubject,int indexTerm){
      return ApplySubjectMapSupport.getInstance().readTemplateTermImpl(indexSubject, indexTerm);
    }

    public TemplateTerm readTemplateTerm(String name){
        return ApplySubjectMapSupport.getInstance().readTemplateTermImpl(name);
    }

    public List<TemplateTerm> readTemplateTerms(int indexSubject){
       return ApplySubjectMapSupport.getInstance().readTemplateTermsImpl(indexSubject);
    }

    public TemplateTermSet readRdfType(int indexSubject,int indexTerm){
        return ApplySubjectMapSupport.getInstance().readRdfTypeImpl(indexSubject, indexTerm);
    }

    public List<TemplateTermSet> readRdfType(String name){
        return ApplySubjectMapSupport.getInstance().readRdfTypeImpl(name);
    }

    public TemplateTermSet readRdfType(String name,int indexTerm){
        return ApplySubjectMapSupport.getInstance().readRdfTypeImpl(name,indexTerm);
    }

    public List<TemplateTermSet> readRdfsType(int indexSubject){
        return ApplySubjectMapSupport.getInstance().readRdfsTypeImpl(indexSubject);
    }

    //--------------------------------------------------------------------------------
    // METHOD ApplyTriplesMapSupport
    //--------------------------------------------------------------------------------
    public boolean hasTriplesMap(String name){
        return ApplyTripleMapSupport.getInstance().hasTriplesMapImpl(name);
    }

    public List<PredicateObjectMap> readPredicateObjectBySubject(String name){
        return ApplyTripleMapSupport.getInstance().readPredicateObjectBySubjectImpl(name);
    }

    public TriplesMap readTriplesMapByKey(String key){
        return ApplyTripleMapSupport.getInstance().readTriplesMapByKeyImpl(key);
    }

    public TriplesMap readTriplesMapByName(String name){
        return ApplyTripleMapSupport.getInstance().readTriplesMapByNameImpl(name);
    }

    public PredicateObjectMap readPredicateObjectMap(int indexSubject,int indexTerm){
        return ApplyTripleMapSupport.getInstance().readPredicateObjectMapImpl(indexSubject,indexTerm);
    }

    public List<PredicateObjectMap> readPredicateObjectMaps(int indexSubject){
        return ApplyTripleMapSupport.getInstance().readPredicateObjectMapsImpl(indexSubject);
    }

    public Predicate readPredicate(int indexSubject,int indexTerm){
        return ApplyTripleMapSupport.getInstance().readPredicateImpl(indexSubject,indexTerm);
    }

    public ObjectMap readObject(int indexSubject,int indexTerm){
        return ApplyTripleMapSupport.getInstance().readObjectImpl(indexSubject, indexTerm);
    }

  /*  public List<Predicate> readPredicatesBySubject(String subject){
        return ApplyTripleMapSupport.getInstance().readPredicatesBySubjectImpl(subject);
    }

    public List<ObjectMap> readObjectMapBySubject(String subject){
        return ApplyTripleMapSupport.getInstance().readObjectMapBySubjectImpl(subject);
    }*/

    public Map<Predicate,ObjectMap> readPredicateObjects(int indexSubject){
        return ApplyTripleMapSupport.getInstance().readPredicateObjectsImpl(indexSubject);
    }

    public Map<Predicate,ObjectMap> readPredicateObjects(String subject){
        return ApplyTripleMapSupport.getInstance().readPredicateObjectsImpl(subject);
    }


    public void getAuxInfo(){
//        km-dev:sourceName "infodocument_2015_09_18" ;

//        km-dev:modelPublicationTime "1436370842550"^^xsd:long ;

//        km-dev:modelVersion "1.7" ;

//        km-dev:hasInputColumns "[[{\"columnName\":\"doc_id\"}],[{\"columnName\":\"url\"}],[{\"columnName\":\"regione\"}],[{\"columnName\":\"provincia\"}],[{\"columnName\":\"city\"}],[{\"columnName\":\"indirizzo\"}],[{\"columnName\":\"iva\"}],[{\"columnName\":\"email\"}],[{\"columnName\":\"telefono\"}],[{\"columnName\":\"fax\"}],[{\"columnName\":\"edificio\"}],[{\"columnName\":\"latitude\"}],[{\"columnName\":\"longitude\"}],[{\"columnName\":\"nazione\"}],[{\"columnName\":\"description\"}],[{\"columnName\":\"indirizzoNoCAP\"}],[{\"columnName\":\"postalCode\"}],[{\"columnName\":\"indirizzoHasNumber\"}],[{\"columnName\":\"identifier\"}],[{\"columnName\":\"name_location\"}]]" ;
//        km-dev:hasOutputColumns "[[{\"columnName\":\"doc_id\"}],[{\"columnName\":\"url\"}],[{\"columnName\":\"regione\"}],[{\"columnName\":\"provincia\"}],[{\"columnName\":\"city\"}],[{\"columnName\":\"indirizzo\"}],[{\"columnName\":\"iva\"}],[{\"columnName\":\"email\"}],[{\"columnName\":\"telefono\"}],[{\"columnName\":\"fax\"}],[{\"columnName\":\"edificio\"}],[{\"columnName\":\"latitude\"}],[{\"columnName\":\"longitude\"}],[{\"columnName\":\"nazione\"}],[{\"columnName\":\"description\"}],[{\"columnName\":\"indirizzoNoCAP\"}],[{\"columnName\":\"postalCode\"}],[{\"columnName\":\"indirizzoHasNumber\"}],[{\"columnName\":\"identifier\"}],[{\"columnName\":\"name_location\"}]]" ;
//        km-dev:hasModelLabel "infodocument_2015_09_18" ;
//        km-dev:hasBaseURI "http://purl.org/goodrelations/v1#" ;
    }

    protected void isOffline(String serverAddress, int port){
        this.isOffline = SupportUtil.isOffline(serverAddress,port);
    }

    protected void isOffline(boolean isOffline){
        this.isOffline = isOffline;
    }






}
