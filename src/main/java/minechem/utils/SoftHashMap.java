package minechem.utils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

// code copied from java.util.WeakHashMap
public class SoftHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>
{

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    Entry<K, V>[] table;
    private int size;
    private int threshold;
    private final float loadFactor;
    private final ReferenceQueue<Object> queue = new ReferenceQueue<Object>();
    int modCount;
    static final int ALTERNATIVE_HASHING_THRESHOLD_DEFAULT = Integer.MAX_VALUE;

    private static class Holder
    {

        static final int ALTERNATIVE_HASHING_THRESHOLD;

        static
        {
            String altThreshold = java.security.AccessController
                    .doPrivileged(new sun.security.action.GetPropertyAction(
                                    "jdk.map.althashing.threshold"));

            int threshold;
            try
            {
                threshold = (null != altThreshold) ? Integer
                        .parseInt(altThreshold)
                        : ALTERNATIVE_HASHING_THRESHOLD_DEFAULT;

                if (threshold == -1)
                {
                    threshold = Integer.MAX_VALUE;
                }

                if (threshold < 0)
                {
                    throw new IllegalArgumentException(
                            "value must be positive integer.");
                }
            } catch (IllegalArgumentException failed)
            {
                throw new Error(
                        "Illegal value for 'jdk.map.althashing.threshold'", failed);
            }
            ALTERNATIVE_HASHING_THRESHOLD = threshold;
        }
    }

    transient boolean useAltHashing;

    transient final int hashSeed = sun.misc.Hashing.randomHashSeed(this);

    private Entry<K, V>[] newTable(int n)
    {
        return new Entry[n];
    }

    public SoftHashMap(int initialCapacity, float loadFactor)
    {
        if (initialCapacity < 0)
        {
            throw new IllegalArgumentException("Illegal Initial Capacity: "
                    + initialCapacity);
        }
        if (initialCapacity > MAXIMUM_CAPACITY)
        {
            initialCapacity = MAXIMUM_CAPACITY;
        }

        if ((loadFactor <= 0) || Float.isNaN(loadFactor))
        {
            throw new IllegalArgumentException("Illegal Load factor: "
                    + loadFactor);
        }
        int capacity = 1;
        while (capacity < initialCapacity)
        {
            capacity <<= 1;
        }
        table = newTable(capacity);
        this.loadFactor = loadFactor;
        threshold = (int) (capacity * loadFactor);
        useAltHashing = sun.misc.VM.isBooted()
                && (capacity >= Holder.ALTERNATIVE_HASHING_THRESHOLD);
    }

    public SoftHashMap(int initialCapacity)
    {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public SoftHashMap()
    {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public SoftHashMap(Map<? extends K, ? extends V> m)
    {
        this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1,
                DEFAULT_INITIAL_CAPACITY), DEFAULT_LOAD_FACTOR);
        putAll(m);
    }

    private static final Object NULL_KEY = new Object();

    private static Object maskNull(Object key)
    {
        return (key == null) ? NULL_KEY : key;
    }

    static Object unmaskNull(Object key)
    {
        return (key == NULL_KEY) ? null : key;
    }

    private static boolean eq(Object x, Object y)
    {
        return (x == y) || x.equals(y);
    }

    int hash(Object k)
    {

        int h;
        if (useAltHashing)
        {
            h = hashSeed;
            if (k instanceof String)
            {
                return sun.misc.Hashing.stringHash32((String) k);
            } else
            {
                h ^= k.hashCode();
            }
        } else
        {
            h = k.hashCode();
        }

        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    private static int indexFor(int h, int length)
    {
        return h & (length - 1);
    }

    private void expungeStaleEntries()
    {
        for (Object x; (x = queue.poll()) != null;)
        {
            synchronized (queue)
            {
                Entry<K, V> e = (Entry<K, V>) x;
                int i = indexFor(e.hash, table.length);

                Entry<K, V> prev = table[i];
                Entry<K, V> p = prev;
                while (p != null)
                {
                    Entry<K, V> next = p.next;
                    if (p == e)
                    {
                        if (prev == e)
                        {
                            table[i] = next;
                        } else
                        {
                            prev.next = next;
                        }
                        e.value = null;
                        size--;
                        break;
                    }
                    prev = p;
                    p = next;
                }
            }
        }
    }

    private Entry<K, V>[] getTable()
    {
        expungeStaleEntries();
        return table;
    }

    @Override
    public int size()
    {
        if (size == 0)
        {
            return 0;
        }
        expungeStaleEntries();
        return size;
    }

    @Override
    public boolean isEmpty()
    {
        return size() == 0;
    }

    @Override
    public V get(Object key)
    {
        Object k = maskNull(key);
        int h = hash(k);
        Entry<K, V>[] tab = getTable();
        int index = indexFor(h, tab.length);
        Entry<K, V> e = tab[index];
        while (e != null)
        {
            if ((e.hash == h) && eq(k, e.get()))
            {
                return e.value;
            }
            e = e.next;
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key)
    {
        return getEntry(key) != null;
    }

    Entry<K, V> getEntry(Object key)
    {
        Object k = maskNull(key);
        int h = hash(k);
        Entry<K, V>[] tab = getTable();
        int index = indexFor(h, tab.length);
        Entry<K, V> e = tab[index];
        while ((e != null) && !((e.hash == h) && eq(k, e.get())))
        {
            e = e.next;
        }
        return e;
    }

    @Override
    public V put(K key, V value)
    {
        Object k = maskNull(key);
        int h = hash(k);
        Entry<K, V>[] tab = getTable();
        int i = indexFor(h, tab.length);

        for (Entry<K, V> e = tab[i]; e != null; e = e.next)
        {
            if ((h == e.hash) && eq(k, e.get()))
            {
                V oldValue = e.value;
                if (value != oldValue)
                {
                    e.value = value;
                }
                return oldValue;
            }
        }

        modCount++;
        Entry<K, V> e = tab[i];
        tab[i] = new Entry<K, V>(k, value, queue, h, e);
        if (++size >= threshold)
        {
            resize(tab.length * 2);
        }
        return null;
    }

    void resize(int newCapacity)
    {
        Entry<K, V>[] oldTable = getTable();
        int oldCapacity = oldTable.length;
        if (oldCapacity == MAXIMUM_CAPACITY)
        {
            threshold = Integer.MAX_VALUE;
            return;
        }

        Entry<K, V>[] newTable = newTable(newCapacity);
        boolean oldAltHashing = useAltHashing;
        useAltHashing |= sun.misc.VM.isBooted()
                && (newCapacity >= Holder.ALTERNATIVE_HASHING_THRESHOLD);
        boolean rehash = oldAltHashing ^ useAltHashing;
        transfer(oldTable, newTable, rehash);
        table = newTable;

        if (size >= (threshold / 2))
        {
            threshold = (int) (newCapacity * loadFactor);
        } else
        {
            expungeStaleEntries();
            transfer(newTable, oldTable, false);
            table = oldTable;
        }
    }

    private void transfer(Entry<K, V>[] src, Entry<K, V>[] dest, boolean rehash)
    {
        for (int j = 0; j < src.length; ++j)
        {
            Entry<K, V> e = src[j];
            src[j] = null;
            while (e != null)
            {
                Entry<K, V> next = e.next;
                Object key = e.get();
                if (key == null)
                {
                    e.next = null;
                    e.value = null;
                    size--;
                } else
                {
                    if (rehash)
                    {
                        e.hash = hash(key);
                    }
                    int i = indexFor(e.hash, dest.length);
                    e.next = dest[i];
                    dest[i] = e;
                }
                e = next;
            }
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        int numKeysToBeAdded = m.size();
        if (numKeysToBeAdded == 0)
        {
            return;
        }

        if (numKeysToBeAdded > threshold)
        {
            int targetCapacity = (int) ((numKeysToBeAdded / loadFactor) + 1);
            if (targetCapacity > MAXIMUM_CAPACITY)
            {
                targetCapacity = MAXIMUM_CAPACITY;
            }
            int newCapacity = table.length;
            while (newCapacity < targetCapacity)
            {
                newCapacity <<= 1;
            }
            if (newCapacity > table.length)
            {
                resize(newCapacity);
            }
        }

        for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
        {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public V remove(Object key)
    {
        Object k = maskNull(key);
        int h = hash(k);
        Entry<K, V>[] tab = getTable();
        int i = indexFor(h, tab.length);
        Entry<K, V> prev = tab[i];
        Entry<K, V> e = prev;

        while (e != null)
        {
            Entry<K, V> next = e.next;
            if ((h == e.hash) && eq(k, e.get()))
            {
                modCount++;
                size--;
                if (prev == e)
                {
                    tab[i] = next;
                } else
                {
                    prev.next = next;
                }
                return e.value;
            }
            prev = e;
            e = next;
        }

        return null;
    }

    boolean removeMapping(Object o)
    {
        if (!(o instanceof Map.Entry))
        {
            return false;
        }
        Entry<K, V>[] tab = getTable();
        Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
        Object k = maskNull(entry.getKey());
        int h = hash(k);
        int i = indexFor(h, tab.length);
        Entry<K, V> prev = tab[i];
        Entry<K, V> e = prev;

        while (e != null)
        {
            Entry<K, V> next = e.next;
            if ((h == e.hash) && e.equals(entry))
            {
                modCount++;
                size--;
                if (prev == e)
                {
                    tab[i] = next;
                } else
                {
                    prev.next = next;
                }
                return true;
            }
            prev = e;
            e = next;
        }

        return false;
    }

    @Override
    public void clear()
    {
        while (queue.poll() != null)
        {
        }

        modCount++;
        Arrays.fill(table, null);
        size = 0;

        while (queue.poll() != null)
        {
        }
    }

    @Override
    public boolean containsValue(Object value)
    {
        if (value == null)
        {
            return containsNullValue();
        }

        Entry<K, V>[] tab = getTable();
        for (int i = tab.length; i-- > 0;)
        {
            for (Entry<K, V> e = tab[i]; e != null; e = e.next)
            {
                if (value.equals(e.value))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean containsNullValue()
    {
        Entry<K, V>[] tab = getTable();
        for (int i = tab.length; i-- > 0;)
        {
            for (Entry<K, V> e = tab[i]; e != null; e = e.next)
            {
                if (e.value == null)
                {
                    return true;
                }
            }
        }
        return false;
    }

    private static class Entry<K, V> extends SoftReference<Object> implements
            Map.Entry<K, V>
    {

        V value;
        int hash;
        Entry<K, V> next;

        Entry(Object key, V value, ReferenceQueue<Object> queue, int hash,
                Entry<K, V> next)
        {
            super(key, queue);
            this.value = value;
            this.hash = hash;
            this.next = next;
        }

        @Override
        public K getKey()
        {
            return (K) SoftHashMap.unmaskNull(get());
        }

        @Override
        public V getValue()
        {
            return value;
        }

        @Override
        public V setValue(V newValue)
        {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        @Override
        public boolean equals(Object o)
        {
            if (!(o instanceof Map.Entry))
            {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            K k1 = getKey();
            Object k2 = e.getKey();
            if ((k1 == k2) || ((k1 != null) && k1.equals(k2)))
            {
                V v1 = getValue();
                Object v2 = e.getValue();
                if ((v1 == v2) || ((v1 != null) && v1.equals(v2)))
                {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode()
        {
            K k = getKey();
            V v = getValue();
            return ((k == null ? 0 : k.hashCode()) ^ (v == null ? 0 : v
                    .hashCode()));
        }

        @Override
        public String toString()
        {
            return getKey() + "=" + getValue();
        }
    }

    private abstract class HashIterator<T> implements Iterator<T>
    {

        private int index;
        private Entry<K, V> entry = null;
        private Entry<K, V> lastReturned = null;
        private int expectedModCount = modCount;

        private Object nextKey = null;

        private Object currentKey = null;

        HashIterator()
        {
            index = isEmpty() ? 0 : table.length;
        }

        @Override
        public boolean hasNext()
        {
            Entry<K, V>[] t = table;

            while (nextKey == null)
            {
                Entry<K, V> e = entry;
                int i = index;
                while ((e == null) && (i > 0))
                {
                    e = t[--i];
                }
                entry = e;
                index = i;
                if (e == null)
                {
                    currentKey = null;
                    return false;
                }
                nextKey = e.get();
                if (nextKey == null)
                {
                    entry = entry.next;
                }
            }
            return true;
        }

        protected Entry<K, V> nextEntry()
        {
            if (modCount != expectedModCount)
            {
                throw new ConcurrentModificationException();
            }
            if ((nextKey == null) && !hasNext())
            {
                throw new NoSuchElementException();
            }

            lastReturned = entry;
            entry = entry.next;
            currentKey = nextKey;
            nextKey = null;
            return lastReturned;
        }

        @Override
        public void remove()
        {
            if (lastReturned == null)
            {
                throw new IllegalStateException();
            }
            if (modCount != expectedModCount)
            {
                throw new ConcurrentModificationException();
            }

            SoftHashMap.this.remove(currentKey);
            expectedModCount = modCount;
            lastReturned = null;
            currentKey = null;
        }

    }

    private class ValueIterator extends HashIterator<V>
    {

        @Override
        public V next()
        {
            return nextEntry().value;
        }
    }

    private class KeyIterator extends HashIterator<K>
    {

        @Override
        public K next()
        {
            return nextEntry().getKey();
        }
    }

    private class EntryIterator extends HashIterator<Map.Entry<K, V>>
    {

        @Override
        public Map.Entry<K, V> next()
        {
            return nextEntry();
        }
    }

    private transient Set<Map.Entry<K, V>> entrySet = null;

    private KeySet keySet;

    private Values values;

    @Override
    public Set<K> keySet()
    {
        return (keySet != null ? keySet : (keySet = new KeySet()));
    }

    private class KeySet extends AbstractSet<K>
    {

        @Override
        public Iterator<K> iterator()
        {
            return new KeyIterator();
        }

        @Override
        public int size()
        {
            return SoftHashMap.this.size();
        }

        @Override
        public boolean contains(Object o)
        {
            return containsKey(o);
        }

        @Override
        public boolean remove(Object o)
        {
            if (containsKey(o))
            {
                SoftHashMap.this.remove(o);
                return true;
            } else
            {
                return false;
            }
        }

        @Override
        public void clear()
        {
            SoftHashMap.this.clear();
        }
    }

    @Override
    public Collection<V> values()
    {
        return (values != null) ? values : (values = new Values());
    }

    private class Values extends AbstractCollection<V>
    {

        @Override
        public Iterator<V> iterator()
        {
            return new ValueIterator();
        }

        @Override
        public int size()
        {
            return SoftHashMap.this.size();
        }

        @Override
        public boolean contains(Object o)
        {
            return containsValue(o);
        }

        @Override
        public void clear()
        {
            SoftHashMap.this.clear();
        }
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet()
    {
        Set<Map.Entry<K, V>> es = entrySet;
        return es != null ? es : (entrySet = new EntrySet());
    }

    private class EntrySet extends AbstractSet<Map.Entry<K, V>>
    {

        @Override
        public Iterator<Map.Entry<K, V>> iterator()
        {
            return new EntryIterator();
        }

        @Override
        public boolean contains(Object o)
        {
            if (!(o instanceof Map.Entry))
            {
                return false;
            }
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            Entry<K, V> candidate = getEntry(e.getKey());
            return (candidate != null) && candidate.equals(e);
        }

        @Override
        public boolean remove(Object o)
        {
            return removeMapping(o);
        }

        @Override
        public int size()
        {
            return SoftHashMap.this.size();
        }

        @Override
        public void clear()
        {
            SoftHashMap.this.clear();
        }

        private List<Map.Entry<K, V>> deepCopy()
        {
            List<Map.Entry<K, V>> list = new ArrayList<Map.Entry<K, V>>(size());
            for (Map.Entry<K, V> e : this)
            {
                list.add(new AbstractMap.SimpleEntry<K, V>(e));
            }
            return list;
        }

        @Override
        public Object[] toArray()
        {
            return deepCopy().toArray();
        }

        @Override
        public <T> T[] toArray(T[] a)
        {
            return deepCopy().toArray(a);
        }
    }
}
