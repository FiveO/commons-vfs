/*
 * Copyright 2002-2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.vfs.util;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.impl.DecoratedFileObject;
import org.apache.commons.vfs.provider.AbstractFileObject;

/**
 * Stuff to get some strange things from an FileObject
 *
 * @author <a href="mailto:imario@apache.org">Mario Ivankovits</a>
 * @version $Revision$ $Date$
 */
public class FileObjectUtils
{
	private FileObjectUtils()
	{
	}

	/**
	 * get access to the base object even if decorated
	 */
	public static AbstractFileObject getAbstractFileObject(final FileObject fileObject) throws FileSystemException
	{
		Object searchObject = fileObject;
		while (searchObject instanceof DecoratedFileObject)
		{
			searchObject = ((DecoratedFileObject) searchObject).getDecoratedFileObject();
		}
		if (searchObject instanceof AbstractFileObject)
		{
			return (AbstractFileObject) searchObject;
		}
        if (searchObject == null)
        {
            return null;
        }

        throw new FileSystemException("vfs.util/find-abstract-file-object.error", fileObject==null?"null":fileObject.getClass().getName());
	}

	/**
	 * check if the given FileObject is instance of given class argument
	 */
	public static boolean isInstanceOf(final FileObject fileObject, final Class wantedClass) throws FileSystemException
	{
		Object searchObject = fileObject;
		while (searchObject instanceof DecoratedFileObject)
		{
			if (wantedClass.isInstance(searchObject))
			{
				return true;
			}

			searchObject = ((DecoratedFileObject) searchObject).getDecoratedFileObject();
		}

		if (wantedClass.isInstance(searchObject))
		{
			return true;
		}

		return false;
	}
}