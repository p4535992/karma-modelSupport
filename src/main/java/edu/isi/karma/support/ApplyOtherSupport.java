package edu.isi.karma.support;

import edu.isi.karma.kr2rml.*;
import edu.isi.karma.kr2rml.formatter.KR2RMLColumnNameFormatter;
import edu.isi.karma.kr2rml.mapping.KR2RMLMappingAuxillaryInformation;
import edu.isi.karma.kr2rml.template.TemplateTerm;
import edu.isi.karma.kr2rml.template.TemplateTermSet;
import edu.isi.karma.rep.metadata.WorksheetProperties;
import edu.isi.karma.supportObject.*;
import edu.isi.karma.webserver.KarmaException;
import org.apache.commons.collections.map.MultiValueMap;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by 4535992 on 30/11/2015.
 * @author 4535992.
 * @version 2015-11-30.
 */
public class ApplyOtherSupport extends ApplyMappingSupport {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApplyOtherSupport.class);
    protected KR2RMLMappingAuxillaryInformation auxInfo;

    protected ApplyOtherSupport() {}

    private static ApplyOtherSupport instance = null;

    public static ApplyOtherSupport getInstance(){
        if(instance == null) {
            instance = new ApplyOtherSupport();
        }
        return instance;
    }

    public void extract() throws KarmaException {
       this.auxInfo = mapping.getAuxInfo();
    }

    protected String formatColumnName(String columnName){
        KR2RMLColumnNameFormatter formatter = mapping.getColumnNameFormatter();
        return formatter.getFormattedColumnName(columnName);
    }

    protected String unFormatColumnName(String columnName){
        KR2RMLColumnNameFormatter formatter = mapping.getColumnNameFormatter();
        return formatter.getColumnNameWithoutFormatting(columnName);
    }

    protected String getModelVersion(){
        KR2RMLVersion version = mapping.getVersion();
        return version.toString();
    }

    protected String getPrefixes(){
        List<Prefix> prefixes =  mapping.getPrefixes();
        StringBuilder b = new StringBuilder();
        for(Prefix p : prefixes){
            b.append("@prefix ").append(p.getPrefix()).append(" : <").append(p.getNamespace()).append(">.\n");
        }
        return b.toString();
    }

    protected String getSourceType(){
        WorksheetProperties.SourceTypes sourceTypes = mapping.getSourceType(); //done
        return sourceTypes.name();
    }

    protected String getSourceName(){
        return mapping.getId().getName();
    }

    protected File getLocationFileR2RML() throws URISyntaxException {
        return new File(mapping.getId().getLocation().toURI());
    }

    /*protected MultiValueMap readAllInfoImpl(){
        Map<String, List<PredicateObjectMap>> columnNameToPredObjMLinks =
                mapping.getAuxInfo().getColumnNameToPredObjLinks();
        MultiValueMap mapSubject = new MultiValueMap();
        int indexEntry = 0;
        for(Map.Entry<String, List<PredicateObjectMap>> entry : columnNameToPredObjMLinks.entrySet()){
            if(indexEntry >= mapping.getAuxInfo().getSubjectMapIdToTemplateAnchor().size()) break;
            indexEntry++;
            for(PredicateObjectMap pom: entry.getValue()){
                List<TemplateTerm> listTemplateTerm = pom.getTriplesMap().getSubject().getTemplate().getAllTerms();
                List<TemplateTermSet> listTemplateTermSet = pom.getTriplesMap().getSubject().getRdfsType();
                //Subject: for each rdf:type assigned to subject
                for(int u=0; u < listTemplateTerm .size();u++) {
                    String subjectValue =listTemplateTerm.get(u).getTemplateTermValue();//name_location
                    for (TemplateTermSet templateTermSet : listTemplateTermSet) {
                        MultiValueMap mapSubjectRdfType = new MultiValueMap();
                        String subjectRdfType =templateTermSet.getAllTerms().get(u).getTemplateTermValue();//http://..../Location
                        //PredicateObjectMap
                        for (int i = 0; i < pom.getTriplesMap().getPredicateObjectMaps().size(); i++) {
                            PredicateObjectMap predObjMap =pom.getTriplesMap().getPredicateObjectMaps().get(i);
                            MultiValueMap mapPredicate = new MultiValueMap();
                            //Predicate: for each predicate->object
                            for (int j = 0; j < predObjMap.getPredicate().getTemplate().getAllTerms().size(); j++) {
                                String predicateRdfType =
                                        predObjMap.getPredicate().getTemplate().getAllTerms().get(j).getTemplateTermValue();
                                //Object term
                                MultiValueMap mapObject = new MultiValueMap();
                                try {
                                    if (predObjMap.getObject().getTemplate() != null) {
                                        for (int k = 0; k < predObjMap.getObject().getTemplate().getAllTerms().size(); k++) {
                                            //Object rdfstype
                                            for (int y = 0; y < predObjMap.getObject().getRdfLiteralType().getAllTerms().size(); y++) {
                                                String objectMapValue =
                                                        predObjMap.getObject().getTemplate().getAllTerms().get(k).getTemplateTermValue();
                                                String objectLiteralType =
                                                        predObjMap.getObject().getRdfLiteralType().getAllTerms().get(y).getTemplateTermValue();
                                                if (objectLiteralType.isEmpty()) continue;
                                                mapObject.put(objectMapValue, objectLiteralType);
                                                //list.add(new String[]{subjectValue, subjectRdfType, predicateRdfType, objectMapValue, objectLiteralType});
                                            }
                                        }
                                    } else {
                                        //Is a uri link between two  class uri
                                        SubjectMap subMap = predObjMap.getObject().getRefObjectMap().getParentTriplesMap().getSubject();
                                        for (int k = 0; k < subMap.getRdfsType().size(); k++) {
                                            String objectMapValue =
                                                    predObjMap.getObject().
                                                            getRefObjectMap().getParentTriplesMap().getSubject()
                                                            .getRdfsType().get(k).getAllTerms()
                                                            .get(0).getTemplateTermValue();
                                            String objectLiteralType = "";
                                            mapObject.put(objectMapValue, objectLiteralType);
                                            //list.add(new String[]{subjectValue, subjectRdfType, predicateRdfType, objectMapValue, objectLiteralType});
                                        }
                                    }
                                } catch (NullPointerException ex) {
                                    ex.printStackTrace();

                                }
                                mapPredicate.put(predicateRdfType,mapObject);
                            }
                            mapSubjectRdfType.put(subjectRdfType,mapPredicate);
                        }
                        mapSubject.put(subjectValue,mapSubjectRdfType);
                    }
                }
            }
        }
        for(Map.Entry<String,String> entry :mapping.getAuxInfo().getSubjectMapIdToTemplateAnchor().entrySet()){
            createColumnNameToPredObjMLinks(entry.getKey(),entry.getValue(),mapSubject,"");
        }

        return mapSubject;
    }*/

    //TODO try to find a better method from the code of Web-karma for extract these information
    protected List<SupportStatement> readAllInfoImpl(){
        Map<String, List<PredicateObjectMap>> columnNameToPredObjMLinks =
                mapping.getAuxInfo().getColumnNameToPredObjLinks();
        List<SupportStatement> listStatement = new ArrayList<>();
        int indexEntry = 0;
        for(Map.Entry<String, List<PredicateObjectMap>> entry : columnNameToPredObjMLinks.entrySet()){

            if(indexEntry >= mapping.getAuxInfo().getSubjectMapIdToTemplateAnchor().size()) break;
            indexEntry++;

            for(PredicateObjectMap pom: entry.getValue()){
                List<TemplateTerm> listTemplateTerm = pom.getTriplesMap().getSubject().getTemplate().getAllTerms();
                List<TemplateTermSet> listTemplateTermSet = pom.getTriplesMap().getSubject().getRdfsType();
                //Subject: for each rdf:type assigned to subject
                SupportSubjectMap supportSubjectMap = new SupportSubjectMap();
                for(int u=0; u < listTemplateTerm .size();u++) {
                    String id = "";
                    String subjectValue =listTemplateTerm.get(u).getTemplateTermValue();//name_location
                    for(Map.Entry<String,SubjectMap> sub :mapping.getSubjectMapIndex().entrySet()){
                        if(sub.getValue().getTemplate().getAllTerms().get(0).getTemplateTermValue().equals(subjectValue)){
                            id = sub.getValue().getId();
                            supportSubjectMap = new SupportSubjectMap(sub.getValue());
                        }
                    }
                    //Start build list of predicate and object.
                    for (TemplateTermSet templateTermSet : listTemplateTermSet) {
                        //String subjectRdfType =
                        // templateTermSet.getAllTerms().get(u).getTemplateTermValue();//http://..../Location

                        //PredicateObjectMap
                        List<SupportPredicate> listPredicate = new ArrayList<>();
                        for (int i = 0; i < pom.getTriplesMap().getPredicateObjectMaps().size(); i++) {
                            PredicateObjectMap predObjMap =pom.getTriplesMap().getPredicateObjectMaps().get(i);
                            SupportPredicateObjectMap supportPredicateObjectMap = new SupportPredicateObjectMap();
                            supportPredicateObjectMap.setPredicate(new SupportPredicate(id,
                                    predObjMap.getPredicate().getTemplate().getAllTerms().get(0).getTemplateTermValue()
                                    ));

                            //Predicate: for each predicate->object
                            for (int j = 0; j < predObjMap.getPredicate().getTemplate().getAllTerms().size(); j++) {
                                /*String predicateRdfType =
                                        predObjMap.getPredicate().getTemplate().getAllTerms().get(j).getTemplateTermValue();*/
                                //listPredicate.add(new SupportPredicate(id,predicateRdfType));
                                //supportPredicateObjectMap.setPredicate(new SupportPredicate(id, predicateRdfType));

                                //Object term
                                //MultiValueMap mapObject = new MultiValueMap();
                                List<SupportObjectMap> olistObject = new ArrayList<>();
                                try {
                                    if (predObjMap.getObject().getTemplate() != null) {
                                        for (int k = 0; k < predObjMap.getObject().getTemplate().getAllTerms().size(); k++) {
                                            //Object rdfstype
                                            for (int y = 0; y < predObjMap.getObject().getRdfLiteralType().getAllTerms().size(); y++) {
                                                String objectMapValue =
                                                        predObjMap.getObject().getTemplate().getAllTerms().get(k).getTemplateTermValue();
                                                String objectLiteralType =
                                                        predObjMap.getObject().getRdfLiteralType().getAllTerms().get(y).getTemplateTermValue();
                                                if (objectLiteralType.isEmpty()) continue;
                                                olistObject.add(
                                                        new SupportObjectMap(id,objectMapValue,objectLiteralType));
                                                //mapObject.put(objectMapValue, objectLiteralType);
                                                //list.add(new String[]{subjectValue, subjectRdfType, predicateRdfType, objectMapValue, objectLiteralType});
                                            }
                                        }
                                    } else {
                                        //Is a uri link between two  class uri
                                        SubjectMap subMap = predObjMap.getObject().getRefObjectMap().getParentTriplesMap().getSubject();
                                        for (int k = 0; k < subMap.getRdfsType().size(); k++) {
                                            String objectMapValue =
                                                    predObjMap.getObject().
                                                            getRefObjectMap().getParentTriplesMap().getSubject()
                                                            .getRdfsType().get(k).getAllTerms()
                                                            .get(0).getTemplateTermValue();
                                            String objectLiteralType = "";
                                            olistObject.add(
                                                    new SupportObjectMap(id,objectMapValue,objectLiteralType));
                                            //mapObject.put(objectMapValue, objectLiteralType);
                                            //list.add(new String[]{subjectValue, subjectRdfType, predicateRdfType, objectMapValue, objectLiteralType});
                                        }
                                    }
                                } catch (NullPointerException ex) {
                                    ex.printStackTrace();

                                }
                                supportPredicateObjectMap.setListObjectMap(olistObject);
                                //mapPredicate.put(predicateRdfType,mapObject);
                            }
                            listStatement.add(new SupportStatement(supportSubjectMap,supportPredicateObjectMap));
                            //mapSubjectRdfType.put(subjectRdfType,mapPredicate);
                        }
                        //mapSubject.put(subjectValue,mapSubjectRdfType);
                    }
                }
            }
        }
        return listStatement;
    }

    protected Map<String, List<PredicateObjectMap>> createColumnNameToPredObjMLinks(
            String id,String subjectNameColumn,Map multiValueMap,String graphUri){
        Set entrySet = multiValueMap.entrySet();
        for (Object anEntrySet : entrySet) {
            Map.Entry mapEntry = (Map.Entry) anEntrySet;
            //String subjectNameColumn = (String) mapEntry.getKey();
            if (subjectNameColumn == mapEntry.getKey()) {
                MultiValueMap predicateObject = (MultiValueMap) multiValueMap.get(mapEntry.getKey());
            }
            String nothing = "";
        }
        return null;
    }













}
