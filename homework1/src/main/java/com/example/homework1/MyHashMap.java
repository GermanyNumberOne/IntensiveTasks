package com.example.homework1;

import java.util.Arrays;

public class MyHashMap<K,V> {

    private final int DEFAULT_CAPACITY = 10;

    private int size;

    static class Node<K,V>{
        final int hash;
        final K key;
        final V value;
        Node<K,V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private int hashcode(Object key){
        return key == null ? 0 : key.hashCode();
    }

    private Node<K,V>[] tab;

    public V get(K k){
        return Arrays.stream(tab).filter(x -> (x.key.equals(k) && x.hash == hashcode(k))).findFirst().get().value;
    }


    public V remove(K k){
        V v = null;
        int hashcode = hashcode(k);
        boolean swipe = false;
        for (int i = 0 ; i < tab.length; i++){
            if(tab[i] != null && tab[i].hash == hashcode && tab[i].key.equals(k) && swipe == false){
                v = tab[i].value;
                tab[i] = null;
                if(i + 1 < size)swipe = true;
                else break;
            }
            if(swipe = true && i + 1 < tab.length && tab[i + 1] != null){
                Node<K, V> temp = tab[i + 1];
                tab[i] = temp;
                tab[i+1] = null;
                continue;
            }
        }
        size--;
        return v;
    }

    public V put(K k, V v){
        int hash = hashcode(k);
        if (tab == null) {
            tab = createNewTab(DEFAULT_CAPACITY);
            tab[0] = newNode(hash, k, v, null);
            return v;
        }

        if(size + 1 > tab.length){
            Node<K, V>[] temp = tab;
            tabCopy(temp, (int)(size*1.5));
        }
        Node<K, V> temp = null;
        for (int i = 0; i < tab.length; i++){
            if (temp != null){
                Node<K, V> e = temp;
                temp = tab[i];
                tab[i] = e;
                continue;
            }
            if(tab[i].hash > hash){
                continue;
            }

            if (tab[i].hash == hash){
                tab[i].next = newNode(hash, k, v, null);
                continue;
            }

            temp = tab[i];
            tab[i] = newNode(hash, k, v, null);
        }
        size++;
        return v;
    }

    private Node<K, V>[] tabCopy(Node<K, V>[] nodes, int newSize){
        return Arrays.copyOf(nodes, newSize);
    }

    private Node<K, V>[] createNewTab(int size){
        return (Node<K, V>[])new Node[size];
    }

    private Node<K, V> newNode(int hash, K k, V v, Node<K, V> next){
        return new Node<>(hash, k, v, next);
    }
}
