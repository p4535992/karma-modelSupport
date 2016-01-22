package edu.isi.karma.supportObject;


<<<<<<< HEAD
=======
import edu.isi.karma.kr2rml.PredicateObjectMap;

import java.util.Collections;
>>>>>>> origin/master
import java.util.List;

public class SupportPredicateObjectMap {

    private SupportPredicate predicate;
    private List<SupportObjectMap> listObjectMap;
<<<<<<< HEAD
=======
    private PredicateObjectMap predicateObjectMap;
>>>>>>> origin/master

    public SupportPredicateObjectMap() {}

    public SupportPredicateObjectMap(SupportPredicate predicate, List<SupportObjectMap> listObjectMap) {
        this.predicate = predicate;
        this.listObjectMap = listObjectMap;
<<<<<<< HEAD
=======

    }

    public SupportPredicateObjectMap(PredicateObjectMap predObj) {
        this.predicate = new SupportPredicate(predObj.getPredicate());
        if(predObj.getObject() != null) {
            this.listObjectMap = Collections.singletonList(new SupportObjectMap(predObj.getObject()));
        }
        this.predicateObjectMap = predObj;
>>>>>>> origin/master
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

<<<<<<< HEAD
=======
    public PredicateObjectMap getPredicateObjectMap() {
        return predicateObjectMap;
    }

    public void setPredicateObjectMap(PredicateObjectMap predicateObjectMap) {
        this.predicateObjectMap = predicateObjectMap;
    }

>>>>>>> origin/master
    @Override
    public String toString() {
        return "SupportPredicateObjectMap{" +
                "predicate=" + ((predicate==null) ? "N/A": predicate.toString() ) +
                ", listObjectMap=" + ((listObjectMap==null) ? "N/A": listObjectMap.toString() ) +
                '}';
    }
}
