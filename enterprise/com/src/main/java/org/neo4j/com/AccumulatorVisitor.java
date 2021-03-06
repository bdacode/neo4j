/**
 * Copyright (c) 2002-2014 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.com;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.neo4j.helpers.Predicate;
import org.neo4j.helpers.Predicates;
import org.neo4j.helpers.collection.Visitor;

public class AccumulatorVisitor<T> implements Visitor<T, IOException>
{
    private final List<T> accumulator = new LinkedList<>();
    private final Predicate<T> include;

    public AccumulatorVisitor()
    {
        this( Predicates.<T>TRUE() );
    }

    public AccumulatorVisitor( Predicate<T> include )
    {
        this.include = include;
    }

    @Override
    public boolean visit( T element ) throws IOException
    {
        if ( !include.accept( element ) )
        {
            return false;
        }
        return accumulator.add( element );
    }

    public List<T> getAccumulator()
    {
        return accumulator;
    }
}