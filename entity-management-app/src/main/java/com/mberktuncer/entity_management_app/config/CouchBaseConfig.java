package com.mberktuncer.entity_management_app.config;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.auditing.EnableCouchbaseAuditing;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@RequiredArgsConstructor
@EnableCouchbaseRepositories(basePackages = {"com.mberktuncer.entity_management_app.repository"})
@EnableTransactionManagement
@EnableCouchbaseAuditing
public class CouchBaseConfig extends AbstractCouchbaseConfiguration {

    @Value("${spring.datasource.couchbase.host}")
    private String host;

    @Value("${spring.datasource.couchbase.bucket.username}")
    private String userName;

    @Value("${spring.datasource.couchbase.bucket.password}")
    private String password;

    @Value("${spring.datasource.couchbase.bucket.name}")
    private String bucketName;

    public static Cluster cluster;

    @PostConstruct
    void init(){
        cluster = Cluster.connect(
                getConnectionString(),
                getUserName(),
                getPassword()
        );
    }

    @Override
    public String getConnectionString() {
        return host;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getBucketName() {
        return bucketName;
    }

    @Bean
    public Bucket getBucket(){
        return cluster.bucket(bucketName);
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    public String typeKey(){
        return "type";
    }

}
