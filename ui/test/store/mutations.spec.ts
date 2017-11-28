import * as assert from 'power-assert';
import { ResponseTableRow } from '../../src/store/model/ResponseTableRow';
import { ResponseText } from '../../src/store/model/ResponseText';

describe('mutations', () => {
  it('convert from CREATE response', () => {
    const json: string = `{
  "mode": 0,
  "sequence": 10,
  "sql": "CREATE STREAM pageviews_xx AS SELECT userid FROM pageviews",
  "text": "Message\\n----------------------------\\nStream created and running"
}`;

    const jsonObj = JSON.parse(json) as ResponseText;

    assert.equal(jsonObj.mode, 0);
    assert.equal(jsonObj.sequence, 10);
    assert.equal(jsonObj.sql, 'CREATE STREAM pageviews_xx AS SELECT userid FROM pageviews');
    assert.equal(jsonObj.text, 'Message\n----------------------------\nStream created and running');
  });

  it('convert from SELECT response', () => {
    const json: string = `{
  "mode": 1,
  "sequence": 11,
  "sql": "SELECT * FROM pageviews_female LIMIT 3",
  "data": [ "aaa1", "bbb1", "ccc1", "ddd1" ]
}`;

    const jsonObj = JSON.parse(json) as ResponseTableRow;

    assert.equal(jsonObj.mode, 1);
    assert.equal(jsonObj.sequence, 11);
    assert.equal(jsonObj.sql, 'SELECT * FROM pageviews_female LIMIT 3');
    assert.equal(jsonObj.data.length, 4);
    assert.equal(jsonObj.data[0], 'aaa1');
    assert.equal(jsonObj.data[1], 'bbb1');
    assert.equal(jsonObj.data[2], 'ccc1');
    assert.equal(jsonObj.data[3], 'ddd1');
  });
});
