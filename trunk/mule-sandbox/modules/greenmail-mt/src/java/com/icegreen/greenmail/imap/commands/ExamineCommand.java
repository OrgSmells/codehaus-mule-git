/* -------------------------------------------------------------------
 * Copyright (c) 2006 Wael Chatila / Icegreen Technologies. All Rights Reserved.
 * This software is released under the LGPL which is available at http://www.gnu.org/copyleft/lesser.html
 * This file has been modified by the copyright holder. Original file can be found at http://james.apache.org
 * -------------------------------------------------------------------
 */
package com.icegreen.greenmail.imap.commands;

/**
 * @author Darrell DeBoer <darrell@apache.org>
 * @version $Revision: 2860 $
 */
class ExamineCommand extends SelectCommand {
    public static final String NAME = "EXAMINE";

    public String getName() {
        return NAME;
    }
}
