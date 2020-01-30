package ru.otus.homework.diyCollections;

import java.util.*;

public class DIYArrayList<T> implements List<T> {
    private static final int INITIAL_CAPACITY = 10;
    private int actualSize = 0;
    private Object[] array = new Object[INITIAL_CAPACITY];


    @Override
    public int size() {
        return actualSize;
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        Object[] returnArray = new Object[actualSize];
        System.arraycopy(array, 0, returnArray, 0, actualSize);
        return returnArray;

    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }


    private void checkIndex(int index) {
        if (index >= actualSize || index < 0) {
            throw new IndexOutOfBoundsException("Size of the DIYArrayList is " + actualSize + ". Index of the element " + index + " is out of the range");
        }
    }

    @Override
    public boolean add(T t) {
        growIfNeed();
        array[actualSize] = t;
        actualSize++;
        return true;
    }

    private void growIfNeed() {
        if (array.length == actualSize)
        array = Arrays.copyOf(array, array.length << 1);
    }


    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return (T) array[index];
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIterator<>() {
            private int indexOfCurrentElement = 0;
            private int lastIndex = 0;

            @Override
            public boolean hasNext() {
                throw new UnsupportedOperationException();
            }

            @Override
            public T next() {
                checkIndex(indexOfCurrentElement);
                lastIndex = indexOfCurrentElement;
                return (T) array[indexOfCurrentElement++];
            }

            @Override
            public boolean hasPrevious() {
                throw new UnsupportedOperationException();
            }

            @Override
            public T previous() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int nextIndex() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int previousIndex() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(T t) {
                array[lastIndex] = t;
            }

            @Override
            public void add(T t) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
