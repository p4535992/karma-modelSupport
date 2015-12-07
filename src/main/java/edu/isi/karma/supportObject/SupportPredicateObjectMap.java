package edu.isi.karma.supportObject;


import java.util.List;

public class SupportPredicateObjectMap {

    private SupportPredicate predicate;
    private List<SupportObjectMap> listObjectMap;

    public SupportPredicateObjectMap() {}

    public SupportPredicateObjectMap(SupportPredicate predicate, List<SupportObjectMap> listObjectMap) {
        this.predicate = predicate;
        this.listObjectMap = listObjectMap;
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

    @Override
    public String toString() {
        return "SupportPredicateObjectMap{" +
                "predicate=" + ((predicate==null) ? "N/A": predicate.toString() ) +
                ", listObjectMap=" + ((listObjectMap==null) ? "N/A": listObjectMap.toString() ) +
                '}';
    }
}
