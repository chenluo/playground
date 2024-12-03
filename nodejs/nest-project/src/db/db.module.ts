import { Module } from '@nestjs/common';
import { MysqlDb } from './mysql-db/mysql-db';
import { PgDb } from './pg-db/pg-db';
import { PgDbEnhanced } from './pg-db-enhanced/pg-db-enhanced';
import { Db } from './abstract-db/abstract-db.interface';


function decideDbProvider() {
  switch(process.env.DB) {
    case "mysql":
      return MysqlDb
    case "pg":
      return PgDb
    case "pgEnhanced":
      return PgDbEnhanced
    default:
      return MysqlDb
  }


}
const dbProvider = {
  provide: "Db",
  useClass: decideDbProvider()
};



@Module({
  providers: [dbProvider],
  exports: [dbProvider]
})
export class DbModule {}

