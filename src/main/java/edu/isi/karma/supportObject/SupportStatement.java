package edu.isi.karma.supportObject;

import org.openrdf.model.vocabulary.RDF;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SupportStatement {

    private SupportSubjectMap subjectMap;
    private List<SupportPredicate> listPredicate;
    private List<SupportObjectMap> listObjectMap;
    private List<SupportPredicateObjectMap> predicateObjectMaps;

    public SupportStatement() {}

    public SupportStatement(SupportSubjectMap subjectMap, SupportPredicate predicate, SupportObjectMap objectMap) {
        this.subjectMap = subjectMap;
        if(objectMap.getRdfsLiteralTypesString().isEmpty()){
            this.listPredicate = Collections.singletonList(new SupportPredicate(subjectMap.getId(),RDF.TYPE.toString()));
        }else{
            this.listPredicate = Collections.singletonList(predicate);
        }

        this.listObjectMap = Collections.singletonList(objectMap);
    }

    public SupportStatement(SupportSubjectMap subjectMap, List<SupportPredicate> listPredicate, List<SupportObjectMap> listObjectMap) {
        this.subjectMap = subjectMap;
        if(listObjectMap.isEmpty()){
            this.listPredicate = new ArrayList<>();
            this.listPredicate.add(new SupportPredicate(subjectMap.getId(), RDF.TYPE.toString()));
            this.listObjectMap = listObjectMap;

        }else {
            this.listPredicate = listPredicate;
            this.listObjectMap = listObjectMap;
        }
    }

    public SupportStatement(SupportSubjectMap subjectMap,SupportPredicateObjectMap supportPredicateObjectMapList) {
        this.subjectMap = subjectMap;
        List<SupportObjectMap> listObjectMap = supportPredicateObjectMapList.getListObjectMap();
        List<SupportPredicate> listPredicate = Collections.singletonList(supportPredicateObjectMapList.getPredicate());
        if(listObjectMap.isEmpty()){
            this.listPredicate = new ArrayList<>();
            this.listPredicate.add(new SupportPredicate(subjectMap.getId(), RDF.TYPE.toString()));
            this.listObjectMap = listObjectMap;
        }else {
            this.listPredicate = listPredicate;
            this.listObjectMap = listObjectMap;
        }
    }

    public SupportSubjectMap getSubjectMap() {
        return subjectMap;
    }

    public void setSubjectMap(SupportSubjectMap subjectMap) {
        this.subjectMap = subjectMap;
    }

    public List<String> getListPredicate() {
        List<String> list = new ArrayList<>();
        for(SupportPredicate pred : listPredicate){
            for(String rdfType : pred.getRdfType()){
                list.add(rdfType);
            }
        }
        return list;
    }

    public void setListPredicate(List<SupportPredicate> listPredicate) {
        this.listPredicate = listPredicate;
    }

    public List<String> getListObjectMap() {
        List<String> list = new ArrayList<>();
        for(SupportObjectMap obj : listObjectMap){
            for(String rdfType : obj.getRdfsLiteralTypesString()){
                list.add(rdfType);
            }
        }
        return list;
    }

    public void setListObjectMap(List<SupportObjectMap> listObjectMap) {
        this.listObjectMap = listObjectMap;
    }

    @Override
    public String toString() {
        return "SupportStatement{" +
                "subjectMap=" + subjectMap.toString() +
                ", listPredicate=" + listPredicate.toString() +
                ", listObjectMap=" + listObjectMap.toString() +
                '}';
    }


}
