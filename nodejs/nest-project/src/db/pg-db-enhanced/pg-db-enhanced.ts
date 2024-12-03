import { Injectable } from "@nestjs/common";
import { Db } from "../abstract-db/abstract-db.interface";
import { PgDb } from "../pg-db/pg-db";

@Injectable()
export class PgDbEnhanced extends PgDb{
    connect(host: string): string {
        super.connect(host)
        return `[enhanced] connecting to pg db: ${host}`
    }
}
