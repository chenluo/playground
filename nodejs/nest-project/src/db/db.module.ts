import { Module } from '@nestjs/common';
import { MysqlDb } from './mysql-db/mysql-db';
import { PgDb } from './pg-db/pg-db';
import { Db as Db } from './abstract-db/abstract-db.interface';

const dbProvider = {
  provide: Db,
  useClass:
    process.env.NODE_ENV === 'development' ? MysqlDb : PgDb,
};

@Module({
  providers: [dbProvider],
  exports: [dbProvider]
})
export class DbModule {}

