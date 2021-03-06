/**
 * Copyright (c) 2002-2014 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.kernel.impl.transaction.xaframework.log.entry;

import java.io.IOException;

import org.neo4j.kernel.impl.nioneo.xa.command.LogHandler;

import static org.neo4j.kernel.impl.transaction.xaframework.log.entry.LogEntryByteCodes.COMMAND;
import static org.neo4j.kernel.impl.transaction.xaframework.log.entry.LogEntryVersions.CURRENT_LOG_ENTRY_VERSION;

public class LogEntryCommand extends LogEntry
{
    private final org.neo4j.kernel.impl.nioneo.xa.command.Command command;

    public LogEntryCommand( org.neo4j.kernel.impl.nioneo.xa.command.Command command )
    {
        this( CURRENT_LOG_ENTRY_VERSION, command );
    }

    public LogEntryCommand( byte version, org.neo4j.kernel.impl.nioneo.xa.command.Command command )
    {
        super( COMMAND, version );
        this.command = command;
    }

    public org.neo4j.kernel.impl.nioneo.xa.command.Command getXaCommand()
    {
        return command;
    }

    @Override
    public String toString()
    {
        return "Command[" + command + "]";
    }

    @Override
    public void accept( LogHandler handler ) throws IOException
    {
        handler.commandEntry( this );
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        LogEntryCommand command1 = (LogEntryCommand) o;

        if ( !command.equals( command1.command ) )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        return command.hashCode();
    }
}
