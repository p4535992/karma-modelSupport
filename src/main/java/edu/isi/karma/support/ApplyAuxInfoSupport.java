package edu.isi.karma.support;

import edu.isi.karma.kr2rml.mapping.KR2RMLMappingAuxillaryInformation;
import edu.isi.karma.webserver.KarmaException;

import java.io.File;

/**
 * Created by 4535992 on 30/11/2015.
 * @author 4535992.
 * @version 2015-11-30.
 */
public class ApplyAuxInfoSupport extends ApplyMappingSupport {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApplyAuxInfoSupport.class);

    protected ApplyAuxInfoSupport() {}

    private static ApplyAuxInfoSupport instance = null;

    public static ApplyAuxInfoSupport getInstance(){
        if(instance == null) {
            instance = new ApplyAuxInfoSupport();
        }
        return instance;
    }

    public void extractWorkSheetHistoryFromModel(String tableName,File r2rmlModelFile) throws KarmaException {
        KR2RMLMappingAuxillaryInformation auxInfo = mapping.getAuxInfo();

    }




}
