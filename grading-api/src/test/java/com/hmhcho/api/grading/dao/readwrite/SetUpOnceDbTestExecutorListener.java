package com.hmhcho.api.grading.dao.readwrite;

import net.logstash.logback.encoder.org.apache.commons.lang.WordUtils;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

/**
 * Created by srikanthk on 4/28/17.
 */
public class SetUpOnceDbTestExecutorListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {

        doDatabaseSetupTeardown(testContext, true);
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {

        doDatabaseSetupTeardown(testContext, false);
    }

    private void doDatabaseSetupTeardown(TestContext testContext, boolean isSetup) throws Exception {
        // Initialize Database Connection
        DataSource dataSource = (DataSource) testContext.getApplicationContext().getBean("readWriteDatasource");
        IDatabaseConnection connection = new DatabaseDataSourceConnection(dataSource);
        DatabaseConfig config = connection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new org.dbunit.ext.postgresql.PostgresqlDataTypeFactory());

        // Initialize DataSet
        String dataSetBeanName = WordUtils.uncapitalize(testContext.getTestClass().getSimpleName()) + "Config";

        try {
            DbUnitTestConfiguration testConfig = (DbUnitTestConfiguration) testContext.getApplicationContext().getBean(dataSetBeanName);
            List<FilenameDatabaseOperation> operationList = testConfig.getFilenameDatabaseOperations();

            if (!isSetup) {
                // Delete all from these tables
                ClearDataFromTables(connection.getConnection(), testConfig.getAfterTestClearTables());

                // Clear down XML file inserts in reverse order
                Collections.reverse(operationList);
            }

            for (FilenameDatabaseOperation fileOperation : operationList) {
                IDataSet dataSet = getDataSet(fileOperation.getFilename());
                try {
                    if(isSetup)
                        fileOperation.getDatabaseOperation().execute(connection, dataSet);
                    else
                        fileOperation.getDatabaseTeardown().execute(connection, dataSet);
                } catch(Exception e) {
                    if(e.getCause()!=null && e.getCause().getMessage().contains("duplicate")) {
                        //do nothing
                    } else {
                        throw e;
                    }

                }
            }
        } finally {
            connection.close();
        }
    }


    private void ClearDataFromTables(Connection connection, List<String> tableNames) throws SQLException {

        if (tableNames.size() == 0)
            return;

        for(String tableName : tableNames) {

            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM " + tableName);
            statement.close();
        }

    }

    public IDataSet getDataSet(String fileName) throws Exception {
        return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResource(fileName));
    }

}
