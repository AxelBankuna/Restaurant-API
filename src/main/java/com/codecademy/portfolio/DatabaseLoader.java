//package com.codecademy.portfolio;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.Resource;
//import org.springframework.jdbc.datasource.init.DataSourceInitializer;
//import org.springframework.jdbc.datasource.init.DatabasePopulator;
//import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//
//@Component
//public class DatabaseLoader implements CommandLineRunner {
//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    private Resource schemaScript;
//
//    @Autowired
//    private Resource dataScript;
//
//    @Bean
//    public DataSourceInitializer dataSourceInitializer() {
//        DataSourceInitializer initializer = new DataSourceInitializer();
//        initializer.setDataSource(dataSource);
//        initializer.setDatabasePopulator(databasePopulator());
//        return initializer;
//    }
//
//    private DatabasePopulator databasePopulator() {
//        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//        populator.addScript(schemaScript);
//        populator.addScript(dataScript);
//        return populator;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        dataSourceInitializer().init();
//    }
//}
//
