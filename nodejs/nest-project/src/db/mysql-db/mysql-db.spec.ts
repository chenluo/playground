import { Test, TestingModule } from '@nestjs/testing';
import { MysqlDb } from './mysql-db';

describe('MysqlDb', () => {
  let provider: MysqlDb;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [MysqlDb],
    }).compile();

    provider = module.get<MysqlDb>(MysqlDb);
  });

  it('should be defined', () => {
    expect(provider).toBeDefined();
  });
});
