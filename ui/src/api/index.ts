import axios, { AxiosRequestConfig } from 'axios';
import { Request as Req } from '../store/model/Request';

const API_END_POINT = `${window.location.protocol}//${window.location.host}/sql`;

export class Api {

  submit(sequence: number, sql: string, callback: (data: string) => void): void {
    const param = new Req();
    param.sequence = sequence;
    param.sql = sql;

    const config: AxiosRequestConfig = {
      headers: {
        Accept: 'application/stream+json',
      },
    };

    axios.post(API_END_POINT, param, config)
        .then((response) => {
          console.log(response.data);
          callback(response.data);
        })
        .catch(error => {
          console.error(error);
        });
  }
}
