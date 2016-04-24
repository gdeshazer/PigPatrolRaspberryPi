package com.PigPatrol;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Created by gdeshazer on 3/16/16.
 *
 * Custom logger formatter.  Data recorded first
 * timestamp recorded second.
 *
 * More will be added to formatter in order to make
 * stored data more useful.  Currently the majority
 * of formatting is being handled elsewhere.
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
