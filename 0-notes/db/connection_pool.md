# connection pool

## hikariCP

HikariDataSource -> HikariPoll -> ConcurrentBag -> ProxyConnection -> Statement

ConcurrentBag<PoolEntry>: PoolEntryCreator(private final class) ->
createPoolEntry()



