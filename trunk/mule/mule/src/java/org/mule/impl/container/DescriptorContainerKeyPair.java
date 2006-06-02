/*
 * $Header: $
 * $Revision: $
 * $Date: $
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.impl.container;

/**
 * <code>ContainerKeyPair</code> is a key strategy that binds a contrainer
 * reference with a container name. This object isn't used directly by users,
 * but it is used when the the Mule xml configuration is processed
 *
 * @author <a href="mailto:ross.mason@symphonysoft.com">Ross Mason</a>
 * @version $Revision: 432 $
 */
public class DescriptorContainerKeyPair extends ContainerKeyPair
{
    private String descriptorName;

    public DescriptorContainerKeyPair(String descriptorName, Object key)
    {
        super(DescriptorContainerContext.DESCRIPTOR_CONTIANER_NAME, key);
        this.descriptorName = descriptorName;
    }

    public String getDescriptorName() {
        return descriptorName;
    }

    public String toFullString() {
        return "Descriptor Container Key{name=" + descriptorName + ", key=" + getKey().toString() +
                ", container=" + getContainerName() + ", required=" + isRequired() + "}";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final DescriptorContainerKeyPair that = (DescriptorContainerKeyPair) o;

        if (descriptorName != null ? !descriptorName.equals(that.descriptorName) : that.descriptorName != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (descriptorName != null ? descriptorName.hashCode() : 0);
        return result;
    }
}
