package com.hmhcho.api.grading.dao.readwrite;

import java.util.ArrayList;
import java.util.List;
import org.dbunit.operation.DatabaseOperation;
/**
 * Created by srikanthk on 4/28/17.
 */
public class DbUnitTestConfiguration {

    private List<String> clearTables;

    private List<FilenameDatabaseOperation> filenameDatabaseOperations;
    public DbUnitTestConfiguration() {
        filenameDatabaseOperations = new ArrayList<FilenameDatabaseOperation>();
        clearTables = new ArrayList<String>();
    }

    public List<FilenameDatabaseOperation> getFilenameDatabaseOperations() {
        return filenameDatabaseOperations;
    }

    public List<String> getAfterTestClearTables() {
        return this.clearTables;
    }




    public FilenameDatabaseOperation refreshData(String filename)  {
        FilenameDatabaseOperation fileOp = new FilenameDatabaseOperation(filename, DatabaseOperation.REFRESH);
        filenameDatabaseOperations.add(fileOp);
        return fileOp;
    }

    public FilenameDatabaseOperation cleanInsertData(String filename)  {
        FilenameDatabaseOperation fileOp = new FilenameDatabaseOperation(filename, DatabaseOperation.CLEAN_INSERT);
        filenameDatabaseOperations.add(fileOp);
        return fileOp;
    }

    public void afterTestsClearTable(String tableName) {
        clearTables.add(tableName);
    }

}
