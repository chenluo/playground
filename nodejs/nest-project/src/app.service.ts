import { Injectable } from '@nestjs/common';
import { Db } from './db/abstract-db/abstract-db.interface';

@Injectable()
export class AppService{
  constructor(private readonly db: Db) {}

  getHello(): string {
    return 'Hello World!';
  }

  connectDb(): string {
    return this.db.connect("host")
  }
}
