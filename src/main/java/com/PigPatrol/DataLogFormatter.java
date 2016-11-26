package com.PigPatrol;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Created by Grant DeShazer on 3/16/16.
 *
 * Custom logger formatter.  Data recorded first
 * timestamp recorded second.
 *
 *  Message formatting is handled in Main loop.
 */
public class DataLogFormatter extends Formatter {

    @Override
    public String format(LogRecord record){
        String out;
        out = record.getMessage() + "\n";
        return out;
    }

}
