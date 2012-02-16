dataSource {
    pooled = true
//	driverClassName = "org.h2.Driver"
//	username = "sa"
//	password = ""
    driverClassName = "com.mysql.jdbc.Driver"
    username = "sieym"
    password = "sieyM1s"
	properties {
		maxActive = 50
		maxIdle = 25
		minIdle = 5
		initialSize = 5
		numTestsPerEvictionRun = 3
		testOnBorrow = true
		testWhileIdle = true
		testOnReturn = true
		validationQuery="SELECT 1 from dual"
		minEvictableIdleTimeMillis = 1800000
		timeBetweenEvictionRunsMillis = 1800000
	}
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
    //dialect = 'org.hibernate.dialect.Oracle10gDialect';
}
// environment specific settings
environments {
    development {
        dataSource {
//            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
//            url = "jdbc:h2:mem:devDb"
            dbCreate = "update" // one of 'create', 'create-drop','update'
             url = "jdbc:mysql://localhost/sieym?autoReconnect=true&createDatabaseIfNotExist=true"
        }
    }
    test {
        dataSource {
//            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
//            url = "jdbc:h2:mem:devDb"
            dbCreate = "update" // one of 'create', 'create-drop','update'
             url = "jdbc:mysql://localhost/sieym?autoReconnect=true"
        }
    }
    production {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
             url = "jdbc:mysql://localhost/sieym?autoReconnect=true"
        }
    }
}
