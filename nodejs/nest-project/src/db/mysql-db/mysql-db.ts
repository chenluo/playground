import { Injectable } from '@nestjs/common';
import { Db } from '../abstract-db/abstract-db.interface';

@Injectable()
export class MysqlDb implements Db {
    connect(host: string): string {
        return `connecting mysql db: ${host}`
    }

}
