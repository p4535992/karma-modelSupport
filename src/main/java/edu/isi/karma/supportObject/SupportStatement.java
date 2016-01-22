package edu.isi.karma.supportObject;

import org.openrdf.model.vocabulary.RDF;

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
        for(SupportObjectMap objectMap: listObjectMap){
            if(objectMap.getRdfsLiteralTypesString().isEmpty()){
                this.listPredicate = Collections.singletonList(new SupportPredicate(subjectMap.getId(),RDF.TYPE.toString()));
            }else{
                this.listPredicate = listPredicate;
            }
            this.listObjectMap =  Collections.singletonList(objectMap);
        }

    }

    public SupportStatement(SupportSubjectMap subjectMap,SupportPredicateObjectMap supportPredicateObjectMapList) {
        this.subjectMap = subjectMap;
        this.listPredicate = Collections.singletonList(supportPredicateObjectMapList.getPredicate());
        this.listObjectMap = supportPredicateObjectMapList.getListObjectMap();
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
