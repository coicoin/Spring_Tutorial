package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    boolean removeById(Integer bookIdToRemove);

    boolean removeByAuthor(String bookAuthorToRemove);

    boolean removeByTitle(String bookTitleToRemove);

    boolean removeBySize(Integer bookSizeToRemove);

    List<T> sortByObject(String sortObject, boolean isDesc);

}
