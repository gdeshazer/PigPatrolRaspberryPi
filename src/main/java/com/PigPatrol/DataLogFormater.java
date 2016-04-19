package com.PigPatrol;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Created by grantdeshazer on 3/16/16.
 *
 * Custom logger formater.  Data recorded first
 * timestamp recorded second.
 *
 * This will need to be changed latter in order to accomadate
 * sensor position.  Likely the arduino will send the actual
 * sensor position.
 *
 */
public class DataLogFormater extends Formatter {

    @Override
    public String format(LogRecord record){
        String out;
        out = record.getMessage() + "\n";
        return out;
    }

}
