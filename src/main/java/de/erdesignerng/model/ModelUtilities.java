/**
 * Mogwai ERDesigner. Copyright (C) 2002 The Mogwai Project.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package de.erdesignerng.model;

import java.util.UUID;

import de.erdesignerng.dialect.Dialect;
import de.erdesignerng.exception.ElementAlreadyExistsException;
import de.erdesignerng.exception.ElementInvalidNameException;

/**
 * @author $Author: mirkosertic $
 * @version $Date: 2008-01-17 19:34:29 $
 */
public final class ModelUtilities {

    private ModelUtilities() {
    }

    /**
     * Check existance in a list.
     * 
     * @param aVector the list
     * @param aName the name
     * @param aDialect  the dialect
     * @throws ElementAlreadyExistsException is thrown if name exists
     */
    public static void checkExistance(ModelItemVector aVector, String aName, Dialect aDialect)
            throws ElementAlreadyExistsException {

        if (aVector.elementExists(aName, aDialect.isCaseSensitive())) {
            throw new ElementAlreadyExistsException(aName);
        }

    }

    /**
     * Check the name of an item and its existance in a list.
     * 
     * @param aVector the list
     * @param aItem the item
     * @param aDialect the dialect
     * @throws ElementInvalidNameException is thrown in case of an error
     * @throws ElementAlreadyExistsException is thrown in case of an error
     */
    public static void checkNameAndExistance(ModelItemVector aVector, OwnedModelItem aItem, Dialect aDialect)
            throws ElementInvalidNameException, ElementAlreadyExistsException {

        aItem.setName(aDialect.checkName(aItem.getName()));

        checkExistance(aVector, aItem.getName(), aDialect);
    }

    /**
     * Find the model properties.
     * 
     * @param aItem
     *            the item to start searching at
     * @return the found model history.
     */
    public static Dialect getModelProperties(OwnedModelItem aItem) {

        Object theOwner = aItem.getOwner();
        if (theOwner instanceof Model) {
            return ((Model) theOwner).getDialect();
        }

        if (theOwner instanceof OwnedModelItem) {
            return getModelProperties((OwnedModelItem) theOwner);
        }

        return null;
    }

    /**
     * Create a unique system id.
     * 
     * @param aObject the object the id shall be created for
     * @return the newly created id
     */
    public static String createSystemIdFor(Object aObject) {
        return UUID.randomUUID().toString();
    }
}