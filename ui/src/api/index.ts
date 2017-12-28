import { Request as Req } from '../store/model/Request';

export class Api {

  submit(sequence: number, sql: string, callback: (data: string) => void) {
    const requestBody = new Req();
    requestBody.sequence = sequence;
    requestBody.sql = sql;

    const headers = new Headers({
      Accept: 'application/stream+json',  // for streaming with WebFlux
      'Content-Type': 'application/json',
      'X-Requested-With': 'XMLHttpRequest',
    });

    // referred to the follows.
    // https://www.chromestatus.com/feature/5804334163951616
    // https://googlechrome.github.io/samples/fetch-api/fetch-response-stream.html
    fetch(`${window.location.protocol}//${window.location.host}/sql`, {
      method: 'POST',
      headers,
      body: JSON.stringify(requestBody),
    }).then((response) => {
      return this.pump(response.body.getReader(), callback);
    });
  }

  pump(reader: ReadableStreamReader, callback: (data: string) => void) {
    reader.read().then(
        (result) => {
          if (result.done) {
            return;
          }

          const rows = String.fromCharCode.apply('', new Uint16Array(result.value));
          callback(rows);

          return this.pump(reader, callback);
        }
    );
  }
}
