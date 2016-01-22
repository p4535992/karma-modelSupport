package edu.isi.karma.supportObject;

import org.openrdf.model.vocabulary.RDF;

<<<<<<< HEAD
=======
import java.util.ArrayList;
>>>>>>> origin/master
import java.util.Collections;
import java.util.List;

public class SupportStatement {

    private SupportSubjectMap subjectMap;
<<<<<<< HEAD
    private List<SupportPredicate> listPredicate;
=======
    //private List<SupportPredicate> listPredicate;
    private SupportPredicate predicate;
>>>>>>> origin/master
    private List<SupportObjectMap> listObjectMap;
    private List<SupportPredicateObjectMap> predicateObjectMaps;

    public SupportStatement() {}

    public SupportStatement(SupportSubjectMap subjectMap, SupportPredicate predicate, SupportObjectMap objectMap) {
        this.subjectMap = subjectMap;
<<<<<<< HEAD
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

=======
        this.listObjectMap = Collections.singletonList(objectMap);
        if(objectMap.getRdfsLiteralTypesString().isEmpty()){
            /*this.listPredicate = Collections.singletonList(new SupportPredicate(subjectMap.getId(),RDF.TYPE.toString()));*/
            predicate = new SupportPredicate(subjectMap.getId(), RDF.TYPE.toString());
            this.predicateObjectMaps.add(new SupportPredicateObjectMap(predicate, this.listObjectMap));
        }else{
           /* this.listPredicate = Collections.singletonList(predicate);*/
            this.predicateObjectMaps.add(new SupportPredicateObjectMap(predicate, this.listObjectMap));
        }
    }

    /*public SupportStatement(SupportSubjectMap subjectMap, List<SupportPredicate> listPredicate, List<SupportObjectMap> listObjectMap) {
        this.subjectMap = subjectMap;
        if(listObjectMap.isEmpty()){
            this.listPredicate = new ArrayList<>();
            this.listPredicate.add(new SupportPredicate(subjectMap.getId(), RDF.TYPE.toString()));
            this.listObjectMap = listObjectMap;

        }else {
            this.listPredicate = listPredicate;
            this.listObjectMap = listObjectMap;
        }
    }*/

    public SupportStatement(SupportSubjectMap subjectMap, SupportPredicate predicate, List<SupportObjectMap> listObjectMap) {
        this.subjectMap = subjectMap;
        if(listObjectMap.isEmpty()){
           /* this.listPredicate = new ArrayList<>();
            this.listPredicate.add(new SupportPredicate(subjectMap.getId(), RDF.TYPE.toString()));
            this.listObjectMap = listObjectMap;*/
            predicate = new SupportPredicate(subjectMap.getId(), RDF.TYPE.toString());
            this.predicateObjectMaps.add(new SupportPredicateObjectMap(predicate, this.listObjectMap));
        }else {
           /* this.listPredicate = Collections.singletonList(predicate);
            this.listObjectMap = listObjectMap;*/
            this.predicateObjectMaps.add(new SupportPredicateObjectMap(predicate, this.listObjectMap));
        }
>>>>>>> origin/master
    }

    public SupportStatement(SupportSubjectMap subjectMap,SupportPredicateObjectMap supportPredicateObjectMapList) {
        this.subjectMap = subjectMap;
<<<<<<< HEAD
        this.listPredicate = Collections.singletonList(supportPredicateObjectMapList.getPredicate());
        this.listObjectMap = supportPredicateObjectMapList.getListObjectMap();
=======
        //List<SupportObjectMap> listObjectMap = supportPredicateObjectMapList.getListObjectMap();
        //List<SupportPredicate> listPredicate = Collections.singletonList(supportPredicateObjectMapList.getPredicate());
        if(supportPredicateObjectMapList.getListObjectMap().isEmpty()){
            //this.listPredicate = new ArrayList<>();
            //this.listPredicate.add(new SupportPredicate(subjectMap.getId(), RDF.TYPE.toString()));
            //this.listObjectMap = listObjectMap;
            predicate = new SupportPredicate(subjectMap.getId(), RDF.TYPE.toString());
            this.predicateObjectMaps =  Collections.singletonList(new SupportPredicateObjectMap(predicate, this.listObjectMap));
        }else {
            /*this.listPredicate = listPredicate;
            this.listObjectMap = listObjectMap;*/
            this.predicateObjectMaps = Collections.singletonList(supportPredicateObjectMapList);
        }
    }

    public SupportSubjectMap getSubjectMap() {
        return subjectMap;
    }

    public void setSubjectMap(SupportSubjectMap subjectMap) {
        this.subjectMap = subjectMap;
    }

    public List<SupportPredicateObjectMap> getPredicateObjectMaps() {
        return predicateObjectMaps;
    }

    public void setPredicateObjectMaps(List<SupportPredicateObjectMap> predicateObjectMaps) {
        this.predicateObjectMaps = predicateObjectMaps;
>>>>>>> origin/master
    }

    @Override
    public String toString() {
        return "SupportStatement{" +
<<<<<<< HEAD
                "subjectMap=" + subjectMap.toString() +
                ", listPredicate=" + listPredicate.toString() +
                ", listObjectMap=" + listObjectMap.toString() +
                '}';
    }


=======
                ", subjectMap=" +
                ((subjectMap==null) ? "N/A": subjectMap.toString()) +
                "predicateObjectMaps=" +
                ((predicateObjectMaps==null || predicateObjectMaps.isEmpty()) ? "N/A": predicateObjectMaps.toString()) +
                '}';
    }
>>>>>>> origin/master
}
