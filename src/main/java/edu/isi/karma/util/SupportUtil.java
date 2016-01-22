package edu.isi.karma.util;

import com.hp.hpl.jena.rdf.model.Statement;
import edu.isi.karma.kr2rml.*;
import edu.isi.karma.kr2rml.planning.TriplesMap;
<<<<<<< HEAD
=======
import edu.isi.karma.kr2rml.planning.TriplesMapPlanGenerator;
import edu.isi.karma.kr2rml.planning.TriplesMapWorkerPlan;
>>>>>>> origin/master
import edu.isi.karma.kr2rml.template.ColumnTemplateTerm;
import edu.isi.karma.kr2rml.template.StringTemplateTerm;
import edu.isi.karma.kr2rml.template.TemplateTermSet;
import edu.isi.karma.rep.Worksheet;
import edu.isi.karma.rep.Workspace;
import edu.isi.karma.rep.WorkspaceManager;
import edu.isi.karma.rep.alignment.Label;
<<<<<<< HEAD
=======
import edu.isi.karma.supportJena.Jena2Kit;
import edu.isi.karma.supportObject.*;
>>>>>>> origin/master
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
=======
import java.util.*;
>>>>>>> origin/master

/**
 * Created by 4535992 on 30/11/2015.
 * @author 4535992.
 * @version 2015-11-30.
 */
public class SupportUtil {

    private static boolean hostAvailabilityCheck(String server_address,int tcp_server_port) {
        try (Socket s = new Socket(server_address, tcp_server_port)) {
            if(s.isConnected()) s.close();
            return true;
        } catch (IOException ex) {
            /* ignore */
        }
        return false;
    }

    public static boolean isOffline(String server_address,int tcp_server_port){
        return !hostAvailabilityCheck(server_address,tcp_server_port);
    }

    public static Workspace createNewWorkspace(){
        return WorkspaceManager.getInstance().createWorkspace();
    }

    public static  Worksheet createNewWorkSheet(String tableName,Workspace workspace){
        return workspace.getFactory().createWorksheet(tableName, workspace, "UTF-8");
    }

    public static void writePrettyPrintedJSONObjectToFile(JSONObject json){
        String prettyPrintedJSONString = json.toString(4);
        System.out.println(prettyPrintedJSONString);
    }

    public static List<Statement> convertToJenaStatement(List<SupportStatement> supportStmt,String subjectValue,String objectValue){
        List<Statement> stmt = new ArrayList<>();
        for(SupportStatement sStmt: supportStmt){
            String graphuri = sStmt.getSubjectMap().getNameSpaceKarma();
            for(SupportPredicateObjectMap pred : sStmt.getPredicateObjectMaps()){
                List<String> predicates = pred.getPredicate().getRdfType();
                for(String predicate: predicates){
                    for(SupportObjectMap objectMap : pred.getListObjectMap()){
                        for(String dataTypeObject : objectMap.getRdfsLiteralTypesString()){
                            stmt.add( Jena2Kit.createStatement(subjectValue,predicate,objectValue,graphuri,dataTypeObject));
                        }
                    }
                }
            }
        }
        return stmt;
    }


    //---------------------------------------------------------
    // CREATE METHOD
    //---------------------------------------------------------

    public static TemplateTermSet createTemplateTermSetByColumn(List<String> termValueColumns){
        TemplateTermSet tts = new TemplateTermSet();
        for(String termValue : termValueColumns){
            tts.addTemplateTermToSet(new ColumnTemplateTerm(termValue));
        }
        return tts;
    }

    public static TemplateTermSet createTemplateTermSetByString(List<String> termValueStrings,Boolean hasUri){
        TemplateTermSet tts = new TemplateTermSet();
        for(String termValue :termValueStrings){
            tts.addTemplateTermToSet(new StringTemplateTerm(termValue,hasUri));
        }
        return tts;
    }

    public static TemplateTermSet createTemplateTermSetByString(List<String> termValueStrings){
        TemplateTermSet tts = new TemplateTermSet();
        Boolean hasUri = false;
        for(String termValue :termValueStrings){
            if(isUri(termValue)) hasUri = true;
            tts.addTemplateTermToSet(new StringTemplateTerm(termValue,hasUri));
        }
        return tts;
    }

    public static TemplateTermSet createTemplateTermSetByString(String termValueStrings){
        return createTemplateTermSetByString(Collections.singletonList(termValueStrings));
    }

    public static List<TemplateTermSet> createRdfsType(List<String> rdfsTypeTermValues){
        TemplateTermSet tts = new TemplateTermSet();
        for(String termValue :rdfsTypeTermValues){
            tts.addTemplateTermToSet(new StringTemplateTerm(termValue,true));
        }
        return new ArrayList<TemplateTermSet>(Collections.singletonList(tts));
    }

    public static SubjectMap createSubjectMap(
            String id,List<String> termValueStrings,String graphUri,List<String> rdfsType){
        TemplateTermSet templateTermSet = createTemplateTermSetByColumn(termValueStrings);
        List<TemplateTermSet> rdfsType2 = createRdfsType(rdfsType);
        return createSubjectMap(id,templateTermSet,graphUri,rdfsType2);
    }

    public static SubjectMap createSubjectMap(
            String id,TemplateTermSet templateTermSet,String graphUri,List<TemplateTermSet> rdfsType){
        NamedGraph graph = new NamedGraph(new Label(graphUri));
        return new SubjectMap(id,templateTermSet,graph,rdfsType);
    }
/*
    public static TriplesMap createTriplesMap(String id,
                      SubjectMap subjectMap,List<PredicateObjectMap> predicateObjectMaps){
         if(predicateObjectMaps != null && !predicateObjectMaps.isEmpty()){
             return new TriplesMap(id,subjectMap,predicateObjectMaps);
         }else{
             return new TriplesMap(id,subjectMap);
         }
    }
*/
   /* public static TriplesMap createTriplesMap(String id,SubjectMap subjectMap){
        return createTriplesMap(id, subjectMap, null);
    }
*/
    protected PredicateObjectMap createPredicateObjectMap(String id,TriplesMap triplesMap){
        return new PredicateObjectMap(id,triplesMap);
    }

    private Predicate createPredicate(String id,String rdfType){
        return createPredicate(id,Collections.singletonList(rdfType));
    }

    private Predicate createPredicate(String id,List<String> rdfsType){
        Predicate pred =  new Predicate(id);
        pred.setTemplate(createTemplateTermSetByString(rdfsType,true));
        return pred;
    }

    private ObjectMap createObjectMap(String id,List<String> objectValue,List<String> rdfLiteralType){
        return new ObjectMap(id,createTemplateTermSetByString(objectValue),createTemplateTermSetByString(rdfLiteralType));
    }

    public static List<TriplesMap> createTriplesMap(List<SupportStatement> listSupport,String graphUri){ //
        List<TriplesMap> finalmap = new ArrayList<>();
        //Prepare TriplesMap from SubjectMap
        List<String> columns = new ArrayList<>();
        for(SupportStatement stmt : listSupport){
            if(!columns.contains(stmt.getSubjectMap().getColumnHNodeId())) {
                columns.add(stmt.getSubjectMap().getColumnHNodeId());
            }
        }

        for(SupportStatement stmt : listSupport) {
            SubjectMap subMap = null;
            String id = UUID.randomUUID().toString();
            List<PredicateObjectMap> list = new ArrayList<>();
            for (String column : columns) {
                if (column.equals(stmt.getSubjectMap().getColumnHNodeId())) {
                    subMap = createSubjectMap(id,
                            Collections.singletonList(stmt.getSubjectMap().getColumnHNodeId()),
                            graphUri,
                            stmt.getSubjectMap().getRdfsTypeString());
                    TriplesMap map = new TriplesMap(subMap.getId(), subMap);
                    for (SupportPredicateObjectMap predObj : stmt.getPredicateObjectMaps()) {
                        TemplateTermSet tts = createTemplateTermSetByString(predObj.getPredicate().getRdfType());
                        Predicate predicate = new Predicate(id);
                        predicate.setTemplate(tts);
                        for (SupportObjectMap objMap : predObj.getListObjectMap()) {
                            ObjectMap objectMap = new ObjectMap(id,
                                    createTemplateTermSetByString(objMap.getColumnHNodeId()),
                                    createTemplateTermSetByString(objMap.getRdfsLiteralTypesString()));
                            PredicateObjectMap predicateObjectMap = new PredicateObjectMap(id, map);
                            predicateObjectMap.setPredicate(predicate);
                            predicateObjectMap.setObject(objectMap);
                            list.add(predicateObjectMap);
                        }
                    }
                    map = new TriplesMap(id, subMap, list);
                    finalmap.add(map);
                }
            }
        }
        return finalmap;
    }


    //----------------------------------------------------------------------------------------------------------

    private static boolean isUri(Object uriResource){
        if(uriResource instanceof URI)return true;
        else{
            try { URI.create(String.valueOf(uriResource));return true;
            }catch(Exception e){ return false;}
        }
    }

<<<<<<< HEAD

    //---------------------------------------------------------
    // CREATE METHOD
    //---------------------------------------------------------

    public static Statement createStatement(SubjectMap subjectMap,Predicate predicate,ObjectMap objectMap){
        String subject = subjectMap.getGraph().getGraphLabel().getUri();
        String pred = predicate.getTemplate().toString();
        String object = objectMap.getRefObjectMap().getId();
        String datatype = objectMap.getRdfLiteralType().getAllTerms().get(0).getTemplateTermValue();
        return null;
=======
    public static  UUID generateUUID(){
        return UUID.randomUUID();
>>>>>>> origin/master
    }

    public static PredicateObjectMap createPredicateObjectMap(String id,SubjectMap subjectMap){
        TriplesMap t = new TriplesMap(id,subjectMap);
        return new PredicateObjectMap(id,t);
    }

    public static TemplateTermSet createTemplateTermSetByColumn(List<String> termValueColumns){
        TemplateTermSet tts = new TemplateTermSet();
        for(String termValue : termValueColumns){
            tts.addTemplateTermToSet(new ColumnTemplateTerm(termValue));
        }
        return tts;
    }

    public static TemplateTermSet createTemplateTermSetByString(List<String> termValueStrings,Boolean hasUri){
        TemplateTermSet tts = new TemplateTermSet();
        for(String termValue :termValueStrings){
            tts.addTemplateTermToSet(new StringTemplateTerm(termValue,hasUri));
        }
        return tts;
    }

    public static List<TemplateTermSet> createRdfsType(List<String> rdfsTypeTermValues){
        TemplateTermSet tts = new TemplateTermSet();
        for(String termValue :rdfsTypeTermValues){
            tts.addTemplateTermToSet(new StringTemplateTerm(termValue,true));
        }
        return new ArrayList<TemplateTermSet>(Collections.singletonList(tts));
    }

    public static SubjectMap createSubjectMap(
            String id,List<String> termValueStrings,String graphUri,List<String> rdfsType){
        TemplateTermSet templateTermSet = createTemplateTermSetByColumn(termValueStrings);
        List<TemplateTermSet> rdfsType2 = createRdfsType(rdfsType);
        return createSubjectMap(id,templateTermSet,graphUri,rdfsType2);
    }

    public static SubjectMap createSubjectMap(
            String id,TemplateTermSet templateTermSet,String graphUri,List<TemplateTermSet> rdfsType){
        NamedGraph graph = new NamedGraph(new Label(graphUri));
        return new SubjectMap(id,templateTermSet,graph,rdfsType);
    }

    public static TriplesMap createTriplesMap(String id,
                      SubjectMap subjectMap,List<PredicateObjectMap> predicateObjectMaps){
         if(predicateObjectMaps != null && !predicateObjectMaps.isEmpty()){
             return new TriplesMap(id,subjectMap,predicateObjectMaps);
         }else{
             return new TriplesMap(id,subjectMap);
         }
    }

    public static TriplesMap createTriplesMap(String id,SubjectMap subjectMap){
        return createTriplesMap(id,subjectMap,null);
    }

    protected PredicateObjectMap createPredicateObjectMap(String id,TriplesMap triplesMap){
       /* Predicate predicate = new Predicate(id);
        ObjectMap objMap = new ObjectMap(id,new TemplateTermSet(),new TemplateTermSet());
        RefObjectMap obj = new RefObjectMap(id,triplesMap);
        ObjectMap objMap2 = new ObjectMap( id,new TemplateTermSet(),new TemplateTermSet());*/
        return new PredicateObjectMap(id,triplesMap);
    }
}
