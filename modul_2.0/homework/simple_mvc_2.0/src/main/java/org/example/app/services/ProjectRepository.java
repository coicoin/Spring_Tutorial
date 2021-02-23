package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    boolean removeById(Integer bookIdToRemove);

    boolean removeByAuthor(String removeItemByAuthor);

    boolean removeByTitle(String removeItemByTitle);

    boolean removeBySize(Integer removeItemBySize);

}
