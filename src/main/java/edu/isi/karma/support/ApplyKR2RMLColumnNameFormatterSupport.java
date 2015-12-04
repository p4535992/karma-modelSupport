package edu.isi.karma.support;


import edu.isi.karma.webserver.KarmaException;

public class ApplyKR2RMLColumnNameFormatterSupport extends ApplyMappingSupport{

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApplyKR2RMLColumnNameFormatterSupport.class);

    protected ApplyKR2RMLColumnNameFormatterSupport() {
        if(isOffline  && !isSetted){
            try {
                setupKarmaMetadata();
            } catch (KarmaException e) {
                logger.error("Error occurred while initializing the Karma MetaData...");
                System.exit(-1);
            }
        }
    }

    protected  ApplyKR2RMLColumnNameFormatterSupport(boolean isOffline) {
        this.isOffline = isOffline;
        new ApplyKR2RMLColumnNameFormatterSupport();
    }

    private static  ApplyKR2RMLColumnNameFormatterSupport instance = null;

    public static  ApplyKR2RMLColumnNameFormatterSupport getInstance(){
        if(instance == null) {
            instance = new  ApplyKR2RMLColumnNameFormatterSupport();
        }
        return instance;
    }

    public static  ApplyKR2RMLColumnNameFormatterSupport getInstance(boolean isOffline){
        if(instance == null) {
            instance = new  ApplyKR2RMLColumnNameFormatterSupport(isOffline);
        }
        return instance;
    }


}
