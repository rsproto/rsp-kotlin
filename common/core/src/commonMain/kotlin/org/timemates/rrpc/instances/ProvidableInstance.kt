package org.timemates.rrpc.instances

/**
 * A contract for classes that can provide instances based on a specified key.
 */
public interface ProvidableInstance {
    public val key: Key<*>

    public interface Key<TInstance : ProvidableInstance>
}