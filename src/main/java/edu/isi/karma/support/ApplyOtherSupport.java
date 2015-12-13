package edu.isi.karma.support;

import edu.isi.karma.cleaning.Template;
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

    //TODO try to find a better method from the code of Web-karma for extract these information
    protected List<SupportStatement> readAllInfoImpl(){
        List<SupportStatement> listStatement = new ArrayList<>();
        int indexEntry;
        Map<String,SubjectMap> mapSubject = mapping.getSubjectMapIndex();
        List<SupportSubjectMap> listSub = new ArrayList<>();
        //Prepare the SupportSubjectMap Object
        for(Map.Entry<String,SubjectMap> entrySub : mapSubject.entrySet()) {
            //SupportSubjectMap supportSubjectMap = new SupportSubjectMap();
            List<String> rdfsTypeSub = new ArrayList<>();
            for (TemplateTermSet tts : entrySub.getValue().getRdfsType()) {
                for (TemplateTerm tt : tts.getAllTerms()) {
                    rdfsTypeSub.add(tt.getTemplateTermValue());
                }
            }
            listSub.add(new SupportSubjectMap(
                    entrySub.getValue().getId(),
                    entrySub.getValue().getTemplate().getAllTerms().get(0).getTemplateTermValue(),
                    rdfsTypeSub));
        }

        for (SupportSubjectMap aListSub : listSub) {
            String id = aListSub.getId();
            indexEntry = 0;
            //Subject: for each rdf:type assigned to subject
            Map<String, List<PredicateObjectMap>> columnNameToPredObjMLinks = mapping.getAuxInfo().getColumnNameToPredObjLinks();
            for (Map.Entry<String, List<PredicateObjectMap>> entry : columnNameToPredObjMLinks.entrySet()) {
                if (indexEntry >= listSub.size()) break;
                indexEntry++;
                for (PredicateObjectMap pom : entry.getValue()) {
                    if(pom.getTriplesMap().getSubject().getTemplate().getAllTerms().get(0).getTemplateTermValue()
                            .equals(aListSub.getColumnHNodeId())) {
                        //Start build list of predicate and object.
                        //PredicateObjectMap
                        List<PredicateObjectMap> listPredicate = pom.getTriplesMap().getPredicateObjectMaps();
                        for (int i = 0; i < listPredicate.size(); i++) {
                            PredicateObjectMap predObjMap = pom.getTriplesMap().getPredicateObjectMaps().get(i);
                            SupportPredicateObjectMap supportPredicateObjectMap;
                            supportPredicateObjectMap = new SupportPredicateObjectMap(predObjMap);
                            listStatement.add(new SupportStatement(aListSub, supportPredicateObjectMap));
                        }//for
                    }//if
                }
            }
        }//for each subject
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
