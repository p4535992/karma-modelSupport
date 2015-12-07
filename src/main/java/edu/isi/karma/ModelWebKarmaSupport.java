package edu.isi.karma;

import edu.isi.karma.kr2rml.*;
import edu.isi.karma.kr2rml.mapping.KR2RMLMapping;
import edu.isi.karma.kr2rml.planning.TriplesMap;
import edu.isi.karma.kr2rml.template.TemplateTerm;
import edu.isi.karma.kr2rml.template.TemplateTermSet;
import edu.isi.karma.rep.Workspace;
import edu.isi.karma.support.ApplyMappingSupport;
import edu.isi.karma.supportObject.SupportStatement;
import edu.isi.karma.webserver.KarmaException;
import org.apache.commons.collections.map.MultiValueMap;
import org.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * Created by 4535992 on 30/11/2015.
 * @author 4535992.
 * @version 2015-11-30.
 */
public class ModelWebKarmaSupport extends  ApplyMappingSupport{

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ModelWebKarmaSupport.class);



    protected ModelWebKarmaSupport() {
        ApplyMappingSupport.getInstance();
    }

    protected ModelWebKarmaSupport(boolean isOffline) {
        ApplyMappingSupport.getInstance(isOffline);
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
            mapping = super.prepareKR2RMLMappingFromModel(workspace, worksheetId, r2rmlModelFile);
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
            mapping = super.prepareKR2RMLMappingFromModel(tableName, r2rmlModelFile);
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
            mapping = super.prepareKR2RMLMappingFromRequest(request, workspace);
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
        return super.extractWorkSheetHistory(tableName, r2rmlModelFile);
    }

    public JSONArray extractWorkSheetHistory(HttpServletRequest request) throws KarmaException {
        return super.extractWorkSheetHistory(request);
    }

    public JSONArray extractWorkSheetHistory(HttpServletRequest request,Workspace workspace) throws KarmaException {
        return super.extractWorkSheetHistory(request, workspace);
    }

   /* public void updtaColumnName(String columname,JSONArray historyJson)  {
        aMapping.updtaColumnName(columname,historyJson);
    }*/

    //------------------------------------------------------------------------------------------------------------

    public boolean hasSubject(String name){
        return super.hasSubject(name);
    }

    public SubjectMap readSubjectMapByKey(String key){
        return super.readSubjectMapByKey(key);
    }

    public SubjectMap readSubjectMapByName(String name){
        return super.readSubjectMapByName(name);
    }

    public TemplateTerm readTemplateTerm(int indexSubject,int indexTerm){
        return super.readTemplateTerm(indexSubject, indexTerm);
    }

    public TemplateTerm readTemplateTerm(String name){
        return super.readTemplateTerm(name);
    }

    public List<TemplateTerm> readTemplateTerms(int indexSubject){
        return super.readTemplateTerms(indexSubject);
    }

    public TemplateTermSet readRdfType(int indexSubject,int indexTerm){
        return super.readRdfType(indexSubject, indexTerm);
    }

    public List<TemplateTermSet> readRdfType(String name){
        return super.readRdfType(name);
   }

    public TemplateTermSet readRdfType(String name,int indexTerm){
        return super.readRdfType(name, indexTerm);
    }

    public List<TemplateTermSet> readRdfsType(int indexSubject){
        return super.readRdfsType(indexSubject);
    }

    //-----------------------------------------------------------------------------------------------------------------
    public boolean hasTriplesMap(String name){
        return super.hasTriplesMap(name);
    }

    public List<PredicateObjectMap> readPredicateObjectBySubject(String name){
        return super.readPredicateObjectBySubject(name);
    }

    public TriplesMap readTriplesMapByKey(String key){
        return super.readTriplesMapByKey(key);
    }

    public TriplesMap readTriplesMapByName(String name){
        return super.readTriplesMapByName(name);
    }

    public PredicateObjectMap readPredicateObjectMap(int indexSubject,int indexTerm){
        return super.readPredicateObjectMap(indexSubject, indexTerm);
    }

    public List<PredicateObjectMap> readPredicateObjectMaps(int indexSubject){
        return super.readPredicateObjectMaps(indexSubject);
    }

    public Predicate readPredicate(int indexSubject,int indexTerm){
        return super.readPredicate(indexSubject, indexTerm);
    }

    public ObjectMap readObject(int indexSubject,int indexTerm){
        return super.readObject(indexSubject, indexTerm);
    }

   /* public List<Predicate> readPredicatesBySubject(String subject){
        return aMapping.readPredicatesBySubject(subject);
    }

    public List<ObjectMap> readObjectMapBySubject(String subject){
        return aMapping.readObjectMapBySubject(subject);
    }*/

    public Map<Predicate,ObjectMap> readPredicateObjects(int indexSubject){
        return super.readPredicateObjects(indexSubject);
    }

    public Map<Predicate,ObjectMap> readPredicateObjects(String subject){
        try {
            return super.readPredicateObjects(subject);
        }catch(java.lang.NullPointerException e){
            return null;
        }
    }

    public List<SupportStatement> readAllInfo(){
        return super.readAllInfo();
    }

    //public String getPrefixes(){ return aMapping.getPrefixes();}

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
