/**
 * Mengenang penderitaan saya dalam mengerjakan hal gila
 * Tapi tidak sesuai spec....
 */


package com.example.calculatingwombat.model.utils;

import java.util.HashMap;

@Deprecated
public class DoubleHashMap<K, V> extends HashMap<K, V> {
    private HashMap<V, K> reversed = new HashMap<V, K>();

    @Override
    public V put(K key, V value) {
        V v = super.put(key, value);
        reversed.put(value, key);
        return v;
    }

    @Override
    public V remove(Object key) {
        if (super.containsKey(key)) {
            V v = super.get(key);
            super.remove(key);

            reversed.values().remove(v);

            return v;
        } else {
            return null;
        }
    }

    public K getValue(V key) {
        return reversed.get(key);
    }
}