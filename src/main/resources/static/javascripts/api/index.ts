import { Request as Req } from '../store/model/Request';
import { ResponseTransferObject } from '../store/model/ResponseTransferObject';
import { UserCancelError } from '../store/error/UserCancelError';

export class Api {

  submit(sequence: number, sql: string, callback: (data: ResponseTransferObject) => void) {
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
    fetch('/sql', {
      method: 'POST',
      headers,
      body: JSON.stringify(requestBody),
    }).then(response => {
      if (response.ok) {
        return this.pump(response.body.getReader(), callback);
      } else {
        response.json()
            .then(value => {
              const obj: ResponseTransferObject = {
                sequence: value.sequence,
                payload: null,
                errorMessage: value.message,
              };
              callback(obj);
            });
      }
    });
  }

  pump(reader: ReadableStreamReader, callback: (data: ResponseTransferObject) => void) {
    reader.read().then(
        (result) => {
          if (result.done) {
            return;
          }

          const rows = String.fromCharCode.apply('', new Uint16Array(result.value));
          const obj: ResponseTransferObject = {
            sequence: -1,
            payload: rows,
            errorMessage: null,
          };

          try {
            callback(obj);
          } catch (err) {
            if (err instanceof UserCancelError) {
              reader.cancel();
              return;
            }
            throw err;
          }

          return this.pump(reader, callback);
        },
    );
  }
}
