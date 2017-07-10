package com.hmhcho.api.grading.dao.readwrite;

/**
 * Created by srikanthk on 4/28/17.
 */

import org.dbunit.operation.DatabaseOperation;

public class FilenameDatabaseOperation {


    private String filename;
    private DatabaseOperation operation;
    private DatabaseOperation teardownOperation;

    public FilenameDatabaseOperation(String filename)  {
        this(filename, DatabaseOperation.REFRESH);
    }

    public FilenameDatabaseOperation(String filename, DatabaseOperation operation) {
        this(filename, operation, DatabaseOperation.NONE);
    }

    public FilenameDatabaseOperation(String filename, DatabaseOperation operation, DatabaseOperation teardownOperation) {
        this.filename = filename;
        this.operation = operation;
        this.teardownOperation = teardownOperation;
    }

    public String getFilename() {
        return this.filename;
    }

    public DatabaseOperation getDatabaseOperation() {
        return this.operation;
    }

    public DatabaseOperation getDatabaseTeardown() {
        return this.teardownOperation;
    }

    public void thenDeleteAfter() {
        this.teardownOperation = DatabaseOperation.DELETE;
    }

    public void thenDeleteAllAfter() {
        this.teardownOperation = DatabaseOperation.DELETE_ALL;
    }
}
