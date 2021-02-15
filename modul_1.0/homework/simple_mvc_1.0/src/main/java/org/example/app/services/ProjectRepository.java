package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T item);

    boolean removeById(Integer itemIdToRemove);

    boolean removeByAuthor(String bookAuthorToRemove);

    boolean removeByTitle(String bookTitleToRemove);

    boolean removeBySize(Integer removeBookBySize);
}
