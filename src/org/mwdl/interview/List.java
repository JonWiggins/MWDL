package org.mwdl.interview;

public interface List<T> {

    public T get(int index);

    public void set(int index, T value);

    public int size();


}
