import { Injectable } from '@nestjs/common';
import { Db } from '../abstract-db/abstract-db.interface';

@Injectable()
export class PgDb extends Db {
    connect(host: string): string {
        return `connecting to pg db: ${host}`
    }
}
