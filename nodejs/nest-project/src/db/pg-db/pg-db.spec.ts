import { Test, TestingModule } from '@nestjs/testing';
import { PgDb } from './pg-db';

describe('PgDb', () => {
  let provider: PgDb;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [PgDb],
    }).compile();

    provider = module.get<PgDb>(PgDb);
  });

  it('should be defined', () => {
    expect(provider).toBeDefined();
  });
});
