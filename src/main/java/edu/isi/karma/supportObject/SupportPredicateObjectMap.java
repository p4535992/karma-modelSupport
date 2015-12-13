package edu.isi.karma.supportObject;


import edu.isi.karma.kr2rml.PredicateObjectMap;

import java.util.Collections;
import java.util.List;

public class SupportPredicateObjectMap {

    private SupportPredicate predicate;
    private List<SupportObjectMap> listObjectMap;
    private PredicateObjectMap predicateObjectMap;

    public SupportPredicateObjectMap() {}

    public SupportPredicateObjectMap(SupportPredicate predicate, List<SupportObjectMap> listObjectMap) {
        this.predicate = predicate;
        this.listObjectMap = listObjectMap;

    }

    public SupportPredicateObjectMap(PredicateObjectMap predObj) {
        this.predicate = new SupportPredicate(predObj.getPredicate());
        if(predObj.getObject() != null) {
            this.listObjectMap = Collections.singletonList(new SupportObjectMap(predObj.getObject()));
        }
        this.predicateObjectMap = predObj;
    }

    public SupportPredicate getPredicate() {
        return predicate;
    }

    public void setPredicate(SupportPredicate predicate) {
        this.predicate = predicate;
    }

    public List<SupportObjectMap> getListObjectMap() {
        return listObjectMap;
    }

    public void setListObjectMap(List<SupportObjectMap> listObjectMap) {
        this.listObjectMap = listObjectMap;
    }

    public PredicateObjectMap getPredicateObjectMap() {
        return predicateObjectMap;
    }

    public void setPredicateObjectMap(PredicateObjectMap predicateObjectMap) {
        this.predicateObjectMap = predicateObjectMap;
    }

    @Override
    public String toString() {
        return "SupportPredicateObjectMap{" +
                "predicate=" + ((predicate==null) ? "N/A": predicate.toString() ) +
                ", listObjectMap=" + ((listObjectMap==null) ? "N/A": listObjectMap.toString() ) +
                '}';
    }
}
