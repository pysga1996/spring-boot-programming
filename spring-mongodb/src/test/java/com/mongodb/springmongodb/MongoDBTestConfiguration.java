//package com.mongodb.springmongodb;
//
//import com.mongodb.MongoClientURI;
//import com.mongodb.WriteConcern;
//import com.mongodb.listeners.DataSeedingListener;
//import de.flapdoodle.embed.mongo.MongodExecutable;
//import de.flapdoodle.embed.mongo.MongodStarter;
//import de.flapdoodle.embed.mongo.config.IMongodConfig;
//import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
//import de.flapdoodle.embed.mongo.config.Net;
//import de.flapdoodle.embed.mongo.distribution.Version;
//import de.flapdoodle.embed.process.runtime.Network;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//
//@TestConfiguration
//public class MongoDBTestConfiguration implements InitializingBean, DisposableBean {
//    //public class QuickBuyTestConfiguration {
//    @MockBean
//    public DataSeedingListener dataSeedingListener;
//
//    public MongodExecutable executable;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        String host = "localhost";
//        int port = 27019;
//
//        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
//                .net(new Net(host, port, Network.localhostIsIPv6()))
//                .build();
//
//        MongodStarter starter = MongodStarter.getDefaultInstance();
//        executable = starter.prepare(mongodConfig);
//        executable.start();
//    }
//
//    @MockBean
//    public JwtAccessTokenConverter jwtAccessTokenConverter;
//
//
////    @Bean
////    @Primary
////    public MongoDbFactory factory() {
////        // also possible to connect to a remote or real MongoDB instance
////        return new SimpleMongoDbFactory(new MongoClientURI("mongodb+srv://pysga1996:1381996@cluster0-xidm2.gcp.mongodb.net/test?retryWrites=true&w=majority"));
////    }
////
////
////    @Bean
////    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
////        MongoTemplate template = new MongoTemplate(mongoDbFactory);
////        template.setWriteConcern(WriteConcern.ACKNOWLEDGED);
////        return template;
////    }
//
//    @Override
//    public void destroy() throws Exception {
//        executable.stop();
//    }
//}
