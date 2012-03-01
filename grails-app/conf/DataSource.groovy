dataSource {
    pooled = true
    driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            pooled = true
            driverClassName = "org.postgresql.Driver"
            dialect = org.hibernate.dialect.PostgreSQLDialect
            dbCreate = "none"
            url = "jdbc:postgresql://localhost:5432/test"
            username = "test"
            password = "123test"
            properties {
                maxActive = 50
                maxIdle = 25
                minIdle = 5
                initialSize = 5
                minEvictableIdleTimeMillis = 60000
                timeBetweenEvictionRunsMillis = 60000
                maxWait = 10000
                validationQuery = "SELECT 1"
            }
            loggingSql = true
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:mem:testDb"
        }
    }
    production {
        dataSource {
            pooled = true
            driverClassName = "org.postgresql.Driver"
            dialect = org.hibernate.dialect.PostgreSQLDialect
            dbCreate = "none"
            url = "jdbc:postgresql://localhost:5432/test"
            username = "test"
            password = "123test"
            properties {
                maxActive = 50
                maxIdle = 25
                minIdle = 5
                initialSize = 5
                minEvictableIdleTimeMillis = 60000
                timeBetweenEvictionRunsMillis = 60000
                maxWait = 10000
                validationQuery = "SELECT 1"
            }
            loggingSql = true
        }
    }
}
