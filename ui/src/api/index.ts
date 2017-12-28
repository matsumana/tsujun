import { Request as Req } from '../store/model/Request';

export class Api {

  submit(sequence: number, sql: string, callback: (data: string) => void): void {
    const requestBody = new Req();
    requestBody.sequence = sequence;
    requestBody.sql = sql;

    const headers = new Headers({
      Accept: 'application/stream+json',  // for streaming with WebFlux
      'Content-Type': 'application/json',
      'X-Requested-With': 'XMLHttpRequest',
    });

    fetch(`${window.location.protocol}//${window.location.host}/sql`, {
      method: 'POST',
      headers,
      body: JSON.stringify(requestBody),
    }).then((response) => {
      response.body.getReader().read().then((result) => {
        const rows = String.fromCharCode.apply('', new Uint16Array(result.value));
        callback(rows);
      });
    });
  }
}
